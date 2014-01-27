package ru.st.confetqa;

import net.lightbody.bmp.core.har.*;
import net.lightbody.bmp.proxy.ProxyServer;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class Sample3_GettingHar {

  @Test
  public void gettingHar() throws Exception {
    ProxyServer bmp = new ProxyServer(8071);
    bmp.start();

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    WebDriver driver = new FirefoxDriver(caps);

    bmp.newHar("localhost");

    driver.get("http://localhost/");

    Har har = bmp.getHar();

    for (HarEntry entry : har.getLog().getEntries()) {
      HarRequest request = entry.getRequest();
      HarResponse response = entry.getResponse();

      System.out.println(request.getUrl() + " : " + response.getStatus()
          + ", " + entry.getTime() + "ms");

      assertThat(response.getStatus(), is(200));
    }

    driver.quit();

    bmp.stop();
  }
}
