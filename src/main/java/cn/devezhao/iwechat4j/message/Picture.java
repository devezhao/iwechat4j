package cn.devezhao.iwechat4j.message;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class Picture extends BaseMessage {

	@Override
	public MessageType geType() {
		return MessageType.Picture;
	}
}
