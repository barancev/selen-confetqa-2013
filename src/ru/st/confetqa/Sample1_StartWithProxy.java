package ru.st.confetqa;

import net.lightbody.bmp.proxy.ProxyServer;
import static org.junit.Assert.*;

import static org.junit.matchers.JUnitMatchers.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;

public class Sample1_StartWithProxy {

  @Test
  public void startWithProxy() throws Exception {
    ProxyServer bmp = new ProxyServer(8081);
    bmp.start();

    DesiredCapabilities caps = new DesiredCapabilities();
    caps.setCapability(CapabilityType.PROXY, bmp.seleniumProxy());

    WebDriver driver = new FirefoxDriver(caps);

    driver.get("http://localhost/");
    assertThat(
        driver.findElement(By.tagName("body")).getText(),
        containsString("WampServer"));

    driver.quit();

    bmp.stop();
  }
}
