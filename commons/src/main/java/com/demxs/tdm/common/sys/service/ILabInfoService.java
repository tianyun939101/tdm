package com.demxs.tdm.common.sys.service;

import com.demxs.tdm.common.sys.entity.User;

import java.util.Map;

public interface ILabInfoService {

    Map<String,String> getLabByUserId(User user);
}
