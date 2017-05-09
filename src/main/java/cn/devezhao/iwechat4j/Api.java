package cn.devezhao.iwechat4j;

import cn.devezhao.iwechat4j.listener.Listener;
import cn.devezhao.iwechat4j.message.Message;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public interface Api {

	/**
	 * 扫码登录
	 */
	void login();
	
	/**
	 * 启动消息监听
	 */
	void startListener();
	
	/**
	 * 添加消息监听器
	 * 
	 * @param messageListener
	 */
	void addListener(Listener messageListener);
	
	/**
	 * 发送消息
	 * 
	 * @param message
	 */
	void sendMessage(Message message);
	
}
