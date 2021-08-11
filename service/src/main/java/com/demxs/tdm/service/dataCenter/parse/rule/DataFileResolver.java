package com.demxs.tdm.service.dataCenter.parse.rule;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;

public interface DataFileResolver<T extends DataView> {
    /**
     * 根据附件路径解析数据
     * @param filePath
     * @return
     */
    DataView resolverDataFile(String filePath, DataParseRule parseRule, Class<T> dataView) throws ServiceException;
}
