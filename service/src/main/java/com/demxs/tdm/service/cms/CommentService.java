package com.demxs.tdm.service.cms;

import com.demxs.tdm.dao.cms.CommentDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.cms.Comment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 评论Service
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class CommentService extends CrudService<CommentDao, Comment> {

	@Override
	public Page<Comment> findPage(Page<Comment> page, Comment comment) {
//		DetachedCriteria dc = commentDao.createDetachedCriteria();
//		if (StringUtils.isNotBlank(comment.getContentId())){
//			dc.add(Restrictions.eq("contentId", comment.getContentId()));
//		}
//		if (StringUtils.isNotEmpty(comment.getTitle())){
//			dc.add(Restrictions.like("title", "%"+comment.getTitle()+"%"));
//		}
//		dc.add(Restrictions.eq(Comment.FIELD_DEL_FLAG, comment.getDelFlag()));
//		dc.addOrder(Order.desc("id"));
//		return commentDao.find(page, dc);
		comment.getSqlMap().put("dsf", dataScopeFilter(comment.getCurrentUser(), "o", "u"));
		
		return super.findPage(page, comment);
	}
	
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Comment entity, Boolean isRe) {
		super.delete(entity);
	}
}
