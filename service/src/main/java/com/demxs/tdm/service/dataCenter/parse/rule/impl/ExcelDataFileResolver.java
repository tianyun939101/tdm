package com.demxs.tdm.service.dataCenter.parse.rule.impl;

import com.demxs.tdm.domain.dataCenter.parse.DataParseRule;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelDataFileResolver extends AbstractDataFileResolver {
    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    @Override
    protected List<String> loadDataFile(File file, DataParseRule parseRule) throws Exception {
        return loadExcel(file.getName(),new FileInputStream(file),0,parseRule);

    }

    private List<String> loadExcel(String fileName, InputStream is, int sheetIndex, DataParseRule parseRule)throws InvalidFormatException, IOException {
        if (StringUtils.isBlank(fileName)){
            throw new RuntimeException("导入文档为空!");
        }else if(fileName.toLowerCase().endsWith("xls")){
            this.wb = new HSSFWorkbook(is);
        }else if(fileName.toLowerCase().endsWith("xlsx")){
            this.wb = new XSSFWorkbook(is);
        }else{
            throw new RuntimeException("文档格式不正确!");
        }
        if (this.wb.getNumberOfSheets()<sheetIndex){
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = this.wb.getSheetAt(sheetIndex);
        return parseExcel(parseRule);
    }

    private List<String> parseExcel(DataParseRule parseRule){
        List<String> dataLines = new ArrayList<>();
        int rowNum = this.getLastDataRowNum();
        for (int i=0;i<rowNum;i++){
            StringBuilder line = new StringBuilder();
            Row row = this.getRow(i);
            if(row == null){
                dataLines.add(line.toString());
                continue;
            }
            int cellNum = getLastCellNum(i);
            for (int j=0;j<cellNum;j++){
                String cellValue = getCellValue(row, j).toString();
                line.append(cellValue).append(",");
            }
            dataLines.add(line.toString());
        }
        return dataLines;
    }

    /**
     * 获取行对象
     * @param rownum
     * @return
     */
    private Row getRow(int rownum){
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取最后一个数据行号
     * @return
     */
    private int getLastDataRowNum(){
        return this.sheet.getLastRowNum();
    }

    /**
     * 获取最后一个列号
     * @return
     */
    private int getLastCellNum(int rowNum){
        return this.getRow(rowNum).getLastCellNum();
    }

    /**
     * 获取单元格值
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    private Object getCellValue(Row row, int column){
        Object val = "";
        try{
            Cell cell = row.getCell(column);
            if (cell != null){
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    val = cell.getNumericCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
                    val = cell.getStringCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
                    val = cell.getCellFormula();
                }else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
                    val = cell.getBooleanCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
                    val = cell.getErrorCellValue();
                }
            }
        }catch (Exception e) {
            return val;
        }
        return val;
    }
}
