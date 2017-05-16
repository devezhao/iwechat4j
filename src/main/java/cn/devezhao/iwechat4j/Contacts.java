package cn.devezhao.iwechat4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 联系人（列表）
 * 
 * @author zhaofang123@gmail.com
 * @since 05/15/2017
 */
public class Contacts {
	
	private static final Log LOG = LogFactory.getLog(Contacts.class);
	
	private Wechat wechat;
	private Map<String, Member> members = new HashMap<>();
	
	public Contacts(Wechat wechat) {
		this.wechat = wechat;
	}
	
	/**
	 * 加载联系人
	 */
	protected void store() {
		String url = String.format("%s/webwxgetcontact?pass_ticket=%s&skey=%s&r=%d",
				wechat.getSession().getBaseUrl(), wechat.getSession().getPassTicket(), wechat.getSession().getSkey(), System.currentTimeMillis());
		String rs = wechat.getHttpClient().get(url);
		BaseResponse bresp = new BaseResponse(rs);
		if (!bresp.isSuccess()) {
			LOG.error("加载联系人列表失败: " + bresp.getErrorMsg());
			return;
		}
		
		JSONObject rJson = bresp.getResponse();
		JSONArray memberList = rJson.getJSONArray("MemberList");
		for (Object o : memberList) {
			Member member = new Member((JSONObject) o);
			members.put(member.getUserName(), member);
		}
		
		// 加载群组成员
		Set<Map<String, String>> groups = new HashSet<>();
		for (Member group : getGroupMembers()) {
			Map<String, String> map = new HashMap<>(2);
			map.put("UserName", group.getUserName());
			map.put("EncryChatRoomId", group.getRaw().getString("EncryChatRoomId"));
			groups.add(map);
		}
		
		Map<String, Object> dataMap = new HashMap<>();
		dataMap.put("BaseRequest", wechat.getSession().getBaseRequest());
		dataMap.put("Count", groups.size());
		dataMap.put("List", groups);
		String data = JSON.toJSONString(dataMap);
		url = String.format("%s/webwxbatchgetcontact?type=ex&r=%d", wechat.getSession().getBaseUrl(), System.currentTimeMillis());
		rs = wechat.getHttpClient().postJson(url, data);
		bresp = new BaseResponse(rs);
		
		JSONArray ContactList = bresp.getResponse().getJSONArray("ContactList");
		for (Object o : ContactList) {
			JSONObject jo = (JSONObject) o;
			String groupName = jo.getString("UserName");
			JSONArray MemberList = jo.getJSONArray("MemberList");
			Set<String> groupMembers = new HashSet<>();
			for (Object o2 : MemberList) {
				JSONObject jo2 = (JSONObject) o2;
				groupMembers.add(jo2.getString("UserName"));
			}
			getMember(groupName).setMemberList(groupMembers);
		}
		
		LOG.info("成功加载 " + members.size() + " 个联系人（好友/群组/公众号）列表");
	}
	
	/**
	 * 获取联系人
	 * 
	 * @param userName
	 * @return
	 */
	public Member getMember(String userName) {
		return members.get(userName);
	}
	
	/**
	 * 根据昵称获取联系人，可能返回多个
	 * 
	 * @param userName
	 * @return
	 */
	public Member[] getMemberByNick(String nickName) {
		Set<Member> set = new HashSet<>();
		for (Member member : members.values()) {
			if (member.getNickName().equalsIgnoreCase(nickName)) {
				set.add(member);
			}
		}
		return set.toArray(new Member[set.size()]);
	}
	
	/**
	 * 获取公众号
	 * 
	 * @return
	 */
	public Set<Member> getMpMembers() {
		return getMembersByType(Member.TYPE_MP);
	}
	
	/**
	 * 获取群组
	 * 
	 * @return
	 */
	public Set<Member> getGroupMembers() {
		return getMembersByType(Member.TYPE_GROUP);
	}
	
	/**
	 * 获取好友
	 * 
	 * @return
	 */
	public Set<Member> getFriendMembers() {
		return getMembersByType(Member.TYPE_FRIEND);
	}
	
	/**
	 * 获取指定类型成员
	 * 
	 * @param memberType
	 * @return
	 */
	private Set<Member> getMembersByType(int memberType) {
		Set<Member> set = new HashSet<>();
		for (Member member : members.values()) {
			if (member.getType() == memberType) {
				set.add(member);
			}
		}
		return set;
	}
}
