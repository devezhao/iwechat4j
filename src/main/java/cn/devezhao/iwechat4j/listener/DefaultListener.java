package cn.devezhao.iwechat4j.listener;

import java.util.Observable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.devezhao.iwechat4j.Wechat;
import cn.devezhao.iwechat4j.message.Image;
import cn.devezhao.iwechat4j.message.Message;
import cn.devezhao.iwechat4j.message.MessageType;
import cn.devezhao.iwechat4j.message.Text;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class DefaultListener implements Listener {

	private static final Log LOG = LogFactory.getLog(DefaultListener.class);
	
	@Override
	final 
	public void update(Observable o, Object data) {
		handle((Wechat) o, (Message) data);
	}
	
	@Override
	public void handle(Wechat wechat, Message message) {
		if (MessageType.Text.equals(message.getType())) {
			handleText(wechat, new Text(message));
		} else if (MessageType.Image.equals(message.getType())) {
			handleImage(wechat, new Image(message));
		} else {
			LOG.debug("收到<其他>消息: " + message);
		}
	}
	
	public void handleText(Wechat wechat, Text message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("收到<文本>消息: " + message.getContent());
		}
	}
	
	public void handleImage(Wechat wechat, Image message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("收到<图片>消息: " + message.getImageUrl());
		}
	}
}
