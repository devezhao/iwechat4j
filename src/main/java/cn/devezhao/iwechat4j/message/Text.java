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
		super(message);
	}
	
	public String getContent() {
		String c = getMessageRaw().getString("Content");
		if (isFromGroup()) {
			c = c.substring(c.indexOf("<br/>") + 5);
		}
		return c;
	}
}
