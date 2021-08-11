package com.demxs.tdm.dao.dataCenter.pr;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.pr.Token;

/**
 * @author: Jason
 * @Date: 2020/4/28 09:56
 * @Description:
 */
@MyBatisDao
public interface TokenDao extends CrudDao<Token> {

    /**
    * @author Jason
    * @date 2020/4/28 10:15
    * @params [token]
    * 修改不为空的字段
    * @return int
    */
    int updateActive(Token token);
}
