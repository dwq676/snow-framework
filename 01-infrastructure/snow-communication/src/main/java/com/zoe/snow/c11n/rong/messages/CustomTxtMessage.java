package com.zoe.snow.c11n.rong.messages;

import com.zoe.snow.c11n.rong.util.GsonUtil;

/**
 *
 * 自定义消息
 *
 */
public class CustomTxtMessage extends BaseMessage {
	private transient static final String TYPE = "RC:TxtMsg";
	private String content = "";
	
	public CustomTxtMessage(String content) {
		this.content = content;
	}
	
	public String getType() {
		return TYPE;
	}
	
	/**
	 * 获取自定义消息内容。
	 *
	 * @returnString
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * 设置自定义消息内容。
	 *
	 * @return
	 */
	public void setContent(String content) {
		this.content = content;
	}  
	
	@Override
	public String toString() {
		return GsonUtil.toJson(this, CustomTxtMessage.class);
	}
}