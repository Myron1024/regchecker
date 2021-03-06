package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "wealth365.com", 
		message = "掌众财富海口联合农商银行存管系统正式上线,已获公安部国家信息安全等级保护三级认证,全新风险保障计划,保护用户的资金安全。掌众财富,为您提供优质债权的出借组合管理。", 
		platform = "wealth365", 
		platformName = "掌众财富", 
		tags = { "P2P", "消费分期" , "借贷" }, 
		testTelephones = { "15985268904", "18212345678" })
public class ZhangZhongCaiFuSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://capi.wealth365.com.cn/storm/userbase/userPCCheck?c=4";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json"), "{\"mobile\":\""+account+"\"}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "capi.wealth365.com.cn")
					.addHeader("Referer", "https://account.wealth365.com.cn/login.html")
					.addHeader("Origin", "https://account.wealth365.com.cn")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getIntValue("content") == 2) {
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
