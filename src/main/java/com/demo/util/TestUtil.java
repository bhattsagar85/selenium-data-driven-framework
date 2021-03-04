package com.demo.util;

import com.demo.base.TestBase;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project Name    : selenium-data-driven-framework
 * Developer       : Sagar Bhatt
 * Version         : 1.0.0
 * Date            : 03-March-2021
 * Time            : 17:00
 * Description     : Common Test Functions
 **/

public class TestUtil extends TestBase {

    private static final String rootDirectory = System.getProperty("user.dir");
    private static final String fileSeparator = File.separator;


    /**
     * @author Sagar Bhatt
     * @param driver
     * @param screenshotName
     * @return
     */

    public static String takeScreenshot(WebDriver driver, String screenshotName){
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").format(new Date());
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
        String destination = rootDirectory+fileSeparator+"screenshot"+fileSeparator+screenshotName
                +"-"+timeStamp+".png";
        File finalDestination = new File(destination);
        try{
            FileUtils.copyFile(source, finalDestination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return destination;
    }

    public void bankManagerLogin(){
        click("btn_bankmanager_login_CSS");
    }

    /**
     * author Sagar Bhatt
     * @param value
     */
    public void customerLogin(String value){
        click("btn_customer_login_CSS");
        selectDropDownValue("dropdown_name_ID", value);
        click("btn_login_CSS");
    }

    public static boolean isTestRunnable(String testName, Xls_reader xls_reader){
        String sheetName = "test_suite";
        int rows = xls_reader.getRowCount(sheetName);
        for(int rowNo=2; rowNo<=rows; rowNo++){
            String testCaseId = xls_reader.getCellData(sheetName, "TCID", rowNo);
            if(testCaseId.equalsIgnoreCase(testName)){
                String runmode = xls_reader.getCellData(sheetName, "Runmode", rowNo);
                if(runmode.equalsIgnoreCase("Y"))
                    return true;
                else
                    return false;

            }
        }
        return false;
    }
}
