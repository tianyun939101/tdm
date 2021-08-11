package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.utils.excel.ImportExcel;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.ZyFileAttributeDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.ZyFileAttribute;
import com.demxs.tdm.service.sys.DictService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ZyFileAttributeService extends  CrudService<ZyFileAttributeDao, ZyFileAttribute> {

    @Autowired
    ZyFileAttributeDao zyFileAttributeDao;

    @Autowired
    private DictService dictService;


    public Page<ZyFileAttribute> list(Page<ZyFileAttribute> page, ZyFileAttribute entity) {
        Page<ZyFileAttribute> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<ZyFileAttribute> findPage(Page<ZyFileAttribute> page, ZyFileAttribute entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public ZyFileAttribute get(String id) {
        ZyFileAttribute equipment = super.dao.get(id);
        return equipment;
    }


    public void save(ZyFileAttribute entity) {
        super.save(entity);

    }


    /**
     * 设备Excel导入
     * @param file
     */
    public void importData(MultipartFile file)throws Exception{

         List<ZyFileAttribute>  list=zyFileAttributeDao.findList(new ZyFileAttribute());
         if(CollectionUtils.isNotEmpty(list)){
             for(ZyFileAttribute  z:list){
                 zyFileAttributeDao.delete(z);
             }
         }
        ImportExcel importExcel = new ImportExcel(file,1,0);
        int rowTotal = importExcel.getLastDataRowNum();
        for (int i=1;i<rowTotal;i++){
            Row row = importExcel.getRow(i);
            for(int j=0;j<row.getLastCellNum();j++) {
                ZyFileAttribute zyFileAttribute = new ZyFileAttribute();
                Dict dict = new Dict();
                String num = importExcel.getCellValue(row, j).toString();//序号
                if(num.contains(".")){
                    zyFileAttribute.setNum(num.substring(0,num.length()-2));
                }else{
                    zyFileAttribute.setNum(num);
                }

                String fileType = importExcel.getCellValue(row, ++j).toString();//文件类别
                dict.setType("file_type");
                dict.setLabel(fileType);
                List<Dict> l=dictService.findList(dict);
                if(CollectionUtils.isNotEmpty(l)){
                    zyFileAttribute.setFileType(l.get(0).getValue());
                }else{
                    zyFileAttribute.setFileType("");
                }
                String content = importExcel.getCellValue(row, ++j).toString();//检查内容
                zyFileAttribute.setContent(content);
                String checkLevel = importExcel.getCellValue(row, ++j).toString();//检查级别
                dict.setType("check_level");
                dict.setLabel(checkLevel);
                List<Dict> checkLevelList=dictService.findList(dict);
                if(CollectionUtils.isNotEmpty(checkLevelList)){
                    zyFileAttribute.setCheckLevel(checkLevelList.get(0).getValue());
                }else{
                    zyFileAttribute.setCheckLevel("");
                }
                String checkContent = importExcel.getCellValue(row, ++j).toString();//检查项
                zyFileAttribute.setCheckContent(checkContent);
                String isFlag = importExcel.getCellValue(row, ++j).toString();//是否显示
                dict.setType("yp_yes_no");
                dict.setLabel(isFlag);
                List<Dict> isFlaglList=dictService.findList(dict);
                if(CollectionUtils.isNotEmpty(isFlaglList)){
                    zyFileAttribute.setIsFlag(isFlaglList.get(0).getValue());
                }else{
                    zyFileAttribute.setIsFlag("1");
                }
                String isCheck = importExcel.getCellValue(row, ++j).toString();//是否显示
                dict.setType("yp_yes_no");
                dict.setLabel(isCheck);
                List<Dict> isChecklList=dictService.findList(dict);
                if(CollectionUtils.isNotEmpty(isChecklList)){
                    zyFileAttribute.setIsCheck(isChecklList.get(0).getValue());
                }else{
                    zyFileAttribute.setIsCheck("1");
                }
                String remarks = importExcel.getCellValue(row, ++j).toString();//问题说
                zyFileAttribute.setRemarks(remarks);
                //保存设备基础信息
                this.save(zyFileAttribute);
            }
        }
    }

    public  List<ZyFileAttribute> getFileByFileId(String fileId){
        return zyFileAttributeDao.getFileByFileId(fileId);
    }

    public  List<ZyFileAttribute> getFileIsByFileId(String fileId){
        return zyFileAttributeDao.getFileIsByFileId(fileId);
    }


}
