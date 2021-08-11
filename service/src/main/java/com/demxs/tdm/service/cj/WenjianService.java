package com.demxs.tdm.service.cj;

import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.dao.cj.CjTableDao;
import com.demxs.tdm.dao.cj.WenjianDao;
import com.demxs.tdm.dao.sys.SysAttachmentDao;
import com.demxs.tdm.domain.cj.*;
import com.demxs.tdm.service.sys.SysAttachmentService;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.*;
import com.demxs.tdm.domain.cj.*;
import com.demxs.tdm.domain.sys.SysAttachment;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 采集文件表Service
 * @author 张仁
 * @version 2017-08-21
 */
@Service
@Transactional(readOnly = true)
public class WenjianService extends CrudService<WenjianDao, Wenjian> {
	@Autowired
	private SysAttachmentService sysAttachmentService;
	@Autowired
	private SysAttachmentDao sysAttachmentDao;
	@Autowired
	private GuizeService guizeService ;
	@Autowired
	private GuizetableService guizetableService;
	@Autowired
	private CjTableService cjTableService;
	@Autowired
	private CjZiduanService cjZiduanService;
	@Autowired
	private GuizeitemService guizeitemService;
	@Autowired
	private WenjianDao wenjianDao;
	@Autowired
	private CjTableDao cjTableDao;
	@Autowired
	private CjIndexService cjIndexService;
	@Autowired
//	private IShiyanrwService shiyanrwService;

	@Override
	public Wenjian get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Wenjian> findList(Wenjian wenjian) {
		return super.findList(wenjian);
	}
	
	@Override
	public Page<Wenjian> findPage(Page<Wenjian> page, Wenjian wenjian) {
		return super.findPage(page, wenjian);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Wenjian wenjian) {
		super.save(wenjian);
	}

	@Transactional(readOnly = false)
	public void save(String fileid, Wenjian wenjian) {
		SysAttachment search = new SysAttachment();
		search.setCodeId(fileid);
		List<SysAttachment> sysAttachmentList = sysAttachmentService.findList(search);
		for (int i=0;i<sysAttachmentList.size();i++){
			Wenjian curwenjian = new Wenjian();
			curwenjian.setGuizeid(wenjian.getGuizeid());
			curwenjian.setIsxuyaocj(wenjian.getIsxuyaocj());
			curwenjian.setRemarks(wenjian.getRemarks());
			curwenjian.setWenjianid(sysAttachmentList.get(i).getId());
			curwenjian.setIscaiji("0");
			super.save(curwenjian);
		}
	}

	/**
	 * 保存手动采集文件
	 * */
//	@Override
	public void shoudongcj(String renwubh, String yangpindbh, String zyAttachFilePath, String shebeiid , String shebeimc, String fangfaid, String fangfamc){
		//0、获取文件扩展名。
		String extName = zyAttachFilePath.substring(zyAttachFilePath.lastIndexOf("."));
		//1、创建文件夹
		String uploadDir = "upload" + File.separatorChar + "attachment" + File.separatorChar;
		//构建年月日路径
		String autoCreatedDateDirByParttern = "yyyy" + File.separatorChar + "MM" + File.separatorChar + "dd"
				+ File.separatorChar;
		String autoCreatedDateDir = DateFormatUtils.format(new java.util.Date(), autoCreatedDateDirByParttern);
		//最后一级由任务编号加方法名称
		String renwudir = renwubh+"_"+fangfamc+ File.separatorChar;
		String filePath = Global.getUserfilesBaseDir()+"attachment"+ File.separatorChar+uploadDir+ autoCreatedDateDir + renwudir;
		filePath = FileUtils.path(filePath);
		//创建文件夹
		FileUtils.createDirectory(filePath);
		//文件名
		String fileName = yangpindbh+"_"+fangfamc+extName;

		String fullPath = "";
		fullPath = filePath+fullPath+fileName;

		String attachPath = uploadDir+ autoCreatedDateDir + renwudir+fileName;

		//2、将资源附件拷贝到系统附件存储位置，并且文件名称用样品编号_方法名称命名
		FileUtils.copyFileCover(zyAttachFilePath,fullPath,true);

		//3、保存到系统附件表
		SysAttachment attachment = new SysAttachment();
		attachment.setCodeId(IdGen.uuid());
		attachment.setFilePath(attachPath);
		attachment.setFileName(fileName);
		attachment.setColumnName("file");
		attachment.setFileDesc("这是描述");
		sysAttachmentService.save(attachment);
		//4、调用采集方法
		Wenjian wenjian = new Wenjian();
		//组装文件，确定规则
		//根据方法ID、设备ID查找规则
		Guize guize = new Guize();
		guize.setPipeiff(fangfaid);
		guize.setPipeisb(shebeiid);
//		guize.setShifouzgz(Global.YES);
		List<Guize> guizeList = guizeService.findList(guize);
		if(guizeList != null && guizeList.size() > 0){
			wenjian.setGuizeid(Collections3.extractToString(guizeList,"id",","));
		}
		wenjian.setWenjianid(attachment.getId());

		//手动采集
//		caiji(wenjian);
	}

	/**
	 * 采集（手动）
	 * @param wenjian
	 */
//	public void caiji(Wenjian wenjian){
//		List<Guize> guizeList = null;
//		//update by guojinlong 2017-11-17 去除自动匹配规则
//		if (wenjian.getGuizeid()==null){
//			logger.error("采集文件失败，没有匹配的解析规则! 文件ID：{},规则ID：{}",wenjian.getId(),wenjian.getGuizeid());
//		}else{
//			Guize searchGuize = new Guize();
//			searchGuize.setArrIDS(wenjian.getGuizeid().split(","));
//			guizeList = guizeService.findList(searchGuize);
//		}
//		if(guizeList!=null && guizeList.size()>0){
//			for(Guize guize:guizeList){
//				SysAttachment sysAttachment = sysAttachmentService.get(wenjian.getWenjianid());
//				String root = Global.getCreateFilePath()+"\\"+"attachment\\"+sysAttachment.getFilePath();
//				File file = new File(root);
//
//				if (file.exists()){
//					String logid = IdGen.uuid();
//					if (guize.getGuizelx().equals("txt")){
//						if(this.getTxtData(file,guize,logid)){
//							wenjian.setIscaiji("1");
//							wenjian.setLogid(logid);
//							wenjian.setGuizeid(guize.getId());
//							wenjian.preUpdate();
//							this.save(wenjian);
//						}
//					}
//					else if (guize.getGuizelx().equals("autotxt")){
//						if(this.getAutoTxtData(file,guize,logid)){
//							wenjian.setIscaiji("1");
//							wenjian.setLogid(logid);
//							wenjian.setGuizeid(guize.getId());
//							wenjian.preUpdate();
//							this.save(wenjian);
//						}
//					}
//					else if (guize.getGuizelx().equals("excel")){
//						if(this.getExcelData(file,guize,logid)){
//							wenjian.setIscaiji("1");
//							wenjian.setLogid(logid);
//							wenjian.setGuizeid(guize.getId());
//							wenjian.preUpdate();
//							this.save(wenjian);
//						}
//					}
//					else if (guize.getGuizelx().equals("autoexcel")){
//						if(this.getAutoExcelData(file,guize,logid)){
//							wenjian.setIscaiji("1");
//							wenjian.setLogid(logid);
//							wenjian.setGuizeid(guize.getId());
//							wenjian.preUpdate();
//							this.save(wenjian);
//						}
//					}
//					else if (guize.getGuizelx().equals("jiaochatxt")){
//						if(this.getJiaochaTxtData(file,guize,logid)){
//							wenjian.setIscaiji("1");
//							wenjian.setLogid(logid);
//							wenjian.setGuizeid(guize.getId());
//							wenjian.preUpdate();
//							this.save(wenjian);
//						}
//					}
//					//保存索引表
//					saveIndex(guize,wenjian,root,logid);
//
//					logger.debug("采集成功！");
//				}
//				else{
//					logger.error("采集文件不存在！ 采集路径：{}",root);
//				}
//			}
//		}
//		else{
//			logger.error("采集文件失败，没有匹配的解析规则! 文件ID：{},规则ID：{}",wenjian.getId(),wenjian.getGuizeid());
//		}
//	}

