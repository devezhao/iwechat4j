package cn.devezhao.iwechat4j.message;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class DefaultMessage implements Message {

	private JSONObject messageRaw;
	
	public DefaultMessage(JSONObject messageRaw) {
		this.messageRaw = messageRaw;
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
	public String toString() {
		return getMessageRaw().toString();
	}
}
