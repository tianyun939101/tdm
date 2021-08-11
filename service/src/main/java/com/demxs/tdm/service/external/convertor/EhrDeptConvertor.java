package com.demxs.tdm.service.external.convertor;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.domain.external.EhrDeptResult;

public class EhrDeptConvertor{
    public static Office convertBean(EhrDeptResult orig, Office dest) {

        dest.setId(orig.getDeptId());
        dest.setEffStatus(orig.getEffStatus());
       /* if(orig.getEffStatus().equals("I")){
            dest.setDelFlag(Office.DEL_FLAG_DELETE);
        }else {
            dest.setDelFlag(Office.DEL_FLAG_NORMAL);
        }*/
        dest.setName(orig.getDescr());
        dest.setEname(orig.getLgiDescrEng());
        dest.setManagerId(orig.getManagerId());
        dest.setManagerName(orig.getManagerName());
        dest.setCompanyId(orig.getCompany());
        dest.setPrimaryPerson(new User(orig.getManagerId()));
        dest.setCompanyName(orig.getCompanyDescr());
        dest.setCenterId(orig.getLgiCostCengerId());
        dest.setSort(orig.getTreeNodeNum());
        if(orig.getParDeptIdChn()==null || orig.getParDeptIdChn().equals("")){
            dest.setParent(new Office("0"));
        }else{
            dest.setParent(new Office(orig.getParDeptIdChn()));
        }
        dest.setTimeStamp(orig.getLgiIntDt());
        dest.setUseable(Global.YES);
        return  dest;
    }
}
