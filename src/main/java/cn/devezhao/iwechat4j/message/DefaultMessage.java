package cn.devezhao.iwechat4j.message;

import com.alibaba.fastjson.JSONObject;

/**
 * 基础消息类
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class DefaultMessage implements Message {

	private JSONObject messageRaw;
	
	public DefaultMessage(JSONObject messageRaw) {
		this.messageRaw = messageRaw;
	}
	
	public DefaultMessage(Message message) {
		this.messageRaw = message.getMessageRaw();
	}
	
	@Override
	public MessageType getType() {
		return MessageType.forNumber(messageRaw.getIntValue("MsgType"));
	}
	
	@Override
	public JSONObject getMessageRaw() {
		return messageRaw;
	}
	
	@Override
	public String getFromUserName() {
		return messageRaw.getString("FromUserName");
	}
	
	@Override
	public String getToUserName() {
		return messageRaw.getString("ToUserName");
	}
	
	@Override
	public boolean isFromGroup() {
		// 群组标识有两个 @ 个人只有一个 @
		return getFromUserName().startsWith("@@");
	}
	
	@Override
	public boolean isFromMp() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public String toString() {
		return getMessageRaw().toString();
	}
}
