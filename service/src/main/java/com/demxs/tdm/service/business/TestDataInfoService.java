package com.demxs.tdm.service.business;

import com.demxs.tdm.comac.common.constant.attach.DocType;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.dao.business.TestDataInfoDao;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.domain.business.TestDataInfo;
import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.service.dataCenter.parse.rule.DataFileResolver;
import com.demxs.tdm.service.dataCenter.parse.rule.DataView;
import com.demxs.tdm.service.dataCenter.parse.rule.constant.FileResolverType;
import com.demxs.tdm.service.dataCenter.parse.rule.constant.ViewType;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = false)
public class TestDataInfoService extends CrudService<TestDataInfoDao, TestDataInfo> {
    @Override
    public Page<TestDataInfo> findPage(Page<TestDataInfo> page, TestDataInfo entity) {
        return super.findPage(page, entity);
    }

    public DataView parseMetaInfo(String filePath, String ruleId, ViewType viewType) throws ServiceException {
        FileResolverType fileResolverType = getDocType(filePath);
        if(fileResolverType == null){
            throw new ServiceException("文件格式不支持");
        }
        DataParseRule parseRule = new DataParseRule();
        parseRule.setDataType("2");
        parseRule.setDataSplit("2");
        parseRule.setInfoSplit("2");
        parseRule.setDataRowSplit("2");
        parseRule.setInfoStart(1);
        parseRule.setInfoEnd(1);
        parseRule.setDataStart(1);
        DataFileResolver fileResolver = fileResolverType.getFileResolver();
        DataView dataView = fileResolver.resolverDataFile(filePath, parseRule, viewType.getDataView());
        return dataView;
    }

    public Map<String,Object> chartData(List testList,List trueList){
        String xAxis;
        String column;
        List<String> xValueList = new ArrayList<>();
        List<String> ytestValueList = new ArrayList<>();
        List<String> ytrueValueList = new ArrayList<>();
        Map<String,Object> resultData = new HashMap<>();
        Map<String, List<String>> chartData = new HashMap<>();
        xAxis = testList.get(0).toString().split(",")[0];//x轴：时间列
        column = testList.get(0).toString().split(",")[1];//y轴：特征值
        for (int i = 1; i <testList.size() ; i++) {
            String[] values = testList.get(i).toString().split(",");
            String yvalue = "0";
            if(values.length>1 && !values[1].isEmpty()){
                yvalue = values[1];
            }
            xValueList.add(values[0]);
            ytestValueList.add(yvalue);
        }
        for (int i = 1; i <trueList.size() ; i++) {
            String[] values = trueList.get(i).toString().split(",");
            String yvalue = "0";
            if(values.length>1 && !values[1].isEmpty()){
                yvalue = values[1];
            }
            ytrueValueList.add(yvalue);
        }
        chartData.put(xAxis,xValueList);
        chartData.put(column+"test",ytestValueList);
        chartData.put(column+"true",ytrueValueList);
        resultData.put("timeData",chartData.get(xAxis));
        resultData.put("testData",chartData.get(column+"test"));
        resultData.put("trueData",chartData.get(column+"true"));
        return resultData;
    }

    private FileResolverType getDocType(String fileName){
        String extName = FileUtils.getFileExtension(fileName);
        for(FileResolverType type : FileResolverType.values()){
            DocType docType = type.getDocType();
            if (docType.contains(extName)){
                return type;
            }
        }
        return null;
    }
}
