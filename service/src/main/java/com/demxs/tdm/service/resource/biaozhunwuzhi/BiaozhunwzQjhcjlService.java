package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzQjhcjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.service.ISystemService;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzQjhcjl;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import com.demxs.tdm.service.sys.ISysMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 标准物质期间核查记录Service
 * @author zhangdengcai
 * @version 2017-07-19
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzQjhcjlService extends CrudService<BiaozhunwzQjhcjlDao, BiaozhunwzQjhcjl> {

	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private ISysMessageService iSysMessageService;

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
	public BiaozhunwzQjhcjl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<BiaozhunwzQjhcjl> findList(BiaozhunwzQjhcjl biaozhunwzQjhcjl) {
		return super.findList(biaozhunwzQjhcjl);
	}
	
	@Override
	public Page<BiaozhunwzQjhcjl> findPage(Page<BiaozhunwzQjhcjl> page, BiaozhunwzQjhcjl biaozhunwzQjhcjl) {
		return super.findPage(page, biaozhunwzQjhcjl);
	}

	/**
	 * 保存
	 * @param biaozhunwzQjhcjl
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(BiaozhunwzQjhcjl biaozhunwzQjhcjl) {
		String id = "";
		String zls = biaozhunwzQjhcjl.getQijianhczl();//核查资料
		Attachment[] zlAtt = fujianshuzu(zls);
		biaozhunwzQjhcjl.setQijianhczl("");//清空、不存库
		if(StringUtils.isNotBlank(biaozhunwzQjhcjl.getId())){//修改
			super.save(biaozhunwzQjhcjl);
			id = biaozhunwzQjhcjl.getId();
		}else{//新增
			biaozhunwzQjhcjl.preInsert();
			id = IdGen.uuid();
			biaozhunwzQjhcjl.setId(id);//覆盖id
			this.dao.insert(biaozhunwzQjhcjl);
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

	/**
	 * 根据计划id获取
	 * @param jihuaId
	 * @return
	 */
	public List<BiaozhunwzQjhcjl> listByHcjhId(String jihuaId) {
		BiaozhunwzQjhcjl biaozhunwzQjhcjl = new BiaozhunwzQjhcjl();
		biaozhunwzQjhcjl.setHechajhid(jihuaId);
		return this.dao.listByHcjhId(biaozhunwzQjhcjl);
	}
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(BiaozhunwzQjhcjl biaozhunwzQjhcjl) {
		super.delete(biaozhunwzQjhcjl);
	}

	/**
	 * 期间核查任务提醒
	 */
	public void qijianhcrwtx(){
		List<User> yangpinglys = getISystemService().searchUserByParam("","yangpingly","", "", "", false, false);//样品管理员
		List<Map<String, Object>> renwuList = this.dao.getAllhcrw(new BiaozhunwzQjhcjl());
		if(renwuList!=null && !renwuList.isEmpty()){
			for (Map<String, Object> renwu : renwuList) {
				String shebeimc = String.valueOf(renwu.get("BIAOZHUNWZMC"));//标准物质名称
				String shebeibh = String.valueOf(renwu.get("BIAOZHUNWZBH"));//标准物质编号
				String jihuahcrq = String.valueOf(renwu.get("JIHUAHCRQ"));//任务计划执行日期
				String tixingtians = String.valueOf(renwu.get("HECHATXTS"));//提前提醒天数
				String chuangjianrid = String.valueOf(renwu.get("CREATER"));//创建人id
				String hechajg = String.valueOf(renwu.get("HECHAJG"));//核查结果
				if(StringUtils.isNotBlank(jihuahcrq) && StringUtils.isNotBlank(tixingtians) && !"null".equals(jihuahcrq) && !"null".equals(tixingtians)
						&& (StringUtils.isBlank(hechajg) || "null".equals(hechajg))//检定结果为空的
						&& DateUtils.getDistanceOfTwoDate(new Date(), DateUtils.parseDate(jihuahcrq)) <= Integer.valueOf(tixingtians).doubleValue()){

					if(yangpinglys!=null && !yangpinglys.isEmpty()){
						for (User ypgly : yangpinglys) {//样品管理员
							if(ypgly!=null && StringUtils.isNotBlank(ypgly.getEmail())){//发送邮件给样品管理原
								//邮件
								SendMailUtil.sendCommonMail(ypgly.getEmail(), "标准物质期间核查任务提醒", "名称为“".concat(shebeimc).concat("”,编号为“").concat(shebeibh).concat("”的标准物质到期间核查的日期（").concat(jihuahcrq).concat("）了。"));
							}
						}
					}
					/*if(StringUtils.isNotBlank(chuangjianrid)){//目前发送邮件给任务创建人
						User creater = UserUtils.get(chuangjianrid);
						if(creater!=null && StringUtils.isNotBlank(creater.getEmail())){
							//邮件
							SendMailUtil.sendCommonMail(creater.getEmail(), "标准物质期间核查任务提醒", "名称为“".concat(shebeimc).concat("”,编号为“").concat(shebeibh).concat("”的标准物质到期间核查的日期（").concat(jihuahcrq).concat("）了。"));
						}
					}*/
				}
			}
		}
	}

	/**
	 * 单个标准物质的所有核查记录列表
	 * @param page
	 * @param bzwzQjhcjl
	 * @return
	 */
	public Page<BiaozhunwzQjhcjl> dangesbsyjl(Page<BiaozhunwzQjhcjl> page, BiaozhunwzQjhcjl bzwzQjhcjl){
		bzwzQjhcjl.setPage(page);
		List<BiaozhunwzQjhcjl> qjhcjls = this.dao.dangesbsyjl(bzwzQjhcjl);
		if(qjhcjls!=null && !qjhcjls.isEmpty()){
			for (BiaozhunwzQjhcjl qjhcjl : qjhcjls) {
				Attachment attach = new Attachment();
				attach.setCodeId(qjhcjl.getId());
				attach.setColumnName("qijianhczl");
				List<Attachment> ziliao = attachmentService.findList(attach);
				qjhcjl.setZiliao(ziliao);
			}
		}
		page.setList(qjhcjls);
		return page;
	}
}