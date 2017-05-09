package cn.devezhao.iwechat4j.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class HttpClientEx {
	
	private CloseableHttpClient httpClient;
	private String userAgent;
	
	public HttpClientEx(int timeout, String userAgent) {
		httpClient = HttpClients.createMinimal();
		this.userAgent = userAgent;
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
	 * @param post
	 * @return
	 */
	public HttpResponse post(HttpPost post) {
		post.addHeader("user-agent", userAgent);
		try {
			return httpClient.execute(post);
		} catch (IOException e) {
			throw new ExecuteHttpMethodException("执行 POST 错误: " + post, e);
		}
	}
	
	public HttpResponse execute(HttpGet get) {
		get.addHeader("user-agent", userAgent);
		try {
			return httpClient.execute(get);
		} catch (IOException e) {
			throw new ExecuteHttpMethodException("执行 GET 错误: " + get, e);
		}
	}
}
