package com.demxs.tdm.service.business.dz;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.domain.business.dz.DzProviderOrCapability;
import org.apache.ibatis.annotations.Param;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface ProviderService{
    Page<DzProviderOrCapability> list(Page<DzProviderOrCapability> page, DzProviderOrCapability dzProviderOrCapability);
    List<DzProviderOrCapability> selectList(String supplier_name, String number, String department, String state);
    String save(String capabilityInfo, String providerInfo, String type) throws UnsupportedEncodingException;
    void delete(DzProviderOrCapability dzProviderOrCapability);
    Map<String,Object> getCapabilityInfo(String id);
    Map<String,Object> getProviderInfo(String id);
    List<Map<String,Object>> getProviderOrCapability(String relationid);
    int update(String relationid);
    List<DzProviderOrCapability> sumlist(String relationid);
    DzProviderOrCapability getAuditInfolist(String relationid);
    void updatestate(String id, String state);
}
