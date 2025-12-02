package com.appium.base;

import com.appium.driver.DriverManager;
import com.appium.reports.ExtentReportManager;
import com.appium.utils.ScreenshotUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

/**
 * BaseTest - Base class for all test classes
 * Handles driver initialization, teardown, and reporting
 */
public class BaseTest {

    protected final Logger logger = LogManager.getLogger(this.getClass());

    /**
     * Suite level setup - Initialize reports
     */
    @BeforeSuite
    public void suiteSetup() {
        logger.info("===== Test Suite Started =====");
        ExtentReportManager.initReports();
    }

    /**
     * Test level setup - Initialize driver and create test in report
     * 
     * @param method test method
     */
    @BeforeMethod
    public void testSetup(Method method) {
        String testName = method.getName();
        String testDescription = method.getAnnotation(Test.class) != null
                ? method.getAnnotation(Test.class).description()
                : "";

        logger.info("===== Starting Test: {} =====", testName);

        // Create test in report
        ExtentReportManager.createTest(testName, testDescription);
        ExtentReportManager.logInfo("Test execution started: " + testName);

        // Initialize driver
        try {
            DriverManager.initializeDriver();
            ExtentReportManager.logInfo("Driver initialized successfully");
            logger.info("Driver initialized for test: {}", testName);
        } catch (Exception e) {
            logger.error("Failed to initialize driver: {}", e.getMessage(), e);
            ExtentReportManager.logFail("Failed to initialize driver: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Test level teardown - Quit driver and update report
     * 
     * @param result test result
     */
    @AfterMethod
    public void testTeardown(ITestResult result) {
        String testName = result.getMethod().getMethodName();

        try {
            // Handle test result
            if (result.getStatus() == ITestResult.SUCCESS) {
                logger.info("Test PASSED: {}", testName);
                ExtentReportManager.logPass("Test passed successfully");
            } else if (result.getStatus() == ITestResult.FAILURE) {
                logger.error("Test FAILED: {}", testName);
                ExtentReportManager.logFail("Test failed: " + result.getThrowable().getMessage());

                // Capture screenshot on failure
                if (DriverManager.isDriverInitialized()) {
                    String screenshotPath = ScreenshotUtils.captureScreenshot(testName + "_FAILED");
                    if (screenshotPath != null) {
                        ExtentReportManager.attachScreenshot(screenshotPath, "Failure Screenshot");
                        logger.info("Failure screenshot captured: {}", screenshotPath);
                    }
                }
            } else if (result.getStatus() == ITestResult.SKIP) {
                logger.warn("Test SKIPPED: {}", testName);
                ExtentReportManager.logSkip("Test skipped: " + result.getThrowable().getMessage());
            }
        } catch (Exception e) {
            logger.error("Error in test teardown: {}", e.getMessage(), e);
        } finally {
            // Quit driver
            try {
                DriverManager.quitDriver();
                logger.info("Driver quit successfully for test: {}", testName);
            } catch (Exception e) {
                logger.error("Error while quitting driver: {}", e.getMessage(), e);
            }

            // Remove test from ThreadLocal
            ExtentReportManager.removeTest();
        }

        logger.info("===== Finished Test: {} =====", testName);
    }

    /**
     * Suite level teardown - Flush reports
     */
    @AfterSuite
    public void suiteTeardown() {
        logger.info("===== Test Suite Finished =====");
        ExtentReportManager.flushReports();
    }
}
