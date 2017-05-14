package cn.devezhao.iwechat4j.utils;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import cn.devezhao.iwechat4j.Wechat;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/14/2017
 */
public class UploadTest {

	@Test
	public void testUploadImage() {
		Wechat wechat = new Wechat();
		wechat.login();
		
		String mediaId = Utils.uploadMedia(wechat, new File("d:/norton-signature.png"));
		Assert.assertNotNull(mediaId);
	}
}
