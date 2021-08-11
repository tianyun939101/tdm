package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.dao.dataCenter.DataBasePermDao;
import com.demxs.tdm.domain.dataCenter.DataBasePerm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = false)
public class DataBasePermService extends CrudService<DataBasePermDao, DataBasePerm> {

    public void detetePermByBaseId(String id) {
        super.dao.detetePermByBaseId(id);
    }

    public List<DataBasePerm> getUserPermByBaseId(String baseId) {
        return super.dao.getUserPermByBaseId(baseId);
    }

    public List<DataBasePerm> getOfficePermByBaseId(String baseId) {
        return super.dao.getOfficePermByBaseId(baseId);
    }

    /**
     * @Describe:根据
     * @Author:WuHui
     * @Date:14:56 2020/9/24
     * @param baseId
     * @return:java.util.List<com.demxs.tdm.domain.dataCenter.DataBasePerm>
    */
    List<DataBasePerm> findPermByBaseId(String baseId, User user){
        return dao.findPermByBaseId(baseId,user);
    }


}
