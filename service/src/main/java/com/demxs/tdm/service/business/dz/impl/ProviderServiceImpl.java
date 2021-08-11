package com.demxs.tdm.service.business.dz.impl;

import com.alibaba.fastjson.JSON;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.dao.business.dz.DzCapabilityInfoDao;
import com.demxs.tdm.dao.business.dz.DzProviderInfoDao;
import com.demxs.tdm.dao.business.dz.DzProviderOrCapabilityDao;
import com.demxs.tdm.domain.business.AuditInfo;
import com.demxs.tdm.domain.business.dz.DzCapabilityInfo;
import com.demxs.tdm.domain.business.dz.DzProviderInfo;
import com.demxs.tdm.domain.business.dz.DzProviderOrCapability;
import com.demxs.tdm.service.business.dz.ProviderService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 专业定制模块-实验服务供应商
 * @author Tiger Song
 * @version 2020-03-02
 */
@Service
public class ProviderServiceImpl  extends CrudService<DzProviderOrCapabilityDao, DzProviderOrCapability> implements ProviderService{
    @Autowired
    DzProviderInfoDao dzProviderInfoDao;
    @Autowired
    DzCapabilityInfoDao dzCapabilityInfoDao;
    @Autowired
    DzProviderOrCapabilityDao dzProviderOrCapabilityDao;

    @Override
    public Page<DzProviderOrCapability> list(Page<DzProviderOrCapability> page, DzProviderOrCapability dzProviderOrCapability) {
        Page<DzProviderOrCapability> Page = super.findPage(page, dzProviderOrCapability);
        return Page;
    }

    @Override
    public List<DzProviderOrCapability> selectList(String supplier_name, String number,String department,String state) {
        List<DzProviderOrCapability> Page = dzProviderOrCapabilityDao.selectList(supplier_name, number,department,state);
        return Page;
    }

    @Override
    public  List<DzProviderOrCapability> sumlist(String relationid) {
        List<DzProviderOrCapability>  sumlists = dzProviderOrCapabilityDao.selectSumListById(relationid);
        return sumlists;
    }

