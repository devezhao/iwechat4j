package cn.devezhao.iwechat4j;

import org.junit.Test;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class WechatTest {

	@Test
	public void testLogin() {
		new Wechat().login();
	}
	
	@Test
	public void testAddFriend() {
		Wechat wechat = new Wechat();
		wechat.login();
		wechat.addFriend("devezhao2", "老赵加我");
	}
}
