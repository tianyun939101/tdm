package com.demxs.tdm.service.resource.renyuan;

import com.demxs.tdm.dao.resource.renyuan.RenyuanPxkcjlDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.utils.IdGen;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.resource.renyuan.RenyuanPxjlgx;
import com.demxs.tdm.domain.resource.renyuan.RenyuanPxkcjl;
import com.demxs.tdm.service.resource.fujian.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 人员课程培训记录Service
 * @author 詹小梅
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class RenyuanPxkcjlService extends CrudService<RenyuanPxkcjlDao, RenyuanPxkcjl> {
	@Autowired
	private AttachmentService attachmentService;
	@Autowired
	private RenyuanPxjlgxService renyuanPxjlgxService;

	@Override
	public RenyuanPxkcjl get(String id) {
		return super.get(id);
	}
	
	@Override
	public List<RenyuanPxkcjl> findList(RenyuanPxkcjl renyuanPxkcjl) {
		return super.findList(renyuanPxkcjl);
	}
	
	@Override
	public Page<RenyuanPxkcjl> findPage(Page<RenyuanPxkcjl> page, RenyuanPxkcjl renyuanPxkcjl) {
		/*if(UserUtils.getUser()!=null){
			//renyuanPxkcjl.getSqlMap().put("dsf", dataScopeFilter(UserUtils.getUser(), "o", "u"));
			renyuanPxkcjl.setDsf(dataScopeFilter(UserUtils.getUser(), "o", "u"));
		}*/
		return super.findPage(page, renyuanPxkcjl);
	}

	/**
	 * 人员培训课程记录保存
	 * @param entity
	 */
	@Override
	public void save(RenyuanPxkcjl entity) {
		String pxjlid = "";
		List<RenyuanPxjlgx> list = new ArrayList<RenyuanPxjlgx>();
		RenyuanPxjlgx pxjlgx =  null;
		if(StringUtils.isNotBlank(entity.getId())){
			String oldbpxrids="";
			pxjlid = entity.getId();
			//编辑
			RenyuanPxjlgx find = new RenyuanPxjlgx();
			find.setPeixunjlzj(pxjlid);
			List<RenyuanPxjlgx> listkc =renyuanPxjlgxService.findList(find);
			String newids = entity.getBeipeixrid();//现被培训人员
			String[] newbpexids = newids.split(",");
			String oldids = "";
			if(listkc.size()>0){
				RenyuanPxjlgx delpxjlgx =  null;
				List<RenyuanPxjlgx> dellist = new ArrayList<RenyuanPxjlgx>();
				for(int i=0;i<listkc.size();i++){
					String oldid = listkc.get(i).getBeipeixrid();//原被培训人员
					if(!newids.contains(oldid)){
						delpxjlgx = new RenyuanPxjlgx();
						delpxjlgx.setId(listkc.get(i).getId());
						dellist.add(delpxjlgx);
					}
					if(i==0){
						oldids=oldid;
					}else{
						oldids+=","+oldid;
					}
				}
				//本次有的历史没有的则插入一条信息记录（）
				if(StringUtils.isNotBlank(oldids)){
					for(String newid:newbpexids){
						if(!oldids.contains(newid)){
							pxjlgx = new RenyuanPxjlgx();
							pxjlgx.setPeixunjlzj(pxjlid);
							pxjlgx.setBeipeixrid(newid);
							pxjlgx.preInsert();
							list.add(pxjlgx);
						}
					}
				}
				if(dellist.size()>0){
					renyuanPxjlgxService.batchDelete(dellist);//删除上一次关联在本次关联中没有的人员
				}
			}else{
				//如果没有全部插入
				String[] bpxrid = entity.getBeipeixrid().split(",");
				for(String id:bpxrid){
					pxjlgx = new RenyuanPxjlgx();
					pxjlgx.setPeixunjlzj(pxjlid);
					pxjlgx.setBeipeixrid(id);
					pxjlgx.preInsert();
					list.add(pxjlgx);
				}
			}
			if(list.size()>0){
				renyuanPxjlgxService.batchInsert(list);//被培训人员与培训记录关系
			}
			super.save(entity);
		}else{
			//新增
			pxjlid =  IdGen.uuid();
			entity.preInsert();
			entity.setId(pxjlid);
			String[] bpxrid = entity.getBeipeixrid().split(",");
			for(String id:bpxrid){
				pxjlgx = new RenyuanPxjlgx();
				pxjlgx.setPeixunjlzj(pxjlid);
				pxjlgx.setBeipeixrid(id);
				pxjlgx.preInsert();
				list.add(pxjlgx);
			}
			this.dao.insert(entity);//保存培训课程记录
			if(list.size()>0){
				renyuanPxjlgxService.batchInsert(list);//被培训人员与培训记录关系
			}
		}
		if(entity.getPeixunnrfjs()!=null){
			attachmentService.guanlianfujian(entity.getPeixunnrfjs(),pxjlid,"peixunnrfjs");
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void delete(RenyuanPxkcjl renyuanPxkcjl) {
		super.delete(renyuanPxkcjl);
	}
	
}