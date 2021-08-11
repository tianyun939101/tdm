package com.demxs.tdm.service.sys;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.dao.FtpConfigDao;
import com.demxs.tdm.common.sys.entity.FtpConfig;
import com.demxs.tdm.common.utils.FtpClientUtil;
import com.demxs.tdm.common.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.rowset.serial.SerialException;

/**
 * @author: Jason
 * @Date: 2020/7/17 14:32
 * @Description:试飞ftp服务器配置
 */
@Service
@Transactional(readOnly = false,rollbackFor = SerialException.class)
public class FtpConfigService extends CrudService<FtpConfigDao, FtpConfig> {

    public int deleteByType(String type){
        return this.dao.deleteByType(type);
    }

    public FtpConfig getByType(String type){
        return this.dao.getByType(type);
    }

    @Override
    public void save(FtpConfig config) {
        FtpClientUtil.clearCache();
        if(StringUtils.isNotBlank(config.getType())){
            this.deleteByType(config.getType());
        }
        super.save(config);
    }
}
