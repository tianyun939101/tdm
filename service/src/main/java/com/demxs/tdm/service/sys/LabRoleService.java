package com.demxs.tdm.service.sys;

import com.demxs.tdm.dao.sys.LabRoleDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.UerLabVO;
import com.demxs.tdm.domain.sys.LabRole;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色人员关系Service
 * @author 詹小梅
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class LabRoleService extends CrudService<LabRoleDao, LabRole> {

	@Override
	public LabRole get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<LabRole> findList(LabRole labRole) {
		return super.findList(labRole);
	}
	
	@Override
	public Page<LabRole> findPage(Page<LabRole> page, LabRole labRole) {
		return super.findPage(page, labRole);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(LabRole labRole) {
		super.save(labRole);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(LabRole labRole) {
		super.delete(labRole);
	}

	public List<LabInfo> getLabInfosByUserId(String roleId,String userId){

		return this.dao.getLabInfosByUserId(roleId,userId);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteByUserId(String userId,String roleId){
		this.dao.deleteByUserId(userId,roleId);
	}


	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void saveUserLabRle(List<UerLabVO> lists){

		if(CollectionUtils.isNotEmpty(lists)){
			for(UerLabVO u:lists){
				//删除该用户的数据
				deleteByUserId(u.getUserId(),u.getRoleId());
				//添加用户新数据
				if(StringUtils.isNotBlank(u.getLabIds())){
					String[] labidArr = u.getLabIds().split(",");
					for(String labId :labidArr){
						this.dao.saveUserLabRole(u.getUserId(),u.getRoleId(),labId);
					}
				}

			}
			//UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
			//UserUtils.removeCache(UserUtils.USER_CACHE);
		}
	}
	
}