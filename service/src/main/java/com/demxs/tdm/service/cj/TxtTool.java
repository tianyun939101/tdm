package com.demxs.tdm.service.cj;

import com.demxs.tdm.domain.cj.Excelcellzb;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtTool {
	private String filepath;
	private String content;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		TxtTool tt = new TxtTool("D:\\caijitest.txt");
		System.out.print(tt.GetCellText(29,2,"	"));
	}
	
	public TxtTool(String _filepath){
		try {
			filepath = _filepath;
			InputStreamReader read = new InputStreamReader(new FileInputStream(filepath),"gbk");
			BufferedReader input = new BufferedReader(read);
		    content = "";
			String s = "";
			while((s = input.readLine())!=null){ 
				content+=s+"\n";
			}
			if (content.length()>0){
				content = content.substring(0,content.length()-1);
			}
			input.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public String GetCellText(int rowIndex, int columnIndex, String fengef)
	{
		if (rowIndex<1||columnIndex<1){
			return "";
		}
		if (fengef==null||fengef.equals("")){
			fengef = "	";
		}
		String[] lineString = content.split("\n");
		if (rowIndex<=lineString.length){
			String curString = lineString[rowIndex-1];
			String curfengef = getCurFengef(curString,fengef);
			String[] cell = curString.split(curfengef);
			if (columnIndex<=cell.length){
				return cell[columnIndex-1];
			}
			else{
				return "";
			}
		}
		else{
			return "";
		}
	}

	public String getCurFengef(String curString, String fengef){
		for(int i=0;i<fengef.length();i++){
			String temp = fengef.substring(i,i+1);
			if (curString.indexOf(temp)>=0){
				return temp;
			}
		}
		return "";
	}

	/**
	 *
	 * @param fengef
	 * @param maxRowSize
	 * @return
	 */
	public Map<String,Excelcellzb> getExcelcellzb(String fengef, int maxRowSize){
		Map<String,Excelcellzb> celllists = new HashMap<String,Excelcellzb>();
		if (fengef==null||fengef.equals("")){
			fengef = "	";
		}
		String[] lineString = content.split("\n");
		for(int i=1;i<=lineString.length;i++){
			String curString = lineString[i-1];
			String curfengef = getCurFengef(curString,fengef);
			String[] cell = curString.split(curfengef);
			for(int j=1;j<=cell.length;j++){
				if (cell[j-1]!=null&&!cell[j-1].equals("")){
					Excelcellzb excelcellzb = new Excelcellzb();
					excelcellzb.setRowindex(i);
					excelcellzb.setColumnindex(j);
					celllists.put(cell[j-1],excelcellzb);
				}
			}
			if (maxRowSize!=-1){
				if (i>=maxRowSize){
					break;
				}
			}
		}
		return celllists;
	}

	public int getTargetRow(String mark){
		String[] lineString = content.split("\n");
		for(int i=1;i<=lineString.length;i++) {
			String curString = lineString[i - 1];
			if (mark.equals(curString)){
				return i;
			}
		}
		return -1;
	}

	public List<String> getFields(){
		int endrow = getTargetRow("Measurement n?1");
    	List<String> lists = new ArrayList<String>();
		String[] lineString = content.split("\n");
		for(int i=5;i<endrow-1;i++) {
			String curString = lineString[i].replace("'","");
			lists.add(curString);
		}
		return lists;
	}
}