	/**
	 * 保存采集索引,用于与业务匹配
	 * @param guize 规则
	 * @param wenjian 文件
	 * @param root 采集文件存储路径
	 * @param logid 采集一个文件的唯一标识
	 */
	public void saveIndex(Guize guize, Wenjian wenjian, String root, String logid){
		//采集索引表，将采集规则ID（guize.getId()）、LOGID（存在）、任务ID、是否成功、描述、采集时间(文件更新时间)
		String cjShiJian = ""; //采集时间
		String renwubh = ""; //任务编号
		//文件更新时间为采集时间
		if(wenjian.getUpdateDate() == null){
			cjShiJian = DateUtils.formatDateTime(wenjian.getUpdateDate());
		}
		//根据文件路径获得任务编号(最后一个以\T开头的位置开始，截取14位长度（任务编号长14位TC201711200201）)
		renwubh = root.substring(root.lastIndexOf("\\T")+1);
		renwubh = renwubh.substring(0,14);

		//1、采集规则对应结果数据，并且存在有子表
		if(Global.YES.equals(guize.getShifouzgz()) && StringUtils.isNotEmpty(guize.getZiguizid())){
			//循环插入索引
			//查找规则对应的表
			if(StringUtils.isNotEmpty(guize.getDataid())){
				CjTable cjTable = cjTableService.get(guize.getDataid());
				StringBuilder selSql = new StringBuilder();
				selSql.append("select "+cjTable.getShiyangbszd()+" from "+cjTable.getEnname()+" where logid = '"+logid+"'");
				List<Map> cjResultData = cjTableDao.exeSelSql(selSql.toString());

				//更新系统样品标识
				StringBuilder updateSql = new StringBuilder();
				updateSql.append("update "+cjTable.getEnname()+" set sysSampleCol = "+cjTable.getShiyangbszd()+" where logid = '"+logid+"'");
				cjTableDao.exesql(updateSql.toString());

				for(Map map : cjResultData){
					if(map == null){
						continue;
					}
					CjIndex cjIndex = new CjIndex();
					cjIndex.setGuizeid(guize.getId());
					cjIndex.setShifoucg(Global.YES);
					cjIndex.setCaijisj(cjShiJian);
					//通过文件路径获取任务编号及样品编号
					cjIndex.setRenwuid(renwubh);
					if(StringUtils.isNotEmpty(cjTable.getShiyangbszd())){
						String yangpinbh = (String)map.get(cjTable.getShiyangbszd().toUpperCase());
						if(StringUtils.isNotEmpty(yangpinbh)){
							cjIndex.setYangpinid(yangpinbh);
						}
					}
					cjIndex.setLogid(logid);
					//保存采集索引
					cjIndexService.save(cjIndex);
				}
			}else{
				logger.error("规则：{} 规则ID：{}，没有获取到dataid！",guize.getGuizemc(),guize.getId());
			}
		}else{
			//只创建一次，样品编号从规范路径中获取
			CjIndex cjindex = new CjIndex();
			cjindex.setGuizeid(guize.getId());
			cjindex.setShifoucg("1");
			cjindex.setCaijisj(cjShiJian);
			//任务ID用任务编号代替
			cjindex.setRenwuid(renwubh);
			//样品编号(样品编号规则：S+业务单号+组号+流水号（01,02.......）SC20171120020101)
			String yangpinbh = root.substring(root.lastIndexOf("\\S")+1);
			//如果是拆样样品长度加三位（“-01”）
			String pattern = "^S(C|G|B)(\\d{14}[-]?\\d{2}|\\d{14})";
			Pattern r = Pattern.compile(pattern);
			Matcher m = r.matcher(yangpinbh);
			if (m.find()){
				cjindex.setYangpinid(m.group());
			}else{
				logger.error("从路径：{} 匹配样品编号失败！,Logid：{}",root,logid);
			}
			cjindex.setLogid(logid);
			//保存采集索引
			cjIndexService.save(cjindex);
			//更新采集表的样品编号

			if(StringUtils.isNotEmpty(guize.getDataid())){
				CjTable cjTable = cjTableService.get(guize.getDataid());
				StringBuilder updateDataSql = new StringBuilder();
				updateDataSql.append("update "+cjTable.getEnname()+" set sysSampleCol = '"+cjindex.getYangpinid()+"' where logid = '"+logid+"'");
				cjTableDao.exesql(updateDataSql.toString());
			}
			if(StringUtils.isNotEmpty(guize.getInfoid())){
				CjTable cjTable = cjTableService.get(guize.getInfoid());
				StringBuilder updateInfoSql = new StringBuilder();
				updateInfoSql.append("update "+cjTable.getEnname()+" set sysSampleCol = '"+cjindex.getYangpinid()+"' where logid = '"+logid+"'");
				cjTableDao.exesql(updateInfoSql.toString());
			}
		}
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Wenjian wenjian) {
		super.delete(wenjian);
	}

