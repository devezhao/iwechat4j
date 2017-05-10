package cn.devezhao.iwechat4j.message;

import java.io.File;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Picture extends DefaultMessage {
	
	public Picture(JSONObject message) {
		super(message);
	}
	
	public Picture(Message message) {
		super(message.getMessageRaw());
	}
	
	public String getPicUrl() {
		String skey = getMessageRaw().getString("__skey");
		String baseUrl = getMessageRaw().getString("__baseUrl");
		String dlUrl = String.format("%s/webwxgetmsgimg?msgid=%s&skey=%s",
				baseUrl, getMessageRaw().getString("NewMsgId"), skey);
		return dlUrl;
	}
	
	public boolean download(File dest) {
		return false;
	}
}
