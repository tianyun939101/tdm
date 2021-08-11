package com.demxs.tdm.dao.widget;

import com.demxs.tdm.common.persistence.annotation.MyBatisDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author wuhui
 * @date 2020/10/15 14:42
 **/
@MyBatisDao
public interface WidgetDao {

    /**
     * @Describe:查询选择器控件数据
     * @Author:WuHui
     * @Date:14:46 2020/10/15
     * @param table
     * @return:java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    */
    List<Map<String,Object>> findSelectedWidgetData(@Param("table") String table);

    List<Map<String,Object>> getSelectedTableList(@Param("table") String table,@Param("where") String where);
}
