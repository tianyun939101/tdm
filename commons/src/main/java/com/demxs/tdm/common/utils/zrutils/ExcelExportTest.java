package com.demxs.tdm.common.utils.zrutils;

/**
 * @Author： 张仁
 * @Description：
 * @Date：Create in 2017-07-18 12:38.
 * @Modefied By：
 */
public class ExcelExportTest {
    private static final long serialVersionUID = 1L;
    private String name;		// 报告名称
    private String dizhi;		// 报告地址

    public ExcelExportTest(String _name,String _dizhi){
        name = _name;
        dizhi = _dizhi;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }
}
