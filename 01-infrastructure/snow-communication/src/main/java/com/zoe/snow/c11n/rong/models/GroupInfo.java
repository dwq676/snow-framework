package com.zoe.snow.c11n.rong.models;

import com.zoe.snow.c11n.rong.util.GsonUtil;

/**
 * 群组信息。
 */
public class GroupInfo {
	// 群组Id。
	String id;
	// 群组名称。
	String name;
	
	public GroupInfo(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	/**
	 * 获取id
	 *
	 * @return String
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 设置id
	 *
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 获取name
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置name
	 *
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return GsonUtil.toJson(this, GroupInfo.class);
	}
}
