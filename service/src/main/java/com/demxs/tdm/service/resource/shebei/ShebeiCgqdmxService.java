package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.dao.resource.shebei.ShebeiCgqdmxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.shebei.ShebeiCgqdmx;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备采购清单明细Service
 * @author zhangdengcai
 * @version 2017-07-15
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiCgqdmxService extends CrudService<ShebeiCgqdmxDao, ShebeiCgqdmx> {
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public ShebeiCgqdmx get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ShebeiCgqdmx> findList(ShebeiCgqdmx shebeiCgqdmx) {
		return super.findList(shebeiCgqdmx);
	}
	
	@Override
	public Page<ShebeiCgqdmx> findPage(Page<ShebeiCgqdmx> page, ShebeiCgqdmx shebeiCgqdmx) {
		return super.findPage(page, shebeiCgqdmx);
	}

	/**
	 * 根据采购清单获取
	 * @param cgqdzj
	 * @return
	 */
	public List<ShebeiCgqdmx> listByShebeicgqdzj(String cgqdzj){
		ShebeiCgqdmx shebeiCgqdmx = new ShebeiCgqdmx();
		shebeiCgqdmx.setCaigouqdzj(cgqdzj);
		List<ShebeiCgqdmx> cgqdmxes = this.dao.listByShebeicgqdzj(shebeiCgqdmx);
		if(cgqdmxes!=null && !cgqdmxes.isEmpty()){
			for (ShebeiCgqdmx mx : cgqdmxes) {
				Attachment attach = new Attachment();
				attach.setCodeId(mx.getId());
				attach.setColumnName("shebeicgzl");
				List<Attachment> attachs = attachmentService.findList(attach);
				mx.setZiliao(attachs);
			}
		}
		return cgqdmxes;
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiCgqdmx shebeiCgqdmx) {
		String id = "";
		String zls = shebeiCgqdmx.getCaigouzl();//设备资料附件ID
		Attachment[] zlAtt = fujianshuzu(zls);

		if(StringUtils.isNotBlank(shebeiCgqdmx.getId())){//修改
			id = shebeiCgqdmx.getId();
			super.save(shebeiCgqdmx);
		}else{//新增
			shebeiCgqdmx.preInsert();
			id = IdGen.uuid();
			shebeiCgqdmx.setId(id);//覆盖id
			this.dao.insert(shebeiCgqdmx);
		}
		//附件关联业务ID
		if(zlAtt.length>0){
			attachmentService.guanlianfujian(zlAtt, id, "shebeicgzl");
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiCgqdmx shebeiCgqdmx) {
		super.delete(shebeiCgqdmx);
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
	 * 批量插入
	 * @param shebeiCgqdmxs
	 */
	public void batchInsert(List<ShebeiCgqdmx> shebeiCgqdmxs){
		this.dao.batchInsert(shebeiCgqdmxs);
	}
}