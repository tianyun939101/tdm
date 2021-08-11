package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.ATAChapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface ATAChapterDao extends TreeDao<ATAChapter> {

    /**
     * 找到所有子节点
     * @param ataChapter
     * @return
     */
    public List<ATAChapter> findByParentIdsLike(ATAChapter ataChapter);


    public List<ATAChapter> findATAList(ATAChapter ataChapter);



}
