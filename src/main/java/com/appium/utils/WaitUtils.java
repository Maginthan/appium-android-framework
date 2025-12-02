package com.appium.utils;

import com.appium.driver.DriverManager;
import io.appium.java_client.AppiumDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Function;

/**
 * WaitUtils - Centralized wait utilities for mobile elements
 * Provides explicit and fluent wait implementations
 */
public final class WaitUtils {

    private static final Logger logger = LogManager.getLogger(WaitUtils.class);
    private static final int DEFAULT_TIMEOUT = 20;
    private static final int DEFAULT_POLLING = 2;

    // Private constructor to prevent instantiation
    private WaitUtils() {
        throw new UnsupportedOperationException("WaitUtils is a utility class and cannot be instantiated");
    }

    /**
     * Get WebDriverWait instance with default timeout
     * 
     * @return WebDriverWait instance
     */
    private static WebDriverWait getWait() {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    /**
     * Get WebDriverWait instance with custom timeout
     * 
     * @param timeoutInSeconds custom timeout
     * @return WebDriverWait instance
     */
    private static WebDriverWait getWait(int timeoutInSeconds) {
        return new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeoutInSeconds));
    }

    /**
     * Get FluentWait instance with default timeout and polling
     * 
     * @return FluentWait instance
     */
    private static FluentWait<AppiumDriver> getFluentWait() {
        return new FluentWait<>(DriverManager.getDriver())
                .withTimeout(Duration.ofSeconds(DEFAULT_TIMEOUT))
                .pollingEvery(Duration.ofSeconds(DEFAULT_POLLING))
                .ignoring(NoSuchElementException.class);
    }

    /**
     * Wait for element to be visible
     * 
     * @param element WebElement to wait for
     * @return WebElement once visible
     */
    public static WebElement waitForElementToBeVisible(WebElement element) {
        try {
            logger.debug("Waiting for element to be visible");
            return getWait().until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error("Element not visible within timeout: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Wait for element to be visible with custom timeout
     * 
     * @param element          WebElement to wait for
     * @param timeoutInSeconds custom timeout
     * @return WebElement once visible
     */
    public static WebElement waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        try {
            logger.debug("Waiting for element to be visible with timeout: {} seconds", timeoutInSeconds);
            return getWait(timeoutInSeconds).until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            logger.error("Element not visible within {} seconds: {}", timeoutInSeconds, e.getMessage());
            throw e;
        }
    }

    /**
     * Wait for element to be clickable
     * 
     * @param element WebElement to wait for
     * @return WebElement once clickable
     */
    public static WebElement waitForElementToBeClickable(WebElement element) {
        try {
            logger.debug("Waiting for element to be clickable");
            return getWait().until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error("Element not clickable within timeout: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Wait for element to be clickable with custom timeout
     * 
     * @param element          WebElement to wait for
     * @param timeoutInSeconds custom timeout
     * @return WebElement once clickable
     */
    public static WebElement waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        try {
            logger.debug("Waiting for element to be clickable with timeout: {} seconds", timeoutInSeconds);
            return getWait(timeoutInSeconds).until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            logger.error("Element not clickable within {} seconds: {}", timeoutInSeconds, e.getMessage());
            throw e;
        }
    }

    /**
     * Wait for element to be invisible
     * 
     * @param element WebElement to wait for
     * @return true if element becomes invisible
     */
    public static boolean waitForElementToBeInvisible(WebElement element) {
        try {
            logger.debug("Waiting for element to be invisible");
            return getWait().until(ExpectedConditions.invisibilityOf(element));
        } catch (Exception e) {
            logger.error("Element still visible after timeout: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Wait for element text to be present
     * 
     * @param element WebElement to check
     * @param text    expected text
     * @return true if text is present
     */
    public static boolean waitForTextToBePresentInElement(WebElement element, String text) {
        try {
            logger.debug("Waiting for text '{}' to be present in element", text);
            return getWait().until(ExpectedConditions.textToBePresentInElement(element, text));
        } catch (Exception e) {
            logger.error("Text '{}' not present in element within timeout: {}", text, e.getMessage());
            return false;
        }
    }

    /**
     * Fluent wait with custom condition
     * 
     * @param condition custom wait condition
     * @param <T>       return type
     * @return result of condition
     */
    public static <T> T fluentWait(Function<AppiumDriver, T> condition) {
        try {
            logger.debug("Executing fluent wait with custom condition");
            return getFluentWait().until(condition);
        } catch (Exception e) {
            logger.error("Fluent wait condition not met: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Wait for a specific duration (use sparingly)
     * 
     * @param seconds duration in seconds
     */
    public static void hardWait(int seconds) {
        try {
            logger.warn("Using hard wait for {} seconds - consider using explicit waits instead", seconds);
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            logger.error("Hard wait interrupted: {}", e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
