package com.djamo.qa.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.djamo.qa.base.BaseTest;

import io.appium.java_client.AppiumDriver;

public class Retry implements IRetryAnalyzer {

    private int retryCount = 0;
    private static final int MAX_RETRY_COUNT = 1;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount < MAX_RETRY_COUNT && shouldRetry(result)) {
            retryCount++;
            logRetryAttempt(result);
            captureScreenshotOnRetry(result);
            return true;
        }
        return false;
    }

    private boolean shouldRetry(ITestResult result) {
        Throwable throwable = result.getThrowable();
        return throwable == null || !throwable.getClass().getName().contains("NoSuchElementException");
    }

    private void logRetryAttempt(ITestResult result) {
        String message = String.format("Retry #%d for test %s", retryCount, result.getName());
        System.out.println(message);
        ExtentTestManager.getTest().log(Status.WARNING, message);
    }

    private void captureScreenshotOnRetry(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            if (testInstance instanceof BaseTest) {
                AppiumDriver driver = ((BaseTest) testInstance).getDriver();
                if (driver != null) {
                    String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                    ExtentTestManager.getTest().log(Status.INFO, "Screenshot before retry",
                            MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to capture screenshot: " + e.getMessage());
            ExtentTestManager.getTest().warning("Screenshot retry failed: " + e.getMessage());
        }
    }
}