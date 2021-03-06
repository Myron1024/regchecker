package com.jisucloud.clawler.regagent.service.impl.social;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;

import com.deep007.goniub.selenium.mitm.ChromeAjaxHookDriver;


import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "linkedin.com", 
		message = "领英,在全球领先职业社交平台查看英领的职业档案。英领的职业档案列出了教育经历。查看英领的完整档案,结识职场人脉和查看相似公司的职位。", 
		platform = "linkedin", 
		platformName = "领英", 
		tags = { "招聘" , "职场" , "人脉" }, 
		testTelephones = { "15985268900", "18212345678" })
public class LinkedinSpider extends PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newIOSInstance(true, false);
			chromeDriver.get("https://www.linkedin.com/uas/login?session_redirect=https%3A%2F%2Fcn%2Elinkedin%2Ecom%2Fin%2F%25E9%25A2%2586-%25E8%258B%25B1-5783b911a&amp;fromSignIn=true&trk=nav_header_signin");smartSleep(1000);
			chromeDriver.findElementById("username").sendKeys("+86"+account);
			chromeDriver.findElementById("password").sendKeys("dx1mxa1la9");
			chromeDriver.findElementByCssSelector("button[type='submit']").click();smartSleep(3000);
			return chromeDriver.checkElement("#error-for-password");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTel;
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
