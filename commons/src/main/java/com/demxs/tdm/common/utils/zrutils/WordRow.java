package com.demxs.tdm.common.utils.zrutils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author： 张仁
 * @Description：
 * @Date：Create in 2017-06-23 10:04.
 * @Modefied By：
 */
public class WordRow {
    private List<WordCell> cells ;

    public WordRow() {
        cells = new ArrayList<WordCell>();
    }

    public void addCell(WordCell cell){
        cells.add(cell);
    }

    public List<WordCell> getCells() {
        return cells;
    }
}
