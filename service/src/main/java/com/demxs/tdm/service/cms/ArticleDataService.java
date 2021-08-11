package com.demxs.tdm.service.cms;

import com.demxs.tdm.dao.cms.ArticleDataDao;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.cms.ArticleData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 站点Service
 * @author ThinkGem
 * @version 2013-01-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ArticleDataService extends CrudService<ArticleDataDao, ArticleData> {

}
