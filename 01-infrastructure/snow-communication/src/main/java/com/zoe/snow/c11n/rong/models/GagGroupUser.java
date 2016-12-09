package com.zoe.snow.c11n.rong.models;

import com.zoe.snow.c11n.rong.util.GsonUtil;

/**
 * 群组用户信息。
 */
public class GagGroupUser {
	// 解禁时间。
	String time;
	// 群成员 Id。
	String userId;
	
	public GagGroupUser(String time, String userId) {
		this.time = time;
		this.userId = userId;
	}
	
	/**
	 * 获取time
	 *
	 * @return String
	 */
	public String getTime() {
		return time;
	}
	
	/**
	 * 设置time
	 *
	 */
	public void setTime(String time) {
		this.time = time;
	}
	
	/**
	 * 获取userId
	 *
	 * @return String
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * 设置userId
	 *
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return GsonUtil.toJson(this, GagGroupUser.class);
	}
}