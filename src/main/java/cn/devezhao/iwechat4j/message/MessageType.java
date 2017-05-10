package cn.devezhao.iwechat4j.message;

/**
 * 消息類型
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public enum MessageType {
	
	Text(1, "Text"), Image(3, "Image"),
	
	;
	
	private int number;
	private String name;
	
	MessageType(int number, String name) {
		this.number = number;
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}
	
	/**
	 * @param msgType
	 * @return
	 */
	public static MessageType forNumber(int msgType) {
		switch (msgType) {
		case 1:
			return MessageType.Text;
		case 3:
			return MessageType.Image;
		default:
			break;
		}
		return null;
	}
}
