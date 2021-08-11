package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.resource.shebei.ShebeiQjhcjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.service.ISystemService;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.shebei.ShebeiQjhcjl;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 设备期间核查记录Service
 * @author zhangdengcai
 * @version 2017-07-13
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiQjhcjlService extends CrudService<ShebeiQjhcjlDao, ShebeiQjhcjl> {

	@Autowired
	private AttachmentService attachmentService;

	private ISystemService systemService;

	/**
	 * 获取ISystemService
	 * @return
	 */
	public ISystemService getISystemService() {
		if(systemService == null){
			systemService = SpringContextHolder.getBean(ISystemService.class);
		}
		return systemService;
	}

	@Override
	public ShebeiQjhcjl get(String id) {
		return super.get(id);
	}

	/**
	 * 根据核查计划id 获取核查记录
	 * @param hechajhid
	 * @return
	 */
	public List<ShebeiQjhcjl> listByHcjhid(String hechajhid) {
		ShebeiQjhcjl shebeiQjhcjl = new ShebeiQjhcjl();
		shebeiQjhcjl.setHechajhid(hechajhid);
		return this.dao.listByHcjhid(shebeiQjhcjl);
	}
	
	@Override
	public List<ShebeiQjhcjl> findList(ShebeiQjhcjl shebeiQjhcjl) {
		return super.findList(shebeiQjhcjl);
	}
	
	@Override
	public Page<ShebeiQjhcjl> findPage(Page<ShebeiQjhcjl> page, ShebeiQjhcjl shebeiQjhcjl) {
		return super.findPage(page, shebeiQjhcjl);
	}

	/**
	 * 保存
	 * @param shebeiQjhcjl
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiQjhcjl shebeiQjhcjl) {
		String id = "";
		String zls = shebeiQjhcjl.getQijianhczl();//核查资料
		Attachment[] zlAtt = fujianshuzu(zls);
		shebeiQjhcjl.setQijianhczl("");//清空、不存库
		if(StringUtils.isNotBlank(shebeiQjhcjl.getId())){//修改
			super.save(shebeiQjhcjl);
			id = shebeiQjhcjl.getId();
		}else{//新增
			shebeiQjhcjl.preInsert();
			id = IdGen.uuid();
			shebeiQjhcjl.setId(id);//覆盖id
			this.dao.insert(shebeiQjhcjl);
		}
		//附件关联业务id
		if(zlAtt.length>0){
			attachmentService.guanlianfujian(zlAtt, id, "qijianhczl");
		}
	}

	/**
	 * 获得附件数组
	 * @param attIds
	 * @return
	 */
	public Attachment[] fujianshuzu(String attIds){
		List<Attachment> attList = new ArrayList<Attachment>();
		if(attIds!=null && attIds!=""){
			if(attIds.contains(",")){
				String[] tpArr = attIds.split(",");
				for(int i=0; i<tpArr.length; i++){
					Attachment att = new Attachment();
					att.setId(tpArr[i]);
					attList.add(att);
				}
			}else{
				Attachment att = new Attachment();
				att.setId(attIds);
				attList.add(att);
			}
		}
		Attachment[] attArray = new Attachment[attList.size()];
		return attList.size()==0? new Attachment[0] : attList.toArray(attArray);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiQjhcjl shebeiQjhcjl) {
		super.delete(shebeiQjhcjl);
	}

	/**
	 * 单个设备的所有核查记录
	 * @param page
	 * @param shebeiQjhcjl
	 * @return
	 */
	public Page<ShebeiQjhcjl> dangesbsyjl(Page<ShebeiQjhcjl> page, ShebeiQjhcjl shebeiQjhcjl) {
		shebeiQjhcjl.setPage(page);
		List<ShebeiQjhcjl> qjhcjls = this.dao.dangesbsyjl(shebeiQjhcjl);
		if(qjhcjls!=null && !qjhcjls.isEmpty()){
			for (ShebeiQjhcjl qjhcjl : qjhcjls) {
				if(qjhcjl.getCreateBy()!=null && StringUtils.isNotBlank(qjhcjl.getCreateBy().getId())){
					qjhcjl.setCreateBy(UserUtils.get(qjhcjl.getCreateBy().getId()));

					Attachment attachment = new Attachment();//附件
					attachment.setCodeId(qjhcjl.getId());
					attachment.setColumnName("qijianhczl");
					List<Attachment> attaches = attachmentService.findList(attachment);
					qjhcjl.setZiliao(attaches);
				}
			}
		}
		page.setList(qjhcjls);
		return page;
	}

	/**
	 * 全部设备的期间核查记录
	 * @param page
	 * @param shebeiQjhcjl
	 * @return
	 */
	public Page<ShebeiQjhcjl> findAllPage(Page<ShebeiQjhcjl> page, ShebeiQjhcjl shebeiQjhcjl) {
//		page.setOrderBy("a.create_by desc");
		shebeiQjhcjl.setPage(page);
		page.setList(this.dao.findAllList(shebeiQjhcjl));
		return page;
	}

	/**
	 * 期间核查任务提醒
	 */
	public void qijianhcrwtx(){
		List<User> shebeiglys = getISystemService().searchUserByParam("","shebeigly","", "", "", false, false);//设备管理员
		List<Map<String, Object>> renwuList = this.dao.getAllhcrw(new ShebeiQjhcjl());
		if(renwuList!=null && !renwuList.isEmpty()){
			for (Map<String, Object> renwu : renwuList) {
				String shebeimc = String.valueOf(renwu.get("SHEBEIMC"));//设备名称
				String shebeibh = String.valueOf(renwu.get("SHEBEIBH"));//设备编号
				String shebeidyfzrid = String.valueOf(renwu.get("SHEBEIDYFZRID"));//设备第一负责人id
				String jihuahcrq = String.valueOf(renwu.get("HECHAJHRQ"));//任务计划执行日期
				String tixingtians = String.valueOf(renwu.get("HECHATXTS"));//提前提醒天数
				String chuangjianrid = String.valueOf(renwu.get("CREATER"));//创建人id
				String hechajg = String.valueOf(renwu.get("QIJIANHCJG"));//核查结果
				if(StringUtils.isNotBlank(jihuahcrq) && StringUtils.isNotBlank(tixingtians) && !"null".equals(jihuahcrq) && !"null".equals(tixingtians)
						&& (StringUtils.isBlank(hechajg) || "null".equals(hechajg))//检定结果为空的
						&& DateUtils.getDistanceOfTwoDate(new Date(), DateUtils.parseDate(jihuahcrq)) <= Integer.valueOf(tixingtians).doubleValue()){
					if(StringUtils.isNotBlank(shebeidyfzrid)){
						User shebeidyfzr = UserUtils.get(shebeidyfzrid);
						if(shebeidyfzr!=null && StringUtils.isNotBlank(shebeidyfzr.getEmail())){//发送邮件给相关设备第一负责人
							SendMailUtil.sendCommonMail(shebeidyfzr.getEmail(), "设备期间核查任务提醒", "名称为“".concat(shebeimc).concat("”,编号为“").concat(shebeibh).concat("”的设备到期间核查的日期（").concat(jihuahcrq).concat("）了。"));
						}
					}
					if(shebeiglys!=null && !shebeiglys.isEmpty()){
						for (User user: shebeiglys) {
							if(user!=null && StringUtils.isNotBlank(user.getEmail())){//发送邮件给设备管理员
								SendMailUtil.sendCommonMail(user.getEmail(), "设备期间核查任务提醒", "名称为“".concat(shebeimc).concat("”,编号为“").concat(shebeibh).concat("”的设备到期间核查的日期（").concat(jihuahcrq).concat("）了。"));
							}
						}
					}
				}
			}
		}
	}
}