	public Boolean getTxtData(File file, Guize guize, String logid) {
		// TODO Auto-generated method stub
		TxtTool tt = new TxtTool(file.getAbsolutePath());

		//处理信息表
		Guizetable searchInfo = new Guizetable();
		searchInfo.setGuizeid(guize.getId());
		searchInfo.setBiaolx("colinfo");
		List<Guizetable> guizetableInfoList = guizetableService.findList(searchInfo);
		if (guizetableInfoList.size()>0){
			Guizetable curGuizeTable = guizetableInfoList.get(0);
			String tableinfoid = curGuizeTable.getTableid();
			CjTable tableentity = cjTableService.get(tableinfoid);
			String tablename = tableentity.getEnname();
			Guizeitem searchGuizeitem = new Guizeitem();
			searchGuizeitem.setCjguizeid(guize.getId());
			searchGuizeitem.setCjtableid(tableinfoid);
			List<Guizeitem> lists = guizeitemService.findList(searchGuizeitem);
			if (lists.size()>0){
				String fields = "";
				for(int i=0;i<lists.size();i++){
					Guizeitem curguizeitem = lists.get(i);
					fields+=curguizeitem.getFieldenname()+",";
				}
				if (fields.length()>0){
					fields = fields.substring(0,fields.length()-1);
				}
				String values = "";
				for(int i=0;i<lists.size();i++){
					Guizeitem curguizeitem = lists.get(i);
					String rowindex = curguizeitem.getRowindex()==null?"":curguizeitem.getRowindex().toString();
					String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
					String value = tt.GetCellText(Integer.parseInt(rowindex), Integer.parseInt(columnindex),guize.getTxtfengef());
					values+="'"+value+"',";
				}
				if (values.length()>0){
					values = values.substring(0,values.length()-1);
				}
				StringBuilder insertSql = new StringBuilder();
				insertSql.append("insert into "+tablename+"(ID,logid,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+values+")");
				wenjianDao.exesql(insertSql.toString());
			}
		}

		//处理数据部分
		Guizetable searchData = new Guizetable();
		searchData.setGuizeid(guize.getId());
		searchData.setBiaolx("coldata");
		List<Guizetable> guizetableDataList = guizetableService.findList(searchData);
		if (guizetableDataList.size()>0){
			//添加如果有多个数据表，根据是否结果数据来确定是否进行多次采集。
			Guizetable curGuizeTable = guizetableDataList.get(0);
			String tabledataid = curGuizeTable.getTableid();
			CjTable tableentity = cjTableService.get(tabledataid);
			String tablename = tableentity.getEnname();
			Guizeitem searchGuizeitem = new Guizeitem();
			searchGuizeitem.setCjguizeid(guize.getId());
			searchGuizeitem.setCjtableid(tabledataid);
			List<Guizeitem> lists = guizeitemService.findList(searchGuizeitem);
			if (lists.size()>0){
				String fields = "";
				for(int i=0;i<lists.size();i++){
					Guizeitem curguizeitem = lists.get(i);
					fields+=curguizeitem.getFieldenname()+",";
				}
				if (fields.length()>0){
					fields = fields.substring(0,fields.length()-1);
				}
				int m=0;
				while(true){
					boolean hasValue = false;
					String values = "";
					for(int i=0;i<lists.size();i++){
						Guizeitem curguizeitem = lists.get(i);
						String rowindex = guize.getStartindex().toString();
						String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
						String value = tt.GetCellText(Integer.parseInt(rowindex)+m, Integer.parseInt(columnindex),guize.getTxtfengef());
						values+="'"+value+"',";
						if (values!=null&&!value.equals("")){
							hasValue = true;
						}
					}
					if (values.length()>0){
						values = values.substring(0,values.length()-1);
					}
					if (hasValue){
						StringBuilder insertSql = new StringBuilder();
						insertSql.append("insert into "+tablename+"(ID,logid,xuhao,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+m+","+values+")");
						wenjianDao.exesql(insertSql.toString());
						m++;
					}
					else{
						break;
					}
				}
			}
		}

		return true;
	}

