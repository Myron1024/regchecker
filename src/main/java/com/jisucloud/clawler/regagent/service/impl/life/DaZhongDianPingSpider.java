package com.jisucloud.clawler.regagent.service.impl.life;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;
import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class DaZhongDianPingSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "大众点评网于2003年4月成立于上海。大众点评是中国领先的本地生活信息及交易平台，也是全球最早建立的独立第三方消费点评网站。大众点评不仅为用户提供商户信息、消费点评及消费优惠等信息服务，同时亦提供团购、餐厅预订、外卖及电子会员卡等O2O（Online To Offline）交易服务。";
	}

	@Override
	public String platform() {
		return "dianping";
	}

	@Override
	public String home() {
		return "dianping.com";
	}

	@Override
	public String platformName() {
		return "大众点评";
	}

	@Override
	public String[] tags() {
		return new String[] {"生活" , "消费点评" , "外卖" , "团购"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910250000", "18210538513");
	}
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(DaZhongDianPingSpider.class);
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.dianping.com/account/ajax/unloginVerifyOldMobile";
			String postdata = "mobile=86_"+account+"&dpid=&cx=&unlogin=true";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", ANDROID_USER_AGENT)
					.addHeader("Host", "m.dianping.com")
					.addHeader("Origin", "https://m.dianping.com")
					.addHeader("X-Requested-With", "com.dianping.v1")
					.addHeader("Referer", "https://m.dianping.com/account/unloginVerifyOldMobile")
					.post(FormBody.create(MediaType.get("application/x-www-form-urlencoded"), postdata))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String errorMsg = response.body().string();
			JSONObject result = JSON.parseObject(errorMsg);
			return result.getIntValue("code") == 200;
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
