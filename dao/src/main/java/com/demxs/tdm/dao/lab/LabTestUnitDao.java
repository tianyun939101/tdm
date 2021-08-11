package com.demxs.tdm.dao.lab;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.lab.LabTestUnit;

/**
 * 试验室试验项DAO接口
 * @author sunjunhui
 * @version 2017-10-30
 */
@MyBatisDao
public interface LabTestUnitDao extends CrudDao<LabTestUnit> {
	
}