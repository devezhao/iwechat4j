package cn.devezhao.iwechat4j.message;

import java.io.File;

import com.alibaba.fastjson.JSONObject;

import cn.devezhao.iwechat4j.Wechat;
import cn.devezhao.iwechat4j.utils.Utils;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Image extends DefaultMessage {
	
	public Image(JSONObject message) {
		super(message);
	}
	
	public Image(Message message) {
		super(message);
	}
	
	/**
	 * 获取下载链接（此链接仅限当前会话可访问）
	 * 
	 * @param wechat
	 * @return
	 */
	public String getImageUrl(Wechat wechat) {
		String dlUrl = String.format("%s/webwxgetmsgimg?msgid=%s&skey=%s",
				wechat.getSession().getBaseUrl(), getMessageRaw().getString("NewMsgId"), wechat.getSession().getSkey());
		return dlUrl;
	}
	
	/**
	 * 下载文件
	 * 
	 * @param wechat
	 * @param dest
	 * @return
	 */
	public boolean download(Wechat wechat, File dest) {
		String dlUrl = getImageUrl(wechat);
		return Utils.downloadFile(wechat.getHttpClient(), dest, dlUrl);
	}
}
