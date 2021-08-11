package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.dao.dataCenter.DataInfoDao;
import com.demxs.tdm.domain.dataCenter.DataInfo;
import com.demxs.tdm.domain.dataCenter.VO.DataSpdmSearch;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = false)
public class DataInfoService extends CrudService<DataInfoDao, DataInfo> {

    @Autowired
    private AttachmentInfoService attachmentInfoService;

    public void deleteByTestId(String testId) {
        super.dao.deleteByTestId(testId);
    }

    public List<DataInfo> getDataList(String name){
        List<DataInfo> dataList = super.dao.getDataList(name);
        for(DataInfo data:dataList){
            if(StringUtils.isNotEmpty(data.getTestData())){
                AttachmentInfo attachmentInfo = attachmentInfoService.get(data.getTestData());
                data.setDataName(attachmentInfo==null?"":attachmentInfo.getName());
            }
        }
        return dataList;
    }

    /**
     * @Describe:根据产品进行数据统计
     * @Author:WuHui
     * @Date:9:04 2020/9/2
     * @param
     * @return:java.util.Map<java.lang.String,java.lang.String>
    */
    public Map<String,String> countByProductModel(){
        Map<String,String> result = new HashMap();
        List<Map<String,String>> list = dao.countByProductModel();
        for(Map<String,String> data : list){
            result.put(data.get("NAME"),data.get("VALUE"));
        }
        return result;
    }
    /**
     * @Describe:根据实验类型进行数据统计
     * @Author:WuHui
     * @Date:9:04 2020/9/2
     * @param
     * @return:java.util.Map<java.lang.String,java.lang.String>
    */
    public Map<String,String> countByTestNature(){
        Map<String,String> result = new HashMap();
        List<Map<String,String>> list = dao.countByTestNature();
        for(Map<String,String> data : list){
            result.put(data.get("NAME"),data.get("VALUE"));
        }
        return result;
    }
    /**
     * @Describe:列表数据查询
     * @Author:WuHui
     * @Date:9:04 2020/9/2
     * @param page
     * @param dataInfo
     * @return:com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.dataCenter.DataInfo>
    */
    public Page<DataInfo> findListByCondition(Page<DataInfo> page, DataInfo dataInfo){
        //门户检索条件
        if(dataInfo!=null && StringUtils.isNotBlank(dataInfo.getPortal())){
            String portal = dataInfo.getPortal();
            String[] portalSplit = portal.split(":");
            switch (portalSplit[0]){
                case "l1":
                    int a = Integer.parseInt(portalSplit[1])-1;
                    dataInfo.setProductModel(String.valueOf(a));
                    break;
                case "l2c":
                    String[] portalPidSplitC = dataInfo.getPortalPid().split(":");
                    dataInfo.setTestNature("1");
                    int b = Integer.parseInt(portalPidSplitC[1])-1;
                    dataInfo.setProductModel(String.valueOf(b));
                    break;
                case "l2r":
                    String[] portalPidSplitR = dataInfo.getPortalPid().split(":");
                    dataInfo.setTestNature("2");
                    int c = Integer.parseInt(portalPidSplitR[1])-1;
                    dataInfo.setProductModel(String.valueOf(c));
                    break;
                default:
                    dataInfo.setAtaChapter(portalSplit[1]);
            }
            switch (portalSplit[1]){

            }

        }
        List<DataInfo> list = dao.findListByCondition(page,dataInfo);
        page.setList(list);
        return page;
    }

    /**
     * @Describe:试验数据统计
     * @Author:WuHui
     * @Date:14:29 2020/9/2
     * @param
     * @return:java.util.Map<java.lang.String,java.lang.String>
    */
    public Map<String,String> countTestData(){
        Map<String,String> result = new HashMap();
        List<Map<String,String>> list = dao.countTestData();
        for(Map<String,String> data : list){
            result.put(data.get("NAME"),data.get("VALUE"));
        }
        return result;
    }

}
