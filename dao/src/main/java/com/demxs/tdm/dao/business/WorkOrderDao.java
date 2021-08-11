package com.demxs.tdm.dao.business;


import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.business.WorkOrder;

import java.util.List;

@MyBatisDao
public interface WorkOrderDao extends CrudDao<WorkOrder> {

     List<WorkOrder>  findAllList(WorkOrder workOrder);
}