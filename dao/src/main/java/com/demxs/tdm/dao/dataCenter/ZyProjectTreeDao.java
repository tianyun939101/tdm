package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.TreeDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.ZyProjectTree;
import com.demxs.tdm.domain.resource.consumeables.HaocaiLx;

import java.util.List;

/**
 * 项目建设DAO接口
 * @author zwm
 * @version 2020-12-9
 */
@MyBatisDao
public interface ZyProjectTreeDao extends TreeDao<ZyProjectTree> {


    List<ZyProjectTree> getDept(String parentId);
}