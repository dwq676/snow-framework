package com.zoe.snow.c11n.rong.models;

import com.zoe.snow.c11n.rong.util.GsonUtil;
import java.util.List;

/**
 *  lisitGagGroupUser 返回结果
 */
public class ListGagGroupUserReslut {
	// 返回码，200 为正常.
	Integer code;
	// 群组被禁言用户列表。
	List<GagGroupUser> users;
	// 错误信息。
	String errorMessage;
	
	public ListGagGroupUserReslut(Integer code, List<GagGroupUser> users, String errorMessage) {
		this.code = code;
		this.users = users;
		this.errorMessage = errorMessage;
	}
	
	/**
	 * 获取code
	 *
	 * @return Integer
	 */
	public Integer getCode() {
		return code;
	}
	
	/**
	 * 设置code
	 *
	 */
	public void setCode(Integer code) {
		this.code = code;
	}
	
	/**
	 * 获取users
	 *
	 * @return List<GagGroupUser>
	 */
	public List<GagGroupUser> getUsers() {
		return users;
	}
	
	/**
	 * 设置users
	 *
	 */
	public void setUsers(List<GagGroupUser> users) {
		this.users = users;
	}
	
	/**
	 * 获取errorMessage
	 *
	 * @return String
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	
	/**
	 * 设置errorMessage
	 *
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	@Override
	public String toString() {
		return GsonUtil.toJson(this, ListGagGroupUserReslut.class);
	}
}
