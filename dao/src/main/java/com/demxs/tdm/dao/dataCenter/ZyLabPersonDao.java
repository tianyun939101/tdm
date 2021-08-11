package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.ZyLabPerson;

import java.util.List;

@MyBatisDao
public interface ZyLabPersonDao extends CrudDao<ZyLabPerson> {

    List<ZyLabPerson>  findList(ZyLabPerson  zyLabPerson);

    void  save(ZyLabPerson  zyLabPerson);


    void  deleteInfo(ZyLabPerson  zyLabPerson);

}