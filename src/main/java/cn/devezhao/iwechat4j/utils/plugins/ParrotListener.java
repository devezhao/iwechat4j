package cn.devezhao.iwechat4j.utils.plugins;

import cn.devezhao.iwechat4j.Wechat;
import cn.devezhao.iwechat4j.listener.DefaultListener;
import cn.devezhao.iwechat4j.message.Image;
import cn.devezhao.iwechat4j.message.Text;
import cn.devezhao.iwechat4j.message.TextSend;

/**
 * 
 * @author zhaofang123@gmail.com
 * @since 05/10/2017
 */
public class ParrotListener extends DefaultListener {

	@Override
	public void handleText(Wechat wechat, Text message) {
		TextSend ts = new TextSend(
				message.getContent().replace("<br/>", "\n"), message.getFromUserName());
		wechat.sendMessage(ts);
	}
	
	@Override
	public void handleImage(Wechat wechat, Image message) {
		// TODO Auto-generated method stub
		super.handleImage(wechat, message);
	}
}
