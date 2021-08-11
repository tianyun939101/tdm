package com.demxs.tdm.service.resource.shebei;

import com.demxs.tdm.dao.resource.shebei.ShebeiPdqdDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.DateUtils;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.fujian.Attachment;
import com.demxs.tdm.domain.resource.shebei.ShebeiPdqd;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备盘点清单（记录）Service
 * @author zhangdengcai
 * @version 2017-09-01
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class ShebeiPdqdService extends CrudService<ShebeiPdqdDao, ShebeiPdqd> {

	@Autowired
	private AttachmentService attachmentService;

	@Override
	public ShebeiPdqd get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<ShebeiPdqd> findList(ShebeiPdqd shebeiPdqd) {
		return super.findList(shebeiPdqd);
	}
	
	@Override
	public Page<ShebeiPdqd> findPage(Page<ShebeiPdqd> page, ShebeiPdqd shebeiPdqd) {
		return super.findPage(page, shebeiPdqd);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(ShebeiPdqd shebeiPdqd) {
		String id = "";
		String fujianid = shebeiPdqd.getFujianid();//附件id
		Attachment[] fjAtt = fujianshuzu(fujianid);
		if (StringUtils.isNotBlank(shebeiPdqd.getId())) {//修改
			super.save(shebeiPdqd);
			id= shebeiPdqd.getId();
		} else {
			shebeiPdqd.preInsert();
			id = IdGen.uuid();
			shebeiPdqd.setId(id);
			if(UserUtils.getUser()!=null){
				shebeiPdqd.setPandianrid(UserUtils.getUser().getId());
				shebeiPdqd.setPandianr(UserUtils.getUser().getName());
			}
			shebeiPdqd.setPandianrq(DateUtils.getDateTime());
			this.dao.insert(shebeiPdqd);
		}
		//附件关联业务id
		if(fjAtt.length>0){
			attachmentService.guanlianfujian(fjAtt, id, "pdfj");
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(ShebeiPdqd shebeiPdqd) {
		super.delete(shebeiPdqd);
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