package cn.devezhao.iwechat4j.message;

import com.alibaba.fastjson.JSONObject;

/**
 * （收到的）消息
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public interface Message {

	/**
	 * 消息类型
	 * 
	 * @return
	 * @see MessageType
	 */
	MessageType getType();
	
	/**
	 * 原始消息（JSON）
	 * 
	 * @return
	 */
	JSONObject getMessageRaw();
	
	/**
	 * 消息发送人
	 * 
	 * @return
	 */
	String getFromUserName();
	
	/**
	 * 消息接收人
	 * 
	 * @return
	 */
	String getToUserName();
	
	/**
	 * 是否群组消息
	 * 
	 * @return
	 */
	boolean isFromGroup();
	
	/**
	 * 是否公众号消息
	 * 
	 * @return
	 */
	boolean isFromMp();
}
