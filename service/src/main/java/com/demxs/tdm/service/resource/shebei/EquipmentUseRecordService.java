package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.resource.shebei.EquipmentUseRecordDao;
import com.demxs.tdm.domain.resource.shebei.EquipmentUseRecord;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Jason
 * @Date: 2020/5/28 14:51
 * @Description:
 */
@Service
@Transactional(readOnly = true)
public class EquipmentUseRecordService extends CrudService<EquipmentUseRecordDao, EquipmentUseRecord> {
}
