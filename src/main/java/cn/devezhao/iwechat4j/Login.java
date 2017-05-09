package cn.devezhao.iwechat4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.devezhao.iwechat4j.utils.NonExpectedException;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Login {

	private Wechat wechat;
	
	public Login(Wechat wechat) {
		this.wechat = wechat;
	}
	
	/**
	 * 获取登录 QR
	 * 
	 * @return
	 */
	public File getLoginQR() {
		String uuid = getQrUuid();
		wechat.getSession().setUuid(uuid);
		
		String url = String.format("%s/qrcode/%s", Config.BASE_URL, uuid);
		byte bs[] = wechat.getHttpClient().readByte(url);
		
		File qr = new File(Config.QR_DIR, "iwechat4j");
		if (!qr.exists()) {
			qr.mkdirs();
		}
		qr = new File(qr, System.currentTimeMillis() + ".jpg");
		OutputStream os = null;
		try {
			os = new FileOutputStream(qr);
			os.write(bs);
			os.flush();
			return qr;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				os.close();
			} catch (Exception ex) { }
		}
	}
	
	/**
	 * 检查登录（扫码）状态
	 * 
	 * @return
	 */
	public int checkStatus() {
		String url = String.format("%s/cgi-bin/mmwebwx-bin/login?loginicon=true&uuid=%s&tip=0&r=%d",
				Config.BASE_URL, wechat.getSession().getUuid(), System.currentTimeMillis());
		String r = wechat.getHttpClient().get(url);
		
		String regex = "window.code=(\\d+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(r);
		if (matcher.find()) {
			String status = matcher.group(1);
			int s = Integer.parseInt(status);
			if (s == 200) {
				try {
					stroageSession(r);
				} catch (DocumentException e) {
					throw new NonExpectedException("无效 XML 返回数据" + r);
				}
			}
			return s;
		}
		throw new NonExpectedException(r);
	}
	
	/**
	 * 获取 QR UUID
	 * 
	 * @return
	 */
	protected String getQrUuid() {
		String url = String.format("%s/jslogin?appid=wx782c26e4c19acffb&fun=new", Config.BASE_URL);
		String r = wechat.getHttpClient().get(url);
		
		String regex = "window.QRLogin.code = (\\d+); window.QRLogin.uuid = \"(\\S+?)\";";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(r);
		if (matcher.find()) {
			String uuid = matcher.group(2);
			return uuid;
		}
		throw new NonExpectedException("获取 QR UUID 错误: " + r);
	}
	
	/**
	 * 存储会话数据
	 * 
	 * @param content
	 * @throws DocumentException 
	 */
	protected void stroageSession(String content) throws DocumentException {
		String regex = "window.redirect_uri=\"(\\S+)\"";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);
		matcher.find();
		String redirectUri = matcher.group(1);
		wechat.getSession().addAttr("url", redirectUri);
		
		String r = wechat.getHttpClient().get(redirectUri);
		Element root = DocumentHelper.parseText(r).getRootElement();
		
		String attrs[] = new String[] { "skey", "wxsid", "wxuin", "pass_ticket" };
		for (String k : attrs) {
			wechat.getSession().addAttr(k, root.selectSingleNode("//" + k).getText());
		}
	}
}
