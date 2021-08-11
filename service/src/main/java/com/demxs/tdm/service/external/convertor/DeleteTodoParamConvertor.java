package com.demxs.tdm.service.external.convertor;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.sys.utils.DictUtils;
import com.demxs.tdm.domain.external.DeleteTodoParam;
import com.demxs.tdm.domain.oa.Message;

public class DeleteTodoParamConvertor {

    public static DeleteTodoParam convertBean(Message orig, DeleteTodoParam dest) {

        dest.setAppName(Global.getConfig("originalSystem"));
        dest.setModelName(DictUtils.getDictLabel(orig.getModule().toString(),"message_module",null));
        dest.setModelId(orig.getId());
        dest.setType(1);
        return  dest;
    }
}
