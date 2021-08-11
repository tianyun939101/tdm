package com.demxs.tdm.service.cj;

import com.demxs.tdm.dao.cj.CjTableDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.cj.CjTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 采集信息表Service
 * @author 张仁
 * @version 2017-08-11
 */
@Service
@Transactional(readOnly = true)
public class CjTableService extends CrudService<CjTableDao, CjTable> {

	@Override
	public CjTable get(String id) {
		CjTable sjTable =  super.get(id);
		return sjTable;
	}
	
	@Override
	public List<CjTable> findList(CjTable cjTable) {
		return super.findList(cjTable);
	}
	
	@Override
	public Page<CjTable> findPage(Page<CjTable> page, CjTable cjTable) {
		return super.findPage(page, cjTable);
	}
	
	@Override
	@Transactional(readOnly = false)
	public void save(CjTable cjTable) {
		//oracle 表名长度限制30个字符,截取30位长度存储。
		String enName = "col_".concat(cjTable.getEnname());
		cjTable.setEnname(enName.length()<30?enName.substring(0,enName.length()):enName.substring(0,29));
		String sql = "";
		String cSql = "COMMENT ON TABLE ".concat(cjTable.getEnname()).concat(" IS ").concat("'").concat(cjTable.getCnname()).concat("'");
		if(StringUtils.isNotBlank(cjTable.getId())){//修改表名
			CjTable table = get(cjTable.getId());
			if(table!=null && StringUtils.isNotBlank(table.getEnname()) && !(cjTable.getEnname()).equals(table.getEnname())){
				sql = "ALTER TABLE ".concat(table.getEnname()).concat(" RENAME TO ").concat(cjTable.getEnname());
				this.dao.exesql(sql);//修改
			}
			if(table!=null && StringUtils.isNotBlank(table.getCnname()) && !(cjTable.getCnname()).equals(table.getCnname())){
				this.dao.exesql(cSql);//加注释
			}
		}else{//新建表
			sql = "create table ".concat(cjTable.getEnname())
					.concat("( id varchar(100) not null , LOGID varchar(100), constraint pk_")
					.concat(cjTable.getEnname().replaceFirst("col_","")).concat(" primary key(id))");
			this.dao.exesql(sql);//修改
			this.dao.exesql(cSql);//加注释
			this.dao.exesql("alter table "+cjTable.getEnname()+" add xuhao number");
			this.dao.exesql("alter table "+cjTable.getEnname()+" add syssamplecol varchar(100)");
		}

		super.save(cjTable);
	}

	/**
	 * 删除
	 * @param cjTable
	 */
	@Override
	@Transactional(readOnly = false)
	public void delete(CjTable cjTable) {
		try {
			cjTable.setEnname("col_".concat(cjTable.getEnname()));
			if(cjTable!=null && StringUtils.isNotBlank(cjTable.getEnname())){
                String sql = "drop table ".concat(cjTable.getEnname());
                this.dao.exesql(sql);//删除
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			super.delete(cjTable);
		}
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void batchDelete(String ids) {
		if(StringUtils.isNotBlank(ids)){
			String[] idsArr = ids.split(",");
			try {
				for(int i=0; i<idsArr.length; i++){
                    CjTable table = get(idsArr[i]);
                    if(table!=null && StringUtils.isNotBlank(table.getEnname())){
                        String sql = "drop table ".concat(table.getEnname());
                        this.dao.exesql(sql);//删除
                    }
                }
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				CjTable cjTable = new CjTable();
				cjTable.setArrIDS(idsArr);
				this.dao.batchDelete(cjTable);
			}
		}
	}
}