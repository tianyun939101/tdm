package com.demxs.tdm.service.external.convertor;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.sys.utils.DictUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.domain.external.SendTodoParam;
import com.demxs.tdm.domain.oa.Message;

public class SendTodoParamConvertor {

    public static SendTodoParam convertBean(Message orig, SendTodoParam dest) {

        dest.setAppName(Global.getConfig("originalSystem"));
        dest.setModelName(DictUtils.getDictLabel(orig.getModule().toString(),"message_module",null));
        dest.setModelId(orig.getId());
        dest.setSubject(orig.getTitle());
        dest.setLink(orig.getHandleUrl());
        dest.setType(orig.getType());
        dest.setTargets(orig.getUserJson());
        dest.setCreateTime(DateUtils.formatDateTime(orig.getCreateDate()));
        return  dest;
    }
}
