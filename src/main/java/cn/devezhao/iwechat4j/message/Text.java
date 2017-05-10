package cn.devezhao.iwechat4j.message;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Text extends DefaultMessage {

	public Text(JSONObject message) {
		super(message);
	}
	
	public Text(Message message) {
		super(message.getMessageRaw());
	}
	
	public String getContent() {
		return getMessageRaw().getString("Content");
	}
}
