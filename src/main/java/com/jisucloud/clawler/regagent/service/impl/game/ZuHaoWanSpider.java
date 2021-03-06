package com.jisucloud.clawler.regagent.service.impl.game;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "zuhaowan.com", 
		message = "租号玩-国内游戏租号服务平台,海量账号出租,吃鸡.CF.LOL.飞车等热门游戏均可租用体验,神级装备，大神账号等你来租, 上号器极速上号.租赁网游账号，体验游戏乐趣，享受虐菜快感！买号太贵，练号太累,来租号玩！", 
		platform = "zuhaowan", 
		platformName = "租号玩", 
		tags = { "游戏" , "游戏租号" }, 
		testTelephones = { "13877117175", "18212345678" })
public class ZuHaoWanSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.zuhaowan.com/Login/checkUserName.html";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.zuhaowan.com")
					.addHeader("Referer", "https://www.zuhaowan.com/login/register.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已被注册")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
