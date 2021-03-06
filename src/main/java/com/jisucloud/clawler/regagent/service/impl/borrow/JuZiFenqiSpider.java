package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@PapaSpiderConfig(
		home = "juzifenqi.com", 
		message = "桔子分期是一家专注于年轻人分期购物的金融服务平台,是国内领先的年轻人分期消费服务商。", 
		platform = "juzifenqi", 
		platformName = "桔子分期", 
		tags = {  "P2P", "消费分期", "借贷"  }, 
		testTelephones = { "18210538577", "18212345678" })
public class JuZiFenqiSpider extends PapaSpider {
	
	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("Host", "juzifenqi.com");
		headers.put("channel", "1");
		headers.put("source", "1");
		headers.put("versionName", "1");
		headers.put("versionCode", "124");
		headers.put("imei", "352284040670808");
		headers.put("token", "");
		headers.put("User-Agent", "okhttp/3.11.0");
		return Headers.of(headers);
	}

	private FormBody getParams(String mobile) {
		Random random = new Random();
		FormBody formBody = new FormBody.Builder().add("mobile", mobile).add("password", "asd2130asd")
				.add("equipmentName", "QiKU 8692-A00").add("loginCode", "352284040670808")
				.add("mobileBrand", "8692-A00").add("apiToken", "5c8cf4f9d0b76095196c1691c9d356a4")
				.add("time", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()))
				.add("accurate", "广西壮族自治区柳州市城中区环江滨水大道168号靠近三门江国家森林公园").add("latitude", random.nextInt(80) + ".349354")
				.add("longitude", random.nextInt(10) + "9.494114").build();
		return formBody;
	}



	public boolean checkTelephone(String account) {
		try {
			Request request = new Request.Builder().url("https://termib.juzifenqi.com/termi/dologin.do")
					.headers(getHeader()).post(getParams(account)).build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("msg").contains("请输入正确的用户名或密码")) {
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

}
