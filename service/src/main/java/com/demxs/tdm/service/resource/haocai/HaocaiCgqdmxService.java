package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiCgqdmxDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.haocai.HaocaiCgqdmx;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 耗材采购清单明细Service
 * @author zhangdengcai
 * @version 2017-07-17
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiCgqdmxService extends CrudService<HaocaiCgqdmxDao, HaocaiCgqdmx> {
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public HaocaiCgqdmx get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<HaocaiCgqdmx> findList(HaocaiCgqdmx haocaiCgqdmx) {
		return super.findList(haocaiCgqdmx);
	}
	
	@Override
	public Page<HaocaiCgqdmx> findPage(Page<HaocaiCgqdmx> page, HaocaiCgqdmx haocaiCgqdmx) {
		return super.findPage(page, haocaiCgqdmx);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiCgqdmx haocaiCgqdmx) {
		String id = "";
		String zls = haocaiCgqdmx.getCaigouzl();//设备资料附件ID
		Attachment[] zlAtt = fujianshuzu(zls);

		if(StringUtils.isNotBlank(haocaiCgqdmx.getId())){//修改
			id = haocaiCgqdmx.getId();
			super.save(haocaiCgqdmx);
		}else{//新增
			haocaiCgqdmx.preInsert();
			id = IdGen.uuid();
			haocaiCgqdmx.setId(id);//覆盖id
			this.dao.insert(haocaiCgqdmx);
		}
		//附件关联业务ID
		if(zlAtt.length>0){
			attachmentService.guanlianfujian(zlAtt, id, "haocaicgzl");
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiCgqdmx haocaiCgqdmx) {
		super.delete(haocaiCgqdmx);
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
	 * 根据采购清单主键 获取采购的耗材
	 * @param cgqdzj
	 * @return
	 */
	public List<HaocaiCgqdmx> listByCgqdzj(String cgqdzj){
		HaocaiCgqdmx haocaiCgqdmx = new HaocaiCgqdmx();
		haocaiCgqdmx.setCaigoudzj(cgqdzj);
		List<HaocaiCgqdmx> cgqdmxes = this.dao.listByCgqdzj(haocaiCgqdmx);
		if(cgqdmxes!=null && !cgqdmxes.isEmpty()){
			for (HaocaiCgqdmx mx : cgqdmxes) {
				Attachment attach = new Attachment();
				attach.setCodeId(mx.getId());
				attach.setColumnName("haocaicgzl");
				List<Attachment> attachs = attachmentService.findList(attach);
				mx.setZiliao(attachs);
			}
		}
		return cgqdmxes;
	}
}