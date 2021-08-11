package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.dao.resource.zhishi.ZhishiGlffDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.resource.zhishi.ZhishiGlff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识关联方法Service
 * @author 詹小梅
 * @version 2017-06-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZhishiGlffService extends CrudService<ZhishiGlffDao, ZhishiGlff> {

	@Override
	public ZhishiGlff get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ZhishiGlff> findList(ZhishiGlff zhishiGlff) {
		return super.findList(zhishiGlff);
	}
	
	@Override
	public Page<ZhishiGlff> findPage(Page<ZhishiGlff> page, ZhishiGlff zhishiGlff) {
		return super.findPage(page, zhishiGlff);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ZhishiGlff zhishiGlff) {
		super.save(zhishiGlff);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ZhishiGlff zhishiGlff) {
		super.delete(zhishiGlff);
	}
	/**
	 * @Author：詹小梅
	 * @param： * @param null
	 * @Description：删除知识关联方法
	 * @Date：18:09 2017/6/24
	 * @Return：
	 * @Exception：
	 */
	public void deleteZhishiGlff(String zhishiid){
		this.dao.deleteZhishiGlff(zhishiid);
	}
	
}