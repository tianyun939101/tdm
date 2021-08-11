package com.demxs.tdm.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.demxs.tdm.common.utils.zrutils.WordUtil;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.springframework.context.annotation.Bean;

/**
 * @author wuhui
 * @date 2020/11/30 13:11
 **/
public class WordUtils {

    /**
     * 根据模板生成新word文档
     * 判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
     * @param inputUrl 模板存放地址
     * @param textMap 需要替换的信息集合
     * @param tableList 需要插入的表格信息集合
     * @return 成功返回true,失败返回false
     */
    public static boolean changWord(String inputUrl, String outputUrl,
                                    Map<String, String> textMap, List<String[]> tableList) {

        //模板转换默认成功
        boolean changeFlag = true;
        try {
            //获取docx解析对象
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //解析替换文本段落对象
            WordUtils.changeText(document, textMap);
            //解析替换表格对象
            WordUtils.changeTable(document, textMap, tableList);

            //生成新的word
            File file = new File(outputUrl);
            FileOutputStream stream = new FileOutputStream(file);
            document.write(stream);
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
            changeFlag = false;
        }

        return changeFlag;

    }

    /**
     * 替换段落文本
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     */
    public static void changeText(XWPFDocument document, Map<String, String> textMap){
        //获取段落集合
        List<XWPFParagraph> paragraphs = document.getParagraphs();

        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();
            if(checkText(text)){
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    //替换模板原来位置
                    run.setText(changeValue(run,run.toString(), textMap),0);
                }
            }
        }

    }

    /**
     * 替换表格对象方法
     * @param document docx解析对象
     * @param textMap 需要替换的信息集合
     * @param tableList 需要插入的表格信息集合
     */
    public static void changeTable(XWPFDocument document, Map<String, String> textMap,
                                   List<String[]> tableList){
        //获取表格对象集合
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            //只处理行数大于等于2的表格，且不循环表头
            XWPFTable table = tables.get(i);
            if(table.getRows().size()>1){
                //判断表格是需要替换还是需要插入，判断逻辑有$为替换，表格无$为插入
                if(checkText(table.getText())){
                    List<XWPFTableRow> rows = table.getRows();
                    //遍历表格,并替换模板
                    eachTable(rows, textMap);
                }else{
                    insertTable(table, tableList);
                }
            }
        }
    }


    /**
     * 遍历表格
     * @param rows 表格行对象
     * @param textMap 需要替换的信息集合
     */
    public static void eachTable(List<XWPFTableRow> rows ,Map<String, String> textMap){
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if(checkText(cell.getText())){
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            run.setText(changeValue(run,run.toString(), textMap),0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 为表格插入数据，行数不够添加新行
     * @param table 需要插入数据的表格
     * @param tableList 插入数据集合
     */
    public static void insertTable(XWPFTable table, List<String[]> tableList){
        String[] header = tableList.get(0);
        int cols = header.length;
        //创建行,根据需要插入的数据添加新行，不处理表头
        for(int i = 1; i < tableList.size(); i++){
            XWPFTableRow row = table.insertNewTableRow(i);
            for(int c=0;c<cols;c++){
                row.createCell();
            }
        }
        //遍历表格插入数据
        List<XWPFTableRow> rows = table.getRows();
        for(int i = 1; i < rows.size()-1 ; i++){
            XWPFTableRow newRow = table.getRow(i);
            List<XWPFTableCell> cells = newRow.getTableCells();
            for(int j = 0; j < cells.size(); j++){
                XWPFTableCell cell = cells.get(j);
                cell.setText(tableList.get(i-1)[j]);
            }
        }

    }

    /**
     * @Describe:写入列表数据
     * @Author:WuHui
     * @Date:13:23 2020/12/1
     * @param table
     * @param tableList
     * @param startRow
     * @return:void
    */
    public static void writeTableRow(XWPFTable table, List<Map<String,String>> tableList,int startRow,int keyIndex,int[] mergeCols){
        String tmp = "";
        //创建根据第一行模板，创建数据行
        for(int i = 1; i < tableList.size(); i++){
            WordUtils.copy(table,table.getRow(startRow),i+startRow);
        }
        //数据填充
        List<XWPFTableRow> rows = table.getRows();
        //前一个键值
        String preKey = "";
        int index = 1;
        int fromRow = 2, toRow = 2;
        for(int i = 0; i < tableList.size(); i++){
            Map<String,String> textMap = tableList.get(i);
            XWPFTableRow row = table.getRow(i+startRow);
            for(int j=0;j<row.getTableCells().size();j++){
                XWPFTableCell col = row.getTableCells().get(j);
                if(checkText(col.getText())){
                    List<XWPFParagraph> paragraphs = col.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            run.setText(changeValue(run,run.toString(), textMap),0);
                            if(keyIndex == (j+1)){
                                tmp = run.getText(0);
                                //设置序号列
                                if(!preKey.equals(tmp)){
                                    row.getTableCells().get(0).setText(String.valueOf(index ++));
                                }
                            }
                            //第一次赋值
                            if(i==0 && keyIndex == (j+1)){
                                preKey = tmp;
                            }
                        }
                    }
                }
                //指定键值列 进行和并单元格判断
                if(keyIndex != -1 && keyIndex == (j+1)){
                    if(preKey.equals(tmp)){
                        toRow++;
                    }else{
                        if(fromRow != toRow){
                            //单元格合并
                            for(int colIndex : mergeCols){
                                WordUtils.mergeCellsVertically(table,colIndex,fromRow,toRow-1);
                            }
                        }
                        //重新合并计数
                        fromRow = i + 2;toRow = i+ 2;
                        preKey = tmp;
                    }
                }
            }
        }
    }

    /**
     * @Describe:复制目标行到指定位置
     * @Author:WuHui
     * @Date:13:22 2020/12/1
     * @param table
     * @param sourceRow
     * @param rowIndex
     * @return:void
    */
    public static void copy(XWPFTable table,XWPFTableRow sourceRow,int rowIndex){
        //在表格指定位置新增一行
        XWPFTableRow targetRow = table.insertNewTableRow(rowIndex);
        //复制行属性
        targetRow.getCtRow().setTrPr(sourceRow.getCtRow().getTrPr());
        List<XWPFTableCell> cellList = sourceRow.getTableCells();
        if (null == cellList) {
            return;
        }
        //复制列及其属性和内容
        XWPFTableCell targetCell = null;
        for (XWPFTableCell sourceCell : cellList) {
            targetCell = targetRow.addNewTableCell();
            //列属性
            targetCell.getCTTc().setTcPr(sourceCell.getCTTc().getTcPr());
            //段落属性
            if(sourceCell.getParagraphs()!=null&&sourceCell.getParagraphs().size()>0){
                targetCell.getParagraphs().get(0).getCTP().setPPr(sourceCell.getParagraphs().get(0).getCTP().getPPr());
                if(sourceCell.getParagraphs().get(0).getRuns()!=null&&sourceCell.getParagraphs().get(0).getRuns().size()>0){
                    XWPFRun cellR = targetCell.getParagraphs().get(0).createRun();
                    cellR.setText(sourceCell.getText());
                    cellR.setBold(sourceCell.getParagraphs().get(0).getRuns().get(0).isBold());
                }else{
                    targetCell.setText(sourceCell.getText());
                }
            }else{
                targetCell.setText(sourceCell.getText());
            }
        }
    }

    /**
     * 判断文本中时候包含$
     * @param text 文本
     * @return 包含返回true,不包含返回false
     */
    public static boolean checkText(String text){
        boolean check  =  false;
        if(text.indexOf("$")!= -1){
            check = true;
        }
        return check;

    }

    /**
     * 匹配传入信息集合与模板
     * @param value 模板需要替换的区域
     * @param textMap 传入信息集合
     * @return 模板需要替换区域信息集合对应值
     */
    public static String changeValue(XWPFRun run,String value, Map<String, String> textMap){
        Set<Entry<String, String>> textSets = textMap.entrySet();
        for (Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${"+textSet.getKey()+"}";
            if(value.indexOf(key)!= -1){
                value = textSet.getValue() == null ? "" : textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if(checkText(value)){
            value = "";
        }
        //判断是否需要换行
        if(value.contains("\r\n")){
            String[] lines = value.split("\r\n");
            for(int i= 1;i<lines.length;i++){
                run.addBreak();
                run.setText(lines[i],i);
            }
            value = lines[0];
        }
        return value;
    }

    // word跨列合并单元格
    public  static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if ( cellIndex == fromCell ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }
    // word跨行并单元格
    public static void mergeCellsVertically(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if ( rowIndex == fromRow ) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }




    public static void main(String[] args) {
        //模板文件地址
        String inputUrl = "E:/001.docx";
        //新生产的模板文件
        String outputUrl = "E:/1.docx";

        Map<String, String> testMap = new HashMap<String, String>();
        testMap.put("name", "小明");
        testMap.put("email", "11@aa.com");
        testMap.put("address", "软件园");
        testMap.put("phone", "88888888");

        List<String[]> testList = new ArrayList<String[]>();
        testList.add(new String[]{"1","1AA","1BB","1CC"});
        testList.add(new String[]{"2","2AA","2BB","2CC"});
        testList.add(new String[]{"3","3AA","3BB","3CC"});
        testList.add(new String[]{"4","4AA","4BB","4CC"});

        WordUtils.changWord(inputUrl, outputUrl, testMap, testList);
    }

}
