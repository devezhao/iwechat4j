package cn.devezhao.iwechat4j.message;

import java.io.File;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import cn.devezhao.iwechat4j.BaseResponse;
import cn.devezhao.iwechat4j.Wechat;
import cn.devezhao.iwechat4j.utils.Utils;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/12/2017
 */
public class ImageSend implements Send {

	private String toUserName;
	private File imgFile;
	
	/**
	 * @param toUserName
	 * @param imgFile
	 */
	public ImageSend(String toUserName, File imgFile) {
		this.toUserName = toUserName;
		this.imgFile = imgFile;
	}
	
	@Override
	public boolean send(Wechat wechat) {
		String mediaId = Utils.uploadMedia(wechat, imgFile);
		
		String url = String.format("%s/webwxsendmsgimg?fun=async&f=json", wechat.getSession().getBaseUrl());
		Map<String, Object> dataMap = Utils.buildBaseRequestParams(wechat);
		@SuppressWarnings("unchecked")
		Map<String, Object> msg = (Map<String, Object>) dataMap.get("Msg");
		msg.put("Type", MessageType.Image.getNumber());
		msg.put("MediaId", mediaId);
		msg.put("ToUserName", toUserName);
		
		String data = JSON.toJSONString(dataMap);
		String rs = wechat.getHttpClient().postJson(url, data);
		BaseResponse bresp = new BaseResponse(rs);
		return bresp.isSuccess();
	}
}
