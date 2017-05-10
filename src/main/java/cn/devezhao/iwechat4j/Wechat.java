package cn.devezhao.iwechat4j;

import java.io.File;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.devezhao.iwechat4j.listener.Listener;
import cn.devezhao.iwechat4j.message.DefaultMessage;
import cn.devezhao.iwechat4j.message.Message;
import cn.devezhao.iwechat4j.utils.HttpClientEx;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Wechat extends Observable implements Api {

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
	
	public boolean isAlive() {
		return alive;
	}
	
	protected Session getSession() {
		return session;
	}
	
	protected HttpClientEx getHttpClient() {
		return httpClient;
	}
	
	@Override
	public void login() {
		if (isAlive()) {
			return;
		}
		
		Login login = new Login(this);
		File qr = login.getLoginQR();
		LOG.info("请扫描二维码登录: " + qr);
		try {
			Runtime.getRuntime().exec("cmd /c start " + qr.getAbsolutePath());
		} catch (Exception e) { }
		for (int i = 0; i < 10; i++) {
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
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
	public void start() {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						TimeUnit.MILLISECONDS.sleep(1000);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
					
					int status = 0;
					try {
						status = checkMessage();
					} catch (Exception ex) {
						LOG.error("检查消息失败", ex);
					}
					
					if (status == -1) {
						LOG.error("离线");
						alive = false;
					} else if (status == 0) {
						continue;
					} else {
						try {
							JSONArray revMessages = revMessages();
							for (Object o : revMessages) {
								JSONObject m = (JSONObject) o;
								m.put("__skey", getSession().getAttr("skey"));
								m.put("__baseUrl", getSession().getAttr("baseUrl"));
								setChanged();
								notifyObservers(new DefaultMessage(m));
							}
						} catch (Exception ex) {
							LOG.error("接收消息失败", ex);
						}
					}
				}
			}
		});
		t.start();
	}
	
	@Override
	public void addListener(Listener messageListener) {
		addObserver(messageListener);
	}
	
	@Override
	public void sendMessage(Message message) {
	}
	
	@Override
	public void addFriend(String uin, String verifyContent) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * 检查是否有消息
	 * 
	 * @return
	 * @throws Exception
	 */
	private int checkMessage() throws Exception {
		String url = String.format("%s/synccheck?r=%d&skey=%s&sid=%s&uin=%s&deviceid=%s&synckey=%s",
				getSession().getBaseUrl(), System.currentTimeMillis(), 
				getSession().getAttr("skey"), getSession().getAttr("wxsid"), getSession().getAttr("wxuin"),
				getSession().getAttr("deviceid"), URLEncoder.encode(getSession().getAttr("syncKey"), "utf-8"));
		String r = getHttpClient().get(url);
		if (r == null) {
			return -1;
		}
		
		String regex = "window.synccheck=\\{retcode:\"(\\d+)\",selector:\"(\\d+)\"\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(r);
		if (!matcher.find() || !"0".equals(matcher.group(1))) {
			LOG.warn("无效的检查是否有消息结果: " + r);
			return 0;
		} else {
			String s = matcher.group(2);
			return Integer.parseInt(s);
		}
	}
	
	/**
	 * 接收消息
	 * 
	 * @return
	 * @throws Exception
	 */
	private JSONArray revMessages() throws Exception {
		String url = String.format("%s/webwxsync?sid=%s&skey=%s&pass_ticket=%s",
				getSession().getBaseUrl(), getSession().getAttr("wxsid"), 
				getSession().getAttr("skey"), getSession().getPassTicket());
		
		Map<String, Object> params = new HashMap<>();
		params.put("BaseRequest", getSession().getBaseRequestParams());
		params.put("SyncKey", getSession().getSyncKeyRaw());
		params.put("rr", -new Date().getTime() / 1000);
		
		String data = JSON.toJSONString(params);
		String r = httpClient.post(url, data);
		JSONObject rJson = JSON.parseObject(r);
		
		JSONObject syncKey = rJson.getJSONObject("SyncCheckKey");
		if (syncKey.getInteger("Count") > 0) {
			getSession().setSyncKeyRaw(syncKey);
		}
		return rJson.getJSONArray("AddMsgList");
	}
}
