package cn.devezhao.iwechat4j;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 封装微信返回消息
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class BaseResponse {

	private JSONObject rawResponse;
	
	public BaseResponse(String response) {
		this.rawResponse = JSON.parseObject(response);
	}
	
	public JSONObject getRawResponse() {
		return rawResponse;
	}
	
	public boolean isSuccess() {
		JSONObject br = rawResponse.getJSONObject("BaseResponse");
		return br.getInteger("Ret") == 0;
	}
	
	public String getErrorMsg() {
		JSONObject br = rawResponse.getJSONObject("BaseResponse");
		return br.getIntValue("Ret") + "#" + br.getString("ErrMsg");
	}
}
