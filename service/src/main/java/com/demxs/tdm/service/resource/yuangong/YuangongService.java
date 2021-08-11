package com.demxs.tdm.service.resource.yuangong;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.dao.lab.LabInfoDao;
import com.demxs.tdm.dao.resource.center.DepartmentDao;
import com.demxs.tdm.dao.resource.yuangong.YuangongCertDao;
import com.demxs.tdm.dao.resource.yuangong.YuangongCertRecordDao;
import com.demxs.tdm.dao.resource.yuangong.YuangongDao;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.service.CrudService;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.domain.ability.Aptitude;
import com.demxs.tdm.domain.resource.attach.AttachmentInfo;
import com.demxs.tdm.domain.resource.center.Department;
import com.demxs.tdm.domain.resource.yuangong.Yuangong;
import com.demxs.tdm.domain.resource.yuangong.YuangongCert;
import com.demxs.tdm.service.ability.AptitudeService;
import com.demxs.tdm.service.lab.LabInfoService;
import com.demxs.tdm.service.resource.attach.AttachmentInfoService;
import com.demxs.tdm.service.resource.center.DepartmentService;
import com.demxs.tdm.service.sys.OfficeService;
import com.demxs.tdm.service.sys.SystemService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.rtf.RTFEditorKit;

/**
 * 员工Service
 * @author sunjunhui
 * @version 2017-11-08
 */
@Service
@Transactional(readOnly = true)
public class YuangongService extends CrudService<YuangongDao, Yuangong> {

	@Autowired
	private AttachmentInfoService attachmentInfoService;
	@Autowired
	private AptitudeService aptitudeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private LabInfoDao labInfoDao;
	@Autowired
	private LabInfoService labInfoService;
	@Autowired
	private DepartmentService departmentService;
	@Autowired
	private OfficeDao officeDao;
	private Yuangong getAllInfo(Yuangong yuangong){
		if(yuangong !=null && StringUtils.isNotEmpty(yuangong.getId())){
			//获取用户详细信息
			yuangong.setUser(systemService.getUser(yuangong.getUser().getId()));
			/*if (yuangong.getUser().getCompany()==null || yuangong.getUser().getCompany().getId()==null){
				yuangong.getUser().setCompany(UserUtils.getUser().getCompany());
			}
			if (yuangong.getUser().getOffice()==null || yuangong.getUser().getOffice().getId()==null){
				yuangong.getUser().setOffice(officeService.get(UserUtils.getUser().getOffice()));
			}else{
				yuangong.getUser().setOffice(officeService.get(yuangong.getUser().getOffice()));
			}*/
			//获取员工证书
			List<AttachmentInfo> zlList = new ArrayList<AttachmentInfo>();
			if(StringUtils.isNotEmpty(yuangong.getZsIds())){
				String[] zlArr =yuangong.getZsIds().split(",");
				for(String zlId:zlArr){
					zlList.add(attachmentInfoService.get(zlId));
				}
				yuangong.setZsList(zlList);
			}
			//获取员工资质
			List<Aptitude> zzList = new ArrayList<Aptitude>();
			if(StringUtils.isNotEmpty(yuangong.getZzIds())){
				String[] zzArr =yuangong.getZzIds().split(",");
				for(String zzid:zzArr){
					zzList.add(aptitudeService.get(zzid));
				}
				yuangong.setZzList(zzList);
			}
			yuangong.setLabInfo(labInfoDao.get(yuangong.getLabInfoId()));

			/*Department dept = yuangong.getDept();
			if(dept!=null){
				Office office = officeDao.get(dept.getId());
				yuangong.setDept(office);
			}*/
			Department deptBM = yuangong.getDeptBM();
			if(deptBM!=null){
				Department department = departmentService.get(deptBM.getId());
				yuangong.setDeptBM(department);
			}
			Department dept = yuangong.getDept();
			if(dept!=null){
				Department department = departmentService.get(dept.getId());
				yuangong.setDept(department);
			}
		}
		return yuangong;
	}

	public Yuangong get(String id) {
		return getAllInfo(super.get(id));
	}
	
	public List<Yuangong> findList(Yuangong yuangong) {
		List<Yuangong> yuangongs = super.findList(yuangong);
		if(!Collections3.isEmpty(yuangongs)){
			for(Yuangong y:yuangongs){
				getAllInfo(y);
			}
		}
		return yuangongs;
	}
	
	public Page<Yuangong> findPage(Page<Yuangong> page, Yuangong yuangong) {

		/*yuangong.getSqlMap().put("dsf", dataScopeFilter(yuangong.getCurrentUser(), "o", "u8"));*/
		Department params = new Department();
		if(yuangong.getDept() != null && StringUtils.isNotBlank(yuangong.getDept().getId())){
			params.setParentIds(yuangong.getDept().getId());
		}
		List<Department> departments = departmentService.findList(params);
		departments.add(yuangong.getDept());
		String deptIds = Collections3.extractToString(departments,"id",",");
		yuangong.setDeptIds(deptIds);
        String orderBy = page.getOrderBy();
        if(StringUtils.isNotBlank(orderBy) && orderBy.indexOf("user.no")>=0){
            String replace = orderBy.replace("user.no", "u8.no");
            page.setOrderBy(replace);
        }
        Page<Yuangong> yuangongPage = super.findPage(page, yuangong);
		if(yuangongPage!=null){
			List<Yuangong> list = yuangongPage.getList();
			Iterator<Yuangong> iterator = list.iterator();

			while(iterator.hasNext()){
				Yuangong next = iterator.next();
				User user = next.getUser();
				if(user!=null){
					String name = user.getName();
					if(StringUtils.isBlank(name) && user.getOffice()==null){
						iterator.remove();
						continue;
					}
				}
				getAllInfo(next);
			}
			/*for(Yuangong s:list){
				User user = s.getUser();
				if(user!=null){
					String name = user.getName();
					if(StringUtils.isBlank(name) && user.getOffice()==null){

						continue;
					}
				}
				getAllInfo(s);
			}*/
		}
		return  yuangongPage;
	}
	
	@Transactional(readOnly = false)
	public void save(Yuangong yuangong) {

		if(StringUtils.isNotBlank(yuangong.getUserIds())){
			String[] idArr = yuangong.getUserIds().split(",");
			for(String userId:idArr){
				//判断该用户是不是已经添加了 已经添加了 就不添加了
				List<Yuangong> yuangongs = findList(new Yuangong(null,userId));
				if(!Collections3.isEmpty(yuangongs)){
				}else{
					Yuangong y = new Yuangong(yuangong.getId(),userId);
					y.setDept(new Department(yuangong.getDeptId()));

					y.setLabInfoId(UserUtils.get(userId).getLabInfoId());
					y.setLabInfoName(UserUtils.get(userId).getLabInfoName());
					super.save(y);
				}
			}
		}else {
		    if(yuangong!=null ){
		        if(yuangong.getLabInfo()!= null){
                    yuangong.setLabInfoId(yuangong.getLabInfo().getId());
                }
		        if(yuangong.getLabInfoId()!=null){
                    yuangong.setLabInfoName(labInfoService.get(yuangong.getLabInfoId()).getName());
                }
            }
			super.save(yuangong);
			//更新用户信息
			User user = systemService.getUser(yuangong.getUser().getId());
			//user.setOffice(officeService.get());
			user.setBirthDate(yuangong.getUser().getBirthDate());
			user.setNativePlace(yuangong.getUser().getNativePlace());
			user.setNativePlace(yuangong.getUser().getNativePlace());
			user.setSex(yuangong.getUser().getSex());
			/*user.setNo(yuangong.getUser().getNo());
			user.setCompany(new Office(user.getCompany().getId()));
			user.setOffice(new Office(user.getOffice().getId()));*/
			userDao.update(user);
			UserUtils.clearCache(user);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Yuangong yuangong) {
		super.delete(yuangong);
	}


	/**
	 * 获取有此资质的员工
	 * @param page
	 * @param yuangong zzIds 逗号隔开的资质ids集合
	 * 包含其中一个资质就表示符合
	 * @return
	 */
	public Page<Yuangong> getPagesByZzids(Page<Yuangong> page,Yuangong yuangong){
		StringBuilder sqlString = new StringBuilder();
		if(StringUtils.isNotBlank(yuangong.getZzIds())){
			String[] zzIdArr = yuangong.getZzIds().split(",");
			sqlString.append(" and ");
			sqlString.append(" ( ");
			for(int i = 0;i<zzIdArr.length;i++){
				if(i!=zzIdArr.length-1){
					sqlString.append(" a.zz_ids like '%"+zzIdArr[i]+"%' or ");
				}else{
					sqlString.append(" a.zz_ids like '%"+zzIdArr[i]+"%' ");
				}
			}
			sqlString.append(" ) ");
		}
		yuangong.getSqlMap().put("dsf", sqlString.toString());
		yuangong.setPage(page);
		page.setList(this.dao.findListByZzids(yuangong));
		return page;

	}

	/**
	 * @Describe:根据分中心编号汇总员工数
	 * @Author:WuHui
	 * @Date:10:05 2020/8/31
	 * @param centerId
	 * @return:int
	*/
	public  List<Integer> countYuangongByCenter(String centerId) {
		//获取实验设备
		List<Integer> cnt = dao.countYuangongByCenter(centerId);
		return cnt;
	}

	/**
	 * @Describe:根据分中心编号查询员工数据
	 * @Author:WuHui
	 * @Date:9:52 2020/8/31
	 * @param page
	 * @param centerId
	 * @return:com.demxs.tdm.common.persistence.Page<com.demxs.tdm.domain.resource.yuangong.Yuangong>
	*/
	public Page<Yuangong> findYuangongByCenter(Page<Yuangong> page, String centerId, String labinfoId){
		List<Yuangong> lists = dao.findYuangongByCenter(page, centerId, labinfoId);
		page.setList(lists);
		if(page!=null){
			for(Yuangong s:page.getList()){
				getAllInfo(s);
			}
		}
		return page;
	}

    /**
     * 部门选用户
     * @param userIds
     * @param deptId
     */
    public void chooseUserToDept(String userIds, String deptId) {
        if(StringUtils.isEmpty(userIds) || StringUtils.isEmpty(deptId)){
            throw new ServiceException("用户ID或部门ID不能为空");
        }
        String[] idArr = userIds.split(",");
        for(String userId:idArr){
            //判断该用户是不是已经添加了 已经添加了 就不添加了
           // List<Yuangong> yuangongs = findList(new Yuangong(null,userId,deptId));
			List<Yuangong> yuangongs = findList(new Yuangong(null,userId));
            if(Collections3.isEmpty(yuangongs)){
                Yuangong yuangong = new Yuangong();
                yuangong.setDept(new Department(deptId));
                yuangong.setUser(new User(userId));
                super.save(yuangong);
            }
        }
    }

    public void choose(String userIds,String deptId){
		if(StringUtils.isEmpty(userIds) ){
			throw new ServiceException("用户ID不能为空");
		}
		String[] idArr = userIds.split(",");
		for(String userId:idArr){
			//判断该用户是不是已经添加了 已经添加了 就不添加了
			List<Yuangong> yuangongs = findList(new Yuangong(null,userId));
			if(Collections3.isEmpty(yuangongs)) {
				Yuangong yuangong = new Yuangong();
				yuangong.setUser(new User(userId));
				yuangong.setDeptBM(new Department(deptId));
				super.save(yuangong);
			}
		}
	}

	private Yuangong getDept(Yuangong yuangong){
		Department department = new Department();
		String parent = departmentService.getParent(yuangong.getLabInfoId());
		department.setId(parent);
		yuangong.setDept(department);
    	return yuangong;
	}

    public Yuangong getByName(String name){
    	Yuangong param = new Yuangong();
    	User temp = new User();
    	temp.setName(name);
    	param.setUser(temp);
		List<Yuangong> users = this.dao.findList(param);
		if(!Collections3.isEmpty(users)){
			Yuangong yuangong = users.get(0);
			if(yuangong != null &&  yuangong.getDept()!=null){
				String deptId = yuangong.getDept().getId();
				Department department = departmentService.get(deptId);
				yuangong.setDept(department);
			}
			return yuangong;
		}
		return null;
	}

	public Yuangong getByUserId(String id){
    	List<Yuangong> list = this.dao.getByUserId(id);
		Yuangong result = null;
		if(CollectionUtils.isNotEmpty(list)){
			result = list.get(0);
		}
    	return result;
	}
}