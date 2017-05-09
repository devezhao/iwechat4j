package cn.devezhao.iwechat4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 会话数据
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Session {

	private Map<String, String> data = new HashMap<String, String>();
	
	public Session() {
		super();
	}

	public String getUuid() {
		return getAttr("uuid");
	}

	public void setUuid(String uuid) {
		addAttr("uuid", uuid);
	}
	
	public void addAttr(String key, String value) {
		data.put(key, value);
	}
	
	public String getAttr(String key) {
		return data.get(key);
	}
	
	@Override
	public String toString() {
		return super.toString() + "#" + data.toString();
	}
}