	public Boolean getAutoTxtData(File file, Guize guize, String logid) {
		// TODO Auto-generated method stub
		TxtTool tt = new TxtTool(file.getAbsolutePath());

		//处理信息表
		Guizetable searchInfo = new Guizetable();
		searchInfo.setGuizeid(guize.getId());
		searchInfo.setBiaolx("colinfo");
		List<Guizetable> guizetableInfoList = guizetableService.findList(searchInfo);
		if (guizetableInfoList.size()>0){
			Guizetable curGuizeTable = guizetableInfoList.get(0);
			String tableinfoid = curGuizeTable.getTableid();
			CjTable tableentity = cjTableService.get(tableinfoid);
			String tablename = tableentity.getEnname();
			CjZiduan searchCjZiduan = new CjZiduan();
			searchCjZiduan.setCjtableid(tableinfoid);
			List<CjZiduan> ziduanlists = cjZiduanService.findList(searchCjZiduan);
			List<Guizeitem> lists = getAutoTxtGuizeitem(tt,guize,ziduanlists);
			if (lists.size()>0){
				String fields = "";
				for(int i=0;i<lists.size();i++){
					Guizeitem curguizeitem = lists.get(i);
					fields+=curguizeitem.getFieldenname()+",";
				}
				if (fields.length()>0){
					fields = fields.substring(0,fields.length()-1);
				}
				String values = "";
				for(int i=0;i<lists.size();i++){
					Guizeitem curguizeitem = lists.get(i);
					String rowindex = curguizeitem.getRowindex()==null?"":curguizeitem.getRowindex().toString();
					String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
					String value = tt.GetCellText(Integer.parseInt(rowindex), Integer.parseInt(columnindex)+1,guize.getTxtfengef());
					values+="'"+value+"',";
				}
				if (values.length()>0){
					values = values.substring(0,values.length()-1);
				}
				StringBuilder insertSql = new StringBuilder();
				insertSql.append("insert into "+tablename+"(ID,logid,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+values+")");
				wenjianDao.exesql(insertSql.toString());
			}
		}

		//处理数据部分
		Guizetable searchData = new Guizetable();
		searchData.setGuizeid(guize.getId());
		searchData.setBiaolx("coldata");
		List<Guizetable> guizetableDataList = guizetableService.findList(searchData);
		if (guizetableDataList.size()>0){
			Guizetable curGuizeTable = guizetableDataList.get(0);
			String tabledataid = curGuizeTable.getTableid();
			CjTable tableentity = cjTableService.get(tabledataid);
			String tablename = tableentity.getEnname();
			CjZiduan searchCjZiduan = new CjZiduan();
			searchCjZiduan.setCjtableid(tabledataid);
			List<CjZiduan> ziduanlists = cjZiduanService.findList(searchCjZiduan);
			List<Guizeitem> lists = getAutoTxtGuizeitem(tt,guize,ziduanlists);
			long rowindex  = -1;
			if (lists.size()>0){
				Long datapianyil = guize.getDatapianyil();
				if(datapianyil == null){
					rowindex =  lists.get(0).getRowindex();
				}else{
					rowindex =  lists.get(0).getRowindex()+datapianyil;
				}
			}
			if (lists.size()>0){
				String fields = "";
				for(int i=0;i<lists.size();i++){
					Guizeitem curguizeitem = lists.get(i);
					fields+=curguizeitem.getFieldenname()+",";
				}
				if (fields.length()>0){
					fields = fields.substring(0,fields.length()-1);
				}
				int m=0;
				while(true){
					boolean hasValue = false;
					String values = "";
					for(int i=0;i<lists.size();i++){
						Guizeitem curguizeitem = lists.get(i);
						String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
						String value = tt.GetCellText((int)rowindex+m, Integer.parseInt(columnindex),guize.getTxtfengef());
						values+="'"+value+"',";
						if (values!=null&&!value.equals("")){
							hasValue = true;
						}
					}
					if (values.length()>0){
						values = values.substring(0,values.length()-1);
					}
					if (hasValue){
						StringBuilder insertSql = new StringBuilder();
						insertSql.append("insert into "+tablename+"(ID,logid,xuhao,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+m+","+values+")");
						wenjianDao.exesql(insertSql.toString());
						m++;
					}
					else{
						break;
					}
				}
			}
		}

		return true;
	}

//	public Boolean getExcelData(File file, Guize guize, String logid) {
//		ExcelTool et = new ExcelTool(file.getAbsolutePath(),1);
//		//处理信息表
//		Guizetable searchInfo = new Guizetable();
//		searchInfo.setGuizeid(guize.getId());
//		searchInfo.setBiaolx("colinfo");
//		List<Guizetable> guizetableInfoList = guizetableService.findList(searchInfo);
//		if (guizetableInfoList.size()>0){
//			Guizetable curGuizeTable = guizetableInfoList.get(0);
//			String tableinfoid = curGuizeTable.getTableid();
//			CjTable tableentity = cjTableService.get(tableinfoid);
//			String tablename = tableentity.getEnname();
//			Guizeitem searchGuizeitem = new Guizeitem();
//			searchGuizeitem.setCjguizeid(guize.getId());
//			searchGuizeitem.setCjtableid(tableinfoid);
//			List<Guizeitem> lists = guizeitemService.findList(searchGuizeitem);
//			if (lists.size()>0){
//				String fields = "";
//				for(int i=0;i<lists.size();i++){
//					Guizeitem curguizeitem = lists.get(i);
//					fields+=curguizeitem.getFieldenname()+",";
//				}
//				if (fields.length()>0){
//					fields = fields.substring(0,fields.length()-1);
//				}
//				String values = "";
//				for(int i=0;i<lists.size();i++){
//					Guizeitem curguizeitem = lists.get(i);
//					String sheetname = curguizeitem.getSheetname()==null?"":curguizeitem.getSheetname().toString();
//					String rowindex = curguizeitem.getRowindex()==null?"":curguizeitem.getRowindex().toString();
//					String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
//					String value = et.GetCells(sheetname, Integer.parseInt(rowindex), Integer.parseInt(columnindex));
//					values+="'"+value+"',";
//				}
//				if (values.length()>0){
//					values = values.substring(0,values.length()-1);
//				}
//				StringBuilder insertSql = new StringBuilder();
//				insertSql.append("insert into "+tablename+"(ID,logid,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+values+")");
//				wenjianDao.exesql(insertSql.toString());
//			}
//		}
//
//		//处理数据部分
//		Guizetable searchData = new Guizetable();
//		searchData.setGuizeid(guize.getId());
//		searchData.setBiaolx("coldata");
//		List<Guizetable> guizetableDataList = guizetableService.findList(searchData);
//		if (guizetableDataList.size()>0){
//			if (guize.getStartindex()!=null){
//				//String startsheetname = guize.getSheetname();
//				String beiginrow = guize.getStartindex().toString();
//				//String beigincolumn = guize.getStartcolumn().toString();
//				int rowstart = Integer.parseInt(beiginrow);
//				//int meargeCount = et.GetCellMergeRows(sheetname,rowstart,beigincolumn);
//				int meargeCount =1;
//				Guizetable curGuizeTable = guizetableDataList.get(0);
//				String tabledataid = curGuizeTable.getTableid();
//				CjTable tableentity = cjTableService.get(tabledataid);
//				String tablename = tableentity.getEnname();
//				Guizeitem searchGuizeitem = new Guizeitem();
//				searchGuizeitem.setCjguizeid(guize.getId());
//				searchGuizeitem.setCjtableid(tabledataid);
//				List<Guizeitem> lists = guizeitemService.findList(searchGuizeitem);
//				if (lists.size()>0){
//					String fields = "";
//					for(int i=0;i<lists.size();i++){
//						Guizeitem curguizeitem = lists.get(i);
//						fields+=curguizeitem.getFieldenname()+",";
//					}
//					if (fields.length()>0){
//						fields = fields.substring(0,fields.length()-1);
//					}
//					int m=0;
//					while(true){
//						boolean hasValue = false;
//						String values = "";
//						for(int i=0;i<lists.size();i++){
//							Guizeitem curguizeitem = lists.get(i);
//							String sheetname = curguizeitem.getSheetname()==null?"":curguizeitem.getSheetname().toString();
//							String rowindex = curguizeitem.getRowindex()==null?"":curguizeitem.getRowindex().toString();
//							String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
//							String value = et.GetCells(sheetname, rowstart+m*meargeCount, Integer.parseInt(columnindex));
//							values+="'"+value+"',";
//							if (values!=null&&!value.equals("")){
//								hasValue = true;
//							}
//						}
//						if (values.length()>0){
//							values = values.substring(0,values.length()-1);
//						}
//						if (hasValue){
//							StringBuilder insertSql = new StringBuilder();
//							insertSql.append("insert into "+tablename+"(ID,logid,xuhao,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+m+","+values+")");
//							wenjianDao.exesql(insertSql.toString());
//							m++;
//						}
//						else{
//							break;
//						}
//					}
//				}
//			}
//		}
//		et.Save();
//		return true;
//	}
//
//	public Boolean getAutoExcelData(File file, Guize guize, String logid) {
//		Long intpianyil = 1l;
//		if (guize.getDatapianyil()!=null){
//			intpianyil = guize.getDatapianyil();
//		}
//		String sheetname = getSheetName(guize);
//		if (guize.getSheetname()!=null&&!guize.getSheetname().equals("")){
//			sheetname = guize.getSheetname();
//		}
//		ExcelTool et = new ExcelTool(file.getAbsolutePath(),1);
//		//处理信息表
//		Guizetable searchInfo = new Guizetable();
//		searchInfo.setGuizeid(guize.getId());
//		searchInfo.setBiaolx("colinfo");
//		List<Guizetable> guizetableInfoList = guizetableService.findList(searchInfo);
//		if (guizetableInfoList.size()>0){
//			Guizetable curGuizeTable = guizetableInfoList.get(0);
//			String tableinfoid = curGuizeTable.getTableid();
//			CjTable tableentity = cjTableService.get(tableinfoid);
//			String tablename = tableentity.getEnname();
//			CjZiduan searchCjZiduan = new CjZiduan();
//			searchCjZiduan.setCjtableid(tableinfoid);
//			List<CjZiduan> ziduanlists = cjZiduanService.findList(searchCjZiduan);
//			List<Guizeitem> lists = getAutoGuizeitem(et,guize,ziduanlists,sheetname);
//			if (lists.size()>0){
//				String fields = "";
//				for(int i=0;i<lists.size();i++){
//					Guizeitem curguizeitem = lists.get(i);
//					fields+=curguizeitem.getFieldenname()+",";
//				}
//				if (fields.length()>0){
//					fields = fields.substring(0,fields.length()-1);
//				}
//				String values = "";
//				for(int i=0;i<lists.size();i++){
//					Guizeitem curguizeitem = lists.get(i);
//					String rowindex = curguizeitem.getRowindex()==null?"":curguizeitem.getRowindex().toString();
//					String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
//					String value = et.GetCells(sheetname, Integer.parseInt(rowindex), Integer.parseInt(columnindex)+1);
//					values+="'"+value+"',";
//				}
//				if (values.length()>0){
//					values = values.substring(0,values.length()-1);
//				}
//				StringBuilder insertSql = new StringBuilder();
//				insertSql.append("insert into "+tablename+"(ID,logid,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+values+")");
//				wenjianDao.exesql(insertSql.toString());
//			}
//		}
//
//		//处理数据部分
//		String firstColumnValues = "";
//		Guizetable searchData = new Guizetable();
//		searchData.setGuizeid(guize.getId());
//		searchData.setBiaolx("coldata");
//		List<Guizetable> guizetableDataList = guizetableService.findList(searchData);
//		if (guizetableDataList.size()>0){
//			int meargeCount =1;
//			Guizetable curGuizeTable = guizetableDataList.get(0);
//			String tabledataid = curGuizeTable.getTableid();
//			CjTable tableentity = cjTableService.get(tabledataid);
//			String tablename = tableentity.getEnname();
//			CjZiduan searchCjZiduan = new CjZiduan();
//			searchCjZiduan.setCjtableid(tabledataid);
//			List<CjZiduan> ziduanlists = cjZiduanService.findList(searchCjZiduan);
//			List<Guizeitem> lists = getAutoGuizeitem(et,guize,ziduanlists,sheetname);
//			long rowstart  = -1;
//			if (lists.size()>0){
//				rowstart =  lists.get(0).getRowindex()+intpianyil;
//			}
//			if (lists.size()>0){
//				String fields = "";
//				for(int i=0;i<lists.size();i++){
//					Guizeitem curguizeitem = lists.get(i);
//					fields+=curguizeitem.getFieldenname()+",";
//				}
//				if (fields.length()>0){
//					fields = fields.substring(0,fields.length()-1);
//				}
//				int m=0;
//				while(true){
//					boolean hasValue = false;
//					String values = "";
//					for(int i=0;i<lists.size();i++){
//						Guizeitem curguizeitem = lists.get(i);
//						String rowindex = curguizeitem.getRowindex()==null?"":curguizeitem.getRowindex().toString();
//						String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
//						String value = et.GetCells(sheetname, (int)rowstart+m*meargeCount, Integer.parseInt(columnindex));
//						if (guize.getJieshubs()!=null&&guize.getJieshubs().equals(value)){
//							break;
//						}
//						values+="'"+value+"',";
//						if (values!=null&&!value.equals("")){
//							hasValue = true;
//						}
//					}
//					if (values.length()>0){
//						values = values.substring(0,values.length()-1);
//					}
//					//获取第一列所有值，用于匹配其它sheet
//					String firstColumnValue = et.GetCells(sheetname, (int)rowstart+m*meargeCount, 1);
//					firstColumnValues+=","+firstColumnValue+",";
//					if (hasValue){
//						StringBuilder insertSql = new StringBuilder();
//						insertSql.append("insert into "+tablename+"(ID,logid,xuhao,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+m+","+values+")");
//						wenjianDao.exesql(insertSql.toString());
//						m++;
//					}
//					else{
//						break;
//					}
//				}
//				if (firstColumnValues.length()>0){
//					firstColumnValues = firstColumnValues.substring(0,firstColumnValues.length()-1);
//				}
//			}
//
//			//处理下级表
//			List sublists = et.getSheetNames();
//			//根据主Sheet第一列的样品编号匹配其它sheet，匹配成功则入库
//			for(int s=0;s<sublists.size();s++){
//				String shiyansheetname = sublists.get(s).toString();
//				if (firstColumnValues.indexOf(shiyansheetname)>=0){
//					//获取子规则
//					String ziguizeid = guize.getZiguizid();
//					if(StringUtils.isNotEmpty(ziguizeid)){
//						Guize ziguize = guizeService.get(ziguizeid);
//						if (ziguize.getDatapianyil()!=null){
//							intpianyil = guize.getDatapianyil();
//						}
////						String zisheetname = getSheetName(ziguize);
//						Guizetable searchZiGuizetable = new Guizetable();
//						searchZiGuizetable.setGuizeid(ziguizeid);
//						searchZiGuizetable.setBiaolx("coldata");
//						List<Guizetable> ziGuizetableDataList = guizetableService.findList(searchZiGuizetable);
//						if (ziGuizetableDataList.size()>0){
//							int ziMeargeCount =1;
//							Guizetable curZiGuizeTable = ziGuizetableDataList.get(0);
//							String zitabledataid = curZiGuizeTable.getTableid();
//							CjTable zitableentity = cjTableService.get(zitabledataid);
//							String zitablename = zitableentity.getEnname();
//							CjZiduan searchZiCjZiduan = new CjZiduan();
//							searchZiCjZiduan.setCjtableid(zitabledataid);
//							List<CjZiduan> ziziduanlists = cjZiduanService.findList(searchZiCjZiduan);
//							List<Guizeitem> ziGuizeLists = getAutoGuizeitem(et,ziguize,ziziduanlists,shiyansheetname);
//							if (ziGuizeLists.size()>0){
//								rowstart =  ziGuizeLists.get(0).getRowindex()+intpianyil;
//							}
//
//							if (ziGuizeLists.size()>0){
//								String fields = "";
//								for(int i=0;i<ziGuizeLists.size();i++){
//									Guizeitem curguizeitem = ziGuizeLists.get(i);
//									fields+=curguizeitem.getFieldenname()+",";
//								}
//								if (fields.length()>0){
//									fields = fields.substring(0,fields.length()-1);
//								}
//								int m=0;
//								while(true){
//									boolean hasValue = false;
//									String values = "";
//									for(int i=0;i<ziGuizeLists.size();i++){
//										Guizeitem curguizeitem = ziGuizeLists.get(i);
//										String rowindex = curguizeitem.getRowindex()==null?"":curguizeitem.getRowindex().toString();
//										String columnindex = curguizeitem.getColumnindex()==null?"":curguizeitem.getColumnindex().toString();
//										String value = et.GetCells(shiyansheetname, (int)rowstart+m*ziMeargeCount, Integer.parseInt(columnindex));
//										if (guize.getJieshubs()!=null&&guize.getJieshubs().equals(value)){
//											break;
//										}
//										values+="'"+value+"',";
//										if (values!=null&&!value.equals("")){
//											hasValue = true;
//										}
//									}
//									if (values.length()>0){
//										values = values.substring(0,values.length()-1);
//									}
//									if (hasValue){
//										StringBuilder insertSql = new StringBuilder();
//										insertSql.append("insert into "+zitablename+"(ID,logid,xuhao,syssamplecol,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+m+",'"+shiyansheetname+"',"+values+")");
//										wenjianDao.exesql(insertSql.toString());
//										m++;
//									}
//									else{
//										break;
//									}
//								}
//							}
//						}
//					}
//				}
//			}
//		}
//		et.Save();
//		return true;
//	}

	public Boolean getJiaochaTxtData(File file, Guize guize, String logid) {
		// TODO Auto-generated method stub
		TxtTool tt = new TxtTool(file.getAbsolutePath());
		//处理数据部分
		Guizetable searchData = new Guizetable();
		searchData.setGuizeid(guize.getId());
		searchData.setBiaolx("coldata");
		List<Guizetable> guizetableDataList = guizetableService.findList(searchData);
		if (guizetableDataList.size()>0){
			Guizetable curGuizeTable = guizetableDataList.get(0);
			String tabledataid = curGuizeTable.getTableid();
			CjTable tableentity = cjTableService.get(tabledataid);
			String tablename = tableentity.getEnname();
			CjZiduan searchCjZiduan = new CjZiduan();
			searchCjZiduan.setCjtableid(tabledataid);
			List<CjZiduan> ziduanlists = cjZiduanService.findList(searchCjZiduan);
			long rowindex  = tt.getTargetRow("Measurement n?1")+1;
			List<String> lists = getJiaochaTxtFileds(tt,guize,ziduanlists);
			if (lists.size()>0){
				String fields = "";
				for(int i=0;i<lists.size();i++){
					fields+=lists.get(i)+",";
				}
				if (fields.length()>0){
					fields = fields.substring(0,fields.length()-1);
				}
				int m=0;
				while(true){
					boolean hasValue = false;
					String values = "";
					for(int i=1;i<=lists.size();i++){
						String value = tt.GetCellText((int)rowindex+m, i+1,";");
						values+="'"+value+"',";
						if (values!=null&&!value.equals("")){
							hasValue = true;
						}
					}
					if (values.length()>0){
						values = values.substring(0,values.length()-1);
					}
					if (hasValue){
						StringBuilder insertSql = new StringBuilder();
						insertSql.append("insert into "+tablename+"(ID,logid,xuhao,"+fields+") values ('"+ IdGen.uuid()+"','"+logid+"',"+m+","+values+")");
						wenjianDao.exesql(insertSql.toString());
						m++;
					}
					else{
						break;
					}
				}
			}
		}
		return true;
	}

