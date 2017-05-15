package cn.devezhao.iwechat4j;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class LoginTest {

	@Test
	public void testLogin() {
		boolean OK = new Wechat().login();
		Assert.assertTrue(OK);
	}
}
