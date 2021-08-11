package com.demxs.tdm.dao.dataCenter;

import com.demxs.tdm.common.persistence.CrudDao;
import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.ZyProjectManager;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;

import java.util.List;

@MyBatisDao
public interface ZyProjectManagerDao extends CrudDao<ZyProjectManager> {


     public  List<ZyProjectManager>  getManager(ZyProjectManager  zyProjectManager);

     public  void changeStatus(@Param("id") String id,@Param("status") String  status);

     public  void changeAuditUser(ZyProjectManager zyProjectManager);

}