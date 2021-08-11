package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.nostandard.NoStandardTestItemDao;
import com.demxs.tdm.domain.business.nostandard.NoStandardTestItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: Jason
 * @Date: 2020/3/3 18:36
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class NoStandardTestItemService extends CrudService<NoStandardTestItemDao,NoStandardTestItem> {

    @Transactional(readOnly = false)
    public int deleteByEntrustId(String id){
        return super.dao.deleteByEntrustId(id);
    }

}
