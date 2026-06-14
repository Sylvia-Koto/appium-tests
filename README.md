# Google Maps Test Automation with Appium

End-to-end mobile test suite for the Google Maps Android app, built with Appium 2, TestNG, and Java.

## Test scenario

1. Open Google Maps on Android
2. Search for a city (e.g. "Abidjan")
3. Open the Directions flow
4. Change the start point (e.g. "Dakar")
5. Assert the map loads correctly and capture a screenshot

Test data is driven from `src/test/resources/data/search_route.json`.

## Tech stack

| Tool | Role |
|---|---|
| Java 21 + Maven | Language and build |
| Appium 2 + UiAutomator2 | Android automation |
| TestNG | Test runner, DataProviders, retry |
| ExtentReports | HTML test report |
| BrowserStack | Cloud device execution (optional) |

## Project structure

```
appium-tests/
├── pom.xml
├── testng.xml
├── src/test/java/com/sylvia/qa/
│   ├── base/         BaseTest.java
│   ├── factory/      DriverFactory.java
│   ├── pages/        SearchAndNavigatePage.java
│   ├── tests/        SearchAndNavigateTest.java
│   └── utils/        AppiumServerManager, ExtentReports, Listeners, Retry
└── src/test/resources/
    ├── capabilities/ *.json (local + BrowserStack profiles)
    └── data/         search_route.json
```

## Prerequisites

- Java JDK 17+, Maven, Node.js
- Appium 2: `npm install -g appium`
- Android Studio with an AVD (Pixel 7 / API 34 recommended)
- Environment variables:

```bash
export JAVA_HOME=$(/usr/libexec/java_home)
export ANDROID_HOME=$HOME/Library/Android/sdk
export PATH=$ANDROID_HOME/platform-tools:$PATH

# Only needed for auto-start mode:
export APPIUM_MAIN_JS=/opt/homebrew/lib/node_modules/appium/build/lib/main.js
```

Run `appium-doctor` to verify your setup.

## Running the tests

**Option 1 — Appium already running:**
```bash
appium                          # start server in a separate terminal
mvn clean test
```

**Option 2 — Auto-start Appium from Java:**
```bash
mvn clean test -DautoStartAppium=true
```

The emulator must be running before either option.

## Results

- HTML report: `reports/index.html`
- Screenshots on failure: `screenshots/`
- Screenshots on success: `screenshots/SearchAndNavigateTest/`

## CI/CD

### Local (Jenkins)

```bash
emulator -avd Pixel_7_API_34 &
adb wait-for-device
mvn clean test -DautoStartAppium=true
```

Archive `reports/` and `screenshots/` as build artifacts.

### Cloud (BrowserStack + GitHub Actions)

```yaml
- name: Run Appium tests on BrowserStack
  env:
    BROWSERSTACK_USERNAME: ${{ secrets.BROWSERSTACK_USERNAME }}
    BROWSERSTACK_ACCESS_KEY: ${{ secrets.BROWSERSTACK_ACCESS_KEY }}
  run: mvn clean test -Dplatform=browserstack -DsuiteXmlFile=testng.xml
```

Use the capability profiles in `src/test/resources/capabilities/bs_android_google_maps.json`.

## Known limitations

- Android 10 and 11 may have UI variations that cause flaky selectors
- Only the car route scenario is covered (no transit, cycling, etc.)
- CI/CD is documented but not yet wired to this repository

---

Author: Sylvia N'Guessan — QA Automation

> "Build for reuse, test for resilience."
