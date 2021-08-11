package com.demxs.tdm.service.configure;

import com.demxs.tdm.dao.configure.YuanshijlmbyxbqDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.zrutils.BiaoqianJson;
import com.demxs.tdm.domain.configure.Yuanshijlmbyxbq;
import com.demxs.tdm.comac.common.constant.PzConstans;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 原始记录已选标签Service
 * @author 张仁
 * @version 2017-07-24
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YuanshijlmbyxbqService extends CrudService<YuanshijlmbyxbqDao, Yuanshijlmbyxbq> implements IYuanshijlmbyxbqService{

	@Override
	public Yuanshijlmbyxbq get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<Yuanshijlmbyxbq> findList(Yuanshijlmbyxbq yuanshijlmbyxbq) {
		return super.findList(yuanshijlmbyxbq);
	}
	
	@Override
	public Page<Yuanshijlmbyxbq> findPage(Page<Yuanshijlmbyxbq> page, Yuanshijlmbyxbq yuanshijlmbyxbq) {
		return super.findPage(page, yuanshijlmbyxbq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(Yuanshijlmbyxbq yuanshijlmbyxbq) {
		super.save(yuanshijlmbyxbq);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(Yuanshijlmbyxbq yuanshijlmbyxbq) {
		super.delete(yuanshijlmbyxbq);
	}
	/**
	 * 查询某个原始记录模板的已选标签 （非采集类的）
	 * @param mbid  模板id
	 * @param biaoqianlx  标签类型
	 * @return
	 */
	@Override
	public List<BiaoqianJson> getYuanshijlmbyxbqList(String mbid, String biaoqianlx){
		Yuanshijlmbyxbq find =new Yuanshijlmbyxbq();
		find.setMbid(mbid);
		find.setBiaoqianlx(biaoqianlx);
		List<Yuanshijlmbyxbq>  list  = this.findList(find);
		List<BiaoqianJson> listm = new ArrayList<BiaoqianJson>();
		for (int i = 0; i <list.size() ; i++) {
			listm.add((BiaoqianJson)BiaoqianJson.returnForNewObject(list.get(i),new BiaoqianJson()));
		}
		return  listm;
	}

	/**
	 * 查询某个原始记录模板的已选标签 （采集类的）
	 * @param mbid  模板id
	 * @param biaoqianlx  标签类型
	 * @return
	 */
	@Override
	public List<BiaoqianJson> getCJYuanshijlmbyxbqList(String mbid, String biaoqianlx){
		Yuanshijlmbyxbq find =new Yuanshijlmbyxbq();
		find.setMbid(mbid);
		find.setCaijilx(biaoqianlx);
		List<Yuanshijlmbyxbq>  list  = this.findList(find);
		List<BiaoqianJson> listm = new ArrayList<BiaoqianJson>();
		for (int i = 0; i <list.size() ; i++) {
			listm.add((BiaoqianJson)BiaoqianJson.returnForNewObject(list.get(i),new BiaoqianJson()));
		}
		return  listm;
	}


	/**
	 * 查询某个原始记录模板的已选标签
	 * @param mbid  模板id
	 * @return
	 */
	public Map getAllYuanshijlmbyxbqJson(String mbid){
		Map returnMap = Maps.newHashMap();
		Yuanshijlmbyxbq find =new Yuanshijlmbyxbq();
		find.setMbid(mbid);
		find.setBiaoqianlx(PzConstans.Biaoqianlx.BIAODAN);
		List<Yuanshijlmbyxbq>  list  = this.findList(find);
		returnMap.put(PzConstans.Biaoqianlx.BIAODAN,list);
		find =new Yuanshijlmbyxbq();
		find.setMbid(mbid);
		find.setBiaoqianlx(PzConstans.Biaoqianlx.GONGJU);
		list  = this.findList(find);
		returnMap.put(PzConstans.Biaoqianlx.GONGJU,list);
		find =new Yuanshijlmbyxbq();
		find.setMbid(mbid);
		find.setBiaoqianlx(PzConstans.Biaoqianlx.LIEBIAO);
		list  = this.findList(find);
		returnMap.put(PzConstans.Biaoqianlx.LIEBIAO,list);
		return  returnMap;
	}

}