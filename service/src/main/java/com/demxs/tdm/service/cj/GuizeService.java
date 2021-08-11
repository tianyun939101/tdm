package com.demxs.tdm.service.cj;

import com.demxs.tdm.dao.cj.GuizeDao;
import com.demxs.tdm.service.business.core.impl.TaskService;
import com.demxs.tdm.service.configure.ShebeiRuleService;
import com.demxs.tdm.service.resource.attach.AssetInfoService;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.file.exception.DownloadException;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.FileUtils;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.business.EntrustSampleGroupItem;
import com.demxs.tdm.domain.business.TestTask;
import com.demxs.tdm.domain.cj.CjTable;
import com.demxs.tdm.domain.cj.Guize;
import com.demxs.tdm.domain.configure.ShebeiRule;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.comac.common.jdbc.utils.DriverConstans;
import com.demxs.tdm.comac.common.jdbc.utils.FileList;
import com.demxs.tdm.comac.common.jdbc.utils.JdbcTools;
import com.demxs.tdm.comac.common.jdbc.utils.SambaUtils;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.google.common.collect.Lists;
import jcifs.smb.NtlmPasswordAuthentication;
import jcifs.smb.SmbFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采集规则Service
 * @author 张仁
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class GuizeService extends CrudService<GuizeDao, Guize> {
	@Autowired
	private GuizeDao guizeDao;

	@Autowired
	private CjTableService cjTableService;
	@Autowired
	private TaskService taskService;

	@Autowired
	private ShebeiRuleService shebeiRuleService;

	@Autowired
	private AssetInfoService assetInfoService;

	@Override
	public Guize get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Guize> findList(Guize guize) {
		return super.findList(guize);
	}
	
	@Override
	public Page<Guize> findPage(Page<Guize> page, Guize guize) {
		return super.findPage(page, guize);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(Guize guize) {
		super.save(guize);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(Guize guize) {
		super.delete(guize);
	}

	public List<Map> findGuizeZiduan(Map maps){
		return guizeDao.findGuizeZiduan(maps);
	}

	public List<Map> getAddressInfo(Map maps){
		return guizeDao.getAddressInfo(maps);
	}

	/**
	 * 返回MAP包含规则ID，规则名称
	 * map {id,cnname}
	 * */
	public List<Map> findGuiZe(String guizemc){
		Guize guize = new Guize();
		guize.setGuizemc(guizemc);
		return this.dao.findGuiZe(guize);
	}

	/***
	 * 根据规则ID获取规则名称
	 * map {id,cnname}
	 */
	public Map findGuiZeMingCheng(String id){
		Guize guize = new Guize();
		guize.setId(id);
		return this.dao.findGuiZeMingCheng(guize);
	}


	/**
	 * 根据方法ID及设备ID获取结果规则ID
	 * @param ffid 方法ID
	 * @param sbid 设备ID
	 * @return
	 */
	public String getGuizeByFFAndSB(String ffid, String sbid){
		Guize searchGuize = new Guize();
		searchGuize.setPipeiff(ffid);
		searchGuize.setPipeisb(sbid);
		searchGuize.setIsResult(Global.YES);//获取结果规则
		List<Guize> guizeList = super.findList(searchGuize);
		String guizeid = "";
		if(guizeList != null && guizeList.size() ==1){
			guizeid = guizeList.get(0).getId();
		}else{
			logger.debug("根据方法ID及设备ID未找到或找到多条规则ID，方法ID：{}，设备ID：{}，获取规则条数：{}",ffid,sbid,guizeList.size());
		}
		return guizeid;
	}

	/**
	 * 根据方法ID及设备ID获取规则ID
	 * @param ffid 方法ID
	 * @param sbid 设备ID
	 * @param isResult 是否结果规则
	 * @return
	 */
	public String getGuizeByFFAndSB(String ffid, String sbid, boolean isResult){
		Guize searchGuize = new Guize();
		searchGuize.setPipeiff(ffid);
		searchGuize.setPipeisb(sbid);
		searchGuize.setIsResult(isResult? Global.YES: Global.NO);
		List<Guize> guizeList = super.findList(searchGuize);
		String guizeid = "";
		if(guizeList != null && guizeList.size() ==1){
			guizeid = guizeList.get(0).getId();
		}else{
			logger.debug("根据方法ID及设备ID未找到或找到多条规则ID，方法ID：{}，设备ID：{}，获取规则条数：{}",ffid,sbid,guizeList.size());
		}
		return guizeid;
	}

	/**
	 * 获取采集数据
	 * @param guizeid 规则ID
	 * @param url      连库URL
	 * @param userName 用户名
	 * @param passWord 密码
	 * @param goodsNums 样品编号集合
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> getCollectionDate(String guizeid, String url, String userName, String passWord, List<String> goodsNums){
		List<Map<String,Object>> resultList = Lists.newArrayList();
		//1、根据规则ID获取规则类型（数据库类型），表名
		Guize guize = get(guizeid);
		if(guize != null && guize.getAccesstablename() != null && guize.getGuizelx() != null){
			String tableName = guize.getAccesstablename();
			String dbTypt = guize.getGuizelx();
			JdbcTools jdbcTools = new JdbcTools(dbTypt,url,userName,passWord);
			//根据DataId查找cjTable获取试样标识字段。
			CjTable cjTable = cjTableService.get(guize.getDataid());
			String goodsNumCol = cjTable.getShiyangbszd();
			//如果试样标识字段为空则查询所有。
			if(StringUtils.isNotEmpty(goodsNumCol)){
				/*if(DriverConstans.DbType.POSTGRE.equals(dbTypt)){
					goodsNumCol = ""+goodsNumCol+"";
				}*/
				//resultList = jdbcTools.getTableResultWithOneParam(tableName,goodsNumCol,"IN",goodsNums);
				resultList = jdbcTools.getTableResultBySql(tableName,goodsNumCol,goodsNums);
			}else {
				resultList = jdbcTools.getTableResult(tableName);
			}
		}
		return resultList;
	}

	/**
	 * 获取该任务的采集文件数据
	 * @param taskId
	 * @return
	 */
	public List<Map<String,Object>> getCollectionByTask(String taskId){

		List<Map<String,Object>> fileList = new ArrayList<>();

		TestTask testTask = taskService.get(taskId);
		if(testTask==null){
			throw  new ServiceException("任务不存在");
		}
		//获取任务的样品编号
		List<String> goodNums =getSampleSns(testTask);
		if(StringUtils.isNotBlank(testTask.getDeviceId())){
			List<ShebeiRule> shebeiRules = getRules(testTask.getDeviceId());
			for(ShebeiRule r:shebeiRules){
				Map map = new HashMap();
				map.put("shebeiid",r.getId());
				map.put("files",getCollectionByFile(r.getGuize().getId(),r,goodNums));
				fileList.add(map);
			}

		}
		return fileList;
	}

	/**
	 * 下载
	 * @param filePath
	 */
	public byte[] downloadFile(String filePath){
		//通过设备获取设备规则
		byte[] bytes = new byte[0];
		File file = FileUtils.getFile(filePath);
		try {
			bytes = FileUtils.readFileToByteArray(file);
		} catch (Exception e) {
			logger.error("download File error,fileName="+file.getName(),e);
			throw new DownloadException(e);
		}
		return bytes;
	}


	/**
	 * 保存附件并且与附件关联
	 * @param taskId
	 * @param fileList
	 */
	public void saveCjDataByTask(String taskId,List<Map<String,Object>> fileList){
		//todo 清楚该任务关联的附件
		List<String> attachIds = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(fileList)){
			for(Map m:fileList){
				AttachmentInfo attachmentInfo = new AttachmentInfo();
				String fileName = m.get("fileName").toString();
				String filePath = m.get("filePath").toString();
				attachIds.add(assetInfoService.save(fileName, filePath,null).getId());
			}
		}
		assetInfoService.saveAttachBusiness(taskId,StringUtils.join(attachIds.toArray(),","));
	}

	/**
	 * 按照文件名称和样品编码过滤数据
	 * @param files
	 * @param samples
	 * @return
	 */
	private  List<Map<String,Object>> checkSampleSn(List<Map<String,Object>> files, List<String> samples){
		logger.error("获取文件："+files);
		List<Map<String,Object>> result = new ArrayList<>();
		if(CollectionUtils.isNotEmpty(files)){
			for(Map<String,Object> file:files){
				for(String sample:samples){
					if(StringUtils.upperCase(sample).contains(StringUtils.upperCase(FileList.getFileNameWithoutExtension(file.get("fileName").toString())))){
						result.add(file);
						break;
					}else {
						continue;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 获取样品编号
	 * @param testTask
	 * @return
	 */
	private List<String> getSampleSns(TestTask testTask){
		//获取任务的样品编号
		List<String> goodNums = new ArrayList<>();
		for(EntrustSampleGroupItem item:testTask.getSampleList()){
			goodNums.add(StringUtils.trim(item.getSn()));
		}
		return goodNums;
	}

	/**
	 * 根据设备获取设备id 只获取采集文件的规则
	 * @param deviceId
	 * @return
	 */
	private List<ShebeiRule> getRules(String deviceId){
		List<ShebeiRule> results = new ArrayList<>();
		//通过设备获取规则id
		String[] dIdArr = deviceId.split(",");
		for(String shebeiid:dIdArr){
			List<ShebeiRule> shebeiRules = shebeiRuleService.findList(new ShebeiRule(new Shebei(shebeiid)));
			if(CollectionUtils.isNotEmpty(shebeiRules)){
				for(ShebeiRule sr:shebeiRules){
					if(sr.getGuize().getGuizelx().equals(DriverConstans.DbType.FILE)){
						results.add(sr);
					}
				}
			}
		}

		return results;
	}
	/**
	 * 获取采集文件数据
	 * @param guizeid
	 * @param shebeiRule
	 * @return
	 */
	public List<Map<String,Object>> getCollectionByFile(String guizeid,ShebeiRule shebeiRule,List<String> samples){
		 Guize guize = get(guizeid);
		 if(guize==null){
		 	throw new ServiceException("无采集规则");
		 }else{
		 	if(!DriverConstans.DbType.FILE.equals(guize.getGuizelx())){
				throw new ServiceException("非文件采集规则");
			}else{
				try {
					//return checkSampleSn(FileList.getAll(new File("\\\\66.0.91.17\\ccc")),samples);
					if(StringUtils.isNotEmpty(shebeiRule.getDataUsername()) && StringUtils.isNotEmpty(shebeiRule.getDataPassword())){
						NtlmPasswordAuthentication authentication = new NtlmPasswordAuthentication(null, shebeiRule.getDataUsername(), shebeiRule.getDataPassword());
						return checkSampleSn(SambaUtils.getAll(new SmbFile(shebeiRule.getDataUrl(),authentication),new ArrayList<>()),samples);
					}else{
						return checkSampleSn(SambaUtils.getAll(new SmbFile(shebeiRule.getDataUrl()),new ArrayList<>()),samples);
					}

					//return checkSampleSn(FileList.getAll(new File(path),new ArrayList<>()),samples);
					//return FileList.getAll(new File(path));
				} catch (Exception e) {
					throw new ServiceException("找不到该路径文件,采集异常",e.getCause());
				}
			}
		 }

	}
}