package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.comac.common.constant.MessageConstants;
import com.demxs.tdm.comac.common.constant.ShebeiConstans;
import com.demxs.tdm.comac.common.constant.ZhishiConstans;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.config.GlobalConstans;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.quartz.QuartzJobDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiTaiTaoDao;
import com.demxs.tdm.dao.resource.shebei.ShebeiTaiTaoReDao;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.quartz.QuartzJob;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.changshangygys.Changshanggysxx;
import com.demxs.tdm.domain.resource.shebei.*;
import com.demxs.tdm.domain.resource.zhishi.ZhishiXx;
import com.demxs.tdm.service.ability.AptitudeService;
import com.demxs.tdm.service.business.TestPlanExecuteDetailService;
import com.demxs.tdm.service.business.TestPlanService;
import com.demxs.tdm.service.external.impl.ExternalApi;
import com.demxs.tdm.service.job.QuartzManager;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.oa.IActAuditService;
import com.demxs.tdm.service.quartz.QuartzJobService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.resource.changshangygys.ChangshanggysxxService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.list.SetUniqueList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

//import com.demxs.tdm.service.job.MyJob;

/**
 * 设备信息Service
 *
 * @author zhangdengcai
 * @version 2017-06-13
 */
@Service
@Transactional(readOnly = true, rollbackFor = ServiceException.class)
public class ShebeiTaiTaoService extends CrudService<ShebeiTaiTaoDao, ShebeiTaiTao> {

    @Autowired
    private ShebeiTaiTaoReDao shebeiTaiTaoReDao;

    @Autowired
    private ShebeiTaiTaoDao shebeiTaiTaoDao;
    /**
     * 查询
     *
     * @param id
     * @return
     */
    @Override
    public ShebeiTaiTao get(String id) {
        ShebeiTaiTao shebeiTaiTao = super.get(id);
        List<Shebei> shebeiList = shebeiTaiTaoReDao.getShebeiList(id);
        shebeiTaiTao.setSbList(shebeiList);
        return shebeiTaiTao;
    }

    /**
     * 保存台套
     *
     * @param shebeiTaiTao
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void save(ShebeiTaiTao shebeiTaiTao) {
        shebeiTaiTao.setLabInfoId(shebeiTaiTao.getLabInfo().getId());
        shebeiTaiTao.setAbilityId(shebeiTaiTao.getAptitude().getId());
        if (StringUtils.isNotBlank(shebeiTaiTao.getId())) {
            //清空台套关联设备信息
            shebeiTaiTaoReDao.removeShebei(shebeiTaiTao.getId());
            //保存台套关联设备信息
            for (Shebei shebei : shebeiTaiTao.getSbList()) {
                ShebeiTaiTaoRe sbtt = new ShebeiTaiTaoRe();
                sbtt.setId(IdGen.uuid());
                sbtt.setShebeiId(shebei.getId());
                sbtt.setTaitaoId(shebeiTaiTao.getId());
                shebeiTaiTaoReDao.insert(sbtt);
            }
            shebeiTaiTaoDao.update(shebeiTaiTao);

        } else {
            super.save(shebeiTaiTao);
            for (Shebei shebei : shebeiTaiTao.getSbList()) {
                ShebeiTaiTaoRe shebeiTaiTaoRe = new ShebeiTaiTaoRe();
                shebeiTaiTaoRe.setId(IdGen.uuid());
                shebeiTaiTaoRe.setShebeiId(shebei.getId());
                shebeiTaiTaoRe.setTaitaoId(shebeiTaiTao.getId());
                shebeiTaiTaoReDao.insert(shebeiTaiTaoRe);
            }
        }
    }

    /**
     * 删除台套
     *
     * @param shebeiTaiTao
     */
    @Override
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void delete(ShebeiTaiTao shebeiTaiTao) {
        shebeiTaiTaoReDao.removeShebei(shebeiTaiTao.getId());
        super.delete(shebeiTaiTao);
    }

    /**
     * 更新台套
     *
     * @param shebeiTaiTao
     */
    @Transactional(readOnly = false, rollbackFor = ServiceException.class)
    public void update(ShebeiTaiTao shebeiTaiTao) {
        //清空台套关联设备信息
        shebeiTaiTaoReDao.removeShebei(shebeiTaiTao.getId());
        //保存台套关联设备信息
        for (Shebei shebei : shebeiTaiTao.getSbList()) {
            ShebeiTaiTaoRe sbtt = new ShebeiTaiTaoRe();
            sbtt.setId(IdGen.uuid());
            sbtt.setShebeiId(shebei.getId());
            sbtt.setTaitaoId(shebeiTaiTao.getId());
            shebeiTaiTaoReDao.insert(sbtt);
        }
        //保存台套信息
        super.save(shebeiTaiTao);
    }
}