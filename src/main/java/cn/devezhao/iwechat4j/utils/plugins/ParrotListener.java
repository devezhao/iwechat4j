package cn.devezhao.iwechat4j.utils.plugins;

import java.io.File;

import cn.devezhao.iwechat4j.Config;
import cn.devezhao.iwechat4j.Wechat;
import cn.devezhao.iwechat4j.listener.DefaultListener;
import cn.devezhao.iwechat4j.message.Image;
import cn.devezhao.iwechat4j.message.ImageSend;
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
		super.handleText(wechat, message);
		TextSend ts = new TextSend(
				message.getFromUserName(), message.getContent().replace("<br/>", "\n"));
		wechat.sendMessage(ts);
	}
	
	@Override
	public void handleImage(Wechat wechat, Image message) {
		super.handleImage(wechat, message);
		File dest = new File(Config.DATA_DIR, "iwechat4j/image/" + System.currentTimeMillis() + ".jpg");
		message.download(wechat, dest);
		
		ImageSend is = new ImageSend(message.getFromUserName(), dest);
		wechat.sendMessage(is);
	}
}
