package com.demxs.tdm.service.business;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.business.EntrustAtaChapterDao;
import com.demxs.tdm.domain.business.EntrustAtaChapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/6/29 17:39
 * @Description:
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class EntrustAtaChapterService extends CrudService<EntrustAtaChapterDao, EntrustAtaChapter> {

    @Transactional(readOnly = false)
    public int deleteByEntrustId(String entrustId){
        return this.dao.deleteByEntrustId(entrustId);
    }
}
