package cn.devezhao.iwechat4j;

import cn.devezhao.iwechat4j.listener.DefaultListener;
import cn.devezhao.iwechat4j.message.Message;
import cn.devezhao.iwechat4j.message.MessageType;
import cn.devezhao.iwechat4j.message.Picture;
import cn.devezhao.iwechat4j.message.Text;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class DEMO {

	public static void main(String[] args) {
		Wechat wechat = new Wechat();
		wechat.login();
		wechat.start();
		
		wechat.addListener(new DefaultListener() {
			@Override
			public void handle(Message message) {
				super.handle(message);
				
				if (MessageType.Text.equals(message.getType())) {
					System.out.println("收到文本消息: " + new Text(message).getContent());
				}
				else if (MessageType.Picture.equals(message.getType())) {
					System.out.println("收到图片消息: " + new Picture(message).getPicUrl());
				}
				
			}
		});
	}
}