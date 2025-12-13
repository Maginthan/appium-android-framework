package com.appium.pages;

import com.appium.driver.DriverManager;
import com.appium.utils.GestureUtils;
import com.appium.utils.ScreenshotUtils;
import com.appium.utils.WaitUtils;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

/**
 * BasePage - Base class for all page objects
 * Provides common methods for mobile interactions and element operations
 */
public abstract class BasePage {

    protected final Logger logger = LogManager.getLogger(this.getClass());
    protected AppiumDriver driver;

    /**
     * Constructor - Initialize driver and page elements
     */
    public BasePage() {
        this.driver = DriverManager.getDriver();
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
        logger.debug("Initialized page: {}", this.getClass().getSimpleName());
    }

    /**
     * Click on element after waiting for it to be clickable
     * 
     * @param element element to click
     */
    protected void click(WebElement element) {
        try {
            WaitUtils.waitForElementToBeClickable(element);
            element.click();
            logger.info("Clicked on element: {}", element);
        } catch (Exception e) {
            logger.error("Failed to click element: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Send keys to element after waiting for it to be visible
     * 
     * @param element element to send keys to
     * @param text    text to send
     */
    protected void sendKeys(WebElement element, String text) {
        try {
            WaitUtils.waitForElementToBeVisible(element);
            element.clear();
            element.sendKeys(text);
            logger.info("Sent text '{}' to element", text);
        } catch (Exception e) {
            logger.error("Failed to send keys to element: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get text from element after waiting for it to be visible
     * 
     * @param element element to get text from
     * @return element text
     */
    protected String getText(WebElement element) {
        try {
            WaitUtils.waitForElementToBeVisible(element);
            String text = element.getText();
            logger.info("Retrieved text from element: {}", text);
            return text;
        } catch (Exception e) {
            logger.error("Failed to get text from element: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Check if element is displayed
     * 
     * @param element element to check
     * @return true if displayed, false otherwise
     */
    protected boolean isDisplayed(WebElement element) {
        try {
            boolean displayed = element.isDisplayed();
            logger.debug("Element displayed status: {}", displayed);
            return displayed;
        } catch (Exception e) {
            logger.debug("Element not displayed: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Check if element is enabled
     * 
     * @param element element to check
     * @return true if enabled, false otherwise
     */
    protected boolean isEnabled(WebElement element) {
        try {
            boolean enabled = element.isEnabled();
            logger.debug("Element enabled status: {}", enabled);
            return enabled;
        } catch (Exception e) {
            logger.debug("Element not enabled: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Wait for element to be visible
     * 
     * @param element element to wait for
     * @return WebElement once visible
     */
    protected WebElement waitForVisibility(WebElement element) {
        return WaitUtils.waitForElementToBeVisible(element);
    }

    /**
     * Wait for element to be clickable
     * 
     * @param element element to wait for
     * @return WebElement once clickable
     */
    protected WebElement waitForClickability(WebElement element) {
        return WaitUtils.waitForElementToBeClickable(element);
    }

    /**
     * Swipe up on the screen
     * 
     * @param percentage percentage of screen to swipe
     */
    protected void swipeUp(double percentage) {
        GestureUtils.swipeUp(percentage);
    }

    /**
     * Swipe down on the screen
     * 
     * @param percentage percentage of screen to swipe
     */
    protected void swipeDown(double percentage) {
        GestureUtils.swipeDown(percentage);
    }

    /**
     * Swipe left on the screen
     * 
     * @param percentage percentage of screen to swipe
     */
    protected void swipeLeft(double percentage) {
        GestureUtils.swipeLeft(percentage);
    }

    /**
     * Swipe right on the screen
     * 
     * @param percentage percentage of screen to swipe
     */
    protected void swipeRight(double percentage) {
        GestureUtils.swipeRight(percentage);
    }

    /**
     * Scroll to element
     * 
     * @param element   element to scroll to
     * @param maxSwipes maximum swipes to attempt
     * @return true if element found, false otherwise
     */
    protected boolean scrollToElement(WebElement element, int maxSwipes) {
        return GestureUtils.scrollToElement(element, maxSwipes);
    }

    /**
     * Tap on element
     * 
     * @param element element to tap
     */
    protected void tap(WebElement element) {
        GestureUtils.tap(element);
    }

    /**
     * Long press on element
     * 
     * @param element           element to long press
     * @param durationInSeconds duration of long press
     */
    protected void longPress(WebElement element, int durationInSeconds) {
        GestureUtils.longPress(element, durationInSeconds);
    }

    /**
     * Capture screenshot
     * 
     * @param screenshotName name for the screenshot
     * @return path to saved screenshot
     */
    protected String captureScreenshot(String screenshotName) {
        return ScreenshotUtils.captureScreenshot(screenshotName);
    }

    /**
     * Get attribute value from element
     * 
     * @param element   element to get attribute from
     * @param attribute attribute name
     * @return attribute value
     */
    protected String getAttribute(WebElement element, String attribute) {
        try {
            WaitUtils.waitForElementToBeVisible(element);
            String value = element.getAttribute(attribute);
            logger.info("Retrieved attribute '{}' value: {}", attribute, value);
            return value;
        } catch (Exception e) {
            logger.error("Failed to get attribute '{}': {}", attribute, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Hide keyboard if visible (Android specific)
     */
    protected void hideKeyboard() {
        try {
            // Use HidesKeyboard interface for Appium 9.x
            if (driver instanceof io.appium.java_client.HidesKeyboard) {
                ((io.appium.java_client.HidesKeyboard) driver).hideKeyboard();
                logger.info("Keyboard hidden");
            }
        } catch (Exception e) {
            logger.debug("Keyboard not visible or failed to hide: {}", e.getMessage());
        }
    }

    /**
     * Navigate back
     */
    protected void navigateBack() {
        try {
            driver.navigate().back();
            logger.info("Navigated back");
        } catch (Exception e) {
            logger.error("Failed to navigate back: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Get page title or current activity
     * 
     * @return current page title
     */
    public abstract String getPageTitle();

    /**
     * Verify page is loaded
     * 
     * @return true if page is loaded, false otherwise
     */
    public abstract boolean isPageLoaded();
}
