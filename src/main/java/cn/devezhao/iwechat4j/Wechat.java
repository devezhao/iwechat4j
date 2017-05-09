package cn.devezhao.iwechat4j;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.devezhao.iwechat4j.listener.Listener;
import cn.devezhao.iwechat4j.message.Message;
import cn.devezhao.iwechat4j.utils.HttpClientEx;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Wechat implements Api {

	static {
		System.setProperty("jsse.enableSNIExtension", "false");
	}
	
	private static final Log LOG = LogFactory.getLog(Wechat.class);
	
	private HttpClientEx httpClient;
	
	private Session session;
	private boolean alive;
	
	public Wechat() {
		this.session = new Session();
		this.httpClient = new HttpClientEx(Config.TIMEOUT, Config.USER_AGENT);
	}
	
	@Override
	public void login() {
		if (isAlive()) {
			return;
		}
		
		Login login = new Login(this);
		File qr = login.getLoginQR();
		LOG.info("请扫描二维码登录: " + qr);
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			int status = login.checkStatus();
			if (status == 200) {
				alive = true;
				LOG.info("登录成功: " + session);
				break;
			} else {
				LOG.debug("检查登录状态: " + status);
			}
		}
	}
	
	@Override
	public void startListener() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addListener(Listener messageListener) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void sendMessage(Message message) {
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	protected Session getSession() {
		return session;
	}
	
	protected HttpClientEx getHttpClient() {
		return httpClient;
	}
}
