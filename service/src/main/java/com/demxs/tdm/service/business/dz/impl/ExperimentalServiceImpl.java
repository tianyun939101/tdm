package com.demxs.tdm.service.business.dz.impl;

import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.dao.business.dz.DzAbilityAssessDao;
import com.demxs.tdm.dao.business.dz.DzAbilityAtlasDao;
import com.demxs.tdm.domain.business.dz.DzAbilityAssess;
import com.demxs.tdm.domain.business.dz.DzAbilityAtlas;
import com.demxs.tdm.service.business.dz.ExperimentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExperimentalServiceImpl  extends CrudService<DzAbilityAssessDao, DzAbilityAssess> implements ExperimentalService {
    @Autowired
    DzAbilityAssessDao dzAbilityAssessDao;
    @Autowired
    DzAbilityAtlasDao dzAbilityAtlasDao;
    @Override
    public List<DzAbilityAssess> selectList(String code,String name,String inOut,String laboratory) {
        List<DzAbilityAssess>  selectLists = dzAbilityAssessDao.selectList(code,name,inOut,laboratory);
        return selectLists;
    }

    @Override
    public List<Map<String,Object>> selectListBysimilar(String similar) {
        List<Map<String,Object>>  selectLists = dzAbilityAssessDao.selectListBysimilar(similar);
        return selectLists;
    }

    @Override
    public List<DzAbilityAtlas> AbilityAtlaslist(String[] ids) {
        List<DzAbilityAtlas>  AbilityAtlaslist = dzAbilityAtlasDao.selectByTgList(ids);
        return AbilityAtlaslist;
    }

    @Override
    public Map<String,Object> selectone(String id) {
        Map<String,Object> one = dzAbilityAssessDao.selectById(id);
        return one;
    }

    @Override
    public void delete(DzAbilityAssess dzAbilityAssess) {
        super.delete(dzAbilityAssess);
    }

    @Override
    public void deleteinsert(String id) {
        dzAbilityAssessDao.deleteinsert(id);
    }

    @Override
    public void deletesimilar(String similar) {
        dzAbilityAssessDao.deletesimilar(similar);
    }

    @Override
    public void updateAbility_atlas(String id,String state) {
        dzAbilityAtlasDao.updateAbility_atlas(id,state);
    }

    @Override
    public void save(DzAbilityAssess dzAbilityAssess) {
        super.save(dzAbilityAssess);
    }

    @Override
    public DzAbilityAssess getAuditInfolist(String id) {
        DzAbilityAssess  selectlist  = dzAbilityAssessDao.selectAuditInfolist(id);
        return selectlist;
    }
}
