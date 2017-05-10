package cn.devezhao.iwechat4j;

import java.io.File;

/**
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Config {
	
	public static final String LOGIN_URL = "https://login.weixin.qq.com";
	public static final String BASE_URL = "https://wx.qq.com/cgi-bin/mmwebwx-bin";
	
	public static final int TIMEOUT = 30 * 1000;
	public static final File QR_DIR = new File(System.getProperty("java.io.tmpdir"));
	
	public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36";
}
