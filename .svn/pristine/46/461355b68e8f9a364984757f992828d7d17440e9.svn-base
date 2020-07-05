set folderDate=%date:~4,2%-%date:~7,2%-%date:~10,4%
set date2=%date%
set txtFile=%time:~0,2%-%time:~3,2%-%time:~6,2%

IF NOT EXIST D:\New\logs\%folderDate% ( mkdir D:\selenium_framework\trunk\Selenium_framework\logs\%folderDate% )

cd /d D:\selenium_framework\trunk\Selenium_framework

java -jar Dploy.jar > D:\selenium_framework\trunk\Selenium_framework\logs\%folderDate%\%txtFile%.txt

taskkill /F /im chromedriver.exe
pause

