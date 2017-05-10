package cn.devezhao.iwechat4j.listener;

import java.util.Observer;

import cn.devezhao.iwechat4j.message.Message;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public interface Listener extends Observer {
	
	void handle(Message message);
}