	/**
	 * 根据字段中文名称解析字段对应的excel坐标
	 * @param et
	 * @param guize
	 * @param ziduanlists
	 * @return
	 */
//	private List<Guizeitem> getAutoGuizeitem(ExcelTool et, Guize guize, List<CjZiduan> ziduanlists, String sheetname){
//		int maxColumnSize = et.GetColumnCount(sheetname);
//		int maxRowSize = et.GetRowCount(sheetname);
//		if (guize.getAutoend()!=null){
//			maxRowSize = Integer.parseInt(guize.getAutoend());
//		}
//		Map<String,Excelcellzb> celllists = new HashMap<String,Excelcellzb>();
//		for(int i=1;i<=maxRowSize;i++){
//			for(int j=1;j<=maxColumnSize;j++){
//				String cellvalue = et.GetCells(sheetname,i,j);
//				if (cellvalue!=null&&!cellvalue.equals("")){
//					cellvalue = cellvalue.trim();//去除空格
//					Excelcellzb excelcellzb = new Excelcellzb();
//					excelcellzb.setRowindex(i);
//					excelcellzb.setColumnindex(j);
//					celllists.put(cellvalue,excelcellzb);
//				}
//			}
//		}
//		List<Guizeitem> lists = new ArrayList<Guizeitem>();
//		for(int i=0;i<ziduanlists.size();i++){
//			CjZiduan cjZiduan = ziduanlists.get(i);
//			Excelcellzb curzb = celllists.get(cjZiduan.getCnname().trim());
//			if (curzb!=null){
//				Guizeitem guizeitem = new Guizeitem();
//				guizeitem.setRowindex((long)curzb.getRowindex());
//				guizeitem.setColumnindex((long)curzb.getColumnindex());
//				guizeitem.setFieldenname(cjZiduan.getEnname());
//				lists.add(guizeitem);
//			}
//		}
//		return lists;
//	}

	/**
	 * 根据字段中文名称解析字段对应的txt坐标
	 * @param txtTool
	 * @param guize
	 * @param ziduanlists
	 * @return
	 */
	private List<Guizeitem> getAutoTxtGuizeitem(TxtTool txtTool, Guize guize, List<CjZiduan> ziduanlists){
		int maxRowSize = -1;
		if (guize.getAutoend()!=null){
			maxRowSize = Integer.parseInt(guize.getAutoend());
		}
		Map<String, Excelcellzb> celllists = txtTool.getExcelcellzb(guize.getTxtfengef(),maxRowSize);
		List<Guizeitem> lists = new ArrayList<Guizeitem>();
		for(int i=0;i<ziduanlists.size();i++){
			CjZiduan cjZiduan = ziduanlists.get(i);
			String cnnameunhtml = StringUtils.getUnHtml(cjZiduan.getCnname());
			Excelcellzb curzb = celllists.get(cnnameunhtml);
			if (curzb!=null){
				Guizeitem guizeitem = new Guizeitem();
				guizeitem.setRowindex((long)curzb.getRowindex());
				guizeitem.setColumnindex((long)curzb.getColumnindex());
				guizeitem.setFieldenname(cjZiduan.getEnname());
				lists.add(guizeitem);
			}
		}
		return lists;
	}

	private List<String> getJiaochaTxtFileds(TxtTool txtTool, Guize guize, List<CjZiduan> ziduanlists){
		int maxRowSize = 15;
		if (guize.getAutoend()!=null){
			maxRowSize = Integer.parseInt(guize.getAutoend());
		}
		List<String> fieldslists = new ArrayList<String>();
		List<String> celllists = txtTool.getFields();
		List<Guizeitem> lists = new ArrayList<Guizeitem>();
		for(int i=0;i<ziduanlists.size();i++){
			CjZiduan cjZiduan = ziduanlists.get(i);
			String cnnameunhtml = cjZiduan.getCnname();
			if (celllists.contains(cnnameunhtml)){
				fieldslists.add(cjZiduan.getEnname());
			}
		}
		return fieldslists;
	}

	public String getSheetName(Guize guize){
		String sheetname = "Sheet1";
		if (guize.getSheetname()!=null&&!guize.getSheetname().equals("")){
			sheetname = guize.getSheetname();
		}
		return sheetname;
	}

