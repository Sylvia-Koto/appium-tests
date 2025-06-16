package com.djamo.qa.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class AppiumServerManager {

	private static AppiumDriverLocalService service;

    public static void startServer() {
        if (service != null && service.isRunning()) {
            return;
        }

        String appiumMainJS = getAppiumMainJSPath();

        AppiumServiceBuilder builder = new AppiumServiceBuilder()
                .withAppiumJS(new File(appiumMainJS))
                .usingAnyFreePort();

        Map<String, String> environment = new HashMap<>(System.getenv());
        builder.withEnvironment(environment);

        service = AppiumDriverLocalService.buildService(builder);
        service.start();
        System.out.println("Appium server started at: " + service.getUrl());
    }

    public static void stopServer() {
        if (service != null && service.isRunning()) {
            service.stop();
            System.out.println("Appium server stopped.");
        }
    }

    public static String getServiceUrl() {
        if (service != null && service.isRunning()) {
            return service.getUrl().toString();
        }
        return null;
    }

    private static String getAppiumMainJSPath() {
        String envPath = System.getenv("APPIUM_MAIN_JS");
        System.out.println("🔍 Valeur APPIUM_MAIN_JS dans Java : " + System.getenv("APPIUM_MAIN_JS"));
        if (envPath != null && !envPath.isEmpty()) {
            File file = new File(envPath);
            if (file.exists()) return envPath;
            throw new RuntimeException("The APPIUM_MAIN_JS variable is defined but the file does not exist: " + envPath);
        }

        String os = System.getProperty("os.name").toLowerCase();
        String[] possiblePaths;

        if (os.contains("mac")) {
            possiblePaths = new String[] {
                    "/opt/homebrew/lib/node_modules/appium/build/lib/main.js",
                    "/usr/local/lib/node_modules/appium/build/lib/main.js"
            };
        } else if (os.contains("win")) {
            throw new RuntimeException("On Windows, please set the APPIUM_MAIN_JS environment variable with the full path to main.js");
        } else if (os.contains("nux") || os.contains("nix")) {
            possiblePaths = new String[] {
                    "/usr/lib/node_modules/appium/build/lib/main.js",
                    "/usr/local/lib/node_modules/appium/build/lib/main.js"
            };
        } else {
            throw new RuntimeException("Unsupported operating system: " + os);
        }

        for (String path : possiblePaths) {
            File file = new File(path);
            if (file.exists()) return path;
        }

        throw new RuntimeException("Appium main.js not found. Please set the APPIUM_MAIN_JS environment variable.");
    }

}
