package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TestDragDrop {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "D:\\selenium_framework\\trunk\\Selenium_framework\\chromedriver_win32\\chromedriver.exe");
		
		WebDriver driver = new ChromeDriver();
		driver.get("http://3pillar.gocanvas.com/forms/607663/pdf_designer");
		Thread.sleep(20000);
		driver.findElement(By.id("login_field")).sendKeys("kailashpathak@gmail.com");
		driver.findElement(By.id("password")).sendKeys("Canvas@321");
		driver.findElement(By.id("btn_Sign In")).click();
		Thread.sleep(10000);
		/*driver.findElement(By.xpath("/html/body/div[1]/div/start-screen/div/div/ul/li[2]/span")).click();
		driver.findElement(By.cssSelector("button.btn.letsgo")).click();*/
		
		
		WebElement dragElement = driver.findElement(By.xpath("//*[@id='sidebar-wrapper']/div/div/ng-include/div[2]/accordion/div/div[1]/div[2]/div/div/div[1]/div[2]"));
		Thread.sleep(5000);
		String dragText = dragElement.getText();
		System.out.println("dragText is"+ dragText);
		
//		WebElement dropElement = driver.findElement(By.cssSelector("div.column-container.ui-sortable-list-table.ui-sortable-sections.ui-sortable-common"));
		WebElement dropElement = driver.findElement(By.xpath("html/body/div[1]/div/div[1]/div[3]/div[3]/a[1]"));
		/*WebElement dropElement = driver.findElement(By.xpath(".//*[@id='workspace']/div/div"));*/
//		driver.findElement(By.xpath("/html/body/div[1]/div/div[1]/div[3]/div[3]/a[2]")).click();
//		driver.findElement(By.xpath("/html/body/div[7]/div/div/div[3]/button[1]")).click();
//		driver.findElement(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/workspace/div/div/div/div/div[3]/div/div")).click();
		WebElement dropElement1 = driver.findElement(By.xpath(".//*[@id='workspace']/div/div/div/div[3]/div/div"));
		
	/*	Actions builder = new Actions(driver);
		
		Thread.sleep(5000);
		builder.clickAndHold(dragElement).moveToElement(dropElement1).release().build().perform();
		builder.clickAndHold(dragElement).moveToElement(dropElement).release(dropElement1).build().perform();*/
		
//		(new Actions(driver)).clickAndHold(dragElement).dragAndDropBy(dragElement, 0, 300).build().perform();
		
		(new Actions(driver)).clickAndHold(dragElement);
		System.out.println("finished");
		JavascriptExecutor javascript = (JavascriptExecutor) driver;
		javascript.executeScript("alert('drag done');");
		  Thread.sleep(5000);
		  driver.switchTo().alert().accept();
		
//		driver.quit();
		
	}

}