	public void exesql(String sql){
		wenjianDao.exesql(sql);
	}

	/**
	 * 将服务器上自动获取的文件上传到指定目录（保存附件表信息，采集文件表）
	 * 并关联采集规则
	 */
	public void autoSaveWjSj(){
		String path = "D:/collectserver/"; //采集文件工具会将客户端相应目录下的文件（夹）全部放到该目录下D:/collectserver/
		traverseFolder(path);
	}

	/**
	 * 遍历文件夹
	 * @param filePath
	 */
	public void traverseFolder(String filePath){
		File f = new File(filePath);
		if (!f.exists()) {
			System.out.println(filePath + " not exists");
			return;
		}

		File fs[] = f.listFiles();
		for (int i = 0; i < fs.length; i++) {
			File file = fs[i];
			if (file.isDirectory()) {
				if(StringUtils.isNotBlank(file.getPath())){
					traverseFolder(file.getPath());
				}
			} else {
				copyFile(file);//拷贝文件到指定目录
			}
		}
		delAllFile(filePath);//删除原文件及文件夹
	}

	/**
	 * 将文件拷贝到指定目录（防止采集数据时找不到原目录下文件）
	 * @param oldFile
	 */
	public void copyFile(File oldFile) {
		//文件保存位置，当前项目下的upload/attachment
		String uploadDir = "upload" + File.separatorChar + "attachment" + File.separatorChar;
		//每天上传的文件根据日期存放在不同的文件夹
		String autoCreatedDateDirByParttern = "yyyy" + File.separatorChar + "MM" + File.separatorChar + "dd"
				+ File.separatorChar;
		String autoCreatedDateDir = DateFormatUtils.format(new java.util.Date(), autoCreatedDateDirByParttern);
		String newDirPath = Global.getConfig("createFilePath") + "\\attachment\\" + uploadDir + autoCreatedDateDir;

		File yicaiDirPath = new File(newDirPath + "\\yicaiwj\\");//已采集到数据的文件存放路径
		File weicaiPath = new File(newDirPath + "\\weicaiwj\\");
		if (!yicaiDirPath.exists()) {
			yicaiDirPath.mkdirs();
		}
		if (!weicaiPath.exists()) {
			weicaiPath.mkdirs();
		}
		String yicaiFilePath = String.valueOf(yicaiDirPath).concat(String.valueOf(File.separatorChar)).concat(oldFile.getName());
		String weicaiFilePath = String.valueOf(weicaiPath).concat(String.valueOf(File.separatorChar)).concat(oldFile.getName());
		Map<String, Object> map = saveToAttach(oldFile);
		boolean flag = (Boolean)map.get("caijicg");//是否采集成功
		String attId = String.valueOf(map.get("attId"));//附件id
		String wjId = String.valueOf(map.get("wjId"));//文件id
		if(flag){//采集成功
			copyFile(oldFile, yicaiFilePath);//放到采集成功的文件夹下
			saveToAtt(attId,  wjId, oldFile, yicaiFilePath);
		}else {
			copyFile(oldFile, weicaiFilePath);//放到采集失败的文件夹下
			saveToAtt(attId,  wjId, oldFile, weicaiFilePath);
		}
	}

	/**
	 * 保存附件
	 * @param attId
	 * @param wjId
	 * @param file
	 * @param filePath
	 */
	public void saveToAtt(String attId, String wjId, File file, String filePath){
		SysAttachment attachment = new SysAttachment();
		attachment.setId(attId);
		attPreInsert(attachment);
		attachment.setCodeId(wjId);
		attachment.setFileName(file.getName());
		attachment.setFilePath(filePath);
		attachment.setFileDesc("自动采集的文件！");
		sysAttachmentDao.insert(attachment);
	}

