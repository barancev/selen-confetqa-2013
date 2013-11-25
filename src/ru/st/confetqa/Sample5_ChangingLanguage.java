package ru.st.confetqa;

import net.lightbody.bmp.proxy.ProxyServer;
import net.lightbody.bmp.proxy.http.BrowserMobHttpRequest;
import net.lightbody.bmp.proxy.http.RequestInterceptor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class Sample5_ChangingLanguage {

  @Test
  public void changingLanguage() throws Exception {
    ProxyServer bmp = new ProxyServer(8081);
    bmp.start();

    RequestInterceptor languageChanger = new LanguageChanger("en,ru");
    bmp.addRequestInterceptor(languageChanger);

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    WebDriver driver = new FirefoxDriver(caps);

    driver.get("http://ci.seleniumhq.org:8080/");
    Thread.sleep(20000);

    driver.quit();

    bmp.stop();
  }

  public static class LanguageChanger implements RequestInterceptor {
    private String lang;

    public LanguageChanger(String lang) {
      this.lang = lang;
    }

    @Override
    public void process(BrowserMobHttpRequest request) {
      request.addRequestHeader("Accept-Language", lang);
    }
  }
}
