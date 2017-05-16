package cn.devezhao.iwechat4j;

import java.util.Set;

import com.alibaba.fastjson.JSONObject;

/**
 * 联系人
 * 
 * @author zhaofang123@gmail.com
 * @since 05/16/2017
 */
public class Member {
	
	/**
	 * 公众号
	 */
	public static final int TYPE_MP = 3;
	/** 
	 * 群组
	 */
	public static final int TYPE_GROUP = 2;
	/** 
	 * 好友
	 */
	public static final int TYPE_FRIEND = 1;

	private JSONObject raw;
	private String userName;
	private String nickName;
	private String signature;
	private String headimgUrl;
	private int type = TYPE_FRIEND;
	
	// 群组成员
	private Set<String> members;

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
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 获取昵称（如昵称为空，则“可能”表示此账号有问题）
	 * 
	 * @return
	 */
	public String getNickName() {
		return nickName;
	}

	/**
	 * 获取签名
	 * 
	 * @return
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 获取头像（URL）
	 * 
	 * @return
	 */
	public String getHeadimgUrl() {
		// https://wx.qq.com/
		return headimgUrl;
	}

	/**
	 * 联系人类型
	 * 
	 * @return
	 * @see Contacts#TYPE_FRIEND
	 * @see Contacts#TYPE_GROUP
	 * @see Contacts#TYPE_MP
	 */
	public int getType() {
		return type;
	}

	/**
	 * 成员列表。仅群组才有
	 */
	public Set<String> getMemberList() {
		return members;
	}
	
	/**
	 * @param members
	 */
	protected void setMemberList(Set<String> members) {
		this.members = members;
	}

	/**
	 * 获取原始数据对象
	 * 
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
