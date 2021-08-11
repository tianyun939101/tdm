package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.dao.resource.shebei.ShebeiQjhcjhDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.shebei.ShebeiQjhcjh;
import com.demxs.tdm.domain.resource.shebei.ShebeiQjhcjl;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备期间核查计划Service
 * @author zhangdengcai
 * @version 2017-07-13
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiQjhcjhService extends CrudService<ShebeiQjhcjhDao, ShebeiQjhcjh> {
	@Autowired
	private ShebeiQjhcjlService shebeiQjhcjlService;
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public ShebeiQjhcjh get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ShebeiQjhcjh> findList(ShebeiQjhcjh shebeiQjhcjh) {
		return super.findList(shebeiQjhcjh);
	}
	
	@Override
	public Page<ShebeiQjhcjh> findPage(Page<ShebeiQjhcjh> page, ShebeiQjhcjh shebeiQjhcjh) {
		return super.findPage(page, shebeiQjhcjh);
	}

	/**
	 * 根据设备id 获取期间核查计划列表
	 * @param page
	 * @param shebeiQjhcjh
	 * @return
	 */
	public Page<ShebeiQjhcjh> findPageByShebeiid(Page<ShebeiQjhcjh> page, ShebeiQjhcjh shebeiQjhcjh) {
//		page.setOrderBy("a.update_date desc");
		shebeiQjhcjh.setPage(page);
		List<ShebeiQjhcjh> jhs = this.dao.lsitByShebeiid(shebeiQjhcjh);
		if(jhs!=null && jhs.size()>0){
			for (ShebeiQjhcjh jh : jhs) {
				if(jh.getCreateBy()!=null && StringUtils.isNotBlank(jh.getCreateBy().getId())){
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
	 * @param shebeiQjhcjh
	 */
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiQjhcjh shebeiQjhcjh) {
		String id = "";
		if(StringUtils.isNotBlank(shebeiQjhcjh.getId())){//修改
			id = shebeiQjhcjh.getId();
			super.save(shebeiQjhcjh);
		}else{//新增
			shebeiQjhcjh.preInsert();
			id = IdGen.uuid();
			shebeiQjhcjh.setId(id);
			this.dao.insert(shebeiQjhcjh);
		}

		//保存任务
		List<String> newIds = new ArrayList<String>();
		List<ShebeiQjhcjl> hcrws = shebeiQjhcjh.getHechajl();
		if(hcrws!=null && hcrws.size()>0){
			for (ShebeiQjhcjl hcrw : hcrws) {
				hcrw.setHechajhid(id);
				shebeiQjhcjlService.save(hcrw);
				newIds.add(hcrw.getId());
			}
		}

		List<ShebeiQjhcjl> oldRws = shebeiQjhcjlService.listByHcjhid(id);//库中任务
		if(oldRws!=null && !oldRws.isEmpty()){
			for (ShebeiQjhcjl rw : oldRws) {
				if(!newIds.contains(rw.getId())){
					shebeiQjhcjlService.delete(rw);//删除调库中有，而页面上没有的
				}
			}
		}
	}

	/**
	 * 获取详情
	 * @param shebeiQjhcjh
	 * @return
	 */
	@Override
	public ShebeiQjhcjh get(ShebeiQjhcjh shebeiQjhcjh) {
		ShebeiQjhcjh hcjh = super.get(shebeiQjhcjh);
		if(hcjh!=null){
			List<ShebeiQjhcjl> hcjls = shebeiQjhcjlService.listByHcjhid(hcjh.getId());
			if(hcjls!=null && hcjls.size()>0){
				for (ShebeiQjhcjl hcjl : hcjls) {
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
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiQjhcjh shebeiQjhcjh) {
		super.delete(shebeiQjhcjh);
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
		ShebeiQjhcjh shebeiQjhcjh = new ShebeiQjhcjh();
		shebeiQjhcjh.setArrIDS(idArray);
		this.dao.deleteMore(shebeiQjhcjh);
	}

	/**
	 * 全部设备的期间核查计划
	 * @param page
	 * @param shebeiQjhcjh
	 * @return
	 */
	public Page<ShebeiQjhcjh> findAllPage(Page<ShebeiQjhcjh> page, ShebeiQjhcjh shebeiQjhcjh) {
//		page.setOrderBy("a.create_date desc");
		shebeiQjhcjh.setPage(page);
		page.setList(this.dao.findAllList(shebeiQjhcjh));
		return page;
	}
}