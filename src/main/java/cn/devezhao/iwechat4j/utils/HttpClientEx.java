package cn.devezhao.iwechat4j.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class HttpClientEx {
	
	private CookieStore cookieStore;
	private CloseableHttpClient httpClient;
	
	private String userAgent;
	
	public HttpClientEx(int timeout, String userAgent) {
		this.cookieStore = new BasicCookieStore();
		this.httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		this.userAgent = userAgent;
	}
	
	public CookieStore getCookieStore() {
		return cookieStore;
	}
	
	/**
	 * @param url
	 * @return
	 */
	public String get(String url) {
		try {
			HttpResponse resp = execute(new HttpGet(url));
			return EntityUtils.toString(resp.getEntity());
		} catch (Exception e) {
			throw new ExecuteHttpMethodException("执行 GET 错误: " + url, e);
		}
	}
	
	/**
	 * @param url
	 * @return
	 */
	public byte[] readByte(String url) {
		try {
			HttpResponse resp = execute(new HttpGet(url));
			return EntityUtils.toByteArray(resp.getEntity());
		} catch (Exception e) {
			throw new ExecuteHttpMethodException("执行 GET 错误: " + url, e);
		}
	}
	
	/**
	 * @param url
	 * @param data
	 * @return
	 */
	public String post(String url, String data) {
		HttpPost post = new HttpPost(url);
		if (data != null) {
			post.setEntity(new StringEntity(data, "utf-8"));
			post.setHeader("content-type", "application/json; charset=utf-8");
		}
		
		try {
			HttpResponse resp = execute(post);
			return EntityUtils.toString(resp.getEntity(), "utf-8");
		} catch (IOException e) {
			throw new ExecuteHttpMethodException("执行 POST 错误: " + post, e);
		}
	}
	
	/**
	 * @param get
	 * @return
	 */
	public HttpResponse execute(HttpGet get) {
		get.addHeader("user-agent", userAgent);
		try {
			return httpClient.execute(get);
		} catch (IOException e) {
			throw new ExecuteHttpMethodException("执行 GET 错误: " + get, e);
		}
	}
	
	/**
	 * @param post
	 * @return
	 */
	public HttpResponse execute(HttpPost post) {
		post.addHeader("user-agent", userAgent);
		try {
			return httpClient.execute(post);
		} catch (IOException e) {
			throw new ExecuteHttpMethodException("执行 POST 错误: " + post, e);
		}
	}
}
