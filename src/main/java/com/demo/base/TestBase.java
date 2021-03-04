package com.demo.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.demo.util.TestUtil;
import com.demo.util.Xls_reader;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.*;
import com.demo.util.DriverManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import static com.demo.util.TestUtil.takeScreenshot;

/**
 * Project Name    : selenium-data-driven-framework
 * Developer       : Sagar Bhatt
 * Version         : 1.0.0
 * Date            : 02-March-2021
 * Time            : 18:00
 * Description     : Webdriver related settings, configuration settings, Extent Report, Basic webdriver function
 **/
public class TestBase {

    protected static WebDriver driver;
    private static WebDriverWait wait;
    private static final String fileSeparator = File.separator;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger("devpinoyLogger");
    private static final String rootDirectory = System.getProperty("user.dir");
    public static Xls_reader xls_reader = new Xls_reader(rootDirectory + fileSeparator + "src" + fileSeparator + "main"+
            fileSeparator + "resources" + fileSeparator + "testdata.xlsx");
    public static ExtentReports extent;
    public ExtentTest test;
    String browserName;


    @BeforeSuite
    public void setup() throws IOException {
        if(driver == null){
            fis = new FileInputStream(rootDirectory + fileSeparator + "src" + fileSeparator + "main" + fileSeparator
                    + "resources" + fileSeparator + "config.properties");
            config.load(fis);
            fis = new FileInputStream(rootDirectory + fileSeparator + "src" + fileSeparator + "main" + fileSeparator
                    + "resources" + fileSeparator + "objectRepository.properties");
            OR.load(fis);
            BasicConfigurator.configure();
            log.debug("Config file loaded !!!");

            //If running test fro Jenkins then need to provide browser name from outside
            if(System.getenv("browser")!=null && !System.getenv("browser").isEmpty()){
                browserName = System.getenv("browser");
            }else {
                browserName= config.getProperty("browserName");
            }
            String testUrl = config.getProperty("testUrl");
            int implicitTimeOut = Integer.parseInt(config.getProperty("implicitWait"));
            initializeDriver(browserName, testUrl, implicitTimeOut);
        }
    }


    public ExtentReports extentReportSetup(){
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(rootDirectory + fileSeparator + "reports"+
                    fileSeparator + "html-report" + fileSeparator + "execution-report.html");
            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
            sparkReporter.config().setDocumentTitle("Test Execution Report - Automation Practise");
            sparkReporter.config().setReportName("Test Execution Report - Automation Practise");
            sparkReporter.config().setTheme(Theme.DARK);
            extent.setSystemInfo("Application Name", "Demo App");
            extent.setSystemInfo("Tester Name", "Sagar Bhatt");
            extent.setSystemInfo("Browser", "Chrome");
        }
        return extent;
    }

    @AfterTest
    public void endReport(){
        if(extent!= null)
            extent.flush();
    }

    @AfterSuite
    public void tearDown(){

        if(driver!=null){
            driver.quit();
        }
    }


    public void initializeDriver(String browserName, String testUrl, int implicitTimeOut){
        driver = DriverManager.getDriver(browserName);
        driver.manage().window().fullscreen();
        driver.manage().timeouts().implicitlyWait(implicitTimeOut, TimeUnit.SECONDS);
        driver.get(testUrl);
        log.debug("Navigated to : " + config.getProperty("testUrl"));
        wait = new WebDriverWait(driver, 10);
    }


    public WebElement getElement(String locator){
        WebElement element = null;
        if(locator.endsWith("_CSS")){
            element = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        }else if(locator.endsWith("_XPATH")){
            element = driver.findElement(By.xpath(OR.getProperty(locator)));
        }else if(locator.endsWith("_ID")){
            element = driver.findElement(By.id(OR.getProperty(locator)));
        }
        return element;
    }

    public void click(String locator){
        getElement(locator).click();
        log.info("Clicking on: "+ locator);
    }

    public void type(String locator, String value){
        getElement(locator).sendKeys(value);
        log.info("Typing: "+ value +" in textbox:"+locator);
    }

    public void selectDropDownValue(String locator, String value){
        WebElement drpdwn_element = getElement(locator);
        Select select = new Select(drpdwn_element);
        select.selectByVisibleText(value);
        log.info(value+ " is selected from: "+ locator);
    }

    public boolean isElementPresent(String locator){
        List<WebElement> allElements = null;
        boolean b = false;
        if(locator.endsWith("_CSS")){
            allElements = driver.findElements(By.cssSelector(OR.getProperty(locator)));
        }else if(locator.endsWith("_XPATH")){
            allElements = driver.findElements(By.xpath(OR.getProperty(locator)));
        }else if(locator.endsWith("_ID")){
            allElements = driver.findElements(By.id(locator));
        }
        if(allElements.size()>0){
            b = true;
        }
        return b;
    }

    public String getText(String locator){
        return getElement(locator).getText();
    }

    public String getAlertMessage(){
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertMessage = alert.getText();
        alert.accept();
        return alertMessage;
    }

    public void verifyTrue(boolean b){
        Assert.assertTrue(b);
    }

    public void assertMessage(String actualMessage, Enums.TestMessages enums){
        String message = enums.getMessage();
        Assert.assertTrue(actualMessage.contains(message), "Message expected was: \n"+ message + "\nBut found: \n"+ actualMessage);
    }

    public void verifyValues(String actualVal, String expectedVal){
        try{
            Assert.assertEquals(actualVal, expectedVal);
        }catch (Throwable t){
            test.log(Status.FAIL, "Verification failed with exception :"+t.getMessage());
            test.fail("Screenshot of the failed test is :"+
                    test.addScreenCaptureFromPath(takeScreenshot(driver, "failedAssertion")));

        }
    }

}
