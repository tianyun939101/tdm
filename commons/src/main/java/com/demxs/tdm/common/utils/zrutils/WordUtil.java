package com.demxs.tdm.common.utils.zrutils;

/**
 * @Author： 张仁
 * @Description：
 * @Date：Create in 2017-06-22 20:58.
 * @Modefied By：
 */
public class WordUtil {
    /*public ActiveXComponent applicationWord = null;
    public Dispatch docs = null;
    public HashMap<String, Dispatch> WordBookMarks =null;
    public Dispatch doc = null;
    public Dispatch activeDocument = null;
    public Dispatch bookMarks;
    public Dispatch selectedTable;
    public Dispatch tables;
    private String filePath = "";

    public WordUtil(String _filePath)
    {
        filePath = _filePath;
        applicationWord = new ActiveXComponent("Word.Application");
        docs = applicationWord.getProperty("Documents").toDispatch();
        doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[]{filePath,new Variant(false)},new int[1]).toDispatch();
        activeDocument = applicationWord.getProperty("ActiveDocument").toDispatch();
        bookMarks = applicationWord.call(activeDocument, "Bookmarks").toDispatch();
        tables = applicationWord.call(doc, "Tables").toDispatch();
    }

    *//**
     * 设置书签信息
     * @param labelmap
     *//*
    public void SetBookMarkValue(Map<String,String> labelmap){
        for(String key:labelmap.keySet()){
            SetBookMarkValue(key,labelmap.get(key));
        }
    }

    *//**
     * 设置书签值
     * @param strbookmark
     * @param strvalue
     *//*
    public void SetBookMarkValue(String strbookmark,String strvalue)
    {
        boolean bookMarksExist = Dispatch.call(bookMarks, "Exists",strbookmark).toBoolean();
        if(bookMarksExist == true){
            Dispatch rangeItem_new = Dispatch.call(bookMarks, "Item",strbookmark).toDispatch();
            Dispatch range_new = Dispatch.call(rangeItem_new, "Range").toDispatch();
            Dispatch.put(range_new,"Text",strvalue);
        }
    }

    *//**
     * 根据标签插入表格
     * @param strbookmark
     * @param wordTable
     * @return
     *//*
    public Dispatch InsertTable(String strbookmark,WordTable wordTable){
        boolean bookMarksExist = Dispatch.call(bookMarks, "Exists",strbookmark).toBoolean();
        List<WordRow> rows = wordTable.getRows();
        if(bookMarksExist == true&&rows.size()>0){
            int row = rows.size();
            int column = rows.get(0).getCells().size();
            Dispatch rangeItem = Dispatch.call(bookMarks, "Item",strbookmark).toDispatch();
            Dispatch.call(rangeItem, "Select");
            Dispatch selection = Dispatch.get(applicationWord, "Selection").toDispatch(); // 输入内容需要的对象
            Dispatch.call(selection, "MoveDown"); // 游标往下一行
            Dispatch range = Dispatch.get(selection, "Range").toDispatch();// /当前光标位置或者选中的区域
            Dispatch newTable = Dispatch.call(tables, "Add", range,
                    new Variant(row), new Variant(column), new Variant(1))
                    .toDispatch(); // 设置row,column,表格外框宽度
            for (int i = 1; i <= row; i++) { // 循环取出每一列
                WordRow currRow = wordTable.getRows().get(i-1);
                for (int j = 1; j <= column; j++) {// 每一列中的单元格数
                    putTxtToCell(newTable, i, j, currRow.getCells().get(j-1).getCellvalue());
                }
            }
            if (wordTable.getTitle()!=null){
                InsertTableTitle(newTable,column,wordTable.getTitle());
            }
            return newTable;
        }
        return null;
    }

    private Dispatch InsertUnderTable(Dispatch curTable,WordTable wordTable){
        List<WordRow> rows = wordTable.getRows();
        int row = rows.size();
        int column = rows.get(0).getCells().size();
        Dispatch.call(curTable, "Select");
        Dispatch tablerange = Dispatch.call(curTable, "Range").toDispatch();
        int tableend = Dispatch.get(tablerange, "End").toInt();
        Dispatch rng = Dispatch.call(activeDocument, "Range",tableend+1,tableend+1).toDispatch();
        Dispatch.put(rng, "Text", "\r\n");
        Dispatch.call(rng, "Select");
        Dispatch newTable = Dispatch.call(tables, "Add", rng,
                new Variant(row), new Variant(column), new Variant(1))
                .toDispatch(); // 设置row,column,表格外框宽度
        for (int i = 1; i <= row; i++) { // 循环取出每一列
            WordRow currRow = wordTable.getRows().get(i-1);
            for (int j = 1; j <= column; j++) {// 每一列中的单元格数
                putTxtToCell(newTable, i, j, currRow.getCells().get(j-1).getCellvalue());
            }
        }
        if (wordTable.getTitle()!=null){
            InsertTableTitle(newTable,column,wordTable.getTitle());
        }
        return newTable;
    }

    *//**
     * 给表格插入标题信息
     * @param table
     * @param columncount
     * @param title
     *//*
    public void InsertTableTitle(Dispatch table,int columncount,String title){
        Dispatch excelrows = Dispatch.call(table, "Rows").toDispatch();
        Dispatch targetRow = Dispatch.call(table, "Rows",1).toDispatch();
        Dispatch.call(excelrows,"Add",targetRow);
        //合并标题部分
        Dispatch cell1 = Dispatch.call(table, "Cell",1,1).toDispatch();
        Dispatch cell2 = Dispatch.call(table, "Cell",1,columncount).toDispatch();
        Dispatch.call(cell1, "Merge",cell2);
        putTxtToCell(table, 1, 1, title);
        Dispatch celltitle = Dispatch.call(table, "Cell",1,1).toDispatch();
        Dispatch cellrange = Dispatch.call(celltitle, "Range").toDispatch();
        setTitleFont(cellrange);
        Dispatch.call(celltitle, "Select");
        Dispatch selection = Dispatch.get(applicationWord, "Selection").toDispatch(); // 输入内容需要的对象
        Dispatch paragraphs = Dispatch.get(selection, "Paragraphs").toDispatch();
        Dispatch.put(paragraphs, "Alignment", new Variant(1)); // 对齐方式

    }

    *//**
     * 插入多个表格
     * @param strbookmark
     * @param wordTables
     *//*
    public void InsertTables(String strbookmark,List<WordTable> wordTables){
        if (wordTables.size()>0){
            Dispatch curTable = InsertTable(strbookmark,wordTables.get(0));
            for(int i=1;i<wordTables.size();i++){
                WordTable wordTable = wordTables.get(i);
                curTable = InsertUnderTable(curTable,wordTable);
            }
        }
    }

    *//**
     * 插入单元格数据
     * @param table
     * @param cellRowIdx
     * @param cellColIdx
     * @param txt
     *//*
    private void putTxtToCell(Dispatch table, int cellRowIdx, int cellColIdx,
                             String txt) {
        Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),
                new Variant(cellColIdx)).toDispatch();
        Dispatch.call(cell, "Select");
        Dispatch selection = Dispatch.get(applicationWord, "Selection").toDispatch(); // 输入内容需要的对象
        Dispatch.put(selection, "Text", txt);
    }

    private void setTitleFont(Dispatch range){
        Dispatch font = Dispatch.get(range, "Font").toDispatch();
        Dispatch.put(font, "Bold", new Variant(true)); // 设置为黑体
        Dispatch.put(font, "Name", new Variant("宋体")); //
        Dispatch.put(font, "Size", new Variant(12)); // 小四
    }

    public void SavePdf(String pdfPath)
    {
        Variant f = new Variant(false);
        Dispatch.call(doc,"SaveAs", pdfPath, 17);
        Dispatch.call(doc, "Save");
        Dispatch.call(doc, "Close", f);
        applicationWord.invoke("Quit",new Variant[]{});
    }

    public void Save()
    {
        Variant f = new Variant(false);
        Dispatch.call(doc, "Save");
        Dispatch.call(doc, "Close", f);
        applicationWord.invoke("Quit",new Variant[]{});
    }

    public static void main(String[] args){
        //数据准备
        Map<String,String> labelmap = new HashMap<String, String>();
        labelmap.put("bianhao","测试数据");
        WordUtil wt = new WordUtil("d:\\1.docx");
        wt.SetBookMarkValue(labelmap);
        List<Map> map = new ArrayList<Map>();
        WordTable wordTable1 = getTabledatas(map);
        wordTable1.setTitle("小梅标题");
        WordTable wordTable2 = getTabledatas(map);
        wordTable2.setTitle("大梅标题");
        List<WordTable> lists = new ArrayList<WordTable>();
        lists.add(wordTable1);
        lists.add(wordTable2);
        wt.InsertTables("shiyansj",lists);
        wt.Save();
        //wt.SavePdf("d:\\1.pdf");
    }

    public static WordTable getTabledatas(List<Map> listrows){
        WordTable wt = new WordTable();
        if (listrows==null || listrows.size()==0)
        {
            return wt;
        }else{
            for (int i = 0; i <listrows.size() ; i++) {
                Map datamap =listrows.get(i);
                WordRow  wr = new WordRow();
                Iterator<Map.Entry<String, Object>> entries = datamap.entrySet().iterator();
                while (entries.hasNext()) {
                    Map.Entry<String, Object> entry = entries.next();
                    WordCell cell = new WordCell(entry.getValue()!=null ? entry.getValue().toString():"");
                    wr.addCell(cell);
                }
                wt.addRow(wr);
            }
        }
        return wt;
    }*/
}
