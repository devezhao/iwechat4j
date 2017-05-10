package cn.devezhao.iwechat4j.listener;

import java.util.Observable;

import cn.devezhao.iwechat4j.message.Message;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class DefaultListener implements Listener {

	@Override
	public void update(Observable o, Object data) {
		handle((Message) data);
	}
	
	@Override
	public void handle(Message message) {
		System.out.println("收到消息: " + message);
	}
}
