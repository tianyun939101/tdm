package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiWxjlDao;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.resource.shebei.ShebeiWxjl;
import com.demxs.tdm.domain.resource.shebei.ShebeiWxjlAll;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXx;
import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.comac.common.constant.ShebeiConstans;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.IActAuditService;
import com.demxs.tdm.service.sys.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 设备维修记录Service
 * @author zhangdengcai
 * @version 2017-07-14
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiWxjlService extends CrudService<ShebeiWxjlDao, ShebeiWxjl> {


	@Autowired
	private SystemService systemService;
	@Autowired
	private IActAuditService iActAuditService;
	@Autowired
	private LabInfoService labInfoService;
	@Autowired
	private ShebeiDao shebeiDao;

	private ShebeiWxjl getAll(ShebeiWxjl shebeiWxjl){
		if(shebeiWxjl!=null && StringUtils.isNotBlank(shebeiWxjl.getId())){
			if(shebeiWxjl.getCreateBy()!=null && StringUtils.isNotBlank(shebeiWxjl.getCreateBy().getId())){
				//获取创建人
				shebeiWxjl.setCreateBy(systemService.getUser(shebeiWxjl.getCreateBy().getId()));
			}
		}
		return shebeiWxjl;
	}

	private List<ShebeiWxjl> getAll(List<ShebeiWxjl> shebeiWxjls){

		if(!Collections3.isEmpty(shebeiWxjls)){
			for(ShebeiWxjl s:shebeiWxjls){
				getAll(s);
			}
		}
		return shebeiWxjls;
	}

	@Override
	public ShebeiWxjl get(String id) {
		return getAll(super.get(id));
	}
	
	@Override
	public List<ShebeiWxjl> findList(ShebeiWxjl shebeiWxjl) {
		return getAll(super.findList(shebeiWxjl));
	}
	
	@Override
	public Page<ShebeiWxjl> findPage(Page<ShebeiWxjl> page, ShebeiWxjl shebeiWxjl) {
		Page<ShebeiWxjl> dataValue = super.findPage(page, shebeiWxjl);
		if(dataValue!=null && !Collections3.isEmpty(dataValue.getList())){
			getAll(dataValue.getList());
		}
		return dataValue;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiWxjl shebeiWxjl) {
		if(shebeiWxjl.getSubmit()!=null){
			if(shebeiWxjl.getSubmit()){
				shebeiWxjl.setStatus(ShebeiConstans.actStatus.DAISHENHE);
			}else{
				shebeiWxjl.setStatus(ShebeiConstans.actStatus.DAITIJIAO);
			}
		}
		super.save(shebeiWxjl);
		if(shebeiWxjl.getStatus().equals(ShebeiConstans.actStatus.DAISHENHE)){
			Map<String,Object> map = new HashMap<String,Object>();
			Shebei shebei = shebeiDao.get(shebeiWxjl.getShebeiid());
			map.put("labLeader",labInfoService.get(shebei.getLabInfoId()).getLeader().getLoginName());
			map.put("message", MessageConstants.shebeiMessage.REPAIR);
			startAudit(shebeiWxjl.getId(),shebeiDao.get(shebeiWxjl.getShebeiid()).getShebeimc()+MessageConstants.auditMessage.REPAIR,shebeiWxjl.getYijian(),map);
		}
	}

	/**
	 * 发起流程审核
	 */
	public void startAudit(String id,String title,String comment,Map<String,Object> map){
		try{
			logger.debug("开始发起设备维修审核流程ID：{}"+id);
			iActAuditService = getiActAuditService();
			iActAuditService.start(id, GlobalConstans.ActProcDefKey.SHEBEIWXSH,title,comment,map);
		}catch (Exception e){
			logger.error("发起设备维修流程失败！",e);
		}
	}

	public IActAuditService getiActAuditService() {
		if(iActAuditService == null){
			iActAuditService = SpringContextHolder.getBean(IActAuditService.class);
		}
		return iActAuditService;
	}
	/**
	 * 保存审核信息
	 * @param id     实体id
	 * @param isPass 状态
	 */
	public void saveShenhe(String id,String isPass){
		if(StringUtils.isNoneBlank(id)){
			ShebeiWxjl entity = super.get(id);
			if(isPass.equals(ZhishiXx.CHEXIAO)){
				entity.setStatus(ShebeiConstans.actStatus.DAITIJIAO);//撤销
			}
			if (isPass.equals(ZhishiXx.TONGGUO)) {
				entity.setStatus(ShebeiConstans.actStatus.YISHENHE);//通过
			}
			if (isPass.equals(ZhishiXx.BOHUI)) {
				entity.setStatus(ShebeiConstans.actStatus.BOHUI);//通过
			}
			entity.preUpdate();
			this.dao.update(entity);
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiWxjl shebeiWxjl) {
		super.delete(shebeiWxjl);
	}

	/**
	 * 获取全部设备的维修记录
	 * @param page
	 * @param shebeiWxjlAll
	 * @return
	 */
	public Page<ShebeiWxjlAll> findAllPage(Page<ShebeiWxjlAll> page, ShebeiWxjlAll shebeiWxjlAll) {
		shebeiWxjlAll.setPage(page);
		page.setList(this.dao.findAllList(shebeiWxjlAll));
		return page;
	}
}