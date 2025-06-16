package com.djamo.qa.base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import com.djamo.qa.factory.DriverFactory;

import io.appium.java_client.AppiumDriver;

public class BaseTest {

	protected AppiumDriver driver;

	    @Parameters({"platform", "profile"})
	    @BeforeClass(alwaysRun = true)
	    public void setUp(@Optional("android") String platform,
	                      @Optional("android_google_maps") String profile) {

	        // Initializes the driver with the chosen JSON profile
	        DriverFactory.initDriver(platform, profile);
	        driver = DriverFactory.getDriver();
	    }

	    @AfterClass(alwaysRun = true)
	    public void tearDown() {
	        // Close the Appium driver and server if necessary
	        DriverFactory.quitDriver();
	    }
	    
	    public AppiumDriver getDriver() {
	        return this.driver;
	    }
	    
	    public String getScreenshot(String testCaseName) throws IOException {
	        File reportsDir = new File(System.getProperty("user.dir") + "/reports/");
	        if (!reportsDir.exists()) {
	            reportsDir.mkdirs();
	        }

	        String fileName = testCaseName + "_" + System.currentTimeMillis() + ".png";
	        String fullPath = reportsDir.getAbsolutePath() + "/" + fileName;

	        try {
	            TakesScreenshot ts = (TakesScreenshot) driver;
	            File source = ts.getScreenshotAs(OutputType.FILE);
	            FileUtils.copyFile(source, new File(fullPath));
	            return fullPath;
	        } catch (Exception e) {
	            System.err.println("Screenshot failed: " + e.getMessage());
	            throw e;
	        }
	    }

}
