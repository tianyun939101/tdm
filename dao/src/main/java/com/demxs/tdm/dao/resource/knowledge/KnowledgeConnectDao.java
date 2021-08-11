package com.demxs.tdm.dao.resource.knowledge;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.kowledge.KnowledgeConnect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface KnowledgeConnectDao extends CrudDao<KnowledgeConnect> {
    List<KnowledgeConnect> getList(KnowledgeConnect knowledgeConnect);

    List<KnowledgeConnect> findListAudit(KnowledgeConnect knowledgeConnect);
    KnowledgeConnect getAudit(String id);
    int insertAudit(KnowledgeConnect knowledgeConnect);
    void deleteAudit(String id);
    void updataFlag(String id);

    void deleteAct(@Param("bussnessId") String bussnessId);
}
