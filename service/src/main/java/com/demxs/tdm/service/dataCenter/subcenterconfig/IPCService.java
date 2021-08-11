package com.demxs.tdm.service.dataCenter.subcenterconfig;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.subcenterconfig.IPCDao;
import com.demxs.tdm.domain.dataCenter.subcenterconfig.IPC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/6/17 16:49
 * @Description:
 */
@Service
@Transactional(readOnly = false,rollbackFor = ServiceException.class)
public class IPCService extends CrudService<IPCDao, IPC> {
    public IPC getByLabId(IPC ipc) {
        return this.dao.getByLabId(ipc);
    }
}
