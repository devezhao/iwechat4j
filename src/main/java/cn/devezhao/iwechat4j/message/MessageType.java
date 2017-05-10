package cn.devezhao.iwechat4j.message;

/**
 * 消息類型
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public enum MessageType {
	
	Text(1, "Text", "文本"), Picture(1, "Picture", "图片"),
	
	;
	
	private int number;
	private String name;
	private String description;
	
	MessageType(int number, String name, String description) {
		this.number = number;
		this.name = name;
		this.description = description;
	}

	public int getNumber() {
		return number;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
	
	/**
	 * @param msgType
	 * @return
	 */
	public static MessageType forNumber(int msgType) {
		switch (msgType) {
		case 1:
			return MessageType.Text;
		case 2:
			return MessageType.Picture;
		default:
			break;
		}
		return null;
	}
}
