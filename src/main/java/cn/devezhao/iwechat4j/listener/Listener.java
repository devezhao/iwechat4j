package cn.devezhao.iwechat4j.listener;

import cn.devezhao.iwechat4j.message.Message;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public interface Listener {

	void handleMessage(Message message);
}
