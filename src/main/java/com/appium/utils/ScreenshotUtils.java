package com.appium.utils;

import com.appium.constants.FrameworkConstants;
import com.appium.driver.DriverManager;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;

/**
 * ScreenshotUtils - Utilities for capturing and managing screenshots
 * Provides methods to capture screenshots and save them with timestamps
 */
public final class ScreenshotUtils {

    private static final Logger logger = LogManager.getLogger(ScreenshotUtils.class);

    // Private constructor to prevent instantiation
    private ScreenshotUtils() {
        throw new UnsupportedOperationException("ScreenshotUtils is a utility class and cannot be instantiated");
    }

    /**
     * Capture screenshot and save to default location
     * 
     * @param screenshotName name for the screenshot file
     * @return absolute path of saved screenshot
     */
    public static String captureScreenshot(String screenshotName) {
        String timestamp = FrameworkConstants.getTimestamp();
        String fileName = screenshotName + "_" + timestamp + FrameworkConstants.SCREENSHOT_EXTENSION;
        String filePath = FrameworkConstants.SCREENSHOTS_PATH + fileName;

        return captureScreenshot(screenshotName, filePath);
    }

    /**
     * Capture screenshot and save to specified path
     * 
     * @param screenshotName  name for the screenshot
     * @param destinationPath full path where screenshot should be saved
     * @return absolute path of saved screenshot
     */
    public static String captureScreenshot(String screenshotName, String destinationPath) {
        try {
            // Create screenshots directory if it doesn't exist
            File screenshotDir = new File(FrameworkConstants.SCREENSHOTS_PATH);
            if (!screenshotDir.exists()) {
                screenshotDir.mkdirs();
                logger.debug("Created screenshots directory: {}", FrameworkConstants.SCREENSHOTS_PATH);
            }

            // Capture screenshot
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverManager.getDriver();
            File source = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destination = new File(destinationPath);

            // Copy screenshot to destination
            FileUtils.copyFile(source, destination);
            logger.info("Screenshot captured successfully: {}", destinationPath);

            return destination.getAbsolutePath();

        } catch (IOException e) {
            logger.error("Failed to capture screenshot '{}': {}", screenshotName, e.getMessage(), e);
            return null;
        } catch (Exception e) {
            logger.error("Unexpected error while capturing screenshot: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Capture screenshot as Base64 string (useful for reports)
     * 
     * @return Base64 encoded screenshot string
     */
    public static String captureScreenshotAsBase64() {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverManager.getDriver();
            String base64Screenshot = takesScreenshot.getScreenshotAs(OutputType.BASE64);
            logger.debug("Screenshot captured as Base64 string");
            return base64Screenshot;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as Base64: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Capture screenshot and return as File object
     * 
     * @return File object of the screenshot
     */
    public static File captureScreenshotAsFile() {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) DriverManager.getDriver();
            File screenshot = takesScreenshot.getScreenshotAs(OutputType.FILE);
            logger.debug("Screenshot captured as File object");
            return screenshot;
        } catch (Exception e) {
            logger.error("Failed to capture screenshot as File: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Delete all screenshots from the screenshots directory
     */
    public static void deleteAllScreenshots() {
        try {
            File screenshotDir = new File(FrameworkConstants.SCREENSHOTS_PATH);
            if (screenshotDir.exists() && screenshotDir.isDirectory()) {
                FileUtils.cleanDirectory(screenshotDir);
                logger.info("All screenshots deleted from: {}", FrameworkConstants.SCREENSHOTS_PATH);
            }
        } catch (IOException e) {
            logger.error("Failed to delete screenshots: {}", e.getMessage(), e);
        }
    }

    /**
     * Delete specific screenshot file
     * 
     * @param screenshotPath path to the screenshot file
     * @return true if deleted successfully, false otherwise
     */
    public static boolean deleteScreenshot(String screenshotPath) {
        try {
            File screenshot = new File(screenshotPath);
            if (screenshot.exists()) {
                boolean deleted = screenshot.delete();
                if (deleted) {
                    logger.info("Screenshot deleted: {}", screenshotPath);
                }
                return deleted;
            }
            logger.warn("Screenshot file not found: {}", screenshotPath);
            return false;
        } catch (Exception e) {
            logger.error("Failed to delete screenshot: {}", e.getMessage(), e);
            return false;
        }
    }
}
