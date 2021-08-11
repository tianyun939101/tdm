package com.demxs.tdm.dao.resource.renyuan;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.resource.renyuan.Renyuan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 人员（员工）DAO接口
 * @author 詹小梅
 * @version 2017-06-26
 */
@MyBatisDao
public interface RenyuanDao extends CrudDao<Renyuan> {
    /**
     * 设置管理范围（将选中的用户插入到员工表中，同一用户不能重复插入）
     * @param userlist
     * @param userID  当前登录id
     */
    public void batchInsert(@Param("userlist") List<Renyuan> userlist,
                            @Param(value = "userID") String userID);

    /**
     * 获取人员工作记录
     * @param entity
     * @return
     */
    public List<Renyuan> findGzjlList(Renyuan entity);


	
}