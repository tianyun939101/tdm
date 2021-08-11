package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzQjhcjhDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzQjhcjh;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzQjhcjl;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准物质期间核查计划Service
 * @author zhangdengcai
 * @version 2017-07-19
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzQjhcjhService extends CrudService<BiaozhunwzQjhcjhDao, BiaozhunwzQjhcjh> {

	@Autowired
	private BiaozhunwzQjhcjlService biaozhunwzQjhcjlService;
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public BiaozhunwzQjhcjh get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<BiaozhunwzQjhcjh> findList(BiaozhunwzQjhcjh biaozhunwzQjhcjh) {
		return super.findList(biaozhunwzQjhcjh);
	}
	
	@Override
	public Page<BiaozhunwzQjhcjh> findPage(Page<BiaozhunwzQjhcjh> page, BiaozhunwzQjhcjh biaozhunwzQjhcjh) {
		return super.findPage(page, biaozhunwzQjhcjh);
	}

	/**
	 * 根据标准物质主键查询 计划列表
	 * @param page
	 * @param biaozhunwzQjhcjh
	 * @return
	 */
	public Page<BiaozhunwzQjhcjh> findPageByBzwzId(Page<BiaozhunwzQjhcjh> page, BiaozhunwzQjhcjh biaozhunwzQjhcjh) {
		biaozhunwzQjhcjh.setPage(page);
		List<BiaozhunwzQjhcjh> jhs = this.dao.findPageByBzwzId(biaozhunwzQjhcjh);
		if(jhs!=null && jhs.size()>0){
			for (BiaozhunwzQjhcjh jh : jhs) {
				if(jh.getCreateBy()!=null){
					User user = UserUtils.get(jh.getCreateBy().getId());
					jh.setCreateBy(user);
				}
			}
		}
		page.setList(jhs);
		return page;
	}
	/**
	 * 根据标准物质主键查询 计划列表
	 * @param page
	 * @param biaozhunwzQjhcjh
	 * @return
	 */
	public Page<BiaozhunwzQjhcjh> findPageByBzwzIdForOther(Page<BiaozhunwzQjhcjh> page, BiaozhunwzQjhcjh biaozhunwzQjhcjh) {
		page.setOrderBy("a.update_date desc");
		biaozhunwzQjhcjh.setPage(page);
		List<BiaozhunwzQjhcjh> jhs = this.dao.findPageByBzwzId(biaozhunwzQjhcjh);
		if(jhs!=null && jhs.size()>0){
			for (BiaozhunwzQjhcjh jh : jhs) {
				if(jh.getCreateBy()!=null){
					User user = UserUtils.get(jh.getCreateBy().getId());
					jh.setCreateBy(user);
				}
			}
		}
		page.setList(jhs);
		return page;
	}

	/**
	 * 保存
	 * @param biaozhunwzQjhcjh
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(BiaozhunwzQjhcjh biaozhunwzQjhcjh) {
		String id = "";
		if(StringUtils.isNotBlank(biaozhunwzQjhcjh.getId())){//修改
			id = biaozhunwzQjhcjh.getId();
			super.save(biaozhunwzQjhcjh);
		}else{//新增
			biaozhunwzQjhcjh.preInsert();
			id = IdGen.uuid();
			biaozhunwzQjhcjh.setId(id);
			this.dao.insert(biaozhunwzQjhcjh);
		}

		//保存任务
		List<String> newIds = new ArrayList<String>();
		List<BiaozhunwzQjhcjl> hcrws = biaozhunwzQjhcjh.getHechajl();
		if(hcrws!=null && hcrws.size()>0){
			for (BiaozhunwzQjhcjl hcrw : hcrws) {
				hcrw.setHechajhid(id);
				biaozhunwzQjhcjlService.save(hcrw);
				newIds.add(hcrw.getId());
			}
		}

		List<BiaozhunwzQjhcjl> oldrws = biaozhunwzQjhcjlService.listByHcjhId(id);
		if(oldrws!=null && !oldrws.isEmpty()){
			for (BiaozhunwzQjhcjl rw : oldrws) {
				if(!newIds.contains(rw.getId())){
					biaozhunwzQjhcjlService.delete(rw);//删除 库中有但，页面没有的
				}
			}
		}
	}

	/**
	 * 详情
	 * @param biaozhunwzQjhcjh
	 * @return
	 */
	@Override
	public BiaozhunwzQjhcjh get(BiaozhunwzQjhcjh biaozhunwzQjhcjh) {
		BiaozhunwzQjhcjh hcjh = super.get(biaozhunwzQjhcjh);
		if(hcjh!=null){
			List<BiaozhunwzQjhcjl> hcjls = biaozhunwzQjhcjlService.listByHcjhId(hcjh.getId());
			if(hcjls!=null && hcjls.size()>0){
				for (BiaozhunwzQjhcjl hcjl : hcjls) {
					Attachment attach = new Attachment();
					attach.setCodeId(hcjl.getId());
					attach.setColumnName("qijianhczl");
					List<Attachment> ziliao = attachmentService.findList(attach);
					hcjl.setZiliao(ziliao);
				}
				hcjh.setHechajl(hcjls);
			}
		}
		return hcjh;
	}

	/**
	 *
	 * @param biaozhunwzQjhcjh
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(BiaozhunwzQjhcjh biaozhunwzQjhcjh) {
		super.delete(biaozhunwzQjhcjh);
	}

	/**
	 * 批量删除
	 * @param ids
	 */
	public void deleteMore(String ids){
		String[] idArray = null;
		if(StringUtils.isNotBlank(ids)){
			if(ids.contains(",")){
				idArray = ids.split(",");
			}else {
				idArray = new String[1];
				idArray[0] = ids;
			}
		}
		BiaozhunwzQjhcjh biaozhunwzQjhcjh = new BiaozhunwzQjhcjh();
		biaozhunwzQjhcjh.setArrIDS(idArray);
		this.dao.deleteMore(biaozhunwzQjhcjh);
	}
}