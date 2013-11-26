package ru.st.confetqa;

import net.lightbody.bmp.proxy.ProxyServer;
import net.lightbody.bmp.proxy.http.BrowserMobHttpRequest;
import net.lightbody.bmp.proxy.http.RequestInterceptor;
import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.protocol.HttpContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class Sample4_ChangingUserAgent {

  @Test
  public void changingUserAgent() throws Exception {
    ProxyServer bmp = new ProxyServer(8071);
    bmp.start();

    //RequestInterceptor userAgentChanger = new UserAgentChanger(
    //    "Mozilla/5.0 (Linux; U; Android 2.2; en-us; Nexus One Build/FRF91)"
    //        + "AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1");
    //bmp.addRequestInterceptor(userAgentChanger);

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    WebDriver driver = new FirefoxDriver(caps);

    driver.get("http://software-testing.ru/forum");
    Thread.sleep(10000);

    driver.quit();

    bmp.stop();
  }

  public static class UserAgentChanger implements RequestInterceptor {
    private String userAgent;

    public UserAgentChanger(String userAgent) {
      this.userAgent = userAgent;
    }

    @Override
    public void process(BrowserMobHttpRequest request) {
      request.addRequestHeader("User-Agent", userAgent);
    }
  }
}
