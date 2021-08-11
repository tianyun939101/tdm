package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.resource.zhishi.ZhiShiProcessDao;
import com.demxs.tdm.dao.resource.zhishi.ZhishiXxDao;
import com.demxs.tdm.domain.resource.zhishi.ZhiShiProcess;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author: Jason
 * @Date: 2020/9/3 13:43
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class ZhiShiProcessService extends CrudService<ZhiShiProcessDao, ZhiShiProcess> {

    @Autowired
    private ZhishiXxDao zhishiXxDao;

    /**
    * @author Jason
    * @date 2020/9/3 13:44
    * @params [defId]
    * 根据知识信息id查询知识流程
    * @return com.demxs.tdm.domain.resource.zhishi.ZhiShiProcess
    */
    public ZhiShiProcess getByDefId(String defId){
        return this.dao.getByDefId(defId);
    }

    /**
    * @author Jason
    * @date 2020/9/3 13:54
    * @params [process]
    * 根据知识信息id修改知识流程
    * 同时更改知识信息的流程类型为手动绘制
    * @return int
    */
    public int updateByDefId(ZhiShiProcess process){
        return this.dao.updateByDefId(process);
    }

    @Override
    public void save(ZhiShiProcess process) {
        zhishiXxDao.updateType(new ZhishiXx(process.getDefId()).setProcessType(ZhishiXx.DRAW_PROCESS));
        if(this.dao.getByDefId(process.getDefId()) != null){
            process.preUpdate();
            this.dao.updateByDefId(process);
        }else{
            process.preInsert();
            this.dao.insert(process);
        }
    }
}
