package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.YuanshijlmbRkDao;
import com.demxs.tdm.dao.configure.YuanshijlmbyxbqDao;
import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.configure.YuanshijlmbRk;
import com.demxs.tdm.comac.common.constant.PzConstans;
import com.demxs.tdm.service.business.core.impl.TaskService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 原始记录模板入库信息配置Service
 * @author 谭冬梅
 * @version 2017-11-30
 */
@Service
@Transactional(readOnly = true)
public class YuanshijlmbRkService extends CrudService<YuanshijlmbRkDao, YuanshijlmbRk> {

	@Autowired
	private TaskService taskService;
	@Autowired
	private YuanshijlmbyxbqDao yuanshijlmbyxbqDao;

	public YuanshijlmbRk get(String id) {
		return super.get(id);
	}
	
	public List<YuanshijlmbRk> findList(YuanshijlmbRk yuanshijlmbRk) {
		return super.findList(yuanshijlmbRk);
	}
	
	public Page<YuanshijlmbRk> findPage(Page<YuanshijlmbRk> page, YuanshijlmbRk yuanshijlmbRk) {
		return super.findPage(page, yuanshijlmbRk);
	}

	public void saveRKXX(YuanshijlmbRk yuanshijlmbRk){
		if(!StringUtils.isNotBlank(yuanshijlmbRk.getId())){//新增字段
            //获取当前模板是否已建对应的表单或列表结果表，如果没有，则新建一张表
			YuanshijlmbRk find = new YuanshijlmbRk();
			find.setMbid(yuanshijlmbRk.getMbid());
			find.setZiduanlx(yuanshijlmbRk.getZiduanlx());
			List<YuanshijlmbRk> findlist  =  this.findList(find);
			String biaom = "DATA_JIEGUO_"+yuanshijlmbRk.getZiduanlx()+"_";
			String enname = "FIELD1";
			//获取结果表名
			if(findlist.size()>0)
			{
                biaom =  findlist.get(0).getBiaom();
				String maxShuxing = this.dao.getMaxshuxing(biaom);
				if(StringUtils.isNotBlank(maxShuxing)){
					maxShuxing = "FIELD"+maxShuxing;
					yuanshijlmbRk.setEnname(maxShuxing);
					yuanshijlmbRk.setBiaom(biaom);
					this.dao.addLie(yuanshijlmbRk);//动态加列
					String sql =  "comment on column "+biaom+"."+maxShuxing+" is  '"+yuanshijlmbRk.getCnname()+"'";
					this.dao.addComment(sql);
				}
			}else{
				String maxBiao = this.dao.getMaxbiao(biaom);
				if(maxBiao==null) {
					biaom = biaom+"1";
				}else{
					biaom = biaom+maxBiao;
				}

				//新建表以及将当前列加入
				yuanshijlmbRk.setBiaom(biaom);
				yuanshijlmbRk.setEnname(enname);
				this.dao.addTable(yuanshijlmbRk);
				String sql =  "comment on column "+biaom+"."+enname+" is  '"+yuanshijlmbRk.getCnname()+"'";
				this.dao.addComment(sql);
			}


		}else{
			//更改已有字段的中文注释
			String sql =  "comment on column "+yuanshijlmbRk.getBiaom()+"."+yuanshijlmbRk.getEnname()+" is  '"+yuanshijlmbRk.getCnname()+"'";
			this.dao.addComment(sql);
		}
		super.save(yuanshijlmbRk);
	}

	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YuanshijlmbRk yuanshijlmbRk) {
		super.save(yuanshijlmbRk);
	}
	public void deleteRKXX(YuanshijlmbRk yuanshijlmbRk) {
		//获取当前字段所属表是否只有一个字段，如果是，则将该表删除 否则删除该字段对应表的列
		YuanshijlmbRk find = new YuanshijlmbRk();
		find.setMbid(yuanshijlmbRk.getMbid());
		find.setZiduanlx(yuanshijlmbRk.getZiduanlx());
		List<YuanshijlmbRk> findlist  =  this.findList(find);
		String sql = "";
		if(findlist.size()==1)
		{
			 sql = "drop table "+yuanshijlmbRk.getBiaom();
		}else{
             sql = "alter table "+yuanshijlmbRk.getBiaom()+" drop column "+yuanshijlmbRk.getEnname();
		}
		this.dao.addComment(sql);//执行sql语句
		super.delete(yuanshijlmbRk);

		//删除关联关系

		yuanshijlmbyxbqDao.deleteByRkId(yuanshijlmbRk.getMbid(),yuanshijlmbRk.getId());

	}
	@Transactional(readOnly = false)
	public void delete(YuanshijlmbRk yuanshijlmbRk) {
		super.delete(yuanshijlmbRk);
	}
	//插入入库数据
	@Transactional(readOnly = false)
	public Map selectJGData(String renwuid,String mbid){

		if(StringUtils.isNotBlank(mbid) && StringUtils.isNotBlank(renwuid)){
			Map returnMap = Maps.newHashMap();
			YuanshijlmbRk find = new YuanshijlmbRk();
			find.setMbid(mbid);
			find.setZiduanlx(PzConstans.ZiduanLx.BIAODAN);
			List<YuanshijlmbRk> findlist  =  this.findList(find);
			if(findlist.size()>0) {
				String allziduan ="";
				String[] ziduans = new String[findlist.size()];
				for (int i = 0; i < findlist.size(); i++) {
					allziduan += findlist.get(i).getEnname() + " as \""+findlist.get(i).getEnname()+"form\" ";
					ziduans[i] = findlist.get(i).getEnname()+"form";
					if(i<(findlist.size()-1)){
						allziduan+=",";
					}
				}
				String biaom = findlist.get(0).getBiaom();
				String sql = "select "+allziduan+" from  "+biaom +" where renwuid='"+renwuid+"'";
				List<Map> returnL =  this.dao.findJgListBySql(sql);
				for (int i = 0; i <returnL.size() ; i++) {
					for (int j = 0; j <ziduans.length ; j++) {
						if(returnL.get(i).get(ziduans[j])==null){
							returnL.get(i).put(ziduans[j],"");
						}
					}
				}
				if(returnL.size()>0){
					returnMap.put("formrkdata",returnL.get(0));
				}

			}
			find = new YuanshijlmbRk();
			find.setMbid(mbid);
			find.setZiduanlx(PzConstans.ZiduanLx.LIEBIAO);
			findlist  =  this.findList(find);
			if(findlist.size()>0) {
				String allziduan ="";
				String[] ziduans = new String[findlist.size()];
				for (int i = 0; i < findlist.size(); i++) {
					allziduan += findlist.get(i).getEnname()+ " as \""+findlist.get(i).getEnname()+"list\" ";
					ziduans[i] = findlist.get(i).getEnname()+"list";
					if(i<(findlist.size()-1)){
						allziduan+=",";
					}
				}
				String biaom = findlist.get(0).getBiaom();
				String sql = "select "+allziduan+" from  "+biaom +" where renwuid='"+renwuid+"' order by CREATE_DATE desc";
				List<Map> returnL =  this.dao.findJgListBySql(sql);
				for (int i = 0; i <returnL.size() ; i++) {
						for (int j = 0; j <ziduans.length ; j++) {
							if(returnL.get(i).get(ziduans[j])==null){
								returnL.get(i).put(ziduans[j],"");
							}
						}
				}
				if(returnL.size()>0){
					returnMap.put("listrkdata",returnL);
				}
			}
			return returnMap;
		}
         return null;
	}
	//插入入库数据
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void insertJGData(String jiluId,String renwuid,String mbid,Map dataMap,List<Map> dataList){
		YuanshijlmbRk find = new YuanshijlmbRk();
		find.setMbid(mbid);
		find.setZiduanlx(PzConstans.ZiduanLx.BIAODAN);
		List<YuanshijlmbRk> findlist  =  this.findList(find);
		User user = UserUtils.getUser();
		Date d = new Date();
		if(findlist.size()>0 && dataMap!=null)
		{
			String[] ziduans = new String[findlist.size()];
			for(int i=0;i<findlist.size();i++){
				ziduans[i] = findlist.get(i).getEnname();
			}
			String biaom = findlist.get(0).getBiaom();
			String sql = "delete from  "+biaom +" where renwuid='"+renwuid+"'";
			this.dao.addComment(sql);//先删除数据
			List<Map> jieguos = Lists.newArrayList();
			dataMap.put("id", IdGen.uuid());
			dataMap.put("renwuid",renwuid);
			if(dataMap.get("yangpinbh")==null)
			{
				dataMap.put("yangpinbh","");
			}

			dataMap.put("createBy",user);
			dataMap.put("updateBy",user);
			dataMap.put("updateDate",d);
			dataMap.put("createDate",d);
			dataMap.put("delFlag", Global.NO);
			jieguos.add(dataMap);
			for(Map m:jieguos){
				this.dao.insertJgBatchOne(m,ziduans,biaom);
			}
			//this.dao.insertJgBatch(jieguos,ziduans,biaom);
		}
		find = new YuanshijlmbRk();
		find.setMbid(mbid);
		find.setZiduanlx(PzConstans.ZiduanLx.LIEBIAO);
		findlist  =  this.findList(find);
		if(findlist.size()>0 /*&& dataList!=null*/)
		{
			String[] ziduans = new String[findlist.size()];
			for(int i=0;i<findlist.size();i++){
				ziduans[i] = findlist.get(i).getEnname();
			}
			String biaom = findlist.get(0).getBiaom();
			String sql = "delete from  "+biaom +" where renwuid='"+renwuid+"'";
			this.dao.addComment(sql);//先删除数据
			if(dataList!=null){
				for (int i = 0; i <dataList.size() ; i++) {
					if(dataList.get(i).get("yangpinbh")==null)
					{
						dataList.get(i).put("yangpinbh","");
					}
					dataList.get(i).put("id", IdGen.uuid());
					dataList.get(i).put("renwuid",renwuid);
					dataList.get(i).put("createBy",user);
					dataList.get(i).put("updateBy",user);
					dataList.get(i).put("updateDate",d);
					dataList.get(i).put("createDate",d);
					dataList.get(i).put("delFlag", Global.NO);
				}
				for(Map m:dataList){
					this.dao.insertJgBatchOne(m,ziduans,biaom);
				}
			}

			//this.dao.insertJgBatch(dataList,ziduans,biaom);
		}

		//修改任务记录id
		taskService.saveOriginRecord(renwuid,jiluId);
	}

	
}