package com.demxs.tdm.service.business.nostandard;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.nostandard.NoStandardATAChapterDao;
import com.demxs.tdm.domain.dataCenter.VO.DataCenterSearch;
import com.demxs.tdm.domain.business.nostandard.NoStandardATAChapter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: Jason
 * @Date: 2020/3/3 19:18
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class NoStandardATAChapterService extends CrudService<NoStandardATAChapterDao, NoStandardATAChapter> {

    @Transactional(readOnly = false)
    public int deleteByEntrustId(String id){
        return super.dao.deleteByEntrustId(id);
    }

    @Transactional(readOnly = true)
    public void getEntrustReATA(DataCenterSearch dataCenterSearch){
        super.dao.getEntrustReATA(dataCenterSearch);
    }

    public List<NoStandardATAChapter> getByEntrustId(String id){
        return this.dao.getByEntrustId(id);
    }
}
