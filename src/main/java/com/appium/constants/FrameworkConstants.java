package com.appium.constants;

import java.io.File;

/**
 * Framework Constants - Central repository for all framework-wide constants
 * This class cannot be instantiated or extended
 */
public final class FrameworkConstants {

    // Private constructor to prevent instantiation
    private FrameworkConstants() {
        throw new UnsupportedOperationException("FrameworkConstants is a utility class and cannot be instantiated");
    }

    // Project Paths
    private static final String PROJECT_PATH = System.getProperty("user.dir");
    public static final String CONFIG_FILE_PATH = PROJECT_PATH + File.separator + "config" + File.separator
            + "config.properties";
    public static final String REPORTS_PATH = PROJECT_PATH + File.separator + "reports" + File.separator;
    public static final String SCREENSHOTS_PATH = PROJECT_PATH + File.separator + "screenshots" + File.separator;
    public static final String TEST_DATA_PATH = PROJECT_PATH + File.separator + "testdata" + File.separator;

    // Timeout Constants (in seconds)
    public static final int IMPLICIT_WAIT = 10;
    public static final int EXPLICIT_WAIT = 20;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    public static final int FLUENT_WAIT_TIMEOUT = 30;
    public static final int FLUENT_WAIT_POLLING = 2;

    // Platform Constants
    public static final String PLATFORM_ANDROID = "Android";
    public static final String PLATFORM_IOS = "iOS";
    public static final String AUTOMATION_NAME_ANDROID = "UiAutomator2";
    public static final String AUTOMATION_NAME_IOS = "XCUITest";

    // Report Constants
    public static final String EXTENT_REPORT_NAME = "AppiumTestReport";
    public static final String EXTENT_REPORT_TITLE = "Appium Android Automation Report";
    public static final String EXTENT_DOCUMENT_TITLE = "Test Execution Report";

    // Screenshot Constants
    public static final String SCREENSHOT_PREFIX = "Screenshot_";
    public static final String SCREENSHOT_EXTENSION = ".png";

    // Test Data Constants
    public static final String EXCEL_EXTENSION = ".xlsx";
    public static final String JSON_EXTENSION = ".json";

    // Logging Constants
    public static final String LOG_FILE_PATH = PROJECT_PATH + File.separator + "logs" + File.separator;
    public static final String LOG_FILE_NAME = "automation.log";

    /**
     * Get the current timestamp in the format: yyyyMMdd_HHmmss
     * 
     * @return formatted timestamp string
     */
    public static String getTimestamp() {
        return new java.text.SimpleDateFormat("yyyyMMdd_HHmmss").format(new java.util.Date());
    }

    /**
     * Get the current date in the format: yyyy-MM-dd
     * 
     * @return formatted date string
     */
    public static String getCurrentDate() {
        return new java.text.SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
    }
}
