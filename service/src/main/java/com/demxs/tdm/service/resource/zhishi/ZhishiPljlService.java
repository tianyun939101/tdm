package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.dao.resource.zhishi.ZhishiPljlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.domain.resource.zhishi.ZhishiPljl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 知识评论Service
 * @author 詹小梅
 * @version 2017-07-11
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZhishiPljlService extends CrudService<ZhishiPljlDao, ZhishiPljl> {

	@Override
	public ZhishiPljl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ZhishiPljl> findList(ZhishiPljl zhishiPljl) {
		return super.findList(zhishiPljl);
	}
	
	@Override
	public Page<ZhishiPljl> findPage(Page<ZhishiPljl> page, ZhishiPljl zhishiPljl) {
		return super.findPage(page, zhishiPljl);
	}

	/**
	 * 知识评论保存
	 * @param zhishiPljl
	 */
	@Override
	public void save(ZhishiPljl zhishiPljl) {
		User user = UserUtils.getUser();
		zhishiPljl.setPinglunrid(user.getId());  //知识评论人
		zhishiPljl.setPinglunr(user.getName());//知识评论人id
		zhishiPljl.setZhishiid(zhishiPljl.getZhishiid());
		zhishiPljl.setPinglunsj(DateUtils.getDateTime());
		super.save(zhishiPljl);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ZhishiPljl zhishiPljl) {
		super.delete(zhishiPljl);
	}
	
}