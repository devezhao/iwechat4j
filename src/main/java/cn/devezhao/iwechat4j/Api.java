package cn.devezhao.iwechat4j;

import cn.devezhao.iwechat4j.listener.Listener;
import cn.devezhao.iwechat4j.message.Send;

/**
 * API of Wechat
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public interface Api {

	/**
	 * 扫码登录
	 */
	boolean login();
	
	/**
	 * 启动消息监听
	 */
	void start();
	
	/**
	 * 添加消息/事件监听器
	 * 
	 * @param messageListener
	 */
	void addListener(Listener listener);
	
	/**
	 * 发送消息
	 * 
	 * @param message
	 * @return
	 */
	boolean sendMessage(Send message);
	
	/**
	 * 添加好友
	 * 
	 * @param useruin 微信ID/手机/UUID
	 * @param verifyContent
	 * @return
	 */
	boolean addFriend(String useruin, String verifyContent);
}
