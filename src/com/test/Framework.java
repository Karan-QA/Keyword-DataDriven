package com.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

import org.junit.After;
import org.junit.Test;
import org.testng.annotations.AfterTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

public class Framework extends Keyword  {
	
	public static Properties CONFIG= null;
	public static Properties OR= null;
	public static String suitName= null;
	public static String testCaseName = null;
	public static String testCaseDescription ;
	public static String subTestCaseDescription ;
	public static File suitExcel = null;
	public static Workbook suitWorkBook = null;
	public static Sheet suitSheet = null;
	public static Sheet dataSheet = null;
	public static int stepRowNumber = 0;
	public static int stepNumber = 0;	
	public static int step = 0;
	public static OutputStream htmlfile = null;
	public static PrintStream printhtml = null;
	public static String OS = System.getProperty("os.name");
	public static String[] subCaseId = new String[1000];
    public static int subCaseIdIndex = 0; 
	public static int subCaseIdIndexForMultipleTestCases = 0;
	//---construct to initialize objects-------
	public Framework()
	{
		try
		{			
			CONFIG = new Properties();
			FileInputStream ip1 = new FileInputStream(System.getProperty("user.dir")+"/Config/Config.Properties");
			CONFIG.load(ip1);
	        System.out.println("RetryCount : "+RetryCount);
	        
	        /* =========== EXTENT  REPORTING =====================*/
	        
			extent= new ExtentReports(System.getProperty("user.dir")+"/test-output/"+ ExecutionTime.folderNameForday +"/"+ ExecutionTime.subFolderNameForExecution +".html",false);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	/*##################################################################################
	Function Name : getTestSuitName
	Description : framework- get batch file path and test excel 
	Created by : Karan Arora
	Created on : 8/5/2014
	####################################################################################*/
    @After
	public void tearDown() throws InterruptedException
	{
		extent.flush();
		extent.close();
		System.out.println("into extent report ending");
	}
	
	
	@Test
	public void getTestSuitName(){
	try{	
		
		File batchExcel =new File(CONFIG.getProperty("BatchRunnerexcel"));
	    Workbook batchWorkBook= Workbook.getWorkbook(batchExcel);
	    Sheet batchSheet= batchWorkBook.getSheet("BatchRunner");
	    int initialtestCasenumber=1;
	    for(int j=1;j<batchSheet.getRows();j++)
	      {	    		
	    	    Cell batchExecuteCell=batchSheet.getCell(1, j);
	            String suitExecutionStatus =batchExecuteCell.getContents();	
	            //Testing whether to execute the test module or not in BatchRunner sheet
	            //If it is 'Y' then simply fetch the module name and the corresponding test Case file 
	            if (suitExecutionStatus.equals("Y"))
	            {
	            	 Cell batchModuleCell=batchSheet.getCell(0, j);
	 	             suitName =batchModuleCell.getContents();//Fetching module name
	 	            System.out.println("suit Name----"+ suitName);
	 	             Cell batchModuleExcelPathCell=batchSheet.getCell(2, j);
	 	             String moduleExcelPath =batchModuleExcelPathCell.getContents();//Path to Test Case file
	 	             String suitExcelPath =  CONFIG.getProperty("CommanPath") + moduleExcelPath;//fetch the Test case file from the TestCase folder
	 	            System.out.println("suitExcelPath----"+ suitExcelPath);
	            	 Sheet moduleSheet= batchWorkBook.getSheet(suitName);
	            	 System.out.println("no of test cases : " + moduleSheet.getRows());
	            	  totalTestCases=moduleSheet.getRows();
	            	  for(int i=1;i<totalTestCases;i++)
	            	  {	  
	            		  testCaseNumber=i;
	            		  Cell testCaseExecutionCell=moduleSheet.getCell(1, i);
	            		  String testCaseExecutionStatus =testCaseExecutionCell.getContents(); 	            		  
	            		  if (testCaseExecutionStatus.equals("Y"))// Checking which test case to be execute
	            		  {
	            			  Cell testCaseNameCell=moduleSheet.getCell(0, i);
	            			  testCaseName = testCaseNameCell.getContents();
	            			  System.out.println("TestCase Name----"+ testCaseName);
	            			 
	            			  suitExcel = new File(suitExcelPath);
	            			  suitWorkBook= Workbook.getWorkbook(suitExcel);	            			
	            			  suitSheet= suitWorkBook.getSheet(suitName); 
	            			  System.out.println("suitSheet----"+ suitSheet);
	                   		  int descriptionNumber = 1; 
	                   		  int stepflag=0;
	                   		  int totalNuberOfTestCaseToBeExecutedInDataSheet =0;

	                   		  if(DataDrivenFlag.equals("Y") || DataDrivenFlag.equals("y"))
	                  		  {   
	                   			int m=1;
	                  			System.out.println("before finding m");
	                  			for(m=1;m<suitSheet.getRows();m++)
	                  			{
	                  				if(suitSheet.getCell(2, m).getContents().equals(testCaseName))
	                  				{  
	                  					break;
	                  				}
	                  			}
	                  			System.out.println("m found at testcase " + m);
	                  			System.out.println("m found at testcase " + suitSheet.getCell(10,m).getContents());
	                  			//String sheetName=suitSheet.getCell(10,m).getContents();
	                  	
	                  			  
	                  			  for(int intialObjectId=m;intialObjectId<suitSheet.getRows();intialObjectId++)
	                  			  {
  	            			    	Cell objectIdCell = suitSheet.getCell(9,intialObjectId);
  	            					String objectId= objectIdCell.getContents();
  	            					if(objectId.equals(""))
  	            					{
  	            						break;		      	            						
  	            					}
  	            					subCaseIdIndexForMultipleTestCases++;
  	            					subCaseId[subCaseIdIndexForMultipleTestCases]=objectId;
  	            					System.out.println("objectId =====================>" +   objectId);
  	            					totalNuberOfTestCaseToBeExecutedInDataSheet++;
	                  			  }
	                  			  System.out.println("suitSheet.getRows()=====================>" + suitSheet.getRows());
	                  		  	while(totalNuberOfTestCaseToBeExecutedInDataSheet>0)
	                  			   	{
	                  		  		  descriptionNumber = 1;
	                  		  		  stepflag=0;
	                  		  		  subCaseIdIndex++;
	                  		  		  for(int k=1;k<suitSheet.getRows();k++)//Inside the test suit eg. login.xls in TestCase folder
	    	            			  { 	
	    	            				  Cell testCaseIDCell = suitSheet.getCell(2, k);
	    	            			  	  String testCaseID =testCaseIDCell.getContents();
	    	            			  	  Cell testCaseIDCell1=suitSheet.getCell(7, k);//getting the screen shot flag from the test case sheet
	    	            			  	  screenShotflag=testCaseIDCell1.getContents();
	    	            			  	  if(!(screenShotflag.equals("N") || screenShotflag.equals("n") || screenShotflag.equals("y") || screenShotflag.equals("Y")))
	    	            			  	  {
	    	            			  		 screenShotflag="N";   			  		 
	    	            			  	  }
	    	            			  	  if (testCaseID.equals(testCaseName))
	    	            			  	  {     
	    	            			  		 
	    	            			  		  if (descriptionNumber == 1)
	    	            			  		  {
	    		            						//go to the data sheet and fetch the available data (stepData)
	    		            						System.out.println("DataDrivenFlag1 : " + DataDrivenFlag);
	    		            						Cell dataSheetNameCell = suitSheet.getCell(10, k);
	    		            						String DataSheetName=dataSheetNameCell.getContents();
	    		            						System.out.println("DataSheetName : " + DataSheetName);	            			
	    		      	            			    dataSheet= suitWorkBook.getSheet(DataSheetName);	    		      	            			    
	    		      	            			    System.out.println("totalNuberOfTestCaseToBeExecutedInDataSheet : " + totalNuberOfTestCaseToBeExecutedInDataSheet);
	    		      	            			    Cell testCaseDescriptionCell = suitSheet.getCell(1, k);
	    		      	            			    testCaseDescription =testCaseDescriptionCell.getContents();
	    		      	            			    extentTestCase = extent.startTest(testCaseName,testCaseDescription);
	    		      	            			    System.out.println("Executing the TestCase: "+ testCaseDescription+" Flag : "+screenShotflag);
	    		      	            			    if(testCaseDescriptionCell!=null)
	    		      	            			    		stepForwardExceptionFlag = 0;//Reseting the flag for the first sub test case of a test case
	    		      	            			    stepflag=0;
	    		      	            			    initialtestCasenumber=k;		            			  			
	    		      	            			    RetryCount=RetryCountTemp;
	    		      	            			    System.out.println("RetryCount : "+RetryCount);
	    		      	            			    if(screenShotflag.equals("N") || screenShotflag.equals("n"))
	    		      	            			    {
	    		      	            			    	nameOfScreenshotFlag2=1;
	    		      	            			    	nameOfScreenshotFlag=1;
	    		      	            			    }
	    		      	            			    else 
	    	            			  				  nameOfScreenshotFlag2=0;
	    		            			  	  }
	    	            			  		  stepflag++;
	    	            			  		  descriptionNumber=0;	            			  	
	                			  		      testName = testCaseName;
	    	            			  		  stepRowNumber= k;
	    	            			  		  stepNumber= stepNumber+1;
	    	            			  		//Test the sub test cases only there is no exception occurred before, otherwise escape 
	    	            			  		//all the sub test case and execute the next test case.
	    	            			  		if(stepForwardExceptionFlag==0)
	    	            			  		{
	    	            			  			System.out.println("Step "+stepflag +" of " + testName + " Executing");
	    	            			  			stepFunction();
	    	            			  		}
	    	            			  		 
	    										
	    	            			  	  }
	    	            			  	  //--check ExecutionTime.stopExecutionflag and stop-------  
	    	            			  	if(ExecutionTime.stopExecutionflag==true){
	    	            			    	k= suitSheet.getRows();
	    	            			    	 if(stepForwardExceptionFlag==1){
	    		            			  			if(RetryCount!=0)
	    		            			           { 
	    		            			  				k=0;
	    		            			  				RetryCount--;	
	    		            			  			}	
	    		            			  		}
	    	            			    	 stepForwardExceptionFlag=0;
	    	            			    	 ExecutionTime.stopExecutionflag=false;
	    	            			    	}
	    	            			  }//end of for loop
	                  				totalNuberOfTestCaseToBeExecutedInDataSheet--;
	                  		  }//end of while loop
	                  	}
	                 else
	                    { 
	                	 
	                  	   for(int testCaseCheck=1;testCaseCheck<suitSheet.getRows();testCaseCheck++)//Inside the test suit eg. login.xls in TestCase folder
	            			  { 
	            				  Cell testCaseIDCell = suitSheet.getCell(2, testCaseCheck);
	            			  	  String testCaseID =testCaseIDCell.getContents();
	            			  	  Cell testCaseIDCell1=suitSheet.getCell(7, testCaseCheck);//getting the screen shot flag from the test case sheet
	            			  	  screenShotflag=testCaseIDCell1.getContents();
	            			  	
	            			  	  if(!(screenShotflag.equals("N") || screenShotflag.equals("n") || screenShotflag.equals("y") || screenShotflag.equals("Y")))
	            			  	  {
	            			  		 screenShotflag="N";   			  		 
	            			  	  }
	            			  	  if (testCaseID.equals(testCaseName))
	            			  	  {     
	            			  		  if (descriptionNumber == 1)
	            			  		  {
	            			  			  Cell testCaseDescriptionCell = suitSheet.getCell(1, testCaseCheck);
	            			  			  testCaseDescription =testCaseDescriptionCell.getContents();
	            			  			  extentTestCase = extent.startTest(testCaseName,testCaseDescription);
	            			  			  System.out.println("Executing the TestCase: "+ testCaseDescription+" Flag : "+screenShotflag);
	            			  			  if(testCaseDescriptionCell!=null)
	            			  			  stepForwardExceptionFlag = 0;//Reseting the flag for the first sub test case of a test case
	            			  			  stepflag=0;
	            			  			  initialtestCasenumber=testCaseCheck;		            			  			
	    		                   		  RetryCount=RetryCountTemp;
	    		                   		  System.out.println("RetryCount : "+RetryCount);
	            			  			  if(screenShotflag.equals("N") || screenShotflag.equals("n"))
	            			  			  {
	            			  				  nameOfScreenshotFlag2=1;
	            			  				  nameOfScreenshotFlag=1;
	            			  			  }
	            			  			  else 
	            			  				  nameOfScreenshotFlag2=0;
		            			  	  }
	            			  		  stepflag++;
	            			  		  descriptionNumber=0;	            			  	
            			  		      testName = testCaseName ;
	            			  		  stepRowNumber= testCaseCheck;
	            			  		  stepNumber= stepNumber+1;
	            			  		//Test the sub test cases only there is no exception occurred before, otherwise escape 
	            			  		//all the sub test case and execute the next test case.
	            			  		if(stepForwardExceptionFlag==0)
	            			  		{
	            			  			System.out.println("Step "+stepflag +" of " + testName + " Executing");
	            			  			stepFunction();
	            			  		}
	            			  	 	
	            			  	  }
	            			  	  //--check ExecutionTime.stopExecutionflag and stop-------  
	            			    if(ExecutionTime.stopExecutionflag==true){
	            			    	testCaseCheck= suitSheet.getRows();
	            			    	 if(stepForwardExceptionFlag==1){
		            			  			if(RetryCount!=0)
		            			           { 
		            			  				testCaseCheck=0;
		            			  				RetryCount--;	
		            			  			}	
		            			  		}
	            			    	 stepForwardExceptionFlag=0;
	            			    	 ExecutionTime.stopExecutionflag=false;
	            			    	}
	            			    }
	                  	}                                                   
	           }
	            	 	  //--check ExecutionTime.stopExecutionflag and stop-------  
	            		  if(ExecutionTime.stopExecutionflag==true){
          			    	i= moduleSheet.getRows();
          			    	ExecutionTime.stopExecutionflag=false;
          			    	} 
	            	  }
	            }
	       	  //--check ExecutionTime.stopExecutionflag and stop-------  
	            if(ExecutionTime.stopExecutionflag==true){
  			    	j= batchSheet.getRows();
  			    	} 
	      }
	}
	catch(Exception e)
	{
		System.out.println("in exception-----------getTestSuitName"+ e); 
		e.printStackTrace();
	}
	
	}
	
	/*##################################################################################
	Function Name : recoveryFunction
	Description   : Used for the recovery process
	Created by    : Karan Arora
	Created on    : 12/28/2014
	####################################################################################*/
	/*public static void recoveryFunction(int initialTestCasenumber,int finalTestCasenumber,int descriptionNumber){
		int k = initialTestCasenumber;
		int stepFlag=0;
		stepForwardExceptionFlag=0;
		System.out.println("RetryCount : "+RetryCount);
		while(k<finalTestCasenumber){
		  Cell testCaseIDCell = suitSheet.getCell(2, k);
	  	  String testCaseID =testCaseIDCell.getContents();
	  	  Cell testCaseIDCell1=suitSheet.getCell(7, k);//getting the screen shot flag from the test case sheet
	  	  screenShotflag=testCaseIDCell1.getContents();	
	  	 if(!(screenShotflag.equals("N") || screenShotflag.equals("n") || screenShotflag.equals("y") || screenShotflag.equals("Y")))
	  	 {
	  		 screenShotflag="N";   			  		 
	  	 }
	  	  if (testCaseID.contains(testCaseName))
	  	  {     
	  		  if (descriptionNumber == 1)
	  		  {
	  			  Cell testCaseDescriptionCell = suitSheet.getCell(1, k);
	  			  testCaseDescription =testCaseDescriptionCell.getContents();
	  			  System.out.println("Executing the TestCase: "+ testCaseDescription+" Flag : "+screenShotflag);
	  			  if(testCaseDescriptionCell!=null)
	  			  stepForwardExceptionFlag = 0;//Reseting the flag for the first sub test case of a test case
	  			  if(screenShotflag.equals("N") || screenShotflag.equals("n"))
	  			  {
	  				  nameOfScreenshotFlag2=1;
	  				  nameOfScreenshotFlag=1;
	  			  }
	  			  else 
	  				  nameOfScreenshotFlag2=0;
		  	  }
	  		  descriptionNumber=0;	            			  	
		      testName = testCaseName ;
	  		  stepRowNumber= k;
	  		  stepNumber= stepNumber+1;
	  		  stepFlag++;
	  		//Test the sub test cases only there is no exception occurred before, otherwise escape 
	  		//all the sub test case and execute the next test case.
	  		if(stepForwardExceptionFlag==0)
	  		{
	  			System.out.println("Step: "+ stepFlag +" of " + testName + " Executing");
	  			stepFunction();
	  		}
				
	  	  }
	  	  //--check ExecutionTime.stopExecutionflag and stop-------  
	  	  if(ExecutionTime.stopExecutionflag==true){
	    	k= suitSheet.getRows();
	    	}
	    	k++;
		 }
	}*/
	
	
	/*##################################################################################
	Function Name : stepFunction
	Description : test step 
	Created by : Karan Arora
	Created on : 8/5/2014
	####################################################################################*/
	public static void stepFunction(){
	
		try{
			Cell stepActionCell = suitSheet.getCell(5, stepRowNumber);
			String stepAction = stepActionCell.getContents();
			Cell stepObjectCell = suitSheet.getCell(4, stepRowNumber);
			String stepObject = stepObjectCell.getContents();
			Cell stepDataCell = suitSheet.getCell(6, stepRowNumber);
			String stepData = stepDataCell.getContents();
						
			
			if(DataDrivenFlag.equals("Y") || DataDrivenFlag.equals("y"))
			{
				//go to the data sheet and fetch the available data (stepData)
				Cell objectIdCell = suitSheet.getCell(8, stepRowNumber);
				String objectId= objectIdCell.getContents();
				String SubCaseTobeSearch = subCaseId[subCaseIdIndex];
				System.out.println("Object Id To Be Search : " + objectId +" SubCase To be Search = " + SubCaseTobeSearch + " SubCase index  = " + subCaseIdIndex );
				int row = 0;	
				int coulmn =0;
				int objectIDFound = 0;
				subTestCaseDescription = SubCaseTobeSearch;
				for(int intialObjectId=3;intialObjectId<dataSheet.getColumns();intialObjectId++)
  			    {
  			    	Cell ObjectIdCell = dataSheet.getCell(intialObjectId, 0);  		
  					String ObjectId1= ObjectIdCell.getContents();
  					if(ObjectId1.equals(objectId) )
  					{
  						coulmn = intialObjectId;
  						objectIDFound = 1;
  						break;
  					}
  			    }
				for(int intialTestId=1;intialTestId<dataSheet.getRows();intialTestId++)
  			    {
  			    	Cell TestIdCell = dataSheet.getCell(2, intialTestId);
  					String TestId= TestIdCell.getContents();
  					if(TestId.equals(SubCaseTobeSearch) )
  					{
  						row = intialTestId;
  						break;
  						
  					}
  			    }
				if(objectIDFound==1)
				{
					Cell DataSheetCell = dataSheet.getCell(coulmn, row);
					String DataSheetData= DataSheetCell.getContents();
					if(!DataSheetData.equals(""))
					{
						stepData = DataSheetData;
					
					}
				}
				System.out.println("Final Data passed  =  " + stepData);
				//String stepAction = stepActionCell.getContents();				
			}
			
			 switch (stepAction) {
			 case "waitForPageLoad":
				 waitForPageLoadWeb();
				 break;
			 case "waitForObjectByXpath":
				 waitForObjectByXpathWeb(stepObject);
				 break;
		     case "navigateurl":
		    	 System.out.println("stepData : " + stepData);	
		    	 navigateurlCommon(stepData);
		    	 break;
		     case "typeTextByXpath":
		    	 typeTextByXpathWeb(stepObject,stepData);
		    	 break;
		     case "typetextbyCssSelector":
		    	 typetextbyCssSelectorWeb(stepObject,stepData);
		    	 break;	 
		     case "clearTextByXpath":
		    	 clearTextByXpathWeb(stepObject);
		    	 break;
		     case "clearTextByCssSelector":
		    	 clearTextByCssSelectorWeb(stepObject);
		    	 break;	  
		     case "typeTextByName":
		    	 typeTextByNameWeb(stepObject,stepData);
		    	 break;
		     case "typeTextByID":
		    	 typeTextByIDWebAndroid(stepObject,stepData);
		    	 break;
		     case "clickByXpath":
		    	 clickByXpathWeb(stepObject);
		    	 break;
		     case "clickByXpathWebAndroid":
		    	 clickByXpathWebAndroid(stepObject);
		    	 break;
		    	 
		     case "clickButtonByName":
		    	 clickByNameWebAndroid(stepObject);
		    	 break;	 
		     case "wait":
		    	 waitCommon(stepData);
		    	 break;	
		     case "verifyTextByXpath":
		    	 verifyTextByXpathWeb(stepObject,stepData);
		    	 break;
		     case "verifyTextByXpathAndroid":
		    	 verifyTextByXpathAndroid(stepObject,stepData);
		    	 break;
		 	 case "closewindow":
		 		closewindowCommon();
		    	 break;	  
		     case "clickWithHandleAlert":
		    	 clickWithHandleAlertWeb();
		    	 break;	  	 
		     case "clickByID":
		    	 clickByIDWebAndroid(stepObject);
		    	 break;	 	 
		     case "verifyValuebyID":
		    	 verifyValuebyIDWeb(stepObject,stepData);
		    	 break;	
		     case "verifyTextByID":
		    	 verifyTextByIDWebAndroid(stepObject,stepData);
		    	 break;	
		     case "verifyTextByName":
		    	 verifyTextByNameWeb(stepObject,stepData);
		    	 break;	 
		     case "verifyWebListSelectedItemByID":
		    	 verifyWebListSelectedItemByIDWeb(stepObject,stepData);
		    	 break;	 
		     case"selectIndexWebListByID":
		    	 selectIndexWebListByIDWeb(stepObject,stepData);
		    	 break;
		     case"selectIndexWebListByxpath":
		    	 selectIndexWebListByxpathWeb(stepObject,stepData);
		    	 break;
		     case"clickItemFromWidgetList":
		    	 clickItemFromWidgetListWeb(stepObject,stepData);
		     	break;
		     case "isEnabledByID":
		    	 isEnabledByIDWeb(stepObject);
		    	 break;
		     case "isNotEnabledByID":
		    	 isNotEnabledByIDWeb(stepObject);
		    	 break;
		     case "selectWebListByID":
		    	 selectWebListByIDWeb(stepObject,stepData);
		    	 break;
		     case "sendkeys":
		    	 sendkeysWeb(stepData );
		    	 break; 
		     case "connectDatabase":
		    	 connectDatabaseWeb(stepData );
		    	 break; 
		     case "disconnectDatabase":
		    	 disconnectDatabaseWeb();
		    	 break; 
		     case "verifyObjectbyID":
		    	 verifyObjectbyIDWeb(stepObject);
		    	 break; 
		     case "verifyDropDownOptionFromDropdownByID":
		    	 verifyDropDownOptionFromDropdownByIDWeb(stepObject,stepData);
		    	 break;  	 
		     case "firstFrameSelect":
		    	 firstFrameSelectWeb();
		    	 break;  
		     case "switchToDefault":
		    	 switchToDefaultWeb();
		    	 break;
		     case "stopExecutionByXpath":
		    	 stopExecutionByXpathWeb(stepObject);
		     	break;
		     case "handleWindowPopUp":
		    	 handleWindowPopUpWeb();	
		     break;
		   //Handling of typical locators through cssSelector//
		   //=============Karan Arora ===============================//
		   //=============12/05/2016===================================//
		     case "verifyTextByCssSelector":
		    	 verifyTextByCssSelectorWeb(stepObject,stepData);
		     	break;
		     case "clickByCSSSelector":
		    	 clickByCSSSelectorWeb(stepObject);
		     	
		    	
		  //----------------------------------------------
		  //--------------application specific------------
		  //---------------------------------------------
		    	 
		     case "VerifyItemFromMenuList":
		    	 VerifyItemFromMenuListWeb(stepObject,stepData);
		    	 break;
		     case "clickItemFromAutoListWeb":
		    	 clickItemFromAutoListWeb(stepObject,stepData);
		    	 break;
		     case "clickItemFromMetricList":
		    	 clickItemFromMetricListWeb(stepObject,stepData);
		    	 break;
		     case "dropdownListt":
		    	 dropdownListtWeb(stepObject,stepData);
		    	 break;
		     case "selectFromQueueList":
		    	 selectFromQueueListWeb(stepObject,stepData);
		    	 break;	
		     case "navigateWindowWithTitle":
		    	 navigateWindowWithTitleWeb(stepData);
		    	 break;
		     case "navigateWindowWithTitleAndSelectFromPickList":
		    	 navigateWindowWithTitleAndSelectFromPickListWeb(stepData);
		    	 break; 
		     case "saveFlow":
		    	 saveFlowWeb();
		    	 break; 
		    	 
		    	 //============comment===============
		     case "selectPosition":
		    	 selectPositionWeb(stepObject,stepData);
		    	 break;
		     case "holdPosition":
		    	 holdPositionWeb(stepObject);
		    	 break;	 
		     case "duplicateCandidate":
		    	 duplicateCandidateWeb(stepObject);
		    	 break;	 
		     case "verifyInterviewQuestionByjquery":
		    	 verifyInterviewQuestionByjquery(stepObject, stepData);
		    	 break;
		    	 
		    // case added while making genric ==========================//
		    //=============Beant Singh ===============================//
		    //=============15/02/16===================================//	
		     case "clickByLinktext":
		    	 clickByLinktextWeb(stepObject);
		     break;
		     case "clickByIDMibile":
		    	 clickByIDMibile(stepObject);
		    	 break;
		    	 // case added for android  ==========================//
				    //=============Beant Singh ===============================//
				    //=============15/02/16===================================//	 
		     case "scrollClickText":
		    	 scrollClickTextAndroid(stepData);
		    	 break;
		     case "clickByText":
		    	 clickByTextAndroid(stepData);
		    	 break;	 
		     case "swipe":
		    	 swipeAndroid(stepData);
		    	 break;
		     case "dashBoard":
		    	 dashBoardWeb(stepObject);
		    	 break;
		     case "screenshotCompare":
		    	 screenshotCompareWeb();
		    	 break;
		   case "infiniteScrollTextClick":
			   infiniteScrollTextClickAndroid(stepData);
		         break;
			case "switchToIFrameByName":
				switchToIFrameByNameWeb(stepObject);
		    	 break;
		    case "switchToIFrameByXpath":
		    	switchToIFrameByXpathWeb(stepObject);
		     	break;
		    case "switchToIFrameByCSSSelector":
		    	switchToIFrameByCSSSelectorWeb(stepObject);
		     	break;
		     	/*======================ASSERTIONS========================*/
		    case "dashBoardAssertion":
		    	dashBoardAssertion(stepObject,stepData);
		    	break;
		    case "widgetAssertion":
		        widgetAssertion(stepObject,stepData);
		        break;
		    case "deployAlertAssert":
		    	deployAlertAssert(stepObject);
		    	break;
		    case "severityAssertion":
		        severityAssertion(stepObject, stepData);
		        break;
		    case "filterAssertion":
		        filterAssertion(stepObject, stepData);
		        break;
		    case "attachmentupdateAssertion":
		    	attachmentupdateAssertion(stepObject, stepData);
		    	break;
		    case "settingAssertion":
		    	settingAssertion(stepObject, stepData);
		    	break;
		    case "MetricAssertion":
		    	MetricAssertion(stepObject, stepData);
		    	break;
		    case "assertHistoryGrid":
		    	assertHistoryGrid(stepObject, stepData);
		    	break;
		    case "performanceGridValueAssertion":
		    	performanceGridValueAssertion(stepObject, stepData);
		        break;
		    case "moveToList":
		    	moveToListWeb(stepObject, stepData);
		    	break;
		    case "minimumPercMissAssertion":
		         minimumPercMissAssertion(stepObject, stepData);
		         break;
		    case "performanceMetricRadio":
		    	performanceMetricRadio(stepObject, stepData);
		    	break;
		    case "DeleteDashBoard":
		    	DeleteDashBoard(stepObject, stepData);
		    	break;
		    default:
		    	 defaultFunctionCommon(stepAction);
		     /*case "webServiceCall":
		     webServiceCall();
		     break;*/	 
			 }

		}
		catch(Exception e)
		{
			stepForwardExceptionFlag = 1;
			
			e.printStackTrace();
			System.out.println("in exception-----------getTestData");
		}
			
	}
	
}
