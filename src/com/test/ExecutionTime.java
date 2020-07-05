package com.test;


import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.junit.Test;

import com.relevantcodes.extentreports.ExtentReports;


public class ExecutionTime {
	
	public static String executionStartTime1;
	public int myDeletioncount = 0;
	public static String subFolderpathForExecution;
	public static String folderNameForday;
	public static String subFolderNameForExecution;
	public static boolean stopExecutionflag;
	public static boolean resumeExecutionflag;
	public static boolean pauseExecutionflag;
	
	/*##################################################################################
	Function Name : testExecutionTime
	Description : The function is used to get start time of exceution and call the function to delete existing files using swings
	Created by : 
	####################################################################################*/
	@Test
	public void testExecutionTime() {
		try{	
			//=========================pause/resume============
			JPanel jplPanel;
			JFrame frame = new JFrame("SeleniumExecutionController");
			frame.setAlwaysOnTop(true); 
			jplPanel = new JPanel();
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
			frame.setSize(150, 350);
			final JButton jbnButton1 = new JButton("Stop");
			final JButton jbnButton2 = new JButton("Pause");
			final JButton jbnButton3 = new JButton("Resume");
			//---------------resume button is disable---------
			jbnButton3.setEnabled(false);
			//---------------Button1- stop--------------------
			jbnButton1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					stopExecutionflag = true;
				}
			});
			//-------------Button2-Pause----------------------
			jbnButton2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jbnButton2.setEnabled(false);
					jbnButton3.setEnabled(true);
					pauseExecutionflag= true;
					resumeExecutionflag = false;
				}
			});
			//-------------Button3-Resume----------------------
			jbnButton3.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					jbnButton2.setEnabled(true);
					jbnButton3.setEnabled(false);
					resumeExecutionflag = true;
					pauseExecutionflag = false;
				}
			});
			
			jplPanel.setLayout(new FlowLayout());
			jplPanel.add(jbnButton1);
			jplPanel.add(jbnButton2);
			jplPanel.add(jbnButton3);
			frame.getContentPane().add(jplPanel, BorderLayout.CENTER);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.pack();
			frame.setVisible(true);
			
			//=========================pause/resume============
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			executionStartTime1 =  dateFormat.format(date);
			System.out.println(dateFormat.format(date));
			deleteAllTestReport();
			folderStructureCreation();
			}
		catch(Exception e)
		{
			System.out.println("in excption in start time");
			e.printStackTrace();
		}
	}
	
	/*##################################################################################
	Function Name : deleteAllTestReport
	Description : The function is used to delete existing files
	Created by : Karan Arora
	####################################################################################*/
	public static void deleteAllTestReport() {
		try{
		File folder_browser = new File(System.getProperty("user.dir")+"/TestCases_Report/");
		File[] s= folder_browser.listFiles();

		for(int i=0;i<s.length;i++)
		{
			if((s[i].getName().endsWith(".txt"))||(s[i].getName().endsWith(".html")))
			{
				s[i].delete();
			}
      	}
		File screenshots_folder = new File(System.getProperty("user.dir") +"/screenshots/");
		File[] screenshots_files = screenshots_folder.listFiles();
		for(int j=0;j<screenshots_files.length;j++)
		  {
			if (screenshots_files[j].isDirectory()){
				File[] screenshots =	screenshots_files[j].listFiles();
				for (File k : screenshots){
					k.delete();
					
			}
			screenshots_files[j].delete();
	      }
		  }
		}
	catch(Exception e)
	{
		System.out.println("Already deleted.");
		
	}
}


	/*==================================================
	===========Function for folderStructureCreation=================
	==================================================*/
	public static void folderStructureCreation(){
		  try{			  
			  	DateFormat folderName = new SimpleDateFormat("ddMMM");
			  	Date date = new Date();
			  	 folderNameForday =  folderName.format(date);
			  	File folderForDayExceution = new File(System.getProperty("user.dir")+"/TestCases_Report/"+ folderNameForday );
			  if(!folderForDayExceution.exists()){
				 folderForDayExceution.mkdirs();
			 }
			  	DateFormat subFolderName = new SimpleDateFormat("ddMMM HH-mm-ss");
			  	 subFolderNameForExecution =  subFolderName.format(date);
			  	subFolderpathForExecution = System.getProperty("user.dir")+"/TestCases_Report/"+ folderNameForday +"/"+subFolderNameForExecution;
			  	File subFolderForExecution = new File(subFolderpathForExecution);
			 if(!subFolderForExecution.exists()){
				 subFolderForExecution.mkdir();
			 }
	  }
			catch(Exception e)
			{
				System.out.println(e);
						
			}
		}
	
	/*##################################################################################
	Function Name : stopOrPauseExecution
	Description : The function is used to delete existing files
	Created by : Karan Arora
	####################################################################################*/
	public static void stopOrPauseExecution() throws InterruptedException{
			
				  while(pauseExecutionflag){
					  System.out.println("Pause....");
					  Thread.sleep(10000);
				  }
		  }
		  
}
