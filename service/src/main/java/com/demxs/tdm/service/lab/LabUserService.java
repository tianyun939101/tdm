package com.demxs.tdm.service.lab;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.ability.TestCategoryModifyDao;
import com.demxs.tdm.dao.ability.TestCategoryModifyRequestDao;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.lab.LabUserDao;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.lab.LabUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/8/25 10:34
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class LabUserService extends CrudService<LabUserDao, LabUser> {

    @Autowired
    private TestCategoryModifyDao modifyDao;
    @Autowired
    private TestCategoryModifyRequestDao requestDao;
    @Autowired
    private LabInfoDao labInfoDao;

    public List<LabUser> findByUserId(LabUser labUser){
        return this.dao.findByUserId(labUser);
    }

    public List<LabUser> findByLabId(LabUser labUser){
        LabInfo labInfo = labInfoDao.get(labUser.getLabId());
        if(labInfo!=null && "1".equals(labInfo.getNewLab())){
            //十四五
            labUser.setFlag("1");
           labUser.setNewLabId(labUser.getLabId());
           labUser.setLabId("");
        }else{
            //十三五
            labUser.setFlag("0");
        }
        return this.dao.findByLabId(labUser);
    }

    public int deleteByUserId(LabUser labUser){
        return this.dao.deleteByUserId(labUser);
    }

    @Override
    public void save(LabUser labUser) {
        LabInfo labInfo = labInfoDao.get(labUser.getLabId());
        if(labInfo!=null && "1".equals(labInfo.getNewLab())){
                //十四五
                labUser.setFlag("1");
            labUser.setNewLabId(labUser.getLabId());
            labUser.setLabId("");
            }else{
                //十三五
                labUser.setFlag("0");
        }
        List<LabUser> list = this.dao.findByUserId(labUser);
        this.dao.deleteByUserId(labUser);
        //判断是否迁移试验室
        if(list != null && !list.isEmpty()){
            /*throw new ServiceException("该用户已归属试验室："+list.get(0).getLabInfo().getName());*/
            modifyDao.updateLabIdByCreateBy(labUser.getUserId(),labUser.getLabId());
            requestDao.updateLabIdByCreateBy(labUser.getUserId(),labUser.getLabId());
        }
        super.save(labUser);
    }
}
