package com.test;
import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class extentReport { 
	
    ExtentReports extent;

	ExtentTest test;
	ExtentTest test1;
	
	@Before
	public void startUp()
	{
		extent= new ExtentReports(System.getProperty("user.dir")+"/test-output/genratedreport.html",false);
		extent.addSystemInfo("HostNmame", "Beant");
		extent.loadConfig(new File(System.getProperty("user.dir")+"/configuration/extent-config.xml"));
	}
	
	@Test
	public void reportPass()
	{
		test=extent.startTest("reportPass","passing the report");
		test.log(LogStatus.INFO,"passing report");
		test.log(LogStatus.INFO,"fgfgfg");
		test.log(LogStatus.INFO,"f6yyy");
		test.log(LogStatus.INFO,"ftyttrtryertyert");
		
	}
	@Test
	public void reportmidPass() throws InterruptedException
	{
		
		test=extent.startTest("reportmidPass","passing second the report");
		test.log(LogStatus.INFO,"passing report");
		test.log(LogStatus.INFO,"fgfgfg");
		Thread.sleep(1000);
		test.log(LogStatus.INFO,"f6yyy");
		test.log(LogStatus.INFO,"ftyttrtryertyert");
	}
	
	@After
	public void tearDown()
	{
		extent.endTest(test);
		extent.flush();
		extent.close();	
	}
	
	

   

	    /*public static void main(String[] args) {
	        extent = new ExtentReports(System.getProperty("user.dir")+"/test-output/genratedreport.html", true);

	        // creates a toggle for the given test, adds all log events under it    
	        ExtentTest test = extent.startTest("My First Test", "Sample description");

	        // log(LogStatus, details) 
	       test.log(LogStatus.INFO, "This step shows usage of log(logStatus, details)");

	        // report with snapshot
	        String img = test.addScreenCapture("img-path");
	        test.log(LogStatus.INFO, "Image", "Image example: " + img);
	        
	        // end test
	        extent.endTest(test);
	        
	        // calling flush writes everything to the log file
	        extent.flush();
	        System.out.println("ending cases");
	    }*/
	}


