package com.demxs.tdm.service.dataCenter;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.dao.dataCenter.DataBackUpDao;
import com.demxs.tdm.dao.dataCenter.UReportFileDao;
import com.demxs.tdm.domain.dataCenter.DataBackUp;
import com.demxs.tdm.domain.dataCenter.UReportFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class UReportFileService extends  CrudService<UReportFileDao, UReportFile> {

    @Autowired
    UReportFileDao uReportFileDao;



    public Page<UReportFile> list(Page<UReportFile> page, UReportFile entity) {
        Page<UReportFile> Page = super.findPage(page, entity);
        return Page;
    }

    public Page<UReportFile> findPage(Page<UReportFile> page, UReportFile entity) {
        entity.setPage(page);
        page.setList(dao.findList(entity));
        return page;
    }


    public UReportFile get(String id) {
        UReportFile equipment = super.dao.get(id);
        return equipment;
    }


    public void save(UReportFile entity) {
        super.save(entity);

    }

    public boolean checkExistByName(String name){
       int s= uReportFileDao.getExistFIle(name);
       if(s>0){
           return true;
       }else{
           return false;
       }
    }

    /**
     *  根据报表名称查询报表
     *
     * @param name 报表名称
     */
    public UReportFile getReportFileByName(String name){
        UReportFile ff= uReportFileDao.getUreportFile(name);
        return ff;
    }

    /**
     * 查询全部报表
     */
    public  List<UReportFile> listAllReportFile(){
        return uReportFileDao.findDataList(new UReportFile());
    }

    /**
     * 根据报表名称删除报表
     *
     * @param name 报表名称
     */
    public  boolean removeReportFileByName(String name){
        uReportFileDao.deleteFile(name);
        return true;
    }


    /**
     *  保存报表
     */
    public boolean saveReportFile(UReportFile entity){
        super.save(entity);
        return true;
    }

    /**
     *  更新报表
     */
    public boolean updateReportFile(UReportFile entity){
       super.save(entity);
       return true;
    }


}
