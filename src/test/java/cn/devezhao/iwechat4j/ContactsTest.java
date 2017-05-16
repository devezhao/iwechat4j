package cn.devezhao.iwechat4j;

import org.junit.Test;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/15/2017
 */
public class ContactsTest {

	@Test
	public void testStore() {
		Wechat wechat = new Wechat();
		wechat.login();
		
		Contacts contacts = wechat.getContacts();
		System.out.println("-- 公众 ---------------");
		for (Member m : contacts.getMpMembers()) {
			System.out.println(" |- " + m.getNickName());
		}
		
		System.out.println("-- 群组 ---------------");
		for (Member m : contacts.getGroupMembers()) {
			System.out.println(" |- " + m.getNickName());
			System.out.println(" --|- " + m.getMemberList());
		}
		
		System.out.println("-- 好友 ---------------");
		for (Member m : contacts.getFriendMembers()) {
			System.out.println(" |- " + m.getNickName());
		}
	}
}
