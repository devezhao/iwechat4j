package cn.devezhao.iwechat4j;

import cn.devezhao.iwechat4j.listener.DefaultListener;

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
		
		wechat.addListener(new DefaultListener());
	}
}