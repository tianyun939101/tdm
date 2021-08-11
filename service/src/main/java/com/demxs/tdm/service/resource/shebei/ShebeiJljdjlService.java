package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.resource.shebei.ShebeiJljdjhDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiJljdjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.service.ISystemService;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.shebei.ShebeiJljdjl;
import com.demxs.tdm.domain.resource.shebei.ShebeiJljdjlAll;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 设备计量检定记录Service
 * @author zhangdengcai
 * @version 2017-07-13
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiJljdjlService extends CrudService<ShebeiJljdjlDao, ShebeiJljdjl> {

	@Autowired
	private AttachmentInfoService attachmentInfoService;
	@Autowired
	private ShebeiJljdjhDao shebeiJljdjhDao;

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

	private ShebeiJljdjl getAll(ShebeiJljdjl s){

		//获取保养资料数组
		List<AttachmentInfo> zlList = Lists.newArrayList();
		if(StringUtils.isNotBlank(s.getJiliangjdzl())){
			String[] zlIdArr = s.getJiliangjdzl().split(",");
			for(String zlId:zlIdArr){
				zlList.add(attachmentInfoService.get(zlId));
			}
		}
		s.setZiliao(zlList);
		return s;
	}

	private List<ShebeiJljdjl> getAll(List<ShebeiJljdjl> sList){

		if(!Collections3.isEmpty(sList)){
			for(ShebeiJljdjl s:sList){
				getAll(s);
			}
		}
		return sList;
	}

	@Override
	public ShebeiJljdjl get(String id) {
		return getAll(super.get(id));
	}
	
	@Override
	public List<ShebeiJljdjl> findList(ShebeiJljdjl shebeiJljdjl) {
		return getAll(super.findList(shebeiJljdjl));
	}
	
	@Override
	public Page<ShebeiJljdjl> findPage(Page<ShebeiJljdjl> page, ShebeiJljdjl shebeiJljdjl) {
		Page<ShebeiJljdjl> dataValue = super.findPage(page, shebeiJljdjl);
		if(dataValue!=null){
			getAll(dataValue.getList());
		}
		return dataValue;
	}

	/**
	 * 根据计量检定计划id 获取计量检定记录
	 * @param jljdjhid 计量检定计划id
	 * @return
	 */
	public List<ShebeiJljdjl> listByJljdjhid(String jljdjhid){
		ShebeiJljdjl shebeiJljdjl = new ShebeiJljdjl();
		shebeiJljdjl.setJiliangjdjhid(jljdjhid);
		List<ShebeiJljdjl> jljdls = this.dao.listByJljdjhid(shebeiJljdjl);
		getAll(jljdls);
		return jljdls;
	}

	/**
	 * 根据计量检定计划id 删除计量检定记录
	 * @param jljdjhid 计量检定计划id
	 */
	public void deleteByJljdjhid(String jljdjhid){
		this.dao.deleteByJljdjhid(jljdjhid);
	}

	/**
	 * 	获取全部设备的计量检定记录
	 * @param page
	 * @param shebeiJljdjlAll
	 * @return
	 */
	public Page<ShebeiJljdjlAll> findAllPage(Page<ShebeiJljdjlAll> page, ShebeiJljdjlAll shebeiJljdjlAll) {
		shebeiJljdjlAll.setPage(page);
		page.setList(this.dao.findAllList(shebeiJljdjlAll));
		return page;
	}

	/**
	 * 保存
	 * @param shebeiJljdjl
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiJljdjl shebeiJljdjl) {
		if(StringUtils.isNotBlank(shebeiJljdjl.getId())){
			ShebeiJljdjl s = this.dao.get(shebeiJljdjl.getId());
			s.setJiliangjdjg(shebeiJljdjl.getJiliangjdjg());
			s.setJiliangjdkssj(shebeiJljdjl.getJiliangjdkssj());
			s.setJiliangjdjssj(shebeiJljdjl.getJiliangjdjssj());
			s.setJiliangjdzl(shebeiJljdjl.getJiliangjdzl());
			s.setJiliangjdzsh(shebeiJljdjl.getJiliangjdzsh());
			s.setRemarks(shebeiJljdjl.getRemarks());
			super.save(s);
		}else{
			//不会出现这种情况
			super.save(shebeiJljdjl);
		}
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiJljdjl shebeiJljdjl) {
		super.delete(shebeiJljdjl);
	}


    /**
     * 单个设备的所有检定记录
     * @param page
     * @param shebeiJljdjl
     * @return
     */
    public Page<ShebeiJljdjl> dangesbsyjl(Page<ShebeiJljdjl> page, ShebeiJljdjl shebeiJljdjl) {
        shebeiJljdjl.setPage(page);
		page.setList(getAll(this.dao.dangesbsyjl(shebeiJljdjl)));
        return page;
    }

	/**
	 * 设备计量检定任务提醒
	 */
	public void jiliangjdrwtx(){
		List<User> shebeiglys = getISystemService().searchUserByParam("","shebeigly","", "", "", false, false);//设备管理员
		List<Map<String, Object>> renwuList = this.dao.getAllJdrw(new ShebeiJljdjl());
		if(renwuList!=null && !renwuList.isEmpty()){
			for (Map<String, Object> renwu : renwuList) {
				String shebeimc = String.valueOf(renwu.get("SHEBEIMC"));//设备名称
				String shebeibh = String.valueOf(renwu.get("SHEBEIBH"));//设备编号
				String shebeidyfzrid = String.valueOf(renwu.get("SHEBEIDYFZRID"));//设备第一负责人id
				String jihuajdrq = String.valueOf(renwu.get("JIHUAJLJDRQ"));//任务计划执行日期
				String tixingtians = String.valueOf(renwu.get("JILIANGJDTXTS"));//提前提醒天数
				String chuangjianrid = String.valueOf(renwu.get("CREATER"));//创建人id
				String jiandingjg = String.valueOf(renwu.get("JILIANGJDJG"));//检定结果
				if(StringUtils.isNotBlank(jihuajdrq) && StringUtils.isNotBlank(tixingtians) && !"null".equals(jihuajdrq) && !"null".equals(tixingtians)
						&& (StringUtils.isBlank(jiandingjg) || "null".equals(jiandingjg))//检定结果为空的
						&& DateUtils.getDistanceOfTwoDate(new Date(), DateUtils.parseDate(jihuajdrq)) <= Integer.valueOf(tixingtians).doubleValue()){
					if(StringUtils.isNotBlank(shebeidyfzrid)){
						User shebeidyfzr = UserUtils.get(shebeidyfzrid);
						if(shebeidyfzr!=null && StringUtils.isNotBlank(shebeidyfzr.getEmail())){//发送邮件给相关设备第一负责人
							SendMailUtil.sendCommonMail(shebeidyfzr.getEmail(), "设备计量检定任务提醒", "名称为“".concat(shebeimc).concat("”,编号为“").concat(shebeibh).concat("”的设备到计量检定的日期（").concat(jihuajdrq).concat("）了。"));
						}
					}
					if(shebeiglys!=null && !shebeiglys.isEmpty()) {
						for (User user : shebeiglys) {
							if(user!=null && StringUtils.isNotBlank(user.getEmail())){//发送邮件给设备管理员
								SendMailUtil.sendCommonMail(user.getEmail(), "设备计量检定任务提醒", "名称为“".concat(shebeimc).concat("”,编号为“").concat(shebeibh).concat("”的设备到计量检定的日期（").concat(jihuajdrq).concat("）了。"));
							}
						}
					}
				}
			}
		}
	}
}