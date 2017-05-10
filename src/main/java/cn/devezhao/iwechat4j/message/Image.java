package cn.devezhao.iwechat4j.message;

import java.io.File;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Image extends DefaultMessage {
	
	public Image(JSONObject message) {
		super(message);
	}
	
	public Image(Message message) {
		super(message.getMessageRaw());
	}
	
	public String getImageUrl() {
		String skey = getMessageRaw().getString("__skey");
		String baseUrl = getMessageRaw().getString("__baseUrl");
		String dlUrl = String.format("%s/webwxgetmsgimg?msgid=%s&skey=%s",
				baseUrl, getMessageRaw().getString("NewMsgId"), skey);
		return dlUrl;
	}
	
	public boolean download(File dest) {
		// TODO 下载图片
		return false;
	}
}
