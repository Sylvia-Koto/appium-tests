package com.djamo.qa.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.ExtentTest;

import com.djamo.qa.base.BaseTest;
import io.appium.java_client.AppiumDriver;

public class Listeners implements ITestListener {

    private static ExtentReports extent = ExtentReporterNG.getReportObject();
    
    private String getCapabilitySafe(AppiumDriver driver, String name) {
        Object value = driver.getCapabilities().getCapability(name);
        return value != null ? value.toString() : "Not set";
    }
    private String getReadableDeviceName(String udid) {
        try {
            ProcessBuilder builder = new ProcessBuilder("adb", "-s", udid, "emu", "avd", "name");
            builder.redirectErrorStream(true);
            Process process = builder.start();

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String deviceName = reader.readLine();
                return (deviceName != null && !deviceName.isEmpty()) ? deviceName : udid;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return udid;
        }
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        ExtentTestManager.setTest(test);
        System.out.println("✅ onTestStart called for: " + result.getMethod().getMethodName());
        

        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            AppiumDriver driver = ((BaseTest) testInstance).getDriver();
            if (driver != null) {
                try {
                    String platform = getCapabilitySafe(driver, "platformName");
                    String version = getCapabilitySafe(driver, "platformVersion");
                    String device = getCapabilitySafe(driver, "deviceName");
                    String udid = getCapabilitySafe(driver, "deviceUDID");
                    String readableName = getReadableDeviceName(udid);
                 

                    test.info("🔎 Platform: " + platform);
                    test.info("📱 Device: " + device);
                    test.info("📱 Device (AVD): " + readableName);
                    test.info("🧩 Version: " + version);
                    
            
                    String appPackage = getCapabilitySafe(driver, "appPackage");
           
                    test.info("📦 Application : " + (appPackage == null || appPackage.equals("Not set") ? "non défini" : appPackage));
                } catch (Exception e) {
                    test.warning("Impossible de récupérer les infos device: " + e.getMessage());
                }
            }
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        ExtentTestManager.getTest().log(Status.PASS, "Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        ExtentTestManager.getTest().fail(result.getThrowable());

        String uniqueId = getUniqueTestId(result);
        boolean alreadyHandled = result.getTestContext().getFailedTests().getAllResults().stream()
                .filter(r -> r != result)
                .anyMatch(r -> getUniqueTestId(r).equals(uniqueId));

        if (!alreadyHandled) {
            try {
                Object testInstance = result.getInstance();
                if (testInstance instanceof BaseTest) {
                    AppiumDriver driver = ((BaseTest) testInstance).getDriver();
                    
                    System.out.println("Listener - Driver on test failure: " + driver);
                    if (driver != null) {
                        String screenshotBase64 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BASE64);
                        ExtentTestManager.getTest().fail("Test failed after retries",
                                MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotBase64).build());
                        saveScreenshotToFile(uniqueId, screenshotBase64);
                    }else {
                        System.out.println("Driver est null au moment de la capture d'écran !");
                    }
                    
                }
            } catch (Exception e) {
                ExtentTestManager.getTest().warning("Échec de la capture: " + e.getMessage());
            }
        }
    }

    private String getUniqueTestId(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String className = result.getTestClass().getName();

        String params = "";
        if (result.getParameters() != null && result.getParameters().length > 0) {
            params = Arrays.stream(result.getParameters())
                    .map(p -> p == null ? "null" : p.toString().replaceAll("[^a-zA-Z0-9]", "_"))
                    .reduce((a, b) -> a + "_" + b).orElse("");
        }

        return className + "_" + methodName + (params.isEmpty() ? "" : "_" + params);
    }

    private void saveScreenshotToFile(String methodName, String base64Screenshot) {
        try {
            byte[] decodedImg = Base64.getDecoder().decode(base64Screenshot);
            String timestamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date());
            String fileName = "screenshots/" + methodName + "_" + timestamp + ".png";

            Files.createDirectories(Paths.get("screenshots"));
            Files.write(Paths.get(fileName), decodedImg);
        } catch (Exception e) {
            ExtentTestManager.getTest().warning("Échec sauvegarde fichier: " + e.getMessage());
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}