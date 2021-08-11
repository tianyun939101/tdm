package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.resource.shebei.ShebeiByjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.service.ISystemService;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.shebei.ShebeiByjl;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 设备保养记录Service
 * @author zhangdengcai
 * @version 2017-07-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiByjlService extends CrudService<ShebeiByjlDao, ShebeiByjl> {

	@Autowired
	private AttachmentInfoService attachmentInfoService;

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

	private ShebeiByjl getAll(ShebeiByjl s){

		//获取保养资料数组
		List<AttachmentInfo> zlList = Lists.newArrayList();
		if(StringUtils.isNotBlank(s.getBaoyangzl())){
			String[] zlIdArr = s.getBaoyangzl().split(",");
			for(String zlId:zlIdArr){
				zlList.add(attachmentInfoService.get(zlId));
			}
		}
		s.setBaoyangzlList(zlList);
		return s;
	}

	@Override
	public ShebeiByjl get(String id) {
		return getAll(super.get(id));
	}
	
	@Override
	public List<ShebeiByjl> findList(ShebeiByjl shebeiByjl) {
		List<ShebeiByjl> shebeiByjls = super.findList(shebeiByjl);
		if(!Collections3.isEmpty(shebeiByjls)){
			for(ShebeiByjl s:shebeiByjls){
				getAll(s);
			}
		}
		return shebeiByjls;
	}
	
	@Override
	public Page<ShebeiByjl> findPage(Page<ShebeiByjl> page, ShebeiByjl shebeiByjl) {
		Page<ShebeiByjl> dataValue = super.findPage(page, shebeiByjl);
		if(dataValue!=null && Collections3.isEmpty(dataValue.getList())){
			for(ShebeiByjl s:dataValue.getList()){
				getAll(s);
			}
		}
		return dataValue;
	}

	/**
	 * 根据设备保养计划id查询记录
	 * @param baoyjhid
	 * @return
	 */
	public List<ShebeiByjl> listByByjhid(String baoyjhid){
		ShebeiByjl shebeiByjl = new ShebeiByjl();
		shebeiByjl.setBaoyangjhid(baoyjhid);
		List<ShebeiByjl> byjls = this.dao.listByByjhid(shebeiByjl);
		if(!Collections3.isEmpty(byjls)){
			for(ShebeiByjl s:byjls){
				getAll(s);
			}
		}
		return byjls;
	}


	/**
	 * 根据设备保养计划id删除记录
	 * @param baoyjhid
	 * @return
	 */
	public void deleteByByjhid(String baoyjhid){
		this.dao.deleteByByjhid(baoyjhid);
	}

	/**
	 * 保存
	 * @param shebeiByjl
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiByjl shebeiByjl) {
		if(StringUtils.isNotBlank(shebeiByjl.getId())){
			ShebeiByjl s = this.dao.get(shebeiByjl.getId());
			s.setBaoyangrId(shebeiByjl.getBaoyangrId());
			s.setBaoyangdw(shebeiByjl.getBaoyangdw());
			s.setBaoyangnr(shebeiByjl.getBaoyangnr());
			s.setBaoyangksrq(shebeiByjl.getBaoyangksrq());
			s.setBaoyangjsrq(shebeiByjl.getBaoyangjsrq());
			s.setBaoyangjg(shebeiByjl.getBaoyangjg());
			s.setBaoyangzl(shebeiByjl.getBaoyangzl());
			s.setBaoyanggcjl(shebeiByjl.getBaoyanggcjl());
			super.save(s);
		}else{
			super.save(shebeiByjl);
		}
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiByjl shebeiByjl) {
		super.delete(shebeiByjl);
	}

	/**
	 * 单个设备的所有核查记录
	 * @param page
	 * @param shebeiByjl
	 * @return
	 */
	public Page<ShebeiByjl> dangesbsyjl(Page<ShebeiByjl> page, ShebeiByjl shebeiByjl) {
		shebeiByjl.setPage(page);
		List<ShebeiByjl> byjls = this.dao.dangesbsyjl(shebeiByjl);
		if(!Collections3.isEmpty(byjls)){
			for(ShebeiByjl s:byjls){
				getAll(s);
			}
		}
		page.setList(byjls);
		return page;
	}

	/**
	 * 全部设备的保养记录
	 * @param page
	 * @param shebeiByjl
	 * @return
	 */
	public Page<ShebeiByjl> findAllPage(Page<ShebeiByjl> page, ShebeiByjl shebeiByjl) {
//		page.setOrderBy("a.create_date desc");
		shebeiByjl.setPage(page);
		page.setList(this.dao.findAllList(shebeiByjl));
		if(!Collections3.isEmpty(this.dao.findAllList(shebeiByjl))){
			for(ShebeiByjl s:this.dao.findAllList(shebeiByjl)){
				getAll(s);
			}
		}
		return page;
	}

	/**
	 * 期间核查任务提醒
	 */
	public void baoyangrwtx(){
		List<User> shebeiglys = getISystemService().searchUserByParam("","shebeigly","", "", "", false, false);//设备管理员
		List<Map<String, Object>> renwuList = this.dao.getAllbyrw(new ShebeiByjl());
		if(renwuList!=null && !renwuList.isEmpty()){
			for (Map<String, Object> renwu : renwuList) {
				String shebeimc = String.valueOf(renwu.get("SHEBEIMC"));//设备名称
				String shebeibh = String.valueOf(renwu.get("SHEBEIBH"));//设备编号
				String shebeidyfzrid = String.valueOf(renwu.get("SHEBEIDYFZRID"));//设备第一负责人id
				String jihuabyrq = String.valueOf(renwu.get("JIHUABYRQ"));//任务计划执行日期
				String tixingtians = String.valueOf(renwu.get("BAOYANGTXTS"));//提前提醒天数
				String chuangjianrid = String.valueOf(renwu.get("CREATER"));//创建人id
				String baoyangjg = String.valueOf(renwu.get("BAOYANGJG"));//保养结果
				if(StringUtils.isNotBlank(jihuabyrq) && StringUtils.isNotBlank(tixingtians) && !"null".equals(jihuabyrq) && !"null".equals(tixingtians)
						&& (StringUtils.isBlank(baoyangjg) || "null".equals(baoyangjg))//检定结果为空的
						&& DateUtils.getDistanceOfTwoDate(new Date(), DateUtils.parseDate(jihuabyrq)) <= Integer.valueOf(tixingtians).doubleValue()){
					if(StringUtils.isNotBlank(shebeidyfzrid)){
						User shebeidyfzr = UserUtils.get(shebeidyfzrid);
						if(shebeidyfzr!=null && StringUtils.isNotBlank(shebeidyfzr.getEmail())){
							SendMailUtil.sendCommonMail(shebeidyfzr.getEmail(), "设备保养任务提醒", "名称为“".concat(shebeimc).concat("”,编号为“").concat(shebeibh).concat("”的设备到保养的日期（").concat(jihuabyrq).concat("）了。"));
						}
					}
					if(shebeiglys!=null && !shebeiglys.isEmpty()) {
						for (User user : shebeiglys) {
							if (user != null && StringUtils.isNotBlank(user.getEmail())) {//发送邮件给设备管理员和设备第一负责人
								SendMailUtil.sendCommonMail(user.getEmail(), "设备保养任务提醒", "名称为“".concat(shebeimc).concat("”,编号为“").concat(shebeibh).concat("”的设备到保养的日期（").concat(jihuabyrq).concat("）了。"));
							}
						}
					}
				}
			}
		}
	}
}