# Appium Android Automation Framework

A comprehensive Appium automation framework for Android applications using Java, Maven, TestNG, and Page Object Model (POM) design pattern.

## 📋 Table of Contents
- [Features](#features)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Configuration](#configuration)
- [Running Tests](#running-tests)
- [Reporting](#reporting)
- [Best Practices](#best-practices)
- [Troubleshooting](#troubleshooting)

## ✨ Features

- **Page Object Model (POM)**: Clean separation of test logic and page elements
- **Thread-Safe Driver Management**: Support for parallel test execution
- **Comprehensive Utilities**: Wait, Gesture, and Screenshot utilities
- **Extent Reports**: Rich HTML reports with screenshots
- **Log4j2 Logging**: Detailed logging for debugging
- **TestNG Integration**: Powerful test execution and management
- **W3C Actions API**: Modern gesture support for Appium 2.x
- **Configuration Management**: Centralized configuration via properties file

## 🔧 Prerequisites

Before running the framework, ensure you have the following installed:

### Required Software
1. **Java JDK** (11 or higher)
   ```bash
   java -version
   ```

2. **Maven** (3.6 or higher)
   ```bash
   mvn -version
   ```

3. **Node.js** (14 or higher)
   ```bash
   node -v
   npm -v
   ```

4. **Appium Server** (2.x recommended)
   ```bash
   npm install -g appium
   appium -v
   ```

5. **Appium UiAutomator2 Driver**
   ```bash
   appium driver install uiautomator2
   ```

6. **Android SDK**
   - Install Android Studio or Android SDK command-line tools
   - Set `ANDROID_HOME` environment variable
   ```bash
   export ANDROID_HOME=/path/to/android/sdk
   export PATH=$PATH:$ANDROID_HOME/platform-tools:$ANDROID_HOME/tools
   ```

7. **Android Emulator or Physical Device**
   - Configure an Android emulator via Android Studio
   - Or connect a physical Android device with USB debugging enabled

## 📁 Project Structure

```
Appium/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/appium/
│   │   │       ├── constants/          # Framework constants
│   │   │       │   └── FrameworkConstants.java
│   │   │       ├── config/             # Configuration management
│   │   │       │   └── ConfigReader.java
│   │   │       ├── driver/             # Driver management
│   │   │       │   └── DriverManager.java
│   │   │       ├── pages/              # Page objects
│   │   │       │   ├── BasePage.java
│   │   │       │   └── CalculatorPage.java
│   │   │       ├── utils/              # Utility classes
│   │   │       │   ├── WaitUtils.java
│   │   │       │   ├── GestureUtils.java
│   │   │       │   └── ScreenshotUtils.java
│   │   │       └── reports/            # Reporting
│   │   │           └── ExtentReportManager.java
│   │   └── resources/
│   │       └── log4j2.xml              # Logging configuration
│   └── test/
│       ├── java/
│       │   └── com/appium/
│       │       ├── base/               # Base test class
│       │       │   └── BaseTest.java
│       │       └── tests/              # Test classes
│       │           └── CalculatorTests.java
│       └── resources/
│           └── testng.xml              # TestNG suite configuration
├── config/
│   └── config.properties               # Application configuration
├── reports/                            # Extent HTML reports
├── screenshots/                        # Test screenshots
├── logs/                               # Log files
├── pom.xml                             # Maven dependencies
└── README.md                           # This file
```

## 🚀 Installation

1. **Clone or navigate to the project directory**
   ```bash
   cd /Users/maginthangr/Documents/Projects/Appium
   ```

2. **Install Maven dependencies**
   ```bash
   mvn clean install -DskipTests
   ```

3. **Start Appium Server**
   ```bash
   appium
   ```
   Or start Appium in the background:
   ```bash
   appium &
   ```

4. **Start Android Emulator or connect device**
   ```bash
   # List available emulators
   emulator -list-avds
   
   # Start an emulator
   emulator -avd <emulator_name>
   
   # Verify device is connected
   adb devices
   ```

## ⚙️ Configuration

Edit `config/config.properties` to match your setup:

### Appium Server Configuration
```properties
appium.server.url=http://127.0.0.1:4723
appium.server.port=4723
```

### Device Configuration
```properties
platform.name=Android
device.name=Android Emulator
platform.version=13.0
automation.name=UiAutomator2
```

### App Configuration

**Option 1: Using APK file**
```properties
app.path=/path/to/your/app.apk
```

**Option 2: Using installed app (default - Calculator)**
```properties
app.package=com.android.calculator2
app.activity=com.android.calculator2.Calculator
```

### Get App Package and Activity
To find package and activity for any app:
```bash
# For currently running app
adb shell dumpsys window | grep -E 'mCurrentFocus'

# List all packages
adb shell pm list packages

# Get activity for a package
adb shell dumpsys package <package_name> | grep -A 1 "android.intent.action.MAIN"
```

## 🏃 Running Tests

### Run all tests
```bash
mvn clean test
```

### Run specific test class
```bash
mvn clean test -Dtest=CalculatorTests
```

### Run specific test method
```bash
mvn clean test -Dtest=CalculatorTests#testAddition
```

### Run tests with TestNG XML
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng.xml
```

### Compile without running tests
```bash
mvn clean compile
```

## 📊 Reporting

### Extent Reports
After test execution, HTML reports are generated in the `reports/` directory with timestamp:
```
reports/AppiumTestReport_YYYYMMDD_HHMMSS.html
```

Open the report in a browser to view:
- Test execution summary
- Pass/Fail status
- Screenshots for failed tests
- Execution timeline
- System information

### Logs
Detailed logs are available in:
```
logs/automation.log
```

### Screenshots
Screenshots (especially for failed tests) are saved in:
```
screenshots/
```

## 🎯 Best Practices

### 1. Page Objects
- Keep page objects focused on a single page/screen
- Use meaningful element names
- Implement page verification methods
- Return page objects for method chaining

### 2. Test Classes
- Extend `BaseTest` for automatic setup/teardown
- Use descriptive test names and descriptions
- Keep tests independent and atomic
- Use appropriate TestNG annotations

### 3. Waits
- Prefer explicit waits over hard waits
- Use `WaitUtils` for consistent wait strategies
- Avoid `Thread.sleep()` unless absolutely necessary

### 4. Logging
- Use appropriate log levels (debug, info, warn, error)
- Log important actions and verifications
- Extent reports automatically capture logs

### 5. Configuration
- Never hardcode values in tests
- Use `config.properties` for environment-specific settings
- Use `FrameworkConstants` for framework-wide constants

## 🔍 Troubleshooting

### Issue: Driver initialization fails
**Solution**: 
- Verify Appium server is running: `appium`
- Check device is connected: `adb devices`
- Verify `config.properties` has correct device details

### Issue: Element not found
**Solution**:
- Use Appium Inspector to verify element locators
- Increase wait timeouts in `config.properties`
- Check if app package/activity is correct

### Issue: Tests fail with "Session not created"
**Solution**:
- Ensure only one Appium server instance is running
- Verify Android SDK is properly configured
- Check `ANDROID_HOME` environment variable

### Issue: Maven dependencies not downloading
**Solution**:
```bash
mvn clean install -U
```

### Issue: App doesn't launch
**Solution**:
- Verify app is installed: `adb shell pm list packages | grep <package_name>`
- Check app permissions are granted
- Ensure `autoGrantPermissions=true` in config

## 📝 Creating New Tests

### 1. Create a new Page Object
```java
public class YourPage extends BasePage {
    @AndroidFindBy(id = "element_id")
    private WebElement element;
    
    public void clickElement() {
        click(element);
    }
    
    @Override
    public String getPageTitle() {
        return "Your Page";
    }
    
    @Override
    public boolean isPageLoaded() {
        return isDisplayed(element);
    }
}
```

### 2. Create a new Test Class
```java
public class YourTests extends BaseTest {
    @Test(description = "Test description")
    public void testSomething() {
        YourPage page = new YourPage();
        // Your test logic
        Assert.assertTrue(page.isPageLoaded());
    }
}
```

### 3. Add test to TestNG XML
```xml
<test name="Your Tests">
    <classes>
        <class name="com.appium.tests.YourTests"/>
    </classes>
</test>
```

## 🤝 Contributing

To extend this framework:
1. Follow the existing code structure
2. Maintain consistent naming conventions
3. Add appropriate logging and reporting
4. Update documentation for new features

## 📄 License

This framework is provided as-is for automation testing purposes.

---

**Happy Testing! 🚀**
