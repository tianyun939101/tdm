package com.demxs.tdm.common.utils.excel.fieldtype;

import com.demxs.tdm.common.sys.entity.Role;
import com.demxs.tdm.common.sys.service.ISystemService;
import com.demxs.tdm.common.utils.Collections3;
import com.demxs.tdm.common.utils.SpringContextHolder;
import com.demxs.tdm.common.utils.StringUtils;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * 字段类型转换
 * @author ThinkGem
 * @version 2013-5-29
 */
public class RoleListType {

	private static ISystemService systemService = SpringContextHolder.getBean(ISystemService.class);
	
	/**
	 * 获取对象值（导入）
	 */
	public static Object getValue(String val) {
		List<Role> roleList = Lists.newArrayList();
		List<Role> allRoleList = systemService.findAllRole();
		for (String s : StringUtils.split(val, ",")){
			for (Role e : allRoleList){
				if (StringUtils.trimToEmpty(s).equals(e.getName())){
					roleList.add(e);
				}
			}
		}
		return roleList.size()>0?roleList:null;
	}

	/**
	 * 设置对象值（导出）
	 */
	public static String setValue(Object val) {
		if (val != null){
			@SuppressWarnings("unchecked")
			List<Role> roleList = (List<Role>)val;
			return Collections3.extractToString(roleList, "name", ", ");
		}
		return "";
	}
	
}
