package com.demxs.tdm.service.resource.biaozhunwuzhi;

import com.demxs.tdm.dao.resource.biaozhunwuzhi.BiaozhunwzPdqdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.biaozhunwuzhi.BiaozhunwzPdqd;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 标准物质盘点清单（记录）Service
 * @author zhangdengcai
 * @version 2017-08-31
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class BiaozhunwzPdqdService extends CrudService<BiaozhunwzPdqdDao, BiaozhunwzPdqd> {
	@Autowired
	private AttachmentService attachmentService;

	@Override
	public BiaozhunwzPdqd get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<BiaozhunwzPdqd> findList(BiaozhunwzPdqd biaozhunwzPdqd) {
		return super.findList(biaozhunwzPdqd);
	}
	
	@Override
	public Page<BiaozhunwzPdqd> findPage(Page<BiaozhunwzPdqd> page, BiaozhunwzPdqd biaozhunwzPdqd) {
		return super.findPage(page, biaozhunwzPdqd);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(BiaozhunwzPdqd biaozhunwzPdqd) {
		String id = "";
		String fujianid = biaozhunwzPdqd.getFujianid();//附件id
		Attachment[] fjAtt = fujianshuzu(fujianid);
		if (StringUtils.isNotBlank(biaozhunwzPdqd.getId())) {//修改
			super.save(biaozhunwzPdqd);
			id= biaozhunwzPdqd.getId();
		} else {
			biaozhunwzPdqd.preInsert();
			id = IdGen.uuid();
			biaozhunwzPdqd.setId(id);
			if(UserUtils.getUser()!=null){
				biaozhunwzPdqd.setPandianrid(UserUtils.getUser().getId());
				biaozhunwzPdqd.setPandianr(UserUtils.getUser().getName());
			}
			biaozhunwzPdqd.setPandianrq(DateUtils.getDateTime());
			this.dao.insert(biaozhunwzPdqd);
		}
		//附件关联业务id
		if(fjAtt.length>0){
			attachmentService.guanlianfujian(fjAtt, id, "pdfj");
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(BiaozhunwzPdqd biaozhunwzPdqd) {
		super.delete(biaozhunwzPdqd);
	}

	/**
	 * 获得附件数组
	 * @param attIds
	 * @return
	 */
	public Attachment[] fujianshuzu(String attIds){
		List<Attachment> attList = new ArrayList<Attachment>();
		if(StringUtils.isNotBlank(attIds)){
			String[] fjArr = attIds.split(",");
			for(int i=0; i<fjArr.length; i++){
				Attachment att = new Attachment();
				att.setId(fjArr[i]);
				attList.add(att);
			}
		}
		Attachment[] attArray = new Attachment[attList.size()];
		return attList.size()==0? new Attachment[0] : attList.toArray(attArray);
	}
}