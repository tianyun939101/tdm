package com.demxs.tdm.service.resource.renyuan;

import com.demxs.tdm.dao.resource.renyuan.RenyuanDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.domain.resource.renyuan.Renyuan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员（员工）Service
 * @author 詹小梅
 * @version 2017-06-26
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class RenyuanService extends CrudService<RenyuanDao, Renyuan> {

	@Override
	public Renyuan get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Renyuan> findList(Renyuan renyuan) {
		return super.findList(renyuan);
	}
	
	@Override
	public Page<Renyuan> findPage(Page<Renyuan> page, Renyuan renyuan) {
		if(UserUtils.getUser()!=null){
			//renyuan.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
			renyuan.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}
		return super.findPage(page, renyuan);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Renyuan renyuan) {
		super.save(renyuan);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Renyuan renyuan) {
		super.delete(renyuan);
	}
	/**
	 * 设置管理范围（将选中的用户插入到员工表中，同一用户不能重复插入）
	 * @param entity  人员实体
	 */
	public void batchInsert(Renyuan entity){
		List<Renyuan> userlist = new ArrayList<Renyuan>();
		User[] users = entity.getUserlist();
		Renyuan renyuan = null;
		if(users!=null){
			for (int i = 0; i < users.length; i++) {
				renyuan = new Renyuan();
				renyuan.setUserId(users[i].getUserid());
				renyuan.preInsert();
				userlist.add(renyuan);
			}
		}
		this.dao.batchInsert(userlist,UserUtils.getUser().getId());
	}
	/**
	 * 获取人员工作记录
	 * @param userId
	 * @return
	 */
	public Page<Renyuan> findGzjlList(Page page,String userId){
		Renyuan entity =new Renyuan();
		entity.setUserId(userId);
		entity.setPage(page);
		List<Renyuan> rys = dao.findGzjlList(entity);
		return page.setList(rys);
	}


}