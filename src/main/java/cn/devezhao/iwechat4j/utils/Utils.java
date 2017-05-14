package cn.devezhao.iwechat4j.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

import cn.devezhao.iwechat4j.BaseResponse;
import cn.devezhao.iwechat4j.Config;
import cn.devezhao.iwechat4j.Wechat;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Utils {
	
	/**
	 * 下载文件
	 * 
	 * @param httpClient
	 * @param dest
	 * @param dlUrl
	 * @return
	 */
	public static boolean downloadFile(HttpClientEx httpClient, File dest, String dlUrl) {
		byte bs[] = httpClient.readByte(dlUrl);
		OutputStream os = null;
		try {
			os = new FileOutputStream(dest);
			os.write(bs);
			os.flush();
			return true;
		} catch (IOException e) {
			throw new RuntimeException("无法现在文件: " + dlUrl, e);
		} finally {
			try {
				os.close();
			} catch (Exception ex) { }
		}
	}
	
	/**
	 * 上传文件
	 * 
	 * @param wechat
	 * @param file
	 * @return
	 */
	public static String uploadMedia(Wechat wechat, File file) {
		long fsize = file.length();
		String fpath = file.getAbsolutePath();
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("BaseRequest", wechat.getSession().getBaseRequest());
		dataMap.put("ClientMediaId", System.currentTimeMillis());
		dataMap.put("TotalLen", fsize);
		dataMap.put("StartPos", 0);
		dataMap.put("DataLen", fsize);
		dataMap.put("MediaType", 4);
//		dataMap.put("UploadType", 2);
//		dataMap.put("FromUserName", "");
//		dataMap.put("ToUserName", "");
//		dataMap.put("FileMd5", "");
		
		String mimeType = new MimetypesFileTypeMap().getContentType(file);
		if (mimeType == null) {
			mimeType = "text/plain";
		}
		String mediaType = mimeType.contains("image/") ? "pic" : "doc";
		
		String webwx_data_ticket = wechat.getHttpClient().readCookie("webwx_data_ticket");
		if (webwx_data_ticket == null) {
		}
		 
		HttpEntity entity = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.addTextBody("id", "WU_FILE_0", ContentType.TEXT_PLAIN)
				.addTextBody("name", fpath, ContentType.TEXT_PLAIN)
				.addTextBody("type", mimeType, ContentType.TEXT_PLAIN)
				.addTextBody("lastModifieDate", new SimpleDateFormat("yyyy MM dd HH:mm:ss").format(new Date()), ContentType.TEXT_PLAIN)
				.addTextBody("size", String.valueOf(fsize), ContentType.TEXT_PLAIN)
				.addTextBody("mediatype", mediaType, ContentType.TEXT_PLAIN)
				.addTextBody("uploadmediarequest", JSON.toJSONString(dataMap), ContentType.TEXT_PLAIN)
				.addTextBody("webwx_data_ticket", webwx_data_ticket, ContentType.TEXT_PLAIN)
				.addTextBody("pass_ticket", wechat.getSession().getPassTicket(), ContentType.TEXT_PLAIN)
				.addBinaryBody("filename", file, ContentType.create(mimeType), fpath)
				.build();
		
		String url = String.format("%s/webwxuploadmedia?f=json", wechat.getSession().getUploadUrl());
		HttpPost post = new HttpPost(url);
		post.setEntity(entity);
		post.setConfig(RequestConfig.custom()
				.setConnectTimeout(Config.TIMEOUT * 6).setSocketTimeout(Config.TIMEOUT * 6).build());
		String rs = null;
		try {
			HttpResponse resp = wechat.getHttpClient().execute(post);
			rs = EntityUtils.toString(resp.getEntity(), "utf-8");
		} catch (IOException e) {
			throw new ExecuteHttpMethodException("上传文件失败: " + file, e);
		}
		
		BaseResponse resp = new BaseResponse(rs);
		if (resp.isSuccess()) {
			return resp.getRawResponse().getString("MediaId");
		}
		return null;
	}
	
	/**
	 * 基础请求参数
	 * 
	 * @param wechat
	 * @return
	 */
	public static Map<String, Object> buildBaseRequestParams(Wechat wechat) {
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("BaseRequest", wechat.getSession().getBaseRequest());
		dataMap.put("Scene", 0);
		Map<String, Object> msg = new HashMap<>();
		msg.put("FromUserName", wechat.getSession().getUserName());
		msg.put("LocalID", System.currentTimeMillis());
		msg.put("ClientMsgId", System.currentTimeMillis());
		dataMap.put("Msg", msg);
		return dataMap;
	}
}
