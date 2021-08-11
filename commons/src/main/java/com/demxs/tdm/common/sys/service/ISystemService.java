package com.demxs.tdm.common.sys.service;

import com.demxs.tdm.common.persistence.Page;
import com.demxs.tdm.common.security.shiro.session.SessionDAO;
import com.demxs.tdm.common.sys.entity.Menu;
import com.demxs.tdm.common.sys.entity.Office;
import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.entity.User;
import org.apache.shiro.session.Session;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author ThinkGem
 * @version 2013-12-05
 */
public interface ISystemService{

    public static final String HASH_ALGORITHM = "SHA-1";
    public static final int HASH_INTERATIONS = 1024;
    public static final int SALT_SIZE = 8;

    public SessionDAO getSessionDao();

    //-- User Service --//


    /**
     * 获取用户角色下的所有试验室--转换成组织
     * @param id
     * @return
     */
    public List<Office> getUser(String id, String roleId);

    /**
     * 获取用户角色下的所有试验室
     * @param id
     * @return
     */
    public List<Map<String,String>> getLabByUserRole(String id, String roleId);

    /**
     * 获取用户
     * @param id
     * @return
     */
    public User getUser(String id);

    /**
     * 根据登录名获取用户
     * @param loginName
     * @return
     */
    public User getUserByLoginName(String loginName);

    public Page<User> findUser(Page<User> page, User user);

    /**
     * 无分页查询人员列表
     * @param user
     * @return
     */
    public List<User> findUser(User user);

    /**
     * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
     * @param officeId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<User> findUserByOfficeId(String officeId);

    List<User> findByLabIdAndRoleId(String labId,String roleId);

    public void saveUser(User user);

    public void updateUserInfo(User user);

    public void deleteUser(User user);

    public void updatePasswordById(String id, String loginName, String newPassword);

    public void updateUserLoginInfo(User user);

    /**
     * 获得活动会话
     * @return
     */
    public Collection<Session> getActiveSessions();

    //-- Role Service --//

    public Role getRole(String id);

    public Role getRoleByName(String name);

    public Role getRoleByEnname(String enname);

    public List<Role> findRole(Role role);

    public List<Role> findAllRole();

    public void saveRole(Role role);

    public void deleteRole(Role role);

    public Boolean outUserInRole(Role role, User user);

    public User assignUserToRole(Role role, User user);

    //-- Menu Service --//

    public Menu getMenu(String id);

    public List<Menu> findAllMenu();

    public void saveMenu(Menu menu);

    public void updateMenuSort(Menu menu);

    public void deleteMenu(Menu menu);

    ///////////////// Synchronized to the Activiti //////////////////

    // 已废弃，同步见：ActGroupEntityServiceFactory.java、ActUserEntityServiceFactory.java

    /**
     * 是需要同步Activiti数据，如果从未同步过，则同步数据。
     */
    public void afterPropertiesSet() throws Exception;

    public void deleteActivitiGroup(Role role);

    ///////////////// Synchronized to the Activiti end //////////////////
    public List<User> searchUserByParam(String ids, String roleeName, String officeId, String userName, String zizhiids, Boolean shifoury, Boolean
            shifouzzry);


    /**
     * 同步用户数据
     * @param users
     */
    public void syncUserFromPS(List<User> users);

}
