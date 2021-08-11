package com.demxs.tdm.service.widget;

import com.demxs.tdm.dao.widget.WidgetDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author wuhui
 * @date 2020/10/15 14:41
 **/
@Service
public class WidgetService {
    @Autowired
    private WidgetDao widgetDao;

    /**
     * @Describe:获取选择器控件数据
     * @Author:WuHui
     * @Date:14:52 2020/10/15
     * @param table
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    public List<Map<String,Object>> getSelectedWidgetData(String table){
        return widgetDao.findSelectedWidgetData(table);
    }

    public List<Map<String,Object>> getSelectedTableList(String table,String where){
        return widgetDao.getSelectedTableList(table,where);
    }
}
