package com.djamo.qa.pages;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.nativekey.PressesKey;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import io.appium.java_client.AppiumBy;


public class SearchAndNavigatePage {

    private AppiumDriver driver;
    private WebDriverWait wait;

    // Locators
    private By searchBox = By.id("com.google.android.apps.maps:id/search_omnibox_text_box");
    private By searchInputField = By.id("com.google.android.apps.maps:id/search_omnibox_edit_text");
    private By startPointEdit = By.id("com.google.android.apps.maps:id/directions_startpoint_textbox");
    private By mapView = By.id("com.google.android.apps.maps:id/layers_fab");
    private By mapCompass = By.id("com.google.android.apps.maps:id/compass_container");

    public SearchAndNavigatePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void openSearchBox() {
        wait.until(ExpectedConditions.elementToBeClickable(searchBox)).click();
    }

    public void dismissSignInPopupIfPresent() {
        try {
            pressEnterKey(); // dismiss pop-up with ENTER
        } catch (Exception e) {
            System.out.println("No sign-in popup detected.");
        } finally {
            wait.withTimeout(Duration.ofSeconds(15));
        }
    }

    private void pressEnterKey() {
        if (driver instanceof PressesKey) {
            ((PressesKey) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        }
    }

    public void enterSearchText(String text) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputField)).sendKeys(text);
        pressEnterKey(); // triggers search
    }

    public void tapOnDirectionsButton() {
        List<String> keywords = Arrays.asList(
            "Directions", "Itinéraire", "Indicazioni", "Cómo llegar",
            "Wegbeschreibung", "Indicaciones", "길찾기", "路线",
            "ルート", "Routebeschrijving", "Direções", "Indicações",
            "Yön tarifi", "الاتجاهات", "Направления", "Indicii de direcție"
        );

        for (String key : keywords) {
            List<WebElement> matches = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
                By.xpath("//android.widget.Button[contains(@content-desc, '" + key + "')]")
            ));

            if (!matches.isEmpty()) {
                WebElement button = wait.until(ExpectedConditions.elementToBeClickable(matches.get(0)));
                button.click();
                System.out.println("✅ 'Directions' button clicked using keyword: " + key);
                return;
            }
        }

        throw new RuntimeException("❌ 'Directions' button not found in supported languages.");
    }

    private String getAndroidVersion() {
        return driver.getCapabilities().getCapability("platformVersion").toString();
    }
    public void changeStartingPoint(String startPoint) {
        try {
            String androidVersion = getAndroidVersion(); // ex: "14", "13.0", "12"
            int majorVersion = Integer.parseInt(androidVersion.split("\\.")[0]);

            if (majorVersion == 14 || majorVersion == 11) {
                // Android 14 & 11
                wait.until(ExpectedConditions.elementToBeClickable(startPointEdit)).click();
            } else if (Arrays.asList(10, 12, 13, 15, 16).contains(majorVersion)) {
                // Android 10, 12, 13, 15, 16 : accessibilityId
                By byAccId = AppiumBy.accessibilityId("Choose start location");
                wait.until(ExpectedConditions.elementToBeClickable(byAccId)).click();
            } else {
                throw new RuntimeException("Android version not supported: " + androidVersion);
            }

            // Entering the starting point
            WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(searchInputField));
            input.clear();
            input.sendKeys(startPoint);
            pressEnterKey();

        } catch (Exception e) {
            throw new RuntimeException("Unable to click 'Choose start location'. Check the selectors for Android." + getAndroidVersion() + " : " + e.getMessage());
        }
    }

    public boolean isMapVisible() {
        try {
        	return wait.until(ExpectedConditions.visibilityOfElementLocated(mapView)).isDisplayed();
        } catch (Exception e) {
            try {
                // other indicator
            	return wait.until(ExpectedConditions.visibilityOfElementLocated(mapCompass)).isDisplayed();
            } catch (Exception ex) {
                return false;
            }
        }
    }
    public void captureMapScreenshotIfVisible(AppiumDriver driver) {

        try {
        	 wait.until(ExpectedConditions.visibilityOfElementLocated(
        	            By.xpath("//android.widget.TextView[contains(@text,'min') or contains(@text,'km')]")
        	        ));
            WebElement map = wait.until(ExpectedConditions.visibilityOfElementLocated(mapView));
            if (map.isDisplayed()) {
                System.out.println("Card detected, capture in progress...");
                File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                String date = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                Path destination = Path.of("screenshots", "SearchAndNavigateTest", "map_" + date + ".png");
                Files.createDirectories(destination.getParent());
                Files.copy(screenshot.toPath(), destination);
                System.out.println("Screenshot saved here:" + destination.toString());
            }
        } catch (Exception e) {
            System.out.println("Map not visible or error during capture.");
        }
    }
}