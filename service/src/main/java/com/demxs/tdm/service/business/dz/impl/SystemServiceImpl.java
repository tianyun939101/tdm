package com.demxs.tdm.service.business.dz.impl;

import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.dao.business.dz.DzSystemDao;
import com.demxs.tdm.domain.business.dz.DzSystem;
import com.demxs.tdm.service.business.dz.SystemService;
import com.demxs.tdm.service.oa.IActAuditService;
import com.demxs.tdm.service.oa.service.ActTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class SystemServiceImpl extends CrudService<DzSystemDao, DzSystem> implements SystemService {

    @Resource
    private ActTaskService actTaskService;
    @Autowired
    private UserDao userDao;
    @Autowired
    private DzSystemDao dzSystemDao;

    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(DzSystem dzSystem) {
        try {
            super.save(dzSystem);
            Map<String, Object> map = new HashMap<String, Object>();
            User auditUser = userDao.get(dzSystem.getAuditUser());
            if(null !=auditUser){
                map.put("message", String.format(MessageConstants.gzzdAuditMessage.ADD, dzSystem.getCreateByName(), dzSystem.getName()));
                String taskTitle = dzSystem.getName() + MessageConstants.auditMessage.ADD;
                actTaskService.apiStartProcess(GlobalConstans.ActProcDefKey.SYGZZD, auditUser.getLoginName(), dzSystem.getId(), taskTitle, map);
            }

        }catch (Exception e){
            throw new ServiceException("保存规章制度失败:"+e.getMessage());
        }
    }

    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void audit(DzSystem dzSystem) {
        super.save(dzSystem);
    }



    public User getById(String id){
      return userDao.get(id);
    }

}
