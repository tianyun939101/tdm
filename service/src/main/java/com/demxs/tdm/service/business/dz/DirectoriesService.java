package com.demxs.tdm.service.business.dz;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.domain.business.dz.DzProviderDirectories;

import java.util.List;

public interface DirectoriesService {
    Page<DzProviderDirectories> list(Page<DzProviderDirectories> page, DzProviderDirectories dzProviderDirectories);
    List<DzProviderDirectories> selectList(String name, String state);
    List<DzProviderDirectories> listCooperation(String name, String state);
    boolean findServiceByPhonePassword(String phone,String password);
}
