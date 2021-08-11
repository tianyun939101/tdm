package com.demxs.tdm.common.utils.zrutils;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;

import java.io.FileOutputStream;
public class PoiExcelUtil{
	

//	设置合并单元格的边框样式
	public static void setRegionStyle(XSSFSheet sheet, CellRangeAddress ca,CellStyle style) { 
        for (int i = ca.getFirstRow(); i <= ca.getLastRow(); i++) { 
       XSSFRow row = sheet.getRow(i); 
            for (int j = ca.getFirstColumn(); j <= ca.getLastColumn(); j++) { 
               XSSFCell cell = row.getCell(j);
                cell.setCellStyle(style); 
            } 

        } 

    } 
	/*
	 * 设置字体
	 */
	 public static Font createFont(XSSFWorkbook wb,short boldweight,short color,short size){
	        Font font=wb.createFont();
	        font.setBoldweight(boldweight);
	        font.setColor(color);
	        font.setFontHeightInPoints(size);
	        return font;

	    }
	 
	  /* 功能：创建HSSFSheet工作簿
	       * @param   wb  HSSFWorkbook
	       * @param   sheetName   String
	       * @return  HSSFSheet
	       */
	      public static XSSFSheet createSheet(XSSFWorkbook wb,String sheetName){
	       XSSFSheet sheet=wb.createSheet(sheetName);
	          sheet.setDefaultColumnWidth(12);
	          
	          sheet.setDisplayGridlines(true);
	          return sheet;
	      }

	 /**
	      * 功能：创建HSSFRow
	      * @param   sheet   HSSFSheet
	      * @param   rowNum  int
	      * @param   height  int
	      * @return  HSSFRow
	      */
	     public static XSSFRow createRow(XSSFSheet sheet,int rowNum,int height){
	       XSSFRow row=sheet.createRow(rowNum);
	         row.setHeight((short)height);
	         return row;
	     }
	     public static XSSFCell createCell (XSSFRow row,int cellNum,CellStyle style){
	    	         XSSFCell cell=row.createCell(cellNum);
	    	         cell.setCellStyle(style);
	    	         return cell;
	    	     }
	 /**
	      * 功能：创建CellStyle样式
	      * @param   backgroundColor 背景色
	      * @param   foregroundColor 前置色
	      * @param   font            字体
	      * @return  CellStyle
	      */

	 public static CellStyle createCellStyle(XSSFWorkbook wb,short backgroundColor,short foregroundColor,short halign,Font font){
		         CellStyle cs=wb.createCellStyle();
		         cs.setAlignment(halign);
		         cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		         cs.setFillBackgroundColor(backgroundColor);
		         cs.setFillForegroundColor(foregroundColor);
		         cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		         cs.setFont(font);
		         return cs;
		     }
		     /**
		      * 功能：创建带边框的CellStyle样式
		      * @param   wb              HSSFWorkbook   
		      * @param   backgroundColor 背景色
		      * @param   foregroundColor 前置色
		      * @param   font            字体
		      * @return  CellStyle
		      */
		     public static CellStyle createBorderCellStyle(XSSFWorkbook wb,short halign,short backgroundColor,short foregroundColor,Font font){
		         CellStyle cs=wb.createCellStyle();
		         cs.setAlignment(halign);
		         cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		         cs.setFillBackgroundColor(backgroundColor);
		         cs.setFillForegroundColor(foregroundColor);
		         cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		         cs.setFont(font);
		         cs.setBorderLeft(CellStyle.BORDER_THIN);
		         cs.setBorderRight(CellStyle.BORDER_THIN);
		         cs.setBorderTop(CellStyle.BORDER_THIN);
		         cs.setBorderBottom(CellStyle.BORDER_THIN); 
		         return cs;
		     }
		     /**
		          * 功能：合并单元格
		          * @param   sheet       HSSFSheet
		          * @param   firstRow    int
		          * @param   lastRow     int
		          * @param   firstColumn int
		          * @param   lastColumn  int
		          * @return  int         合并区域号码
		          */
		         public static int mergeCell(XSSFSheet sheet,int firstRow,int lastRow,int firstColumn,int lastColumn){
		             return sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,firstColumn,lastColumn));   
		         }

                 //给定义名称赋值
		         public static void setNameValue(String filePath,String[] cellNames,String[] cellValues){
					 try {
					 	 FileInputStream file= new FileInputStream(filePath);
						 /*POIFSFileSystem ts= new POIFSFileSystem(file);
						 HSSFWorkbook wb=new HSSFWorkbook(ts);*/

						 Workbook wb = null;
						 try {
							 wb = new XSSFWorkbook(file);
						 } catch (Exception ex) {
							 POIFSFileSystem ts= new POIFSFileSystem(file);
							 wb = new HSSFWorkbook(ts);
						 }

						 for (int ii = 0; ii <cellNames.length ; ii++) {
						 	 int namedCellIdx = wb.getNameIndex(cellNames[ii]);
							 Name aNamedCell = wb.getNameAt(namedCellIdx);
							 // Retrieve the cell at the named range and test its contents
							 // Will get back one AreaReference for C10, and
							 //  another for D12 to D14
							 AreaReference[] arefs = AreaReference.generateContiguous(aNamedCell.getRefersToFormula());
							 for (int i=0; i<arefs.length; i++) {
								 // Only get the corners of the Area
								 // (use arefs[i].getAllReferencedCells() to get all cells)
								 CellReference[] crefs = arefs[i].getAllReferencedCells();//.getCells();
								 for (int j=0; j<crefs.length; j++) {
									 // Check it turns into real stuff
									 Sheet s = wb.getSheet(crefs[j].getSheetName());
									 Row r = s.getRow(crefs[j].getRow());
									 Cell c = r.getCell(crefs[j].getCol());//如果单元格为一个空单元格无法赋值
									 // Do something with this corner cell
									 if(c!=null){
										 c.setCellValue(cellValues[ii]);
									 }
								 }
							 }
						 }
						 FileOutputStream fos = new FileOutputStream(filePath);
						 wb.write(fos);
						 fos.close();
					 } catch (Exception e) {
						 e.printStackTrace();
					 }
					 System.out.println("ok");
				 }

				 public static void main(String[] args){
		         	String[] cellNames = new String[1];
					 cellNames[0] = "user.name";
					 String[] cellValues = new String[1];
					 cellValues[0] = "吴列鹏";
		         	PoiExcelUtil.setNameValue("d://uuuu.xlsx",cellNames,cellValues);
				 }
}
 