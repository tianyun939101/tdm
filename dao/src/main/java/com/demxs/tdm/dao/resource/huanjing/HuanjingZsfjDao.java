package com.demxs.tdm.dao.resource.huanjing;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.huanjing.HuanjingZsfj;

/**
 * 环境数据，设置展示的房间DAO接口
 * @author zhangdengcai
 * @version 2017-10-09
 */
@MyBatisDao
public interface HuanjingZsfjDao extends CrudDao<HuanjingZsfj> {
    public void batchDelete(HuanjingZsfj huanjingZsfj);
}