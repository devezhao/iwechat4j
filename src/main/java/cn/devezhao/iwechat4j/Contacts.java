package cn.devezhao.iwechat4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 联系人（列表）
 * 
 * @author zhaofang123@gmail.com
 * @since 05/15/2017
 */
public class Contacts {
	
	/** 公众号 */
	public static final int TYPE_MP = 3;
	/** 群组     */
	public static final int TYPE_GROUP = 2;
	/** 好友     */
	public static final int TYPE_FRIEND = 1;
	
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
		String r = wechat.getHttpClient().get(url);
		BaseResponse bresp = new BaseResponse(r);
		if (!bresp.isSuccess()) {
			LOG.error("加载联系人列表失败: " + bresp.getErrorMsg());
			return;
		}
		
		JSONObject rJson = bresp.getRawResponse();
		JSONArray memberList = rJson.getJSONArray("MemberList");
		for (Object o : memberList) {
			Member member = new Member((JSONObject) o);
			members.put(member.getUserName(), member);
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
		return getSpecMembers(TYPE_MP);
	}
	
	/**
	 * 获取群组
	 * 
	 * @return
	 */
	public Set<Member> getGroupMembers() {
		return getSpecMembers(TYPE_GROUP);
	}
	
	/**
	 * 获取好友
	 * 
	 * @return
	 */
	public Set<Member> getFriendMembers() {
		return getSpecMembers(TYPE_FRIEND);
	}
	
	/**
	 * 获取指定类型成员
	 * 
	 * @param specType
	 * @return
	 */
	private Set<Member> getSpecMembers(int specType) {
		Set<Member> set = new HashSet<>();
		for (Member member : members.values()) {
			if (member.getType() == specType) {
				set.add(member);
			}
		}
		return set;
	}
	
	/**
	 * 联系人
	 */
	public static class Member {
		private JSONObject raw;
		private String userName;
		private String nickName;
		private String signature;
		private String headimgUrl;
		private int type = TYPE_FRIEND;
		
		Member(JSONObject raw) {
			this.raw = raw;
			this.userName = raw.getString("UserName");
			this.nickName = raw.getString("NickName");
			this.signature = raw.getString("Signature");
			this.headimgUrl = raw.getString("HeadImgUrl");
			
			int verifyFlag = raw.getIntValue("VerifyFlag");
			if (verifyFlag > 0 && verifyFlag % 8 == 0) {
				this.type = TYPE_MP;
			} else if (this.userName.startsWith("@@")) {
				this.type = TYPE_GROUP;
			}
		}
		
		/**
		 * 获取成员标识
		 * @return
		 */
		public String getUserName() {
			return userName;
		}
		
		/**
		 * 获取昵称（如昵称为空，则“可能”表示此账号有问题）
		 * @return
		 */
		public String getNickName() {
			return nickName;
		}
		
		/**
		 * 获取签名
		 * @return
		 */
		public String getSignature() {
			return signature;
		}
		
		/**
		 * 获取头像（URL）
		 * @return
		 */
		public String getHeadimgUrl() {
			return headimgUrl;
		}
		
		/**
		 * 联系人类型
		 * @return
		 * @see Contacts#TYPE_FRIEND
		 * @see Contacts#TYPE_GROUP
		 * @see Contacts#TYPE_MP
		 */
		public int getType() {
			return type;
		}
		
		/**
		 * 获取原始数据对象
		 * @return
		 */
		public JSONObject getRaw() {
			return raw;
		}
		
		@Override
		public String toString() {
			return raw.toJSONString();
		}
	}
}
