package com.zoe.snow.c11n.rong.models;

import com.zoe.snow.c11n.rong.util.GsonUtil;

/**
 *  http 成功返回结果
 */
public class CodeSuccessReslut {
	// 返回码，200 为正常。
	Integer code;
	// 错误信息。
	String errorMessage;
	
	public CodeSuccessReslut(Integer code, String errorMessage) {
		this.code = code;
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
		return GsonUtil.toJson(this, CodeSuccessReslut.class);
	}
}
