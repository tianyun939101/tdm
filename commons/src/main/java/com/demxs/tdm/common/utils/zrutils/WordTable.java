package com.demxs.tdm.common.utils.zrutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author： 张仁
 * @Description：
 * @Date：Create in 2017-06-23 10:03.
 * @Modefied By：
 */
public class WordTable {
    private String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<WordRow> rows;

    public WordTable() {
        rows = new ArrayList<WordRow>();
    }

    public void addRow(WordRow row){
        rows.add(row);
    }

    public List<WordRow> getRows() {
        return rows;
    }
}
