package com.zoe.snow.c11n.rong.models;

import com.zoe.snow.c11n.rong.util.GsonUtil;

/**
 * 用于Push中的message。
 */
public class MsgObj {
	// push 消息中的消息体。
	String content;
	// 聊天室名称。
	String objectName;
	
	public MsgObj(String content, String objectName) {
		this.content = content;
		this.objectName = objectName;
	}
	
	/**
	 * 获取content
	 *
	 * @return String
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * 设置content
	 *
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * 获取objectName
	 *
	 * @return String
	 */
	public String getObjectName() {
		return objectName;
	}
	
	/**
	 * 设置objectName
	 *
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	
	@Override
	public String toString() {
		return GsonUtil.toJson(this, MsgObj.class);
	}
}
