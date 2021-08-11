package com.demxs.tdm.service.dataCenter.parse.rule.impl;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;
import com.demxs.tdm.service.dataCenter.parse.rule.DataFileResolver;
import com.demxs.tdm.service.dataCenter.parse.rule.DataParseException;
import com.demxs.tdm.service.dataCenter.parse.rule.DataView;

import java.io.File;
import java.util.List;

public abstract class AbstractDataFileResolver<T extends DataView> implements DataFileResolver<T> {

    @Override
    public DataView resolverDataFile(String filePath, DataParseRule parseRule, Class<T> view) throws ServiceException {
        DataView dataView = null;
        try {
            dataView = view.newInstance();
            dataView.setParseRule(parseRule);
        } catch (Exception e) {
            throw new ServiceException("解析失败",e);
        }
        try {
            //if(parseRule.getParseMetaInfo()) {
                List<String> dataLines = loadDataFile(new File(filePath), parseRule);
                dataView.parse(dataLines);
            /*}else if(parseRule.getColumnData()){
                List<String> dataLines = loadDataFile(new File(filePath), parseRule);
                dataView
            }*/
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataParseException("解析失败");
        }
        return dataView;
    }

    protected abstract List<String> loadDataFile(File file, DataParseRule parseRule) throws Exception;
}
