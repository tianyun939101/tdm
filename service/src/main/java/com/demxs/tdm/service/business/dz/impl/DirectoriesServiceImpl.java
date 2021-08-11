package com.demxs.tdm.service.business.dz.impl;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.dz.DzProviderDirectoriesDao;
import com.demxs.tdm.domain.business.dz.DzProviderDirectories;
import com.demxs.tdm.service.business.dz.DirectoriesService;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DirectoriesServiceImpl extends CrudService<DzProviderDirectoriesDao, DzProviderDirectories> implements DirectoriesService {
    @Autowired
    DzProviderDirectoriesDao dzProviderDirectoriesDao;
    @Override
    public Page<DzProviderDirectories> list(Page<DzProviderDirectories> page, DzProviderDirectories dzProviderDirectories) {
        Page<DzProviderDirectories> Page = super.findPage(page, dzProviderDirectories);
        return Page;
    }

    @Override
    public List<DzProviderDirectories> selectList(String name, String state) {
        List<DzProviderDirectories> List = dzProviderDirectoriesDao.selectList(name, state);
        return List;
    }

    @Override
    public List<DzProviderDirectories> listCooperation(String name, String state) {
        List<DzProviderDirectories> List = dzProviderDirectoriesDao.listCooperation(name, state);
        return List;
    }

    @Override
    public boolean findServiceByPhonePassword(String phone, String password) {
        boolean isExist = false;
        List<String> list = dzProviderDirectoriesDao.findServiceByPhonePassword(phone,password);
        for(String pw:list){
            if(SystemService.validatePassword(password,pw)){
                isExist = true;
                break;
            }
        }
        return isExist;
    }

    @Override
    public DzProviderDirectories get(String id) {
        DzProviderDirectories dzDevelopmentPlanning = super.dao.get(id);
        return dzDevelopmentPlanning;
    }

    @Override
    public void save(DzProviderDirectories dzDevelopmentPlanning) {
        super.save(dzDevelopmentPlanning);
    }
}
