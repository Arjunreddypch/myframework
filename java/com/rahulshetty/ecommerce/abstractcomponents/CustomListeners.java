package com.rahulshetty.ecommerce.abstractcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.rahulshetty.ecommerce.extentreports.ExtentReportsForReporting;

public class CustomListeners extends BaseTest implements ITestListener{
	
	ExtentTest test;
	ExtentReports report=ExtentReportsForReporting.getReportObject();
	ThreadLocal<ExtentTest> tl=new ThreadLocal<ExtentTest>();
	
	@Override
	public void onTestStart(ITestResult result) {
		test=report.createTest(result.getMethod().getMethodName());
		tl.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		tl.get().log(Status.PASS, "test is passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		tl.get().fail(result.getThrowable());
		try {
			driver=(WebDriver)result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String filePath=null;
		try {
			 filePath=getScreenshot(result.getMethod().getMethodName(),driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tl.get().addScreenCaptureFromPath(filePath,result.getMethod().getMethodName());
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestSkipped(result);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedButWithinSuccessPercentage(result);
	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {
		// TODO Auto-generated method stub
		ITestListener.super.onTestFailedWithTimeout(result);
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ITestListener.super.onStart(context);
	}

	@Override
	public void onFinish(ITestContext context) {
		report.flush();
	}

	
}
