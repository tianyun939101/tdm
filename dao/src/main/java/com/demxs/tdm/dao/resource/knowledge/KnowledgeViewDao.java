package com.demxs.tdm.dao.resource.knowledge;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.kowledge.KnowledgeView;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@MyBatisDao
public interface KnowledgeViewDao  extends CrudDao<KnowledgeView> {
    List<KnowledgeView> sendEmail();
    void updateStatus(@Param("tabName") String tabName,@Param("id") String id);
    KnowledgeView get(@Param("id")String id);
    List<KnowledgeView> findCount(@Param("moduleName") String tabName,@Param("plan") String plan);
    List<KnowledgeView> findZHISHI(@Param("tabName") String tabName,@Param("plan") String plan);
}
