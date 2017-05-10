package cn.devezhao.iwechat4j;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 会话数据
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Session {

	private Map<String, String> data = new HashMap<String, String>();
	
	private JSONObject userRaw;
	private JSONObject syncKeyRaw;
	
	public Session() {
		super();
	}
	
	public String getPassTicket() {
		return getAttr("pass_ticket");
	}
	
	public String getBaseUrl() {
		return getAttr("baseUrl");
	}
	
	public String getSkey() {
		return getAttr("skey");
	}
	
	public void addAttr(String key, String value) {
		data.put(key, value);
	}
	
	public String getAttr(String key) {
		return data.get(key);
	}
	
	public Map<String, String> getBaseRequestParams() {
		Map<String, String> map = new HashMap<>();
		map.put("Skey", getAttr("skey"));
		map.put("Sid", getAttr("wxsid"));
		map.put("Uin", getAttr("wxuin"));
		map.put("DeviceID", getAttr("deviceid"));
		return map;
	}
	
	public void setUserRaw(JSONObject userRaw) {
		this.userRaw = userRaw;
	}
	
	public JSONObject getUserRaw() {
		return userRaw;
	}
	
	public void setSyncKeyRaw(JSONObject syncKeyRaw) {
		this.syncKeyRaw = syncKeyRaw;
		
		JSONArray syncKeyList = syncKeyRaw.getJSONArray("List");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < syncKeyList.size(); i++) {
			JSONObject jo = syncKeyList.getJSONObject(i);
			sb.append(jo.getString("Key") + "_" + jo.getString("Val") + "|");
		}
		// eg. 1_675424816|2_675424828|3_675424645|1000_1494394202|
		addAttr("syncKey", sb.toString());
	}
	
	public JSONObject getSyncKeyRaw() {
		return syncKeyRaw;
	}
	
	public String getUserName() {
		return userRaw.getString("UserName");
	}
	
	public String getUserNick() {
		return userRaw.getString("UserNick");
	}
	 
	@Override
	public String toString() {
		return super.toString() + "#" + data.toString();
	}
}
