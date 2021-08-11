package com.demxs.tdm.service.cj;

import com.demxs.tdm.dao.cj.CjZiduanDao;
import com.demxs.tdm.dao.cj.GuizeDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.cj.CjTable;
import com.demxs.tdm.domain.cj.CjZiduan;
import com.demxs.tdm.domain.cj.Guize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采集字段信息Service
 * @author 张仁
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class CjZiduanService extends CrudService<CjZiduanDao, CjZiduan> {

	@Autowired
	private CjTableService cjTableService;

	@Override
	public CjZiduan get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<CjZiduan> findList(CjZiduan cjZiduan) {
		return super.findList(cjZiduan);
	}
	
	@Override
	public Page<CjZiduan> findPage(Page<CjZiduan> page, CjZiduan cjZiduan) {
		return super.findPage(page, cjZiduan);
	}

	@Autowired
	private GuizeDao guizeDao;
	
	@Override
	@Transactional(readOnly = false)
	public void save(CjZiduan cjZiduan) {
		String sql = "";
		String cSql = "";
		if(StringUtils.isNotBlank(cjZiduan.getCjtableid())){
			CjTable table = cjTableService.get(cjZiduan.getCjtableid());
			if(table!=null && StringUtils.isNotBlank(table.getEnname())){
				cSql = "comment on column ".concat(table.getEnname()).concat(".").concat(cjZiduan.getEnname()).concat(" is '").concat(cjZiduan.getCnname()).concat("'");
				if(StringUtils.isNotBlank(cjZiduan.getId())){//修改
					CjZiduan ziduan = get(cjZiduan.getId());
					if(ziduan!=null && StringUtils.isNotBlank(ziduan.getEnname())){
						if(StringUtils.isNotBlank(cjZiduan.getEnname()) && !(cjZiduan.getEnname()).equals(ziduan.getEnname())){//列名改变
							sql = "alter table ".concat(table.getEnname()).concat(" RENAME COLUMN ").concat(ziduan.getEnname()).concat(" to ").concat(cjZiduan.getEnname());
							this.dao.exesql(sql);//修改
						}
						if(StringUtils.isNotBlank(cjZiduan.getCnname()) && !(cjZiduan.getCnname()).equals(ziduan.getCnname())){//字段中文名改变
							cSql = "comment on column ".concat(table.getEnname()).concat(".").concat(cjZiduan.getEnname()).concat(" is '").concat(cjZiduan.getCnname()).concat("'");
							this.dao.exesql(cSql);//加注释
						}
					}
				}else{//新增
					sql = "alter table ".concat(table.getEnname()).concat(" add ").concat(cjZiduan.getEnname()).concat(" ").concat(cjZiduan.getZiduanlx()).concat(" (").concat(String.valueOf(cjZiduan.getZiduancd())).concat(")");
					this.dao.exesql(sql);//新增
					this.dao.exesql(cSql);//加注释
				}
			}
		}

		super.save(cjZiduan);
	}

	/**
	 * 删除
	 * @param cjZiduan
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(CjZiduan cjZiduan) {
		try {
			if(StringUtils.isNotBlank(cjZiduan.getCjtableid())) {
                String sql = "";
                CjTable table = cjTableService.get(cjZiduan.getCjtableid());
                if(table!=null && StringUtils.isNotBlank(table.getEnname()) && StringUtils.isNotBlank(cjZiduan.getEnname())){
                    sql = "alter table ".concat(table.getEnname()).concat(" drop column ").concat(cjZiduan.getEnname());
                    this.dao.exesql(sql);//删除
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.delete(cjZiduan);
		}
	}

	/**
	 * 根据规则ID获取信息表字段的ID
	 * */
	public List<Map> findInfoZiduan(String guizeid){
		Guize guize = guizeDao.get(guizeid);
		CjZiduan cjZiduan = new CjZiduan();
		if(guize != null && guize.getInfoid() != null){
			cjZiduan.setCjtableid(guize.getInfoid());
		}else{
			return null;
		}
		return this.dao.findZiduan(cjZiduan);
	}

	/**
	 * 根据规则ID获取列表字段的ID
	 * */
	public List<Map> findDataZiduan(String guizeid){
		Guize guize = guizeDao.get(guizeid);
		CjZiduan cjZiduan = new CjZiduan();
		if(guize != null && guize.getDataid() != null){
			cjZiduan.setCjtableid(guize.getDataid());
		}else{
			return null;
		}
		return this.dao.findZiduan(cjZiduan);
	}

	/**
	 * 根据字段ID获取中英文名称
	 * */
	public Map findMingCheng(String id){
		if("syssamplecol".equals(id)){
			Map map = new HashMap();
			map.put("cnname","系统样品标识");
			map.put("enname","syssamplecol");
			return map;
		}
		CjZiduan cjZiduan = new CjZiduan();
		cjZiduan.setId(id);
		return this.dao.findMingCheng(cjZiduan);
	}
	
}