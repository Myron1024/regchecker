package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ChuChuJieSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "楚楚街，移动端电子商务平台。业务类型有9块9包邮、品牌限时抢购等，主营类目涵盖男女服装、化妆品、母婴、零食等。";
	}

	@Override
	public String platform() {
		return "ctfmall";
	}

	@Override
	public String home() {
		return "ctfmall.com";
	}

	@Override
	public String platformName() {
		return "楚楚街";
	}

	@Override
	public String[] tags() {
		return new String[] {"购物" , "9.9包邮"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18779861101", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://api-passport.chuchujie.com/api.php";
			String json = "{\"channel\":\"QD_appstore\",\"package_name\":\"com.culiukeji.huanletao\",\"client_version\":\"3.9.101\",\"ageGroup\":\"AG_0to24\",\"client_type\":\"h5\",\"api_version\":\"v5\",\"imei\":129926157972059,\"method\":\"login_by_phone\",\"gender\":\"1\",\"token\":\"\",\"userId\":\"\",\"verify_code\":\"\",\"phone_number\":\""+account+"\",\"update_verify_flag\":false,\"password\":\"c142ba19c1d6285b1a4516c25f25aef0\",\"sms_code\":\"\"}";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("data", json)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Host", "api-passport.chuchujie.com")
					.addHeader("Referer", "https://m.chuchujie.com/user/user_login.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("status\":11")) {
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