package com.demo.base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.demo.util.TestUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.SkipException;

import static com.demo.util.TestUtil.takeScreenshot;

/**
 * Project Name    : selenium-data-driven-framework
 * Developer       : Sagar Bhatt
 * Version         : 1.0.0
 * Date            :
 * Time            :
 * Description     :
 **/
public class Listener extends TestBase implements ITestListener {

    ExtentReports reports = extentReportSetup();
    @Override
    public void onTestStart(ITestResult result) {
        test = reports.createTest(result.getName());
        if(!TestUtil.isTestRunnable(result.getMethod().getMethodName(), xls_reader)){
            throw new SkipException("Skipping test as the runmode is set as NO");
        }
        driver.get(config.getProperty("testUrl"));
        log.info("navigating to the homepage");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
        test.log(Status.FAIL, MarkupHelper.createLabel(result.getThrowable().getMessage(), ExtentColor.RED));
        test.fail("Screenshot of the failed test is :"+
                test.addScreenCaptureFromPath(takeScreenshot(driver, result.getName())));

    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.GREY));
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }
}