	/**
	 * 拷贝文件
	 * @param oldFile
	 * @param newFilePath
	 */
	public void copyFile(File oldFile, String newFilePath){
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldFile.getPath());
			if (oldfile.exists()) { //文件存在时
				InputStream inStream = new FileInputStream(oldFile.getPath()); //读入原文件
				FileOutputStream fs = new FileOutputStream(newFilePath);
				byte[] buffer = new byte[1024*5];
				//int length;
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; //字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			System.out.println("复制单个文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 保存附件信息和采集文件，关联采集规则
	 * @param oldFile 原文件
	 */
	public Map<String, Object> saveToAttach(File oldFile){
		String wjId = IdGen.uuid();
		String attId = IdGen.uuid();

		Wenjian wenjian = new Wenjian();
		wenjian.setId(wjId);
		wenjian.setWenjianid(attId);
		wjPreInsert(wenjian);
		wenjian.setFilename(oldFile.getName());
		wenjian.setIsxuyaocj(Global.YES);
		wenjian.setRemarks("自动采集的文件！");
		wenjian.setIscaiji("0");
		this.dao.insert(wenjian);

//		boolean flag = autoCaijiSj(wenjian, oldFile);
		//规则存入wenjian中
		Map<String, Object> map = Maps.newHashMap();
//		map.put("caijicg", flag);//是否采集成功
		map.put("attId", attId);//附件id
		map.put("wjId", wjId);//文件id
		return map;
	}

	/**
	 * 数据采集(自动)
	 * @param wenjian
	 * @param oldFile 原文件
	 * @return
	 */
//	public boolean autoCaijiSj(Wenjian wenjian,File oldFile) {
//		boolean flag = false;
//		Guize guize = null;
//		String renwubh = null;
//		String yangpinbh = null;
//		if (wenjian.getGuizeid()==null){
//			String fName = oldFile.getName();
//			String filename = fName.substring(0,fName.indexOf("."));
//			//匹配规则
//			if(StringUtils.isNotBlank(filename) && filename.contains("_")){
//				String[] partFileNameArr = filename.split("_");
//				renwubh = partFileNameArr[0];
//				yangpinbh = partFileNameArr[1];
//				Map ffsbMap = shiyanrwService.getRenwuFfidSbid(renwubh, yangpinbh);
//				guize = new Guize();
//				guize.setPipeiff(String.valueOf(ffsbMap.get("FANGFAID")));
//				guize.setPipeisb(String.valueOf(ffsbMap.get("SHEBEIID")));
//				List<Guize> guizeList = guizeService.findList(guize);
//				if(guizeList!=null && !guizeList.isEmpty()){
//					guize = guizeList.get(0);
//				}
//			}
//		}
//		if (guize!=null){
//			if (oldFile.exists()){
//				String logid = IdGen.uuid();
//				if (guize.getGuizelx().equals("txt")){
//					if(getTxtData(oldFile,guize,logid)){
//						wenjian.setIscaiji("1");
//						wenjian.setLogid(logid);
//						save(wenjian);
//					}
//				} else if (guize.getGuizelx().equals("autotxt")){
//					if(getAutoTxtData(oldFile,guize,logid)){
//						wenjian.setIscaiji("1");
//						wenjian.setLogid(logid);
//						save(wenjian);
//					}
//				} else if (guize.getGuizelx().equals("excel")){
//					if(getExcelData(oldFile,guize,logid)){
//						wenjian.setIscaiji("1");
//						wenjian.setLogid(logid);
//						save(wenjian);
//					}
//				} else if (guize.getGuizelx().equals("autoexcel")){
//					if(getAutoExcelData(oldFile,guize,logid)){
//						wenjian.setIscaiji("1");
//						wenjian.setLogid(logid);
//						save(wenjian);
//					}
//				} else if (guize.getGuizelx().equals("jiaochatxt")){
//					if(getJiaochaTxtData(oldFile,guize,logid)){
//						wenjian.setIscaiji("1");
//						wenjian.setLogid(logid);
//						save(wenjian);
//					}
//				}
//				if(renwubh!=null && yangpinbh!=null){
//					saveIndexForAuto(guize, wenjian, renwubh, yangpinbh, logid);
//					flag = true;
//				}
//			}/* else{
//				throw new RuntimeException("采集文件不存在");
//			}*/
//		} /*else{
//			throw new RuntimeException("采集文件失败，没有匹配的解析规则");
//		}*/
//		return flag;
//	}

	/**
	 * 附件保存前操作
	 * @param att
	 */
	public void attPreInsert(SysAttachment att){
		User user = UserUtils.get("1");
		if (StringUtils.isNotBlank(user.getId())){
			att.setCreateBy(user);
			att.setUpdateBy(user);
		}
		Date d = new Date();
		att.setCreateDate(d);
		att.setUpdateDate(d);
	}

	/**
	 * 文件保存前操作
	 * @param wj
	 */
	public void wjPreInsert(Wenjian wj){
		User user = UserUtils.get("1");
		if (StringUtils.isNotBlank(user.getId())){
			wj.setCreateBy(user);
			wj.setUpdateBy(user);
		}
		Date d = new Date();
		wj.setCreateDate(d);
		wj.setUpdateDate(d);
	}

	/**
	 * 删除文件夹里面的所有文件
	 * @param path String 文件夹路径 如 c:/fqf
	 */
	public void delAllFile(String path) {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			}
			else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path+"/"+ tempList[i]);//先删除文件夹里面的文件
				delFolder(path+"/"+ tempList[i]);//再删除空文件夹
			}
		}
	}

	/**
	 * 删除文件
	 * @param filePathAndName String 文件路径及名称 如c:/fqf.txt
	 * @return boolean
	 */
	public void delFile(String filePathAndName) {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePath);
			myDelFile.delete();
		}
		catch (Exception e) {
			System.out.println("删除文件操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件夹
	 * @param folderPath String 文件夹路径及名称 如c:/fqf
	 * @return boolean
	 */
	public void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); //删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); //删除空文件夹
		}
		catch (Exception e) {
			System.out.println("删除文件夹操作出错");
			e.printStackTrace();
		}
	}

	/**
	 * 保存采集索引,用于与业务匹配
	 * @param guize
	 * @param wenjian
	 * @param renwubh 任务编号
	 * @param yangpinbh 样品编号
	 * @param logid
	 */
	public void saveIndexForAuto(Guize guize, Wenjian wenjian, String renwubh, String yangpinbh, String logid){
		//采集索引表，将采集规则ID（guize.getId()）、LOGID（存在）、任务ID、是否成功、描述、采集时间(文件更新时间)
		String cjShiJian = ""; //采集时间
		//文件更新时间为采集时间
		if(wenjian.getUpdateDate() == null){
			cjShiJian = DateUtils.formatDateTime(wenjian.getUpdateDate());
		}

		//1、采集规则对应结果数据，并且存在有子表
		if(Global.YES.equals(guize.getShifouzgz()) && StringUtils.isNotEmpty(guize.getZiguizid())){
			//循环插入索引
			//查找规则对应的表
			if(StringUtils.isNotEmpty(guize.getDataid())){
				CjTable cjTable = cjTableService.get(guize.getDataid());
				StringBuilder selSql = new StringBuilder();
				selSql.append("select "+cjTable.getShiyangbszd()+" from "+cjTable.getEnname()+" where logid = '"+logid+"'");
				List<Map> cjResultData = cjTableDao.exeSelSql(selSql.toString());

				//更新系统样品标识
				StringBuilder updateSql = new StringBuilder();
				updateSql.append("update "+cjTable.getEnname()+" set sysSampleCol = "+cjTable.getShiyangbszd()+" where logid = '"+logid+"'");
				cjTableDao.exesql(updateSql.toString());

				for(Map map : cjResultData){
					if(map == null){
						continue;
					}
					CjIndex cjIndex = new CjIndex();
					cjIndex.setGuizeid(guize.getId());
					cjIndex.setShifoucg(Global.YES);
					cjIndex.setCaijisj(cjShiJian);
					//通过文件路径获取任务编号及样品编号
					cjIndex.setRenwuid(renwubh);
					if(StringUtils.isNotEmpty(cjTable.getShiyangbszd())){
						if(StringUtils.isNotEmpty(yangpinbh)){
							cjIndex.setYangpinid(yangpinbh);
						}
					}
					cjIndex.setLogid(logid);
					//保存采集索引
					cjIndexService.save(cjIndex);
				}
			}else{
				logger.error("规则：{} 规则ID：{}，没有获取到dataid！",guize.getGuizemc(),guize.getId());
			}
		}else{
			//只创建一次，样品编号从规范路径中获取
			CjIndex cjindex = new CjIndex();
			cjindex.setGuizeid(guize.getId());
			cjindex.setShifoucg("1");
			cjindex.setCaijisj(cjShiJian);
			//任务ID用任务编号代替
			cjindex.setRenwuid(renwubh);
			cjindex.setYangpinid(yangpinbh);
			cjindex.setLogid(logid);
			//保存采集索引
			cjIndexService.save(cjindex);
			//更新采集表的样品编号

			if(StringUtils.isNotEmpty(guize.getDataid())){
				CjTable cjTable = cjTableService.get(guize.getDataid());
				StringBuilder updateDataSql = new StringBuilder();
				updateDataSql.append("update "+cjTable.getEnname()+" set sysSampleCol = '"+cjindex.getYangpinid()+"' where logid = '"+logid+"'");
				cjTableDao.exesql(updateDataSql.toString());
			}
			if(StringUtils.isNotEmpty(guize.getInfoid())){
				CjTable cjTable = cjTableService.get(guize.getInfoid());
				StringBuilder updateInfoSql = new StringBuilder();
				updateInfoSql.append("update "+cjTable.getEnname()+" set sysSampleCol = '"+cjindex.getYangpinid()+"' where logid = '"+logid+"'");
				cjTableDao.exesql(updateInfoSql.toString());
			}
		}
	}
}