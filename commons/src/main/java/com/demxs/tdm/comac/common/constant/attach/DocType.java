package com.demxs.tdm.comac.common.constant.attach;



import com.demxs.tdm.common.sys.entity.Dict;
import com.demxs.tdm.common.sys.utils.DictUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文档类型定义接口
 * User: wuliepeng
 * Date: 2017-07-20
 * Time: 下午3:40
 */
public abstract class DocType {

    /**
     * 数据字典的key值
     */
    protected String dic_key;

    /**
     * 数据字典中定义的所有文档类型
     */
    protected List<String> types = new ArrayList<String>();

    /**
     * 判断是否包函文档类型
     * @param docType 文档类型
     * @return
     */
    public Boolean contains(String docType){
        if(types.isEmpty()){
            List<Dict> dicts = DictUtils.getDictList(dic_key);
            for (Dict dict : dicts){
                types.add(dict.getValue());
            }
        }
        return types.contains(docType);
    }
}
