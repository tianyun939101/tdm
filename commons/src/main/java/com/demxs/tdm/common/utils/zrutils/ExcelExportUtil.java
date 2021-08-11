package com.demxs.tdm.common.utils.zrutils;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.utils.IdGen;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelExportUtil {

	public static String getExportFilePath(String excel_name, List dataList, Map<String,String> columnNames)
			throws Exception {
		// 创建新的Excel 工作簿
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 在Excel工作簿中建一工作表，其名为缺省值
		// 如要新建一名为"效益指标"的工作表，其语句为：
		// HSSFSheet sheet = workbook.createSheet("效益指标");
		HSSFSheet sheet = workbook.createSheet();
//---------------add--------- start1---------------------------------------------------------------------------------------
		// 设置字体
		HSSFFont font = workbook.createFont();
		font.setFontName("黑体");
		font.setFontHeightInPoints((short) 10);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗

		HSSFFont headfont = workbook.createFont();
		headfont.setFontName("黑体");
		headfont.setFontHeightInPoints((short) 22);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗

		// 另一个样式
		HSSFCellStyle headstyle = workbook.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		headstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		headstyle.setLocked(true);
		headstyle.setWrapText(true);// 自动换行

		// 另一个样式
		HSSFCellStyle centerstyle = workbook.createCellStyle();
		centerstyle.setFont(font);
		//centerstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		centerstyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		centerstyle.setWrapText(false);
		centerstyle.setLeftBorderColor(HSSFColor.BLACK.index);
		centerstyle.setBorderLeft((short) 1);
		centerstyle.setRightBorderColor(HSSFColor.BLACK.index);
		centerstyle.setBorderRight((short) 1);
		//centerstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 设置单元格的边框为粗体
		centerstyle.setBottomBorderColor(HSSFColor.BLACK.index); // 设置单元格的边框颜色．
		centerstyle.setFillForegroundColor(HSSFColor.WHITE.index);// 设置单元格的背景颜色．

		// 在索引0的位置创建行（最顶端的行）
		HSSFRow row = sheet.createRow(0);
		// ===============================================================

		int i=0;
		for (String mapkey : columnNames.keySet()) {
			String str = columnNames.get(mapkey);//得到每个key多对用value的值
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(str);
			cell.setCellStyle(centerstyle);
			i++;
		}

		for (int n = 0; n < dataList.size(); n++) {
			// 在索引1的位置创建行（最顶端的行）
			HSSFRow row_value = sheet.createRow(n + 1);
			JsonMapper jsonMapper = JsonMapper.getInstance();
			String entitystr = jsonMapper.toJson(dataList.get(n));
			Map<String,Object> maps = jsonMapper.fromJson(entitystr,Map.class);
			int j=0;
			for (String mapkey : columnNames.keySet()) {
				// 在索引0的位置创建单元格（左上端）
				HSSFCell cell = row_value.createCell(j);
				// 定义单元格为字符串类型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				// 在单元格中输入一些内容
				cell.setCellValue(objToString(maps.get(mapkey)));
				j++;
			}
		}
		String root = Global.getConfig("createFilePath");
		if (root.equals("")){
			root = "D:\\tdm\\";
		}
		String filename = root+""+IdGen.uuid()+".xls";
		FileOutputStream fileOut = new FileOutputStream(filename);
		workbook.write(fileOut);
		fileOut.close();
		return filename;
	}

	private static String objToString(Object obj) {
		if (obj == null) {
			return "";
		} else {
			if (obj instanceof String) {
				return (String) obj;
			} else if (obj instanceof Date) {
				//return dateToString();
				SimpleDateFormat stf=new SimpleDateFormat("yyyy-MM-dd");
				String str=stf.format(obj);
				return str;
			} else {
				return obj.toString();
			}
		}
	}

	public static void main(String[] args){
		Map columnNames = new LinkedHashMap<String,String>();
		columnNames.put("name","报告名称");
		columnNames.put("dizhi","报告地址");

		List baogaoList = new ArrayList();
		ExcelExportTest b1 = new ExcelExportTest("地点","cc");
		ExcelExportTest b2 = new ExcelExportTest("22","11");
		baogaoList.add(b1);
		baogaoList.add(b2);
		try{
			ExcelExportUtil.getExportFilePath("hehe",baogaoList,columnNames);
		}
		catch (Exception exp){
			exp.printStackTrace();
		}

	}
}

