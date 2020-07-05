package com.test;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.junit.Test;

public class HTMLBatchRunner extends Keyword{
	
	boolean testCaseStatusFlag;
	int linenum = 1;
	int testcaseNumber = 0;
	int countForPassTestCase = 0;
	int countForFailTestCase = 0;
    int totalStepNumber = 0;
    int sentinelFlag=0;
    public static int totalTestCase ;
	String strTestCaseName;
	OutputStream htmlfile = null;
	PrintStream printhtml = null;
	String testCaseName;
	String testCaseStatus = "Pass";
	int stepNumber[] = new int[500];
  	String stepAction[] = new String[500];
	String stepStatus[] = new String[500];
	public static String executionStartTime;
	public static String executionEndTime;
	public static int testCaseStepNumber =0;
	public static String myRowID = null;
	public static String folderPath;
	
	public static Properties CONFIG = null;
	public static String configSendReportByMailFlag = "N";
	public static String sendReportByMailFlag = "N";
	public static String RecepientToUser=null;
	public static String RecepientCCUser=null;
	
	
	/*------------------------------------------------------------
	//----------get all the  text report files-------------------
	------------------------------------------------------------*/
	@Test
	public void getNameOfTxtFileFromReportFolder(){
		System.out.println("Genrating Report...");
		executionStartTime = ExecutionTime.executionStartTime1;
		executionEndTime = executiontime();
		totalTestCase = 0;
		folderPath = ExecutionTime.subFolderpathForExecution +"/";
		System.out.println("folderPath"+folderPath);
		File folder_browser = new File(folderPath);
		System.out.println("folder_browser  "+folder_browser);
		File[] s= folder_browser.listFiles();
		
		for(int i=0;i<s.length;i++)
		{
			System.out.println(s.length);
			if (s[i].getName().endsWith(".txt"))
			{
				String nameOfFile = s[i].getName();
				System.out.println("nameOfFile  "+nameOfFile);
				checkTestcase(nameOfFile);
				totalTestCase++;
            }
		}
		summaryReport();
		sendReportByMail();
	//	closeAllWindow();
	}
	/*####################################################################################
	Function Name : closeAllWindow
	Description   : This is used to close window
	Created by    : Satyam Vats
	Created on    : 12/30/2015
	 ####################################################################################*/
	public static void closeAllWindow()
	{
		try
		{	
			ExecutionTime.stopOrPauseExecution();
			writeOutput("Browser is closed successfully:- Pass", testName);
			Thread.sleep(1000);
			driver.close();
			//driver.quit();
		}
		catch(Exception e)
		{
			stepForwardExceptionFlag = 1;			
		}
		finally{
			System.out.println("Finished.");
		}
	}
	/*------------------------------------------------------------
	//----------get current time-------------------
	------------------------------------------------------------*/
	public  static String  executiontime(){
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date date = new Date();
	return dateFormat.format(date);
	}
	
	/*------------------------------------------------------------
	//----------check testcase pass or fail and call the function to generate report------------------
	------------------------------------------------------------*/
	public  boolean checkTestcase(String myTestCasename) {
		try {
			testcaseNumber++;
			this.testCaseStatusFlag = true;
			this.testCaseName = myTestCasename.subSequence(0,myTestCasename.indexOf(".")).toString();
			System.out.println("this.testCaseName : "+ this.testCaseName);
			this.testCaseStatus = "Pass";
			BufferedReader txtfile = new BufferedReader(new FileReader(folderPath + myTestCasename ));
			String txtfiledata = "";
			String txtfiledata1 = "";
			int failFlag=0;
			// ----------generate html report first time-------------------
			generateHTMLreport();
			System.out.println("myTestCasename : "+ myTestCasename);
			// ---------check testcase is pass or fail------------------
			while ((txtfiledata = txtfile.readLine()) != null) {
				
				if (txtfiledata.contains("sentinel"))
				{
					failFlag=0;
				}
				if (txtfiledata.contains("Fail") || txtfiledata.contains("UIFail"))
				{					
					failFlag=1;					
				}			
								
			}
			if(failFlag==1)
			{
				this.testCaseStatusFlag = false;
				this.testCaseStatus = "Fail";
			}
			System.out.println("failFlag : "+ failFlag);
			txtfile.close();
			// ---------generate report for every test case------------------------
			generateHTMLReportForTestCase();
			
			BufferedReader txtfile1 = new BufferedReader(new FileReader(folderPath + myTestCasename));
				this.totalStepNumber = 0;
				this.sentinelFlag=0;
				while ((txtfiledata1 = txtfile1.readLine()) != null) {
					if (txtfiledata1.contains("sentinel"))
						sentinelFlag=0;
						if (txtfiledata1.contains(":-")) {
							String[] parts = txtfiledata1.split(":-");
							this.stepNumber[this.totalStepNumber] = sentinelFlag+1;
							this.stepAction[this.totalStepNumber] = parts[0];
							this.stepStatus[this.totalStepNumber] = parts[1];
							totalStepNumber++;
							sentinelFlag++;
						}
					this.linenum++;
				}
				generateHTMLData();
				txtfile1.close();
			
		} catch (Exception e) {
			System.out.println("in exception------in checkTestcase");
		}
		return testCaseStatusFlag;
	}

	/*------------------------------------------------------------
	//----------generate html report first time-------------------
	------------------------------------------------------------*/
	public void generateHTMLreport() {
		try {
			String testReportname = "test.html";			
			if (this.testcaseNumber == 1) {
				File f = new File(folderPath + testReportname);
				this.htmlfile = new FileOutputStream(f);
				this.printhtml = new PrintStream(htmlfile);
				this.printhtml.println("<html><HEAD><TITLE>Detailed Test Results</TITLE> "
						+ "<script type=\"text/javascript\"> function toggle_visibility(event1) { var e = document.getElementById(event1); if(e.style.display == 'block'){ e.style.display = 'none'}"
						+ " else{ e.style.display = 'block' } }</script>" + "</HEAD><body bgcolor=\"#E6E6FA\"><h1 align=\"left\"><FONT COLOR=\"660066\" FACE=\"Arial\"SIZE=3.75>&nbsp;&nbsp;&nbsp;&nbsp;<b><u>Detailed Report</u></b></h1>");
				this.printhtml.println("<table border=2 cellspacing=1 cellpadding=1 ><TR><TD width=150 align=\"left\"><FONT COLOR=\"660000\" SIZE=2><b>Operating System:</b></TD><TD width=150 align=\"left\"><FONT COLOR=\"660000\" SIZE=2>"+ OSNameFromGeneric +"</TD></TR><TR><TD width=150 align=\"left\"><FONT COLOR=\"660000\" SIZE=2><b>Browser/Application:</b></TD><TD width=150 align=\"left\"><FONT COLOR=\"660000\" SIZE=2>"+ browserNameFromGeneric +"</TD></TR></table>");
				this.printhtml.println("<table cellspacing=1 cellpadding=1   border=1>");
				this.printhtml.println("<tr><td width=80  align=\"center\" bgcolor=\"#153E7E\"><FONT COLOR=\"#E0E0E0\" FACE=\"Arial\" SIZE=2><b>S.No.</b></td>");
				this.printhtml.println("<td width=75 align=\"center\" bgcolor=\"#153E7E\"><FONT COLOR=\"#E0E0E0\" FACE=\"Arial\" SIZE=2><b>Module Name</b></td>");
				this.printhtml.println("<td width=400 align=\"center\" bgcolor=\"#153E7E\"><FONT COLOR=\"#E0E0E0\" FACE=\"Arial\" SIZE=2><b>Description</b></td>");
				this.printhtml.println("<td width=600 align=\"center\" bgcolor=\"#153E7E\"><FONT COLOR=\"#E0E0E0\" FACE=\"Arial\" SIZE=2><b>TestCase ID</b></td>");
				this.printhtml.println("<td width=75 align=\"center\" bgcolor=\"#153E7E\"><FONT COLOR=\"#E0E0E0\" FACE=\"Arial\" SIZE=2><b>TestCase Status</b></td></tr>");
				}
		} 
			catch (Exception e) {
				System.out.println("in exception------generateHTMLreport");
			}
		}

