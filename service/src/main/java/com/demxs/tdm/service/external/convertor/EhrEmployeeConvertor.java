package com.demxs.tdm.service.external.convertor;

import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.external.EhrEmployeeResult;

public class EhrEmployeeConvertor {

    public static User convertBean(EhrEmployeeResult orig, User dest) {

        dest.setId(orig.getEmplId());
        dest.setName(orig.getName());
        dest.setEname(orig.getNameAc());
        if(StringUtils.isNotBlank(orig.getSex())){
            if(orig.getSex().equals("M")){
                dest.setSex("0");
            }
            if(orig.getSex().equals("F")){
                dest.setSex("1");
            }
            if(orig.getSex().equals("U")){
                dest.setSex("");
            }
        }
        dest.setMobile(orig.getMobilePhone());
        dest.setEmail(orig.getEmailAddr());
        if(StringUtils.isNotBlank(orig.getHrStatus())){
            if(orig.getHrStatus().equals("A")){
                dest.setHrStatus("1");
                dest.setLoginFlag("1");
                dest.setDelFlag(User.DEL_FLAG_NORMAL);

            }
            if(orig.getHrStatus().equals("I")){
                dest.setHrStatus("0");
                dest.setLoginFlag("0");
                dest.setDelFlag(User.DEL_FLAG_DELETE);
            }
        }
        dest.setCompany(new Office(orig.getCompany()));
        dest.setOffice(new Office(orig.getDeptId()));
        dest.setLoginName(orig.getOprId());
        dest.setLastUpdateTime(orig.getLastUpdDtTm());
        dest.setNo(orig.getEmplId());
        return  dest;
    }
}
