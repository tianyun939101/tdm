package com.demxs.tdm.domain.oa;

import com.demxs.tdm.common.mapper.JsonMapper;
import com.demxs.tdm.common.persistence.DataEntity;
import com.demxs.tdm.common.sys.entity.User;
import com.demxs.tdm.common.utils.Collections3;
import com.github.ltsopensource.core.commons.utils.CollectionUtils;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 消息管理Entity
 * @author sunjunhui
 * @version 2017-12-04
 */
public class Message extends DataEntity<Message> {
	
	private static final long serialVersionUID = 1L;
	private Integer type;		// 类别
	private String acceptUsers;		// 接受人
	private String title;		// 标题
	private String handleUrl;		// 处理地址url
	private Integer send;		// 是否发送
	private Integer handle;		// 是否处理
	private Integer module;		// 所属模块

	private List<User> acceptUserList = Lists.newArrayList();
	private String userNames;//页面展示
	private String userJson;//外部接口需要
	
	public Message() {
		super();
	}

	public Message(String id){
		super(id);
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	@Length(min=0, max=2000, message="接受人长度必须介于 0 和 2000 之间")
	public String getAcceptUsers() {
		return acceptUsers;
	}

	public void setAcceptUsers(String acceptUsers) {
		this.acceptUsers = acceptUsers;
	}
	
	@Length(min=0, max=100, message="标题长度必须介于 0 和 100 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="处理地址url长度必须介于 0 和 255 之间")
	public String getHandleUrl() {
		return handleUrl;
	}

	public void setHandleUrl(String handleUrl) {
		this.handleUrl = handleUrl;
	}
	
	public Integer getSend() {
		return send;
	}

	public void setSend(Integer send) {
		this.send = send;
	}
	
	public Integer getHandle() {
		return handle;
	}

	public void setHandle(Integer handle) {
		this.handle = handle;
	}
	
	public Integer getModule() {
		return module;
	}

	public void setModule(Integer module) {
		this.module = module;
	}

	public List<User> getAcceptUserList() {
		return acceptUserList;
	}

	public void setAcceptUserList(List<User> acceptUserList) {
		this.acceptUserList = acceptUserList;
	}

	public String getUserNames() {
		StringBuilder names = new StringBuilder();
		if(!Collections3.isEmpty(acceptUserList)){
			for(int i = 0;i<acceptUserList.size();i++){
				if(i==acceptUserList.size()-1){
					names.append(acceptUserList.get(i).getName());
				}else{
					names.append(acceptUserList.get(i).getName()+",");
				}

			}
		}
		return names.toString();
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public String getUserJson() {
		List<Map> mapList = Lists.newArrayList();
		if(CollectionUtils.isNotEmpty(acceptUserList)){
			for(User u:acceptUserList){
				Map oneMap = new HashMap();
				oneMap.put("id",u.getId());
				mapList.add(oneMap);
			}
		}
		return JsonMapper.toJsonString(mapList);
	}

	public void setUserJson(String userJson) {
		this.userJson = userJson;
	}

	public static final Integer NO_SEND = 0;

	public static final Integer SENDED = 1;

	public static final Integer NO_HANDLE = 0;

	public static final Integer HANDLED = 1;
}