package com.demxs.tdm.service.sys;

import com.demxs.tdm.common.config.Global;
import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.security.Digests;
import com.demxs.tdm.common.security.shiro.session.SessionDAO;
import com.demxs.tdm.common.service.BaseService;
import com.demxs.tdm.common.service.ServiceException;
import com.demxs.tdm.common.sys.dao.MenuDao;
import com.demxs.tdm.common.sys.dao.OfficeDao;
import com.demxs.tdm.common.sys.dao.RoleDao;
import com.demxs.tdm.common.sys.dao.UserDao;
import com.demxs.tdm.common.sys.entity.Menu;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.sys.security.SystemAuthorizingRealm;
import com.demxs.tdm.common.sys.service.ISystemService;
import com.demxs.tdm.common.sys.utils.LogUtils;
import com.demxs.tdm.common.sys.utils.UserUtils;
import com.demxs.tdm.common.utils.CacheUtils;
import com.demxs.tdm.common.utils.Encodes;
import com.demxs.tdm.common.utils.StringUtils;
import com.demxs.tdm.common.web.Servlets;
import com.demxs.tdm.dao.resource.shebei.ShebeiDao;
import com.demxs.tdm.domain.lab.LabInfo;
import com.demxs.tdm.domain.resource.shebei.Shebei;
import com.demxs.tdm.domain.sys.UserRole;
import com.demxs.tdm.job.JobType;
import com.demxs.tdm.job.JobUtil;
import com.github.ltsopensource.core.commons.utils.DateUtils;
import com.github.ltsopensource.core.domain.Job;
import com.github.ltsopensource.core.exception.JobSubmitException;
import com.github.ltsopensource.jobclient.domain.Response;
import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author ThinkGem
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true,rollbackFor = ServiceException.class)
public class SystemService extends BaseService implements InitializingBean, ISystemService {

	private static final Logger log = LoggerFactory.getLogger(SystemService.class);
	
	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	@Autowired
	private UserRoleService userRoleService;
	@Override
	public SessionDAO getSessionDao() {
		return sessionDao;
	}

	@Autowired
	private LabRoleService labRoleService;

	@Autowired
	private OfficeDao   officeDao;
	@Autowired
	private ShebeiDao shebeiDao;

	@Override
	public List<Office> getUser(String id, String roleId) {
		List<Office> offices = new ArrayList<>();
		List<LabInfo> labInfos = labRoleService.getLabInfosByUserId(roleId,id);
		if(CollectionUtils.isNotEmpty(labInfos)){
			for(LabInfo labInfo:labInfos){
				offices.add(labInfo.getOffice());
			}
		}
		return offices;
	}

	@Override
	public List<Map<String, String>> getLabByUserRole(String id, String roleId) {
		List<Map<String, String>> result = new ArrayList<>();

		List<LabInfo> labInfos = labRoleService.getLabInfosByUserId(roleId,id);
		if(CollectionUtils.isNotEmpty(labInfos)){
			for(LabInfo l:labInfos){
				Map map = new HashMap();
				map.put("labId",l.getId());
				result.add(map);
			}
		}
		return result;
	}

	@Autowired
	private IdentityService identityService;

	//-- User Service --//
	
	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	@Override
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 从数据库获取用户
	 * @param id
	 * @return
	 */
	public User getUserFromDb(String id) {
		return this.userDao.get(id);
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	@Override
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}
	
	@Override
	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}

	public Page<User> findAllUser(Page<User> page, User user) {
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}

	public Page<User> findAllUserByEnabledLabinfo(Page<User> page, User user) {
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findAllUserByEnabledLabinfo(user));
		return page;
	}

	public Page<User> findUserByRole(Page<User> page, User user) {
		user.setPage(page);
		// 执行分页查询
		if(user.getArrIDS().length<=0){
			return new Page<User>();
		}else{
			page.setList(userDao.findList(user));
			return page;
		}

	}
	
	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	@Override
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id/name/phone/email/photo（树查询用户时用）
	 * @param officeId
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		List<User> list = (List<User>) CacheUtils.get(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		if (list == null){
			User user = new User();
			user.setOffice(new Office(officeId));
			list = userDao.findUserByOfficeId(user);
			CacheUtils.put(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId, list);
		}
		return list;
	}

	/**
	 * 根据OfficeId(当前机构及其所有后代机构)获取用户
	 * @param user
	 * @return
	 */
	public Page<User> listUserByOfficeId(Page page,User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		//user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		user.setPage(page);
		List users = userDao.listUserByOfficeId(user);
		page.setList(users);
		return page;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void saveUser(User user) {
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
		}else{
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
				CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 将当前用户同步到Activiti
			saveActivitiUser(user);
			// 清除用户缓存
			UserUtils.clearCache(user);
//			// 清除权限缓存
//			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteUser(User user) {
		userDao.delete(user);
		// 同步到Activiti
		deleteActivitiUser(user);
		// 清除用户缓存
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPassword(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		UserUtils.clearCache(user);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(StringUtils.getRemoteAddr(Servlets.getRequest()));
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}
	
	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}
	
	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		String plain = Encodes.unescapeHtml(plainPassword);
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	/**
	 * 获得活动会话
	 * @return
	 */
	@Override
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}
	
	//-- Role Service --//
	
	@Override
	public Role getRole(String id) {
		return roleDao.get(id);
	}
	public Shebei getSheBei(String id){
		return shebeiDao.get(id);
	}

	@Override
	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}
	
	@Override
	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}
	
	@Override
	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}
	
	@Override
	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
			// 同步到Activiti
			saveActivitiGroup(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0){
			roleDao.insertRoleOffice(role);
		}
		// 同步到Activiti
		saveActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 同步到Activiti
		deleteActivitiGroup(role);
		// 清除用户角色缓存
		UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//
	
	@Override
	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	@Override
	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}
	
	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void saveMenu(Menu menu) {
		
		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));
		
		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds();
		
		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}
		
		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
//		// 清除权限缓存
//		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}
	
	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+ Global.getConfig("productName")+"  - Powered By http://jeesite.com\r\n");
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}
	
	///////////////// Synchronized to the Activiti //////////////////
	
	// 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

	/**
	 * 是需要同步Activiti数据，如果从未同步过，则同步数据。
	 */
	private static boolean isSynActivitiIndetity = true;
	@Override
	public void afterPropertiesSet() throws Exception {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if (isSynActivitiIndetity){
			isSynActivitiIndetity = false;
	        // 同步角色数据
			List<Group> groupList = identityService.createGroupQuery().list();
			if (groupList.size() == 0){
			 	Iterator<Role> roles = roleDao.findAllList(new Role()).iterator();
			 	while(roles.hasNext()) {
			 		Role role = roles.next();
			 		saveActivitiGroup(role);
			 	}
			}
		 	// 同步用户数据
			List<org.activiti.engine.identity.User> userList = identityService.createUserQuery().list();
			if (userList.size() == 0){
			 	Iterator<User> users = userDao.findAllList(new User()).iterator();
			 	while(users.hasNext()) {
			 		saveActivitiUser(users.next());
			 	}
			}
		}
	}
	
	private void saveActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		String groupId = role.getEnname();
		
		// 如果修改了英文名，则删除原Activiti角色
		if (StringUtils.isNotBlank(role.getOldEnname()) && !role.getOldEnname().equals(role.getEnname())){
			identityService.deleteGroup(role.getOldEnname());
		}
		
		Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
		if (group == null) {
			group = identityService.newGroup(groupId);
		}
		group.setName(role.getName());
		group.setType(role.getRoleType());
		identityService.saveGroup(group);
		
		// 删除用户与用户组关系
		List<org.activiti.engine.identity.User> activitiUserList = identityService.createUserQuery().memberOfGroup(groupId).list();
		for (org.activiti.engine.identity.User activitiUser : activitiUserList){
			identityService.deleteMembership(activitiUser.getId(), groupId);
		}

		// 创建用户与用户组关系
		List<User> userList = findUser(new User(new Role(role.getId())));
		for (User e : userList){
			String userId = e.getLoginName();//ObjectUtils.toString(user.getId());
			// 如果该用户不存在，则创建一个
			org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
			if (activitiUser == null){
				activitiUser = identityService.newUser(userId);
				activitiUser.setFirstName(e.getName());
				activitiUser.setLastName(StringUtils.EMPTY);
				activitiUser.setEmail(e.getEmail());
				activitiUser.setPassword(StringUtils.EMPTY);
				identityService.saveUser(activitiUser);
			}
			identityService.createMembership(userId, groupId);
		}
	}

	@Override
	public void deleteActivitiGroup(Role role) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if(role!=null) {
			String groupId = role.getEnname();
			identityService.deleteGroup(groupId);
		}
	}

	public void saveActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
		org.activiti.engine.identity.User activitiUser = identityService.createUserQuery().userId(userId).singleResult();
		if (activitiUser == null) {
			activitiUser = identityService.newUser(userId);
		}
		activitiUser.setFirstName(user.getName());
		activitiUser.setLastName(StringUtils.EMPTY);
		activitiUser.setEmail(user.getEmail());
		activitiUser.setPassword(StringUtils.EMPTY);
		identityService.saveUser(activitiUser);
		
		// 删除用户与用户组关系
		List<Group> activitiGroups = identityService.createGroupQuery().groupMember(userId).list();
		for (Group group : activitiGroups) {
			identityService.deleteMembership(userId, group.getId());
		}
		// 创建用户与用户组关系
		for (Role role : user.getRoleList()) {
	 		String groupId = role.getEnname();
	 		// 如果该用户组不存在，则创建一个
		 	Group group = identityService.createGroupQuery().groupId(groupId).singleResult();
            if(group == null) {
	            group = identityService.newGroup(groupId);
	            group.setName(role.getName());
	            group.setType(role.getRoleType());
	            identityService.saveGroup(group);
            }
			identityService.createMembership(userId, role.getEnname());
		}
	}

	private void deleteActivitiUser(User user) {
		if (!Global.isSynActivitiIndetity()){
			return;
		}
		if(user!=null) {
			String userId = user.getLoginName();//ObjectUtils.toString(user.getId());
			identityService.deleteUser(userId);
		}
	}
	
	///////////////// Synchronized to the Activiti end //////////////////

	/**
	 * 根据父ID获得菜单列表
	 * @param parentid
	 * @return
	 */
	public List<Menu> searchByParentId(String parentid){
		return menuDao.searchByParentId(parentid);
	}

	/**
	 * 根据参数返回用户列表
	 * @param roleeName 角色英文名
	 * @param officeId 部门ID
	 * @param userName 用户名
	 * @param zizhiids 资质ID集合
	 * @param shifouzzry 是否资质人员（该参数如果为true,则只获取资质人员）
	 * @return List<User>
	 */
	@Override
	public List<User> searchUserByParam(String ids, String roleeName, String officeId, String userName, String zizhiids, Boolean shifoury, Boolean shifouzzry){
		return userDao.searchUserByParam(StringUtils.isNotBlank(ids)?ids.split(","):null,roleeName,officeId,userName,StringUtils.isNotBlank(zizhiids)?zizhiids.split(","):null,shifoury,shifouzzry);
	}

	@Override
	public List<User> findByLabIdAndRoleId(String labId,String roleId){
		return userDao.findByLabIdAndRoleId(labId,roleId);
	}


	/**
	 * 根据试验室 角色 获取用户列表
	 * @param labInfoId
	 * @param enname　角色英文名称
	 * @return
	 */
	public List<User> getUserByLabRole(String labInfoId,String enname){
		List<User> labUsers = new ArrayList<>();
		List<User> roleUsers = new ArrayList<>();
		//获取该角色的用户
		List<UserRole> userRoles = new ArrayList<>();
		if (StringUtils.isNotBlank(enname)) {
			UserRole userRole = new UserRole();
			Role role = new Role();
			role.setEnname(enname);
			userRole.setRoleId(roleDao.getByEnname(role).getId());
			userRoles = userRoleService.findList(userRole);
			if(CollectionUtils.isNotEmpty(userRoles)){
				for(UserRole ur:userRoles){
					if(UserUtils.get(ur.getUser().getId())!=null && StringUtils.isNotBlank(UserUtils.get(ur.getUser().getId()).getId())){
						roleUsers.add(UserUtils.get(ur.getUser().getId()));
					}
				}
			}
		} else {
			//取所有的用户
			roleUsers = userDao.findAllList(new User());
		}

		//在判断该用户是不是该试验室的
		for(User u:roleUsers){
			if(StringUtils.isBlank(labInfoId)){
				labUsers.add(u);
			}else{
				if(labInfoId.equals(u.getLabInfoId())){
					labUsers.add(u);
				}
			}

		}
		return labUsers;
	}

	/**
	 * 根据试验室 角色 获取用户列表
	 * @param labInfoId
	 * @param enname　角色英文名称
	 * @return
	 */
	public List<User> getUserByLabRoleExt(String labInfoId,String enname){
		List<User> result = new ArrayList<>();
		//获取该角色的用户
		List<UserRole> userRoles = new ArrayList<>();
		//if (StringUtils.isNotBlank(enname) && StringUtils.isNotEmpty(labInfoId)) {
			Role role = new Role();
			role.setEnname(enname);
			role = roleDao.getByEnname(role);
			if(role != null && StringUtils.isNotEmpty(role.getId())){
				result = userDao.findByLabIdAndRoleId(labInfoId,role.getId());
			}else{
				result = userDao.findByLabIdAndRoleId(labInfoId,null);
			}
		//} else {
			//取所有的用户
		//	result = userDao.findAllList(new User());
		//}
		return result;
	}


	@Override
	@Transactional(readOnly = false,rollbackFor = ServiceException.class)
	public void syncUserFromPS(List<User> users) {
		if(CollectionUtils.isNotEmpty(users)){
			for(User u:users){
					if(userDao.get(u.getId())!=null && StringUtils.isNotBlank(userDao.get(u.getId()).getId())){//说明存在
						User oldUser = userDao.get(u.getId());
						if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
							CacheUtils.remove(UserUtils.USER_CACHE, UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + oldUser.getOffice().getId());
						}
						//u.setPassword(entryptPassword(Global.getConfig("initPassword")));
						u.setUpdateBy(new User("1"));
						u.setUpdateDate(new Date());
						if(u.getDelFlag().equals(User.DEL_FLAG_DELETE)){
							userDao.delete(u);
						}else{
							userDao.updateIgnorePassword(u);
						}

					}else{
						u.setCreateBy(new User("1"));
						u.setUpdateBy(new User("1"));
						u.setCreateDate(new Date());
						u.setUpdateDate(new Date());
						//u.setDelFlag(User.DEL_FLAG_NORMAL);
						//设置默认密码
						u.setPassword(entryptPassword(Global.getConfig("initPassword")));
						userDao.insert(u);
						saveActivitiUser(u);
					}
					//给每个用户添加资料员角色
					UserRole userRole = new UserRole();
					userRole.setUser(u);
					Role role = new Role();
					role.setEnname("TheClient");
					userRole.setRoleId(roleDao.getByEnname(role).getId());
					List<UserRole> userRoles = userRoleService.findList(userRole);
					if(CollectionUtils.isNotEmpty(userRoles)){//表示有资料员角色
					}else{
						//表示没有资料员角色
						userRoleService.save(userRole);
					}
					UserUtils.clearCache(u);

			}
		}
	}


	/**
	 * 添加用户同步发送任务(增量)
	 * @param apiAddress
	 * @param userName
	 * @param password
	 */
	private void addSyncOfficeAll(String apiAddress,String userName,String password)  {
		try {
			Map<String,String> param = new HashMap<String,String>();
			param.put("apiAddress",apiAddress);
			param.put("userName",userName);
			param.put("password",password);
			param.put("synFlag","A");
			param.put("synDate", DateUtils.format(new Date(),"yyyy-MM-dd"));
			Job job = new Job();
			job.setParam("type", JobType.SyncUser.getType());
			job.setTaskId(String.format("%s-%s", JobType.SyncUser.getType(), DateUtils.format(new Date(), DateUtils.YMD_HMS)));
			job.setParam("param", JsonMapper.toJsonString(param));
			job.setTaskTrackerNodeGroup(JobType.SyncUser.getTaskTracker());
			job.setNeedFeedback(true);
			Response response = JobUtil.getJobClient().submitJob(job);
			if (!response.isSuccess()) {
				String msg = String.format("用户同步发送全量任务失败: %s",response.toString());
				log.error(msg);
			}
		}catch (JobSubmitException e){
		}
	}


	/**
	 * 添加组织同步发送任务(每天晚上增量同步)
	 * @param apiAddress
	 * @param userName
	 * @param password
	 */
	private void addSyncOffice(String apiAddress,String userName,String password)  {
		try {
			Map<String,String> param = new HashMap<String,String>();
			param.put("apiAddress",apiAddress);
			param.put("userName",userName);
			param.put("password",password);
			param.put("synFlag","U");
			param.put("synDate",DateUtils.format(new Date(),"yyyy-MM-dd"));
			Job job = new Job();
			job.setParam("type", JobType.SyncUser.getType());
			job.setTaskId(String.format("%s-%s", JobType.SyncUser.getType(), DateUtils.format(new Date(), DateUtils.YMD_HMS)));
			job.setParam("param", JsonMapper.toJsonString(param));
			job.setTaskTrackerNodeGroup(JobType.SyncUser.getTaskTracker());
			job.setNeedFeedback(true);
			//设置任务的定时参数
			job.setCronExpression("0 50 23 ? * *");
			Response response = JobUtil.getJobClient().submitJob(job);
			if (!response.isSuccess()) {
				String msg = String.format("用户同步发送增量任务失败: %s",response.toString());
				log.error(msg);
			}
		}catch (JobSubmitException e){
		}
	}


    public User getUserBaseInfo(User u) {
        return userDao.getByLoginName(u);
    }


    public void updateDelete(String falg,String id) {
         userDao.updateDelete(falg,id);
    }

	public void updateIsDelete(String falg,String id) {
		userDao.updateIsDelete(falg,id);
	}

    public User getUserInfoMess(String  name,String loginName,String delflag) {
	    User  u=new User();
	    u.setLoginName(loginName);
	    u.setEname(name);
	    u.setDelFlag(delflag);
        return userDao.getUserInfoMess(u);
    }

    public List<User> getUserByRoleName( String roleName){
		return userDao.getUserByRoleName(roleName);
	}



	public  String  getUserBelongCenter(String name){
		String parentId=userDao.getUserBelongCenter(name);
		//获取根结点数据
		String[]  parentIds=parentId.split(",");
		String office="";
		if(parentIds.length>1){
			if(parentIds.length >2){
				office=parentIds[0]+","+parentIds[1]+","+parentIds[2];
			}
           // office=parentIds[0]+","+parentIds[1]+","+parentIds[2];
			/*Office  o=officeDao.get(rootId);

			if(o !=null){
				String officeName=o.getName();
				if("上海飞机制造有限公司".equals(officeName)){
					office="上飞公司";
				}else if("上海飞机客户服务有限公司".equals(officeName)){
					office="客服公司";
				}else if("北京民用飞机技术研究中心".equals(officeName)){
					office="北研中心";
				}else if("上海飞机设计研究院".equals(officeName)){
					office="上飞院";
				}else{
					office="试飞中心";
				}
			}*/
		}
		return office;
	}

    public  String  getUserBelongTestCenter(String name){
        String parentId=userDao.getUserBelongCenter(name);
        //获取根结点数据
        String[]  parentIds=parentId.split(",");
        String office="";
        if(parentIds.length>1){
			String rootId  = "";
        	if(parentIds.length>2){
				 rootId=parentIds[2];
			}
			//rootId=parentIds[2];
			Office  o=officeDao.get(rootId);
			if(o !=null){
				String officeName=o.getName();
				if("上海飞机制造有限公司".equals(officeName)){
					office="上飞公司";
				}else if("上海飞机客户服务有限公司".equals(officeName)){
					office="客服公司";
				}else if("北京民用飞机技术研究中心".equals(officeName)){
					office="北研中心";
				}else if("上海飞机设计研究院".equals(officeName)){
					office="上飞院";
				}else{
					office="试飞中心";
				}
			}
        }
        return office;
    }

}
