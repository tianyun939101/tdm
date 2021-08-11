package com.demxs.tdm.service.resource.yangpin;

import com.demxs.tdm.dao.resource.yangpin.YangpinCyrwDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.yangpin.YangpinCygcs;
import com.demxs.tdm.domain.resource.yangpin.YangpinCyrw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 拆样任务Service
 * @author 谭冬梅
 * @version 2017-07-16
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class YangpinCyrwService extends CrudService<YangpinCyrwDao, YangpinCyrw> {
	@Autowired
	private YangpinCygcsService yangpinCygcsService;

	@Override
	public YangpinCyrw get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<YangpinCyrw> findList(YangpinCyrw yangpinCyrw) {
		return super.findList(yangpinCyrw);
	}
	
	@Override
	public Page<YangpinCyrw> findPage(Page<YangpinCyrw> page, YangpinCyrw yangpinCyrw) {
		return super.findPage(page, yangpinCyrw);
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void save(YangpinCyrw entity) {
		String chaiyangid = "";
		if(StringUtils.isNoneBlank(entity.getId())){
			super.save(entity);
		}else{
			chaiyangid = IdGen.uuid();
			entity.preInsert();
			entity.setId(chaiyangid);
			//拆样工程师
			YangpinCygcs[] cygcs = entity.getGongchengshilist();
			if(cygcs!=null){
				for (int i = 0; i < cygcs.length; i++) {
					cygcs[i].setChaiyangzj(chaiyangid);
					yangpinCygcsService.save(cygcs[i]);
				}
			}
			this.dao.insert(entity);
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(YangpinCyrw yangpinCyrw) {
		super.delete(yangpinCyrw);
	}
	
}