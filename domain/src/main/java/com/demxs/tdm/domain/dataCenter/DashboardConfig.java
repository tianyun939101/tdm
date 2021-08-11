package com.demxs.tdm.domain.dataCenter;

import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.utils.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author: Jason
 * @Date: 2020/6/2 10:24
 * @Description: 大屏配置实体类
 */
public class DashboardConfig extends DataEntity<DashboardConfig> {

    private static final long serialVersionUID = 1L;
    /**
     * 类型
     */
    private String type;
    /**
     * 列名
     */
    private String valueName;
    /**
     * 列值
     */
    private String value;
    private List<String> valueNameList;
    private List<String> valueList;
    private final static String SYMBOL = "/-/";
    private final static String SPLIT_SYMBOL = "<->";

    private String status;

    private String center;

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DashboardConfig() {
    }

    public DashboardConfig(String id) {
        super(id);
    }

    public String getType() {
        return type;
    }

    public DashboardConfig setType(String type) {
        this.type = type;
        return this;
    }

    public String getValueName() {
        return valueName;
    }

    public DashboardConfig setValueName(String valueName) {
        this.valueName = valueName;
        if(StringUtils.isNotBlank(valueName)){
            String[] data;
            if(valueName.contains(SYMBOL)){
                data = valueName.split(SYMBOL);
            }else{
                data = valueName.split(",");
            }
            this.setValueNameList(Arrays.asList(data));
        }
        return this;
    }

    public String getValue() {
        return value;
    }

    public DashboardConfig setValue(String value) {
        this.value = value;
        if(StringUtils.isNotBlank(value)){
            String[] data;
            if(value.contains(SYMBOL)){
                data = value.split(SYMBOL);
            }else{
                data = value.split(",");
            }
            this.setValueList(Arrays.asList(data));
        }
        return this;
    }

    public List<String> getValueNameList() {
        return valueNameList;
    }

    public DashboardConfig setValueNameList(List<String> valueNameList) {
        this.valueNameList = valueNameList;
        return this;
    }

    public List<String> getValueList() {
        return valueList;
    }

    public DashboardConfig setValueList(List<String> valueList) {
        this.valueList = valueList;
        return this;
    }

    public static String getSYMBOL() {
        return SYMBOL;
    }

    public static String getSplitSymbol() {
        return SPLIT_SYMBOL;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("\"type\":\"")
                .append(type).append('\"');
        sb.append(",\"valueNameList\":")
                .append(valueNameList);
        sb.append(",\"valueList\":")
                .append(valueList);
        sb.append('}');
        return sb.toString();
    }
}
