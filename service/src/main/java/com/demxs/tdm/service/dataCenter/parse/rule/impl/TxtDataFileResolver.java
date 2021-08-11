package com.demxs.tdm.service.dataCenter.parse.rule.impl;

import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;

import java.io.File;
import java.util.List;

public class TxtDataFileResolver extends AbstractDataFileResolver {
    @Override
    protected List<String> loadDataFile(File file, DataParseRule parseRule) throws Exception {
        List<String> dataLines = FileUtils.readLines(file);
        return dataLines;
    }
}
