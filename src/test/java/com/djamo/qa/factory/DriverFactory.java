package com.djamo.qa.factory;

import java.io.FileReader;
import java.net.URL;
import java.net.URI;

import com.djamo.qa.utils.AppiumServerManager;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

public class DriverFactory {

    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();

    public static AppiumDriver getDriver() {
        return driver.get();
    }

    public static void initDriver(String platform, String profile) {
        try {
            String path = "src/test/resources/capabilities/" + profile + ".json";
            JsonObject json = JsonParser.parseReader(new FileReader(path)).getAsJsonObject();

            boolean autoStartAppium = Boolean.parseBoolean(System.getProperty("autoStartAppium", "false"));

            URL serverUrl;
            if (autoStartAppium) {
                AppiumServerManager.startServer();
                serverUrl = URI.create(AppiumServerManager.getServiceUrl()).toURL();
            } else {
                String serverURL = json.has("serverURL") ? json.get("serverURL").getAsString() : "http://127.0.0.1:4723";
                serverUrl = URI.create(serverURL).toURL();
            }

            if (platform.equalsIgnoreCase("android")) {
                UiAutomator2Options options = new UiAutomator2Options();

                // Gestion du deviceName (ligne de commande ou JSON)
                String deviceName = System.getProperty("deviceName",
                        json.has("deviceName") ? json.get("deviceName").getAsString() : null);
                if (deviceName != null) {
                    options.setDeviceName(deviceName);
                }

                // Application des autres capabilities
                json.entrySet().forEach(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue().getAsString();
                    if (!key.equals("deviceName") && !key.equals("serverURL")) {
                        options.setCapability(key, value);
                    }
                });

                driver.set(new AndroidDriver(serverUrl, options));

            } else if (platform.equalsIgnoreCase("ios")) {
                XCUITestOptions options = new XCUITestOptions();

                String deviceName = System.getProperty("deviceName",
                        json.has("deviceName") ? json.get("deviceName").getAsString() : null);
                if (deviceName != null) {
                    options.setDeviceName(deviceName);
                }

                json.entrySet().forEach(entry -> {
                    String key = entry.getKey();
                    String value = entry.getValue().getAsString();
                    if (!key.equals("deviceName") && !key.equals("serverURL")) {
                        options.setCapability(key, value);
                    }
                });

                driver.set(new IOSDriver(serverUrl, options));

            } else {
                throw new IllegalArgumentException("Unsupported platform: " + platform);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error initializing driver: " + e.getMessage(), e);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }

        if (Boolean.parseBoolean(System.getProperty("autoStartAppium", "false"))) {
            AppiumServerManager.stopServer();
        }
    }
}