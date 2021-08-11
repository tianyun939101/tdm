package com.demxs.tdm.service.resource.center;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.service.TreeService;
import com.demxs.tdm.dao.resource.center.DepartmentDao;
import com.demxs.tdm.dao.resource.knowledge.DesignFlowDao;
import com.demxs.tdm.domain.resource.center.Department;
import com.demxs.tdm.domain.resource.kowledge.DesignFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DepartmentService extends TreeService<DepartmentDao, Department> {
    @Autowired
    private DepartmentDao departmentDao;

    public List<Map<String,String>> findTree(){
        return departmentDao.findLabTree();
    }

    public String getParent(String id){
       return  departmentDao.getParent(id);
    }
}
