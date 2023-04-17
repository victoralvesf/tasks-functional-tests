package br.ce.wcaquino.tasks.prod;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class HealthCheckIT {

	@Test
	public void healthCheck() throws MalformedURLException {
		String seleniumGridUrl = System.getProperty("selenium.grid.hub.url") != null 
				? System.getProperty("selenium.grid.hub.url")
				: "http://localhost:4444/wd/hub";
		
		DesiredCapabilities cap = DesiredCapabilities.chrome();
		WebDriver driver = new RemoteWebDriver(new URL(seleniumGridUrl), cap);
		
		String appUrl = System.getProperty("selenium.app.url") != null 
				? System.getProperty("selenium.app.url")
				: "http://tomcat:8080/tasks";
		
		try {
			driver.navigate().to(appUrl);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			String version = driver.findElement(By.id("version")).getText();
			Assert.assertTrue(version.startsWith("build"));
		} finally {
			driver.quit();
		}
	}
}
