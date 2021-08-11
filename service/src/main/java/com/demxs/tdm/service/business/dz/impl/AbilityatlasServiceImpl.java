package com.demxs.tdm.service.business.dz.impl;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.dz.DzAbilityAtlasDao;
import com.demxs.tdm.domain.business.dz.DzAbilityAtlas;
import com.demxs.tdm.service.business.dz.AbilityatlasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class AbilityatlasServiceImpl extends CrudService<DzAbilityAtlasDao, DzAbilityAtlas> implements AbilityatlasService {
    @Autowired
    DzAbilityAtlasDao dzAbilityAtlasDao;
    @Override
    public List<DzAbilityAtlas> AbilityAtlaslist(DzAbilityAtlas dzAbilityAtlas) {
        String levelNumber = dzAbilityAtlas.getLevelNumber();
        String name =  dzAbilityAtlas.getName();
        String inOut = dzAbilityAtlas.getInOut();
        String laboratory = dzAbilityAtlas.getLaboratory();
        String category = dzAbilityAtlas.getCategory();
        String code = dzAbilityAtlas.getCode();
        List<DzAbilityAtlas>  AbilityAtlaslist = dzAbilityAtlasDao.selectByList(levelNumber,name,inOut,laboratory,category,code);
        return AbilityAtlaslist;
    }


    @Override
    public List<DzAbilityAtlas> AbilityAtlaspggllist(DzAbilityAtlas dzAbilityAtlas) {
        String levelNumber = dzAbilityAtlas.getLevelNumber();
        String name =  dzAbilityAtlas.getName();
        String inOut = dzAbilityAtlas.getInOut();
        String laboratory = dzAbilityAtlas.getLaboratory();
        String category = dzAbilityAtlas.getCategory();
        String code = dzAbilityAtlas.getCode();
        List<DzAbilityAtlas>  AbilityAtlaslist = dzAbilityAtlasDao.selectpgglByList(levelNumber,name,inOut,laboratory,category,code);
        return AbilityAtlaslist;
    }

    @Override
    public List<DzAbilityAtlas> AbilityAtlasJslist(DzAbilityAtlas dzAbilityAtlas) {
        String levelNumber = dzAbilityAtlas.getLevelNumber();
        String name =  dzAbilityAtlas.getName();
        String inOut = dzAbilityAtlas.getInOut();
        String laboratory = dzAbilityAtlas.getLaboratory();
        String category = dzAbilityAtlas.getCategory();
        String code = dzAbilityAtlas.getCode();
        List<DzAbilityAtlas>  AbilityAtlaslist = dzAbilityAtlasDao.selectJsByList(levelNumber,name,inOut,laboratory,category,code);
        return AbilityAtlaslist;
    }

    @Override
    public Map<String,Object> getone(String id) {
        Map<String,Object> dzAbilityClass = dzAbilityAtlasDao.selectByone(id);
        return dzAbilityClass;
    }

    @Override
    public void save(DzAbilityAtlas zAbilityAtlas) {
        zAbilityAtlas.setAssessState("未评估");
        super.save(zAbilityAtlas);
    }

    @Override
    public void updateAbilityState(String id,String state) {
        dzAbilityAtlasDao.updateAbilityState(id,state);
    }
}
