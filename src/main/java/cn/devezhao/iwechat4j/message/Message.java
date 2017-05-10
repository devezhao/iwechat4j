package cn.devezhao.iwechat4j.message;

import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public interface Message {

	MessageType getType();
	
	JSONObject getMessageRaw();
	
	String getFromUser();
	
	String getToUser();
}
