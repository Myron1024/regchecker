package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class BaiCaiJinRongSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "白菜金融网是龙环普惠集团旗下的网络借贷信息中介平台,遵循 公平公正诚信透明的准则,为大众富裕阶层提供便捷的出借方式。";
	}

	@Override
	public String platform() {
		return "baicaif";
	}

	@Override
	public String home() {
		return "baicaif.com";
	}

	@Override
	public String platformName() {
		return "白菜金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.baicaif.com/login/checkPhone.do?phone=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.baicaif.com")
					.addHeader("Referer", "https://www.baicaif.com")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			return res.contains("已存在");
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
