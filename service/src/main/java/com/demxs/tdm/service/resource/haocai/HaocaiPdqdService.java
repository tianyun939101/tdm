package com.demxs.tdm.service.resource.haocai;

import com.demxs.tdm.dao.resource.haocai.HaocaiPdqdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.haocai.HaocaiPdqd;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * 耗材盘点清单（记录）Service
 * @author zhangdengcai
 * @version 2017-08-31
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class HaocaiPdqdService extends CrudService<HaocaiPdqdDao, HaocaiPdqd> {

	@Autowired
	private AttachmentService attachmentService;

	@Override
	public HaocaiPdqd get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<HaocaiPdqd> findList(HaocaiPdqd haocaiPdqd) {
		return super.findList(haocaiPdqd);
	}
	
	@Override
	public Page<HaocaiPdqd> findPage(Page<HaocaiPdqd> page, HaocaiPdqd haocaiPdqd) {
		return super.findPage(page, haocaiPdqd);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(HaocaiPdqd haocaiPdqd) {
		String id = "";
		String fujianid = haocaiPdqd.getFujianid();//附件id
		Attachment[] fjAtt = fujianshuzu(fujianid);
		if (StringUtils.isNotBlank(haocaiPdqd.getId())) {//修改
			super.save(haocaiPdqd);
			id= haocaiPdqd.getId();
		} else {
			haocaiPdqd.preInsert();
			id = IdGen.uuid();
			haocaiPdqd.setId(id);
			if(UserUtils.getUser()!=null){
				haocaiPdqd.setPandianrid(UserUtils.getUser().getId());
				haocaiPdqd.setPandianr(UserUtils.getUser().getName());
			}
			haocaiPdqd.setPandianrq(DateUtils.getDateTime());
			this.dao.insert(haocaiPdqd);
		}
		//附件关联业务id
		if(fjAtt.length>0){
			attachmentService.guanlianfujian(fjAtt, id, "pdfj");
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(HaocaiPdqd haocaiPdqd) {
		super.delete(haocaiPdqd);
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