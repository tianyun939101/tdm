package com.demxs.tdm.service.resource.zhishi;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.resource.zhishi.ZhiShiProcessNodeInfoDao;
import com.demxs.tdm.domain.resource.zhishi.ZhiShiProcessNodeInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/9/4 10:28
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class ZhiShiProcessNodeInfoService extends CrudService<ZhiShiProcessNodeInfoDao, ZhiShiProcessNodeInfo> {

    public ZhiShiProcessNodeInfo getByDefIdAndNodeId(ZhiShiProcessNodeInfo nodeInfo){
        return this.dao.getByDefIdAndNodeId(nodeInfo);
    }

    public int updateByDefIdAndNodeId(ZhiShiProcessNodeInfo nodeInfo){
        return this.dao.updateByDefIdAndNodeId(nodeInfo);
    }

    @Override
    public void save(ZhiShiProcessNodeInfo nodeInfo) {
        if(null != this.dao.getByDefIdAndNodeId(nodeInfo)){
            nodeInfo.preUpdate();
            this.dao.updateByDefIdAndNodeId(nodeInfo);
        }else{
            nodeInfo.preInsert();
            this.dao.insert(nodeInfo);
        }
    }
}
