package com.test;

import java.awt.List;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class Testing {
	public static void main(String[] args) throws InterruptedException {
		int count=1;
		System.setProperty("webdriver.chrome.driver", "D:\\selenium_framework\\trunk\\Selenium_framework\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("http://192.168.60.82:3680/auth/authLogon.aspx");
		driver.manage().timeouts().implicitlyWait(3000,TimeUnit.SECONDS);
		
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_loginForm_UserName")).sendKeys("tjones@abcpaint.com");
		Thread.sleep(1000);
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_loginForm_Password")).sendKeys("jonesABC!1");
		
		driver.findElement(By.id("ctl00_ContentPlaceHolder1_loginForm_lnkLogin")).click();
		driver.findElement(By.xpath("//span[text()='Portal']")).click();
		driver.findElement(By.xpath("//span[text()=' Daily Management']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//a[@href='/Performance']")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("//a[@href='#UpdateMetric']")).click();
		Thread.sleep(4000);
		java.util.List<WebElement> values= driver.findElements(By.xpath("//div[@id='UpdateMetricGrid']/div[2]/table/tbody/tr"));
		for(WebElement val: values)
		{
			System.out.println("value present in web element :" + val.findElement(By.xpath("./td[3]/div[1]/span[2]")).getText() +"counter" + count);
			if(val.findElement(By.xpath("./td[3]/div[1]/span[2]")).getText().contains("OTIF%"))
			{
				System.out.println("success");
				val.findElement(By.xpath("./td[3]/div[1]/span[2]")).click();
				break;
			}
			count++;
		}
        
		/*driver.findElement(By.xpath("//a[@href='/SQDC']")).click();	
		Thread.sleep(4000);
		driver.findElement(By.xpath("//a[@href='#Actions']")).click();
		Thread.sleep(1000);
		driver.findElement(By.id("sqdcCAFilterClick")).click();
		Thread.sleep(1000);
		WebElement autoField= driver.findElement(By.id("SQDCActionUserAutoCompleteId"));
		autoField.sendKeys("jo");
		java.util.List<WebElement> values= driver.findElements(By.xpath("//ul[@id='ui-id-1']/li"));
		for(WebElement val: values)
		{
			System.out.println("value present in web element :" + val.getText());
			if(val.getText().contains("Jones"))
			{
				System.out.println("success");
				driver.findElement(By.xpath("//ul[@id='ui-id-1']/li[text()='"+ val.getText() +"   ']")).click();
			}
		}*/
		
	}

}