	/*----------------------------------------------------------------------
	//---------generate report for every test case in HTML Report------------------------
	----------------------------------------------------------------------*/
	public void generateHTMLReportForTestCase() {
		try {
			String[] testSuitName;
			if(DataDrivenFlag.equals("Y") || DataDrivenFlag.equals("y"))
			{
				testSuitName = this.testCaseName.split("\\^");
			}
			else
			{
				testSuitName = this.testCaseName.split("\\^");
			}
			
			if (this.testCaseStatusFlag == true) {
				this.countForPassTestCase++;				
				myRowID = "test"+this.testcaseNumber;
				if(DataDrivenFlag.equals("Y") || DataDrivenFlag.equals("y"))
				{
					this.printhtml.println("<tr onclick=\"toggle_visibility('"+myRowID+"');\"><td width=80  align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>"
							+ this.testcaseNumber + "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[0] + "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[1] + "</b></td>");
					this.printhtml.println("<td width=600 height=50 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[2]+ "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + this.testCaseStatus + "</b></td></tr>");
				}
				else{
					this.printhtml.println("<tr onclick=\"toggle_visibility('"+myRowID+"');\"><td width=80  align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>"
						+ this.testcaseNumber + "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[0] + "</b></td>");
					this.printhtml.println("<td width=75 height=50 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[1] + "</b></td>");
					this.printhtml.println("<td width=600 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[0] + "</b></td>");			
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + this.testCaseStatus + "</b></td></tr>");
				}
			} else {
				this.countForFailTestCase++;
				myRowID = "test"+this.testcaseNumber;
				if(DataDrivenFlag.equals("Y") || DataDrivenFlag.equals("y"))
				{
					this.printhtml.println("<tr onclick=\"toggle_visibility('"+myRowID+"');\"><td width=80  align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>"
							+ this.testcaseNumber + "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[0] + "</b></td>");		
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[1] + "</b></td>");
					this.printhtml.println("<td width=600 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[2] + "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"red\" FACE=\"Arial\" SIZE=1.5><b>" + this.testCaseStatus + "</b></td></tr>");
				}
				else
				{
					this.printhtml.println("<tr onclick=\"toggle_visibility('"+myRowID+"');\"><td width=80  align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>"
							+ this.testcaseNumber + "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[0] + "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[1] + "</b></td>");
					this.printhtml.println("<td width=600 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + testSuitName[0] + "</b></td>");
					this.printhtml.println("<td width=75 align=\"center\"><FONT COLOR=\"red\" FACE=\"Arial\" SIZE=1.5><b>" + this.testCaseStatus + "</b></td></tr>");
				}
				
			}
			if (this.testcaseNumber == totalTestCase){
				this.printhtml.println("</table></body></html>");
			}
		} catch (Exception e) {
			System.out.println("in exception------in generateHTMLReportForTestCase");
		}
	}

	/*-------------------------------------------------------------------
	------write detail for pass/failed test case in HTML Report----------------------------
	-------------------------------------------------------------------*/
	public void generateHTMLData() {
		try {			
			String screenshot;
			String caseName[]=this.testCaseName.split("\\^");
			System.out.println("caseName"+caseName);
			this.printhtml.println("<tr><td></td><td></td><td></td><td bgcolor=\"#B8860B\"><table style=\"display:none\" id="+myRowID + " border=1 cellspacing=1 cellpadding=1 >");
			for (int j=0;j<this.totalStepNumber;j++){
				if(DataDrivenFlag.equals("Y") || DataDrivenFlag.equals("y"))
				{
						String[] testSuitName;
						testSuitName = this.testCaseName.split("\\^");
						screenshot ="screenshots/"+ testSuitName[0]+"_"+testSuitName[2]+"/" +this.stepNumber[j] + ".png";
						System.out.println("screen shot place "+ testSuitName[0]+"_"+testSuitName[2]);
				}
				else
				{
					String[] testSuitName;
					testSuitName = this.testCaseName.split("\\^");
					//screenshot ="screenshots/"+ this.testCaseName +"/" +this.stepNumber[j] + ".png";
					screenshot ="screenshots/"+ testSuitName[0] +"/" +this.stepNumber[j]+ ".png";
					
					
				}
			String subCases[]=this.stepAction[j].split("%");
			boolean screenShotCheck = new File(folderPath + screenshot).exists();
			if(!DataDrivenFlag.equalsIgnoreCase("y"))
			this.printhtml.println("<tr onclick=\"toggle_visibility('"+caseName[0]+"_"+j+"');\"><td width=80  align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + this.stepNumber[j] + "</b></td>");
			if(DataDrivenFlag.equalsIgnoreCase("y"))
			this.printhtml.println("<tr onclick=\"toggle_visibility('"+caseName[0]+"_"+caseName[2]+"_"+j+"');\"><td width=80  align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + this.stepNumber[j] + "</b></td>");
			this.printhtml.println("<td width=600 align=\"left\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + subCases[0] + "</b></td>");
			if(screenShotCheck==true)
				this.printhtml.println("<td width=90 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b><a href="+ screenshot +">" + this.stepStatus[j] + "</b></td></tr>");
			else
			    this.printhtml.println("<td width=90 align=\"center\"><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + this.stepStatus[j] + "</b></td></tr>");
			   
			  if(subCases.length>1)
			     generateMultiLineHTMLData(j,subCases); // for multiline functionality
			}
			this.printhtml.println("</table></td></tr>");
			
			
			
		} catch (Exception e) {
			System.out.println("in exception------in generateHTMLData");
		}
	}
	
	/*--------------------------------------------------------------------
	 -------------write multiline code here-------------------------------
	 ---------------------------------------------------------------------------------*/
	
	public void generateMultiLineHTMLData(int j,String []subCases) {
		try {
			String caseName[]=this.testCaseName.split("\\^");
			if(DataDrivenFlag.equalsIgnoreCase("y"))
			this.printhtml.println("<tr><td></td><td><table style=\"display:none\" id="+caseName[0]+"_"+caseName[2]+"_"+j+">");//cellspacing=1 cellpadding=1 border=1 
			else
			this.printhtml.println("<tr><td></td><td><table style=\"display:none\" id="+caseName[0]+"_"+j+">");
				for(int i=1;i<subCases.length;i++)
				{
					int z=j+1;
					this.printhtml.println("<tr><td><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + z +"."+ i + "</b></td><td><FONT COLOR=\"660000\" FACE=\"Arial\" SIZE=1.5><b>" + subCases[i] + "</b></td></tr>");
				}
				this.printhtml.println("</table><td></td></td></tr>");
				
		} catch (Exception e) {
			System.out.println("in exception------in generateHTMLData");
		}
	}

	/*-------------------------------------------------------------------
	------write summary report in HTML Report----------------------------
	----------------------------------------------------------------------------------*/
	public void summaryReport() {

		int summaryResultForPass = (this.countForPassTestCase * 100) / this.testcaseNumber;
		int summaryResultForFail = (this.countForFailTestCase * 100) / this.testcaseNumber;
		this.printhtml.println("<br><table border=0 cellspacing=2 cellpadding=2 ><TR><TD width=150 align=\"center\"><FONT COLOR=\"660000\" SIZE=3.75><b><u>Summary Report</u></b></TD></TR><br></table>");
		this.printhtml.println("<br><table border=2 cellspacing=1 cellpadding=1 ><TR><TD width=150 align=\"left\"><FONT COLOR=\"660000\" SIZE=2>Execution Start Time:</TD><TD width=200 align=\"left\"><FONT COLOR=\"660000\" SIZE=2>"+executionStartTime +"</TD></TR><TR><TD width=150 align=\"left\"><FONT COLOR=\"660000\" SIZE=2>Execution End Time:</TD><TD width=200 align=\"left\"><FONT COLOR=\"660000\" SIZE=2>"+executionEndTime +"</TD></TR></table>");
		this.printhtml.println("<br><table  border=2> <tr><td>");
		
		this.printhtml
		.println("<table border=0 cellspacing=2 cellpadding=2 ><tr><td width=70><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>Total:</b></td><td width=35><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>"
				+ this.testcaseNumber + "</b></td> <td width=400 bgcolor=\"#E7A1B0\"></td><td width=20><FONT COLOR=\"#000000\" FACE=\"Arial\" SIZE=1><b>100.0%</b></td></tr></table></td></tr>");
this.printhtml
		.println("<tr><td ><table border=0 cellspacing=2 cellpadding=2 ><tr><td width=70><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>Pass:</b></td> <td width=35><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>"
				+ this.countForPassTestCase
				+ "</b></td> <td width="
				+ summaryResultForPass
				* 4
				+ " bgcolor=\"#008000\"></td><td width=20><FONT COLOR=\"#000000\" FACE=\"Arial\" SIZE=1><b>"
				+ summaryResultForPass + "%</b></td></tr></table></td></tr>");
this.printhtml
		.println("<tr><td><table border=0 cellspacing=2 cellpadding=2 ><tr><td width=70><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>Fail:</b></td> <td width=35><FONT COLOR=\"#000066\" FACE=\"Arial\" SIZE=2.75><b>"
				+ this.countForFailTestCase
				+ "</b></td> <td width="
				+ summaryResultForFail
				* 4
				+ " bgcolor=\"#FF0000\"></td><td width=20><FONT COLOR=\"#000000\" FACE=\"Arial\" SIZE=1><b>"
				+ summaryResultForFail + "%</b></td></tr></table>");
this.printhtml.println(" </td></tr></table>");
	}
	
	/*
	 * ##########################################################################
	 * ######## Function Name : sendReportByMail Description : The function is used for
	 * sending the mail report Created by : Karan Arora Created On: 28/3/2015
	 * ##############################################################
	 */
	public static void sendReportByMail()
	{
		
		try {
			CONFIG = new Properties();
			FileInputStream ip1;
			ip1 = new FileInputStream(
					System.getProperty("user.dir")
							+ "/Config/Config.Properties");
			CONFIG.load(ip1);
			
			configSendReportByMailFlag = CONFIG.getProperty("sendReportByMailFlag");
			sendReportByMailFlag = configSendReportByMailFlag;
			RecepientToUser = CONFIG.getProperty("RecepientToUser");
			RecepientCCUser = CONFIG.getProperty("RecepientCCUser");
			System.out.println("sendReportByMailFlag from config is:" +sendReportByMailFlag.toString());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (sendReportByMailFlag.equals("Y") || sendReportByMailFlag.equals("y")) {
		final String username = "karan.arora@newgen.co.in";
		final String password = "Hell8aASDsd#";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.starttls.enable", "true");
		/*props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "25");*/

		props.put("mail.smtp.host", "mx.newgen.co.in");
		props.put("mail.smtp.port", "25");
		
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("npc.admin@newgen.co.in"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(RecepientToUser));
			message.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(RecepientCCUser));
			message.setSubject("Test Run Report!");
			message.setText("Dear Mail Crawler,"
				+ "\n\n No spam to my email, please!");
			
			/*===================Code for attaching attachment====================*/
	         BodyPart messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setText("PFA report of Test Run");
	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);
	         messageBodyPart = new MimeBodyPart();
	         
	         
//	         String filename = "d:/abc.txt";
	         String htmlReport = folderPath+"test.html";
	         DataSource source = new FileDataSource(htmlReport);
	         messageBodyPart.setDataHandler(new DataHandler(source));
	         messageBodyPart.setFileName(htmlReport);
	         multipart.addBodyPart(messageBodyPart);
	         message.setContent(multipart);
			
			/*===============================End of Attachment Code=======================================*/
			
			Transport.send(message);
			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		}
		else
		{
			System.out.println("sendReportByMail flag is set as N in config so mail report will not send");
		}
		
	}
	
}