    @Override
    public String save(String capabilityInfo, String providerInfo,String type)  throws UnsupportedEncodingException{
        capabilityInfo =  StringEscapeUtils.unescapeHtml4(capabilityInfo);
        providerInfo =  StringEscapeUtils.unescapeHtml4(providerInfo);
        List<DzCapabilityInfo> capabilityInfolist = new ArrayList<>();
        capabilityInfolist = JSON.parseArray(capabilityInfo,DzCapabilityInfo.class);
        List<DzProviderInfo> providerInfolist = new ArrayList<>();
        providerInfolist = JSON.parseArray(providerInfo,DzProviderInfo.class);
        String ID = UUID.randomUUID().toString();
        List<Object> CapabilityInfoIdList= new ArrayList<Object>();
        List<Object> ProviderInfoIdList = new ArrayList<Object>();
        for(DzCapabilityInfo clist :capabilityInfolist) {
            String CapabilityInfoid = UUID.randomUUID().toString();
            DzCapabilityInfo inserCapability = new DzCapabilityInfo();
            inserCapability.setId(CapabilityInfoid);
            inserCapability.setDepartment(clist.getDepartment());
            inserCapability.setName(clist.getName());
            inserCapability.setNumber(clist.getNumber());
            inserCapability.setSettlementPrice(clist.getSettlementPrice());
            inserCapability.setCapabilityId(clist.getCapabilityId());
            inserCapability.setRelationIds(ID);
            inserCapability.setUndertakeTasks(clist.getUndertakeTasks());
            CapabilityInfoIdList.add("@Tiger@"+CapabilityInfoid);
            dzCapabilityInfoDao.insert(inserCapability);
        }
        for(DzProviderInfo prov : providerInfolist){
            String ProviderInfoid = UUID.randomUUID().toString();
            DzProviderInfo inserProvider = new DzProviderInfo();
            inserProvider.setId(ProviderInfoid);
            inserProvider.setEquipment(prov.getEquipment());
            inserProvider.setEquipmentattr(prov.getEquipmentattr());
            inserProvider.setExperience(prov.getExperience());
            inserProvider.setProfessionalNumber(prov.getProfessionalNumber());
            inserProvider.setStandard(prov.getStandard());
            inserProvider.setSupplierName(prov.getSupplierName());
            //inserProvider.setAuthentication(prov.getAuthentication());
            inserProvider.setQualifications(prov.getQualifications());
            //inserProvider.setCreateBy(prov.getCreateBy());
            inserProvider.setFileIds(prov.getFileIds());
            //inserProvider.setQualitysystem(prov.getQualitysystem());
            ProviderInfoIdList.add(ProviderInfoid);
            dzProviderInfoDao.insert(inserProvider);
        }
        List<Object[]> dataList=new ArrayList<Object[]>();
        dataList.add(CapabilityInfoIdList.toArray());
        dataList.add(ProviderInfoIdList.toArray());
        List<Object[]> resultList= combination(dataList,0,null);
        String newCapabilityId = "";
        String newProviderId = "";
        int counts = 0;
        for(int i=0;i<resultList.size();i++){
            Object[] objArr=resultList.get(i);
            for(Object obj : objArr){
                if((obj.toString().split("@Tiger@")).length>1){
                    newCapabilityId = obj.toString();
                }else{
                    newProviderId = obj.toString();
                }
                counts++;
                if(counts%2!=1){
                    String[] newCapabilityIds = newCapabilityId.toString().split("@Tiger@");
                    DzProviderOrCapability  inserProviderOrCapability = new DzProviderOrCapability();
                    inserProviderOrCapability.setCapabilityId(newCapabilityIds[newCapabilityIds.length-1]);
                    inserProviderOrCapability.setProviderId(newProviderId.toString());
                    inserProviderOrCapability.setRelationId(ID);
                    inserProviderOrCapability.setId(UUID.randomUUID().toString());
                    inserProviderOrCapability.setState(type);
                    dzProviderOrCapabilityDao.insert(inserProviderOrCapability);
                    newCapabilityId = "";
                    newProviderId = "";
                }

            }
        }
        return "Y";
    }

    @Override
    public int update(String relationid) {
        int count = 0;
        count = dzProviderOrCapabilityDao.deleteList(relationid);
        return count;
    }

    @Override
    public void delete(DzProviderOrCapability dzProviderOrCapability) {
        super.delete(dzProviderOrCapability);
    }

    public  List<Map<String,Object>> getProviderOrCapability(String relationid) {
        List<Map<String,Object>> selectlist  = dzProviderOrCapabilityDao.selectlist(relationid);
        return selectlist;
    }
    @Override
    public DzProviderOrCapability getAuditInfolist(String relationid) {
        DzProviderOrCapability selectlist  = dzProviderOrCapabilityDao.selectAuditInfolist(relationid);
        return selectlist;
    }

    @Override
    public void updatestate(String id,String state) {
        dzProviderOrCapabilityDao.updatestate(id,state);
    }

    public Map<String,Object> getCapabilityInfo(String id) {
        return dzCapabilityInfoDao.selectByPrimaryKey(id);
    }

    public Map<String,Object> getProviderInfo(String id) {
        return dzProviderInfoDao.selectByPrimaryKey(id);
    }

    public static List<Object[]> combination(List<Object[]> dataList, int index, List<Object[]> resultList){
        if(index==dataList.size()){
            return resultList;
        }

        List<Object[]> resultList0=new ArrayList<Object[]>();
        if(index==0){
            Object[] objArr=dataList.get(0);
            for(Object obj : objArr){
                resultList0.add(new Object[]{obj});
            }
        }else{
            Object[] objArr=dataList.get(index);
            for(Object[] objArr0: resultList){
                for(Object obj : objArr){
                    Object[] objArrCopy=new Object[objArr0.length+1];
                    System.arraycopy(objArr0, 0, objArrCopy, 0, objArr0.length);
                    objArrCopy[objArrCopy.length-1]=obj;
                    resultList0.add(objArrCopy);
                }
            }
        }
        return combination(dataList,++index,resultList0);
    }

}