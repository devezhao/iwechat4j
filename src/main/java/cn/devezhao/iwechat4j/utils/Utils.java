package cn.devezhao.iwechat4j.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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
}
