package com.demxs.tdm.dao.business;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.EntrustAtaChapter;

import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/29 17:28
 * @Description:
 */
@MyBatisDao
public interface EntrustAtaChapterDao extends CrudDao<EntrustAtaChapter> {

    /**
    * @author Jason
    * @date 2020/6/29 17:28
    * @params [entrustId]
    * 根据申请单id查询出所有关联的ata章节
    * @return java.util.List<com.demxs.tdm.domain.business.EntrustAtaChapter>
    */
    List<EntrustAtaChapter> findByEntrustId(String entrustId);
    /**
    * @author Jason
    * @date 2020/6/29 17:34
    * @params [entrustId]
    * 根据申请单主键删除所有关联的ata章节
    * @return int
    */
    int deleteByEntrustId(String entrustId);
}
