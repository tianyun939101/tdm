package com.demxs.tdm.service.cj;

import com.demxs.tdm.dao.cj.CjIndexDao;
import com.demxs.tdm.dao.cj.CjTableDao;
import com.demxs.tdm.dao.cj.GuizeDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.cj.CjIndex;
import com.demxs.tdm.domain.cj.CjTable;
import com.demxs.tdm.domain.cj.Guize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采集索引Service
 * @author 郭金龙
 * @version 2017-10-12
 */
@Service
@Transactional(readOnly = true)
public class CjIndexService extends CrudService<CjIndexDao, CjIndex> {

	@Autowired
	private GuizeDao guizeDao;

	@Autowired
	private CjTableDao cjTableDao;

	@Override
	public CjIndex get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CjIndex> findList(CjIndex cjIndex) {
		return super.findList(cjIndex);
	}
	
	@Override
	public Page<CjIndex> findPage(Page<CjIndex> page, CjIndex cjIndex) {
		return super.findPage(page, cjIndex);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CjIndex cjIndex) {
		super.save(cjIndex);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void delete(CjIndex cjIndex) {
		super.delete(cjIndex);
	}

	/**
	 * 根据规则ID、任务编号ID返回数据表信息
	 * */
	public List<Map> dataTableResult(String renwubh, String guizeid){
		String sql = "";
		String tablename = "";
		//根据规则ID找到表名
		Guize guize = guizeDao.get(guizeid);
		String dataid = guize.getDataid();
		if(StringUtils.isNotBlank(dataid)){
			CjTable cjTable = cjTableDao.get(dataid);
			tablename = cjTable.getEnname();
		}else{
			return null;
		}
		//查询索引
		CjIndex searchIndex = new CjIndex();
		searchIndex.setGuizeid(guizeid);
		searchIndex.setRenwuid(renwubh);

		//获取所有Logid
		List<CjIndex> cjIndexList = this.dao.findList(searchIndex);
		String logids =  Collections3.extractToString(cjIndexList, "logid", ",");

		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from  "+tablename+" where instr('"+logids+"',logid)>0");
		return this.dao.exesql(selectSql.toString());
	}

	/**
	 * 根据规则ID、任务编号ID、样品编号返回数据表信息
	 * @param renwubh 任务编号
	 * @param guizeid 规则ID
	 * @param yangpinbh 样品编号
	 * @return
	 */
	public List<Map> dataTableResult(String renwubh, String guizeid, String yangpinbh){
		String sql = "";
		String tablename = "";
		//根据规则ID找到表名
		Guize guize = guizeDao.get(guizeid);
		String dataid = guize.getDataid();
		CjTable cjTable = new CjTable();
		if(StringUtils.isNotBlank(dataid)){
			cjTable = cjTableDao.get(dataid);
			tablename = cjTable.getEnname();
		}else{
			return null;
		}
		//查询索引
		CjIndex searchIndex = new CjIndex();
		searchIndex.setGuizeid(guizeid);
		searchIndex.setRenwuid(renwubh);
		searchIndex.setYangpinid(yangpinbh);
		//获取所有Logid
		List<CjIndex> cjIndexList = this.dao.findList(searchIndex);
		String logids =  Collections3.extractToString(cjIndexList, "logid", ",");

		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from  "+tablename+" where instr('"+logids+"',logid)>0 and "+cjTable.getShiyangbszd()+" = '"+yangpinbh+"'");
		return this.dao.exesql(selectSql.toString());
	}

	/**
	 * 根据规则ID、任务编号ID、样品编号返回数据中心所需数据
	 * @param renwubh 任务编号
	 * @param guizeid 规则ID
	 * @param yangpinbhs 样品编号集合
	 * @param xAxis X轴
	 * @param yAxiss Y轴集合
	 * @return
	 */
	public List<Map> getDataCentersResult(String renwubh, String guizeid, String yangpinbhs, String xAxis, String yAxiss){

		String sql = "";
		String tablename = "";
		//根据规则ID找到表名
		Guize guize = guizeDao.get(guizeid);
		String dataid = guize.getDataid();
		CjTable cjTable = new CjTable();
		if(StringUtils.isNotBlank(dataid)){
			cjTable = cjTableDao.get(dataid);
			tablename = cjTable.getEnname();
		}else{
			return null;
		}
		List<Map> resultList = new ArrayList<Map>();
		for(String yangpinbh:yangpinbhs.split(",")){
			Map resultMap = new HashMap();
			//设置样品编号
			resultMap.put("yangpinid",yangpinbh);
			//查询索引
			CjIndex searchIndex = new CjIndex();
			searchIndex.setGuizeid(guizeid);
			searchIndex.setRenwuid(renwubh);
			searchIndex.setYangpinid(yangpinbh);

			//获取所有Logid
			List<CjIndex> cjIndexList = this.dao.findList(searchIndex);
			String logids =  Collections3.extractToString(cjIndexList, "logid", ",");

			//1、获取X轴数据
			StringBuilder selectXAxisSql = new StringBuilder();
			selectXAxisSql.append("select "+xAxis+" from  "+tablename+" where instr('"+logids+"',logid)>0");
			List<Map> xAxisList = this.dao.exesql(selectXAxisSql.toString());

			Object[] xda = new Object[xAxisList.size()];
			for(int i = 0; i<xAxisList.size();i++){
				Map map = xAxisList.get(i);
				Object obj = map.get(xAxis.toUpperCase());
				xda[i] = obj;
                resultMap.put("xliedata",xda);
			}

			//2、获取Y轴数据
			String[] ylis = yAxiss.split(",");
			for (int j = 0; j < ylis.length; j++) {
				Object[] da = new Object[xda.length];
				for (int k = 0; k < da.length; k++) {
					StringBuilder selectYAxisSql = new StringBuilder();
					selectYAxisSql.append("select "+yAxiss+" from  "+tablename+" where instr('"+logids+"',logid)>0 and "+ xAxis+" = '"+ xda[k] + "'");
					List<Map> yAxisList = this.dao.exesql(selectYAxisSql.toString());
					for(int y = 0; y<yAxisList.size();y++){
						Map map = yAxisList.get(0);
						Object obj = map.get(ylis[j].toUpperCase());
						da[k]= obj;
					}
				}
				resultMap.put(ylis[j],da);
			}
			resultList.add(resultMap);
		}


		return resultList;
	}

	/**
	 * 根据规则ID、任务ID返回信息表数据
	 * @param renwubh 任务编号
	 * @param guizeid 规则ID
	 * */
	public List<Map> infoTableResult(String renwubh, String guizeid){
		String sql = "";
		String tablename = "";
		//根据规则ID找到表名
		Guize guize = guizeDao.get(guizeid);
		String infoid = guize.getInfoid();
		if(StringUtils.isNotBlank(infoid)){
			CjTable cjTable = cjTableDao.get(infoid);
			tablename = cjTable.getEnname();
		}else{
			return null;
		}
		//查询索引
		CjIndex searchIndex = new CjIndex();
		searchIndex.setGuizeid(guizeid);
		searchIndex.setRenwuid(renwubh);

		//获取所有Logid
		List<CjIndex> cjIndexList = this.dao.findList(searchIndex);
		String logids =  Collections3.extractToString(cjIndexList, "logid", ",");

		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from  "+tablename+" where instr('"+logids+"',logid)>0");
		return this.dao.exesql(selectSql.toString());
	}

	/**
	 * 根据规则ID、任务ID返回信息表数据
	 * @param renwubh 任务编号
	 * @param guizeid 规则ID
	 * @param yangpinbh 样品编号
	 * @return
	 */
    public List<Map> infoTableResult(String renwubh, String guizeid, String yangpinbh){
		String sql = "";
		String tablename = "";
		//根据规则ID找到表名
		Guize guize = guizeDao.get(guizeid);
		String infoid = guize.getInfoid();
		if(StringUtils.isNotBlank(infoid)){
			CjTable cjTable = cjTableDao.get(infoid);
			tablename = cjTable.getEnname();
		}else{
			return null;
		}
		//查询索引
		CjIndex searchIndex = new CjIndex();
		searchIndex.setGuizeid(guizeid);
		searchIndex.setRenwuid(renwubh);
		searchIndex.setYangpinid(yangpinbh);
		//获取所有Logid
		List<CjIndex> cjIndexList = this.dao.findList(searchIndex);
		String logids =  Collections3.extractToString(cjIndexList, "logid", ",");

		StringBuilder selectSql = new StringBuilder();
		selectSql.append("select * from  "+tablename+" where instr('"+logids+"',logid)>0");
		return this.dao.exesql(selectSql.toString());
	}
	
}