package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzCgqdmxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzCgqdmx;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准物质采购清单明细Service
 * @author zhangdengcai
 * @version 2017-07-18
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzCgqdmxService extends CrudService<BiaozhunwzCgqdmxDao, BiaozhunwzCgqdmx> {
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public BiaozhunwzCgqdmx get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<BiaozhunwzCgqdmx> findList(BiaozhunwzCgqdmx biaozhunwzCgqdmx) {
		return super.findList(biaozhunwzCgqdmx);
	}
	
	@Override
	public Page<BiaozhunwzCgqdmx> findPage(Page<BiaozhunwzCgqdmx> page, BiaozhunwzCgqdmx biaozhunwzCgqdmx) {
		return super.findPage(page, biaozhunwzCgqdmx);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(BiaozhunwzCgqdmx biaozhunwzCgqdmx) {
		String id = "";
		String zls = biaozhunwzCgqdmx.getCaigouzl();//设备资料附件ID
		Attachment[] zlAtt = fujianshuzu(zls);

		if(StringUtils.isNotBlank(biaozhunwzCgqdmx.getId())){//修改
			id = biaozhunwzCgqdmx.getId();
			super.save(biaozhunwzCgqdmx);
		}else{//新增
			biaozhunwzCgqdmx.preInsert();
			id = IdGen.uuid();
			biaozhunwzCgqdmx.setId(id);//覆盖id
			this.dao.insert(biaozhunwzCgqdmx);
		}
		//附件关联业务ID
		if(zlAtt.length>0){
			attachmentService.guanlianfujian(zlAtt, id, "bzwzcgzl");
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(BiaozhunwzCgqdmx biaozhunwzCgqdmx) {
		super.delete(biaozhunwzCgqdmx);
	}

	/**
	 * 获得附件数组
	 * @param attIds
	 * @return
	 */
	public Attachment[] fujianshuzu(String attIds){
		List<Attachment> attList = new ArrayList<Attachment>();
		if(attIds!=null && attIds!=""){
			if(attIds.contains(",")){
				String[] tpArr = attIds.split(",");
				for(int i=0; i<tpArr.length; i++){
					Attachment att = new Attachment();
					att.setId(tpArr[i]);
					attList.add(att);
				}
			}else{
				Attachment att = new Attachment();
				att.setId(attIds);
				attList.add(att);
			}
		}
		Attachment[] attArray = new Attachment[attList.size()];
		return attList.size()==0? new Attachment[0] : attList.toArray(attArray);
	}

	/**
	 * 根据采购单主键 获取采购的标准物质
	 * @param cgdzj
	 * @return
	 */
	public List<BiaozhunwzCgqdmx> listByCgdzj(String cgdzj){
		BiaozhunwzCgqdmx biaozhunwzCgqdmx = new BiaozhunwzCgqdmx();
		biaozhunwzCgqdmx.setCaigoudzj(cgdzj);
		List<BiaozhunwzCgqdmx> cgqdmxes = this.dao.listByCgdzj(biaozhunwzCgqdmx);
		if(cgqdmxes!=null && !cgqdmxes.isEmpty()){
			for (BiaozhunwzCgqdmx mx : cgqdmxes) {
				Attachment attach = new Attachment();
				attach.setCodeId(mx.getId());
				attach.setColumnName("bzwzcgzl");
				List<Attachment> attachs = attachmentService.findList(attach);
				mx.setZiliao(attachs);
			}
		}
		return cgqdmxes;
	}
}