package cn.devezhao.iwechat4j.message;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public abstract class Event implements Message {

	@Override
	public MessageType geType() {
		return null;
	}
	
	/**
	 * 获取事件类型
	 * 
	 * @return
	 */
	abstract public MessageType getEventType();
}
