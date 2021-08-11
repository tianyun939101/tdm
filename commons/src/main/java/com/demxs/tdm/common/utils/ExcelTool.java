package com.demxs.tdm.common.utils;



public class ExcelTool {
	/*public ActiveXComponent applicationExcel = null;
	private String filePath = "";
	public Dispatch workBooks;
	public Dispatch workBook;
	public Dispatch currentSheet;


    public ExcelTool(String _filePath,int sheetIndex)
	{
		filePath = _filePath;
		applicationExcel = new ActiveXComponent("Excel.Application");
		workBooks = applicationExcel.getProperty("Workbooks").toDispatch();
		workBook = Dispatch.invoke(workBooks, "Open", Dispatch.Method, new Object[]{filePath,new Variant(true)},new int[1]).toDispatch();
		currentSheet = Dispatch.call(workBook, "Sheets",sheetIndex).toDispatch();
	}

	public String GetCellText(int rowIndex,int columnIndex)
	{
		try{
			Dispatch rng = Dispatch.call(currentSheet,"Cells",rowIndex,columnIndex).toDispatch();
			return Dispatch.call(rng,"Text").toString();
		}
		catch(Exception exp)
		{
			Close();
			return "";
		}
	}

	public void MergeCell(String begin,String end)
	{
		try{
			Dispatch rng = Dispatch.call(currentSheet, "Range",begin,end).toDispatch();
			Dispatch.call(rng,"Merge");
		}
		catch(Exception exp)
		{
			Close();
		}
	}

    public String GetCells(int sheetIndex,int rowIndex,int columnIndex)
	{
    	Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetIndex).toDispatch();
		Dispatch rng = Dispatch.call(workSheet,"Cells",rowIndex,columnIndex).toDispatch();
		return Dispatch.call(rng,"Text").toString();
	}

    public String GetCells(String sheetName,int rowIndex,int columnIndex)
	{
    	Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetName).toDispatch();
		Dispatch rng = Dispatch.call(workSheet,"Cells",rowIndex,columnIndex).toDispatch();
		return Dispatch.call(rng,"Text").toString();
	}

    *//**
     * �жϵ�ǰ��Ԫ���ɼ���Ԫ���кϲ����
     * @param sheetIndex
     * @param rowIndex
     * @param columnIndex
     * @return
     *//*
    public Integer GetCellMergeRows(int sheetIndex,int rowIndex,int columnIndex)
	{
    	Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetIndex).toDispatch();
    	//TANGER_OCX.ActiveDocument.Sheets("Sheet1").Range("B1").MergeArea.Rows.Count
		try{
			Dispatch cell = Dispatch.call(workSheet,"Cells",rowIndex,columnIndex).toDispatch();
			Dispatch mergeArea = Dispatch.call(cell,"MergeArea").toDispatch();
			Dispatch Rows = Dispatch.call(mergeArea,"Rows").toDispatch();
			String rows = Dispatch.call(Rows, "Count").toString();
			return Integer.parseInt(rows);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}

    public Integer GetCellMergeRows(String sheetName,int rowIndex,int columnIndex)
	{
    	Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetName).toDispatch();
		try{
			Dispatch cell = Dispatch.call(workSheet,"Cells",rowIndex,columnIndex).toDispatch();
			Dispatch mergeArea = Dispatch.call(cell,"MergeArea").toDispatch();
			Dispatch Rows = Dispatch.call(mergeArea,"Rows").toDispatch();
			String rows = Dispatch.call(Rows, "Count").toString();
			return Integer.parseInt(rows);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

    public void InsertRows(int sheetIndex,int rowIndex, int count)
    {
    	Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetIndex).toDispatch();
    	Dispatch range = Dispatch.call(workSheet,"Rows",rowIndex).toDispatch();

    	for (int i = 0; i < count; i++)
        {
    		Dispatch.call(range,"Insert",-4121);
        }
    }

    public void CopyRows(String sFile, int sSheetIndex, int sBeginRow, int  sRowCount, String tFile, int tSheetIndex, int tBeginRow)
    {
    	applicationExcel = new ActiveXComponent("Excel.Application");
    	workBooks = applicationExcel.getProperty("Workbooks").toDispatch();
    	Dispatch workBook1 = Dispatch.invoke(workBooks, "Open", Dispatch.Method, new Object[]{sFile,new Variant(true)},new int[1]).toDispatch();
    	Dispatch workBook2 = Dispatch.invoke(workBooks, "Open", Dispatch.Method, new Object[]{tFile,new Variant(true)},new int[1]).toDispatch();
		Dispatch sheet1 = Dispatch.call(workBook1, "Sheets",sSheetIndex).toDispatch();
		Dispatch sheet2 = Dispatch.call(workBook2, "Sheets",tSheetIndex).toDispatch();

		for(int i=0;i<sRowCount;i++)
		{
			Dispatch range1 = Dispatch.call(sheet1, "Rows",sBeginRow+i).toDispatch();
			Dispatch range2 = Dispatch.call(sheet2, "Rows",tBeginRow+i).toDispatch();
			Dispatch.call(range1, "Copy",range2);
		}

        Dispatch.call(workBook2, "Save");
        Variant f = new Variant(false);
		Dispatch.call(workBook1, "Close", f);
		Dispatch.call(workBook2, "Close", f);
		applicationExcel.invoke("Quit");
		ComThread.Release();
    }

    public void CopyRange(String sFile, int sSheetIndex, String sBegin, String  sEnd, String tFile, int tSheetIndex, String tBegin)
    {
    	applicationExcel = new ActiveXComponent("Excel.Application");
    	workBooks = applicationExcel.getProperty("Workbooks").toDispatch();
    	Dispatch workBook1 = null;
    	Dispatch workBook2 = null;
		Dispatch sheet1 = null;
		Dispatch sheet2 = null;
    	try{
    	    workBook1 = Dispatch.invoke(workBooks, "Open", Dispatch.Method, new Object[]{sFile,new Variant(true)},new int[1]).toDispatch();
        	workBook2 = Dispatch.invoke(workBooks, "Open", Dispatch.Method, new Object[]{tFile,new Variant(true)},new int[1]).toDispatch();
    		sheet1 = Dispatch.call(workBook1, "Sheets",sSheetIndex).toDispatch();
    	    sheet2 = Dispatch.call(workBook2, "Sheets",tSheetIndex).toDispatch();

    		int sBeginx = GetRowIndex(sBegin);
    		int sBeginy = GetColumnIndex(sBegin);
    		int sEndx = GetRowIndex(sEnd);
    		int sEndy = GetColumnIndex(sEnd);
    		int tBeginx = GetRowIndex(tBegin);
    		int tBeginy = GetColumnIndex(tBegin);

    		for (int i=0;i<=sEndx-sBeginx;i++)
    		{
    			for (int j=0;j<=sEndy-sBeginy;j++)
    			{
    				Dispatch range1 = Dispatch.call(sheet1, "Cells",sBeginx+i,sBeginy+j).toDispatch();
    				Dispatch range2 = Dispatch.call(sheet2, "Cells",tBeginx+i,tBeginy+j).toDispatch();
    				Dispatch.call(range1, "Copy",range2);
    			}
    		}

            Dispatch.call(workBook2, "Save");
            Variant f = new Variant(false);
    		Dispatch.call(workBook1, "Close", f);
    		Dispatch.call(workBook2, "Close", f);
    		applicationExcel.invoke("Quit");
    	}
    	catch(Exception exp){
    		Dispatch.call(workBook2, "Save");
            Variant f = new Variant(false);
    		Dispatch.call(workBook1, "Close", f);
    		Dispatch.call(workBook2, "Close", f);
    		applicationExcel.invoke("Quit");
    	}
    }
    public int GetRowCount(String sheetName)
    {
    	Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetName).toDispatch();
    	Dispatch usedRange = Dispatch.call(workSheet, "UsedRange").toDispatch();
    	Dispatch cells = Dispatch.call(usedRange, "Cells").toDispatch();
    	Dispatch rows = Dispatch.call(cells, "Rows").toDispatch();
    	return Integer.parseInt(Dispatch.call(rows, "Count").toString());
    }

	public int GetRowCount()
	{
		//int n=xsheet.UsedRange.Cells.Rows.Count;
		Dispatch usedRange = Dispatch.call(currentSheet, "UsedRange").toDispatch();
		Dispatch cells = Dispatch.call(usedRange, "Cells").toDispatch();
		Dispatch rows = Dispatch.call(cells, "Rows").toDispatch();
		return Integer.parseInt(Dispatch.call(rows, "Count").toString());
	}

    public int GetColumnCount()
    {
    	//int n=xsheet.UsedRange.Cells.Rows.Count;
    	Dispatch usedRange = Dispatch.call(currentSheet, "UsedRange").toDispatch();
    	Dispatch cells = Dispatch.call(usedRange, "Cells").toDispatch();
    	Dispatch rows = Dispatch.call(cells, "Columns").toDispatch();
    	return Integer.parseInt(Dispatch.call(rows, "Count").toString());
    }

	public int GetColumnCount(String sheetname)
	{
		Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetname).toDispatch();
		Dispatch usedRange = Dispatch.call(workSheet, "UsedRange").toDispatch();
		Dispatch cells = Dispatch.call(usedRange, "Cells").toDispatch();
		Dispatch rows = Dispatch.call(cells, "Columns").toDispatch();
		return Integer.parseInt(Dispatch.call(rows, "Count").toString());
	}

    public int GetRowIndex(String address)
    {
    	String rowValue = "";
        char[] sb = address.toCharArray();

		for (int i=0;i<sb.length;i++)
		{
			if (Character.isDigit(sb[i]))
			{
				rowValue+=sb[i];
			}
		}
    	return Integer.parseInt(rowValue);
    }

    public int GetColumnIndex(String address)
    {
    	String rowValue = "";
        char[] sb = address.toCharArray();

		for (int i=0;i<sb.length;i++)
		{
			if (Character.isLetter(sb[i]))
			{
				rowValue+=sb[i];
			}
		}
    	return LetterToInt(rowValue);
    }

    public void SetCells(int rowIndex, int columnIndex, String text)
    {
    	Dispatch rng = Dispatch.call(currentSheet,"Cells",rowIndex,columnIndex).toDispatch();
    	Dispatch.put(rng,"Value2",text);
    }


    //��ָ��sheet��ֵ
    public void SetCells(int sheetIndex,int rowIndex, int columnIndex, String text)
    {
    	Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetIndex).toDispatch();
    	Dispatch rng = Dispatch.call(workSheet,"Cells",rowIndex,columnIndex).toDispatch();
    	Dispatch.put(rng,"Value2",text);
    }
    *//**
     *  ����µĹ����(sheet)������Ӻ�ΪĬ��Ϊ��ǰ����Ĺ���?
     *//*
    public Dispatch addSheet() {
        return Dispatch.get(Dispatch.get(workBook, "sheets").toDispatch(), "add").toDispatch();
    }

    public List getSheetNames(){
    	List lists = new ArrayList();
    	int count = Dispatch.get(Dispatch.get(workBook, "sheets").toDispatch(), "count").toInt();
		for(int i=1;i<=count;i++){
			Dispatch workSheet = Dispatch.call(workBook, "Sheets",i).toDispatch();
			String name = Dispatch.get(workSheet,"Name").toString();
			lists.add(name);
		}
		return lists;
	}

    *//**
     * �õ���ǰsheet
     * @return
     *//*
    public Dispatch getCurrentSheet() {
        currentSheet = Dispatch.get(workBook, "ActiveSheet").toDispatch();
        return currentSheet;
    }
    public int LetterToInt(String letter)
    {
        int n = 0;
        if (letter.length() >= 2)
        {
            char c1 = letter.toCharArray()[0];
            char c2 = letter.toCharArray()[1];

            c1 = Character.toUpperCase(c1);
            c2 = Character.toUpperCase(c2);

            int i = Integer.parseInt(String.valueOf(c1)) - 64;
            int j = Integer.parseInt(String.valueOf(c2)) - 64;

            n = i * 26 + j;
        }

        if (letter.length() == 1)
        {
            char c1 = letter.toCharArray()[0];

            c1 = Character.toUpperCase(c1);

            n = (int)c1-64;
        }

        return n;
    }

    public void CopyRanges(String startRange,String endRange,String text,String targetRange)
    {
    	Dispatch rng1 = Dispatch.call(currentSheet,"Range",startRange,endRange).toDispatch();
    	Dispatch rng2 = Dispatch.call(currentSheet,"Range",targetRange).toDispatch();
    	Dispatch.call(rng1, "Copy",rng2);
    }

	public void Save()
	{
		Variant f = new Variant(false);
    	Dispatch.call(workBook, "Save");
		Dispatch.call(workBook, "Close", f);
		applicationExcel.invoke("Quit");
		ComThread.Release();
	}

	public void SaveFile()
	{
		Save();
	}

    public void SaveAsFile(String path, String format)
    {
    	Variant f = new Variant(false);
		Dispatch.call(workBook, "SaveAs",path);
		Dispatch.call(workBook, "Close", f);
		applicationExcel.invoke("Quit");
		ComThread.Release();
    }

    public void Close()
    {
    	Variant f = new Variant(false);
		Dispatch.call(workBook, "Close", f);
		applicationExcel.invoke("Quit");
		ComThread.Release();
    }
    *//**
     * �ͷ���Դ
     *//*
    public void releaseSource(){
        if(applicationExcel!=null){
        	applicationExcel.invoke("Quit", new Variant[] {});
        	applicationExcel = null;
        }
        workBooks = null;
        ComThread.Release();
        System.gc();
    }
    *//**
     * �޸ĵ�ǰ����������
     * @param newName
     *//*
    public void modifyCurrentSheetName(String newName) {
        Dispatch.put(getCurrentSheet(), "name", newName);
    }
    *//**
     *
     *��������:ɾ��sheet
     *@author: wangxd
     *@date: 2014-7-11����09:34:59
     *@param index
     *//*
    public void DellSheet(int index){
		Dispatch workSheet = Dispatch.call(workBook, "Sheets",index).toDispatch();
		Dispatch.call(workSheet, "Delete");
	}
    *//**
     *
     *��������:�ж�sheet�Ƿ����
     *@author: wangxd
     *@date: 2014-7-11����10:30:00
     *@param index
     *@return
     *//*
    public boolean sfcunzaiSheet(int index){
    	boolean flag = true;
    	try {
    		Dispatch workSheet = Dispatch.call(workBook, "Sheets",index).toDispatch();
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
    }
    *//**
     *
     *��������:���sheet
     *@author: wangxd
     *@date: 2014-7-11����02:49:25
     *@param sheetIndex
     *//*
    public void InsertSheet(int sheetIndex)
    {
    	Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetIndex).toDispatch();
    	Dispatch.call(Dispatch.get(workBook, "sheets").toDispatch(), "add",workSheet).toDispatch();
    }
    *//**
     *
     *��������:�޸�ĳ��sheet�����
     *@author: wangxd
     *@date: 2014-7-14����04:25:41
     *@param sheetIndex
     *@param newName
     *//*
    public void editSheetName(int sheetIndex,String newName){
    	 Dispatch workSheet = Dispatch.call(workBook, "Sheets",sheetIndex).toDispatch();
    	 Dispatch.put(workSheet, "name", newName);
    }

    *//**
     *  �õ�sheets�ļ��϶���
     * @return
     *//*
    private Dispatch getSheets() {
        if(currentSheet==null)
        	currentSheet = Dispatch.get(currentSheet, "sheets").toDispatch();
        return currentSheet;
    }
    *//**
     *
     *��������:��������
     *@author: wangxd
     *@date: 2014-7-21����11:33:09
     *@param sSheetIndex
     *@param startRange
     *@param endRange
     *@param tSheetIndex
     *//*
    public void CopyRanges1(String sFile,int sSheetIndex,String startRange,String endRange,String tFile,int tSheetIndex)
    {
    	Dispatch workBook1 = Dispatch.invoke(workBooks, "Open", Dispatch.Method, new Object[]{sFile,new Variant(true)},new int[1]).toDispatch();
    	Dispatch workBook2 = Dispatch.invoke(workBooks, "Open", Dispatch.Method, new Object[]{tFile,new Variant(true)},new int[1]).toDispatch();

    	Dispatch workSheet1 = Dispatch.call(workBook1, "Sheets",sSheetIndex).toDispatch();
    	Dispatch workSheet2 = Dispatch.call(workBook2, "Sheets",tSheetIndex).toDispatch();

    	Dispatch rng1 = Dispatch.call(workSheet1,"Range",startRange,endRange).toDispatch();
    	Dispatch rng2 = Dispatch.call(workSheet2,"Range",startRange,endRange).toDispatch();
    	Dispatch.call(rng1, "Copy",rng2);

    	Dispatch.call(workBook2, "Save");
        Variant f = new Variant(false);
 		Dispatch.call(workBook1, "Close", f);
 		Dispatch.call(workBook2, "Close", f);
 		applicationExcel.invoke("Quit");
 		ComThread.Release();

    }

    public void SetCellColor(int rowIndex,int columnIndex,String colorindex){
    	Dispatch rng = Dispatch.call(currentSheet,"Cells",rowIndex,columnIndex).toDispatch();
    	Dispatch interior = Dispatch.get(rng, "Interior").toDispatch();
    	Dispatch.put(interior, "ColorIndex", colorindex);
    }

    public static void main(String[] args) {
    	ExcelTool et = new ExcelTool("D:\\三点弯曲测试.xls",1);
    	//et.SetCellColor(1, 1, "4");
		List lists = et.getSheetNames();
    	et.Save();//System.out.println("=============");
	}*/

}
