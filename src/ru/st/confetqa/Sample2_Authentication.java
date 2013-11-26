package ru.st.confetqa;

import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.core.har.HarEntry;
import net.lightbody.bmp.core.har.HarRequest;
import net.lightbody.bmp.core.har.HarResponse;
import net.lightbody.bmp.proxy.ProxyServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.matchers.JUnitMatchers.containsString;

public class Sample2_Authentication {

  @Test
  public void autoBasicAuthorization() throws Exception {
    ProxyServer bmp = new ProxyServer(8071);
    bmp.start();

    //
    //bmp.autoBasicAuthorization("", "admin", "password");

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    WebDriver driver = new FirefoxDriver(caps);

    driver.get("http://localhost/restricted");
    assertThat(
        driver.findElement(By.tagName("body")).getText(),
        containsString("Access granted"));

    driver.quit();

    bmp.stop();
  }
}
