package com.demxs.tdm.service.quartz;


import com.demxs.tdm.dao.quartz.QuartzJobDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.quartz.QuartzJob;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class QuartzJobService extends CrudService<QuartzJobDao, QuartzJob>{



    public QuartzJob get(String id) {
        return super.get(id);
    }

    public List<QuartzJob> findList(QuartzJob sysQuartzLog) {
        return super.findList(sysQuartzLog);
    }

    public Page<QuartzJob> findPage(Page<QuartzJob> page, QuartzJob sysQuartzLog) {
        return super.findPage(page, sysQuartzLog);
    }

    @Transactional(readOnly = false)
    public void save(QuartzJob sysQuartzLog) {
        super.save(sysQuartzLog);
    }

    @Transactional(readOnly = false)
    public void delete(QuartzJob sysQuartzLog) {
        super.delete(sysQuartzLog);
    }

    @Transactional(readOnly = false)
    public void updateExecuteType(String id){

        this.dao.updateExecuteType(id);
    }

}
