package com.test;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class testdrag
{

	public static void main(String[] args) throws InterruptedException {
		//System.setProperty("webdriver.chrome.driver", "D:\\selenium_framework\\trunk\\Selenium_framework\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new FirefoxDriver();
		driver.get("http://www.w3schools.com/html/html5_draganddrop.asp");
		driver.manage().window().maximize();
		
		/*driver.findElement(By.xpath("/html/body/div[1]/div/start-screen/div/div/ul/li[2]/span")).click();
		driver.findElement(By.cssSelector("button.btn.letsgo")).click();*/
		
		 driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		 driver.findElement(By.xpath(".//*[@id='div1']")).click();
		WebElement dragElement = driver.findElement(By.xpath(".//*[@id='drag1']"));
		Thread.sleep(5000);
		String dragText = dragElement.getText();
		System.out.println("dragText is"+ dragText);
		

		WebElement dropElement = driver.findElement(By.xpath(".//*[@id='div2']"));
	
		
		(new Actions(driver)).clickAndHold(dragElement).moveToElement(dropElement).release().build().perform();
		System.out.println("finished");
		JavascriptExecutor javascript = (JavascriptExecutor)driver;
		javascript.executeScript("alert('drag done');");
		Thread.sleep(5000);
		driver.switchTo().alert().accept();

		
	}

}
