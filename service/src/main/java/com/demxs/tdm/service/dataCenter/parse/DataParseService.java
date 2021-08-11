package com.demxs.tdm.service.dataCenter.parse;

import com.demxs.tdm.comac.common.constant.attach.DocType;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.file.FileStore;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.resource.attach.AttachmentInfoDao;
import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.dataCenter.parse.rule.DataFileResolver;
import com.demxs.tdm.service.dataCenter.parse.rule.DataView;
import com.demxs.tdm.service.dataCenter.parse.rule.constant.FileResolverType;
import com.demxs.tdm.service.dataCenter.parse.rule.constant.ViewType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class DataParseService{

    @Autowired
    private DataParseRuleService dataParseRuleService;
    @Autowired
    private FileStore fileStore;
    @Autowired
    private AttachmentInfoDao attachmentInfoDao;

    public Map<String,Object> parseContrastMeta(MultipartFile dataFile, String ruleId, String dataId){
        if(StringUtils.isNotBlank(ruleId) && StringUtils.isNotBlank(dataId)
                && null != dataFile && !dataFile.isEmpty()){
            try {
                String filePath1;
                AttachmentInfo attachmentInfo = attachmentInfoDao.get(dataId);
                filePath1 = Global.getConfig("sourceDir")+attachmentInfo.getPath();
                DataView dataView1 = this.parseMetaInfo(filePath1,ruleId, ViewType.TREE_VIEW);

                String filename = dataFile.getOriginalFilename();
                byte[] bytes = dataFile.getBytes();
                String filePath2;
                filePath2 = fileStore.saveFile(filename, bytes);
                filePath2 = Global.getConfig("sourceDir")+filePath2;
                DataView dataView2 = this.parseMetaInfo(filePath2,ruleId, ViewType.TREE_VIEW);

                Map<String,Object> resultData = new HashMap<>(3);
                resultData.put("metaInfo",dataView2.contrastRender(dataFile.getOriginalFilename(),
                        attachmentInfo.getName(),dataView1.getMetaInfo()));
                resultData.put("filePath",filePath2);
                resultData.put("xAxis",dataView2.getxAxis());

                return resultData;
            }catch (Exception e){
                e.printStackTrace();
            }
        }else{
            return null;
        }
        return null;
    }

    public DataView parseMetaInfo(String filePath, String ruleId, ViewType viewType) throws ServiceException {
        FileResolverType fileResolverType = getDocType(filePath);
        if(fileResolverType == null){
            throw new ServiceException("文件格式不支持");
        }
        DataParseRule parseRule = dataParseRuleService.get(ruleId);
        if(parseRule == null){
            throw new ServiceException("解析规则未定义");
        }
        parseRule.setParseMetaInfo(true);
        DataFileResolver fileResolver = fileResolverType.getFileResolver();
        DataView dataView = fileResolver.resolverDataFile(filePath, parseRule, viewType.getDataView());
        return dataView;
    }

    public List<Object> contrastChartData(String filePath1,String dataId, String ruleId, String xAxis, String column){
        FileResolverType fileResolverType = getDocType(filePath1);
        if(fileResolverType == null){
            throw new ServiceException("文件格式不支持");
        }
        DataParseRule parseRule = dataParseRuleService.get(ruleId);
        if(parseRule == null){
            throw new ServiceException("解析规则未定义");
        }
        parseRule.setColumnData(true);
        parseRule.setColumnName(column);
        parseRule.setxAxis(xAxis);
        DataFileResolver fileResolver = fileResolverType.getFileResolver();
        DataView dataView1 = fileResolver.resolverDataFile(filePath1, parseRule, ViewType.TREE_VIEW.getDataView());
        Map<String, Object> map1 = new HashMap<>(2);
        map1.put("timeData",dataView1.getColumnData(xAxis));
        map1.put("serieData",dataView1.getColumnData(column));
        List<Object> list = new ArrayList<>();
        list.add(map1);

        AttachmentInfo attachmentInfo = attachmentInfoDao.get(dataId);
        String filePath2 = Global.getConfig("sourceDir")+attachmentInfo.getPath();
        DataView dataView2 = fileResolver.resolverDataFile(filePath2, parseRule, ViewType.TREE_VIEW.getDataView());
        Map<Object, Object> map2 = new HashMap<>(2);
        map2.put("timeData",dataView2.getColumnData(xAxis));
        map2.put("serieData",dataView2.getColumnData(column));

        list.add(map2);
        return list;
    }

    public Map<String,List<String>> chartData(String filePath, String ruleId, String xAxis, String column){
        FileResolverType fileResolverType = getDocType(filePath);
        if(fileResolverType == null){
            throw new ServiceException("文件格式不支持");
        }
        DataParseRule parseRule = dataParseRuleService.get(ruleId);
        if(parseRule == null){
            throw new ServiceException("解析规则未定义");
        }
        parseRule.setColumnData(true);
        parseRule.setColumnName(column);
        parseRule.setxAxis(xAxis);
        DataFileResolver fileResolver = fileResolverType.getFileResolver();
        DataView dataView = fileResolver.resolverDataFile(filePath, parseRule, ViewType.TREE_VIEW.getDataView());
        Map<String,List<String>> result = new HashMap<>();
        result.put(xAxis,dataView.getColumnData(xAxis));
        result.put(column,dataView.getColumnData(column));
        return result;
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
