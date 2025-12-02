package com.appium.utils;

import com.appium.driver.DriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;

import java.time.Duration;
import java.util.Collections;

/**
 * GestureUtils - Mobile gesture utilities for swipe, scroll, tap, and long
 * press
 * Uses W3C Actions API for Appium 2.x compatibility
 */
public final class GestureUtils {

    private static final Logger logger = LogManager.getLogger(GestureUtils.class);
    private static final int DEFAULT_SWIPE_DURATION = 1000; // milliseconds

    // Private constructor to prevent instantiation
    private GestureUtils() {
        throw new UnsupportedOperationException("GestureUtils is a utility class and cannot be instantiated");
    }

    /**
     * Get screen dimensions
     * 
     * @return Dimension object with screen width and height
     */
    private static Dimension getScreenSize() {
        return DriverManager.getDriver().manage().window().getSize();
    }

    /**
     * Swipe from one point to another
     * 
     * @param startX   start X coordinate
     * @param startY   start Y coordinate
     * @param endX     end X coordinate
     * @param endY     end Y coordinate
     * @param duration swipe duration in milliseconds
     */
    public static void swipe(int startX, int startY, int endX, int endY, int duration) {
        try {
            logger.info("Swiping from ({}, {}) to ({}, {}) with duration {} ms",
                    startX, startY, endX, endY, duration);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);

            swipe.addAction(finger.createPointerMove(Duration.ZERO,
                    PointerInput.Origin.viewport(), startX, startY));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(duration),
                    PointerInput.Origin.viewport(), endX, endY));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            DriverManager.getDriver().perform(Collections.singletonList(swipe));
            logger.debug("Swipe completed successfully");

        } catch (Exception e) {
            logger.error("Failed to perform swipe: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Swipe up on the screen
     * 
     * @param percentage percentage of screen to swipe (0.0 to 1.0)
     */
    public static void swipeUp(double percentage) {
        Dimension size = getScreenSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.8);
        int endY = (int) (size.height * (0.8 - percentage));

        logger.info("Swiping up {}% of screen", percentage * 100);
        swipe(startX, startY, startX, endY, DEFAULT_SWIPE_DURATION);
    }

    /**
     * Swipe down on the screen
     * 
     * @param percentage percentage of screen to swipe (0.0 to 1.0)
     */
    public static void swipeDown(double percentage) {
        Dimension size = getScreenSize();
        int startX = size.width / 2;
        int startY = (int) (size.height * 0.2);
        int endY = (int) (size.height * (0.2 + percentage));

        logger.info("Swiping down {}% of screen", percentage * 100);
        swipe(startX, startY, startX, endY, DEFAULT_SWIPE_DURATION);
    }

    /**
     * Swipe left on the screen
     * 
     * @param percentage percentage of screen to swipe (0.0 to 1.0)
     */
    public static void swipeLeft(double percentage) {
        Dimension size = getScreenSize();
        int startX = (int) (size.width * 0.8);
        int endX = (int) (size.width * (0.8 - percentage));
        int startY = size.height / 2;

        logger.info("Swiping left {}% of screen", percentage * 100);
        swipe(startX, startY, endX, startY, DEFAULT_SWIPE_DURATION);
    }

    /**
     * Swipe right on the screen
     * 
     * @param percentage percentage of screen to swipe (0.0 to 1.0)
     */
    public static void swipeRight(double percentage) {
        Dimension size = getScreenSize();
        int startX = (int) (size.width * 0.2);
        int endX = (int) (size.width * (0.2 + percentage));
        int startY = size.height / 2;

        logger.info("Swiping right {}% of screen", percentage * 100);
        swipe(startX, startY, endX, startY, DEFAULT_SWIPE_DURATION);
    }

    /**
     * Scroll to element using swipe gestures
     * 
     * @param element   target element to scroll to
     * @param maxSwipes maximum number of swipes to attempt
     * @return true if element is found, false otherwise
     */
    public static boolean scrollToElement(WebElement element, int maxSwipes) {
        logger.info("Scrolling to element with max {} swipes", maxSwipes);

        for (int i = 0; i < maxSwipes; i++) {
            try {
                if (element.isDisplayed()) {
                    logger.info("Element found after {} swipes", i);
                    return true;
                }
            } catch (Exception e) {
                logger.debug("Element not visible, continuing to scroll");
            }
            swipeUp(0.5);
        }

        logger.warn("Element not found after {} swipes", maxSwipes);
        return false;
    }

    /**
     * Tap on element
     * 
     * @param element element to tap
     */
    public static void tap(WebElement element) {
        try {
            Point location = element.getLocation();
            Dimension size = element.getSize();
            int centerX = location.getX() + (size.getWidth() / 2);
            int centerY = location.getY() + (size.getHeight() / 2);

            logger.info("Tapping element at ({}, {})", centerX, centerY);
            tapByCoordinates(centerX, centerY);

        } catch (Exception e) {
            logger.error("Failed to tap element: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Tap at specific coordinates
     * 
     * @param x X coordinate
     * @param y Y coordinate
     */
    public static void tapByCoordinates(int x, int y) {
        try {
            logger.info("Tapping at coordinates ({}, {})", x, y);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence tap = new Sequence(finger, 1);

            tap.addAction(finger.createPointerMove(Duration.ZERO,
                    PointerInput.Origin.viewport(), x, y));
            tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            DriverManager.getDriver().perform(Collections.singletonList(tap));
            logger.debug("Tap completed successfully");

        } catch (Exception e) {
            logger.error("Failed to tap at coordinates: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Long press on element
     * 
     * @param element           element to long press
     * @param durationInSeconds duration of long press
     */
    public static void longPress(WebElement element, int durationInSeconds) {
        try {
            Point location = element.getLocation();
            Dimension size = element.getSize();
            int centerX = location.getX() + (size.getWidth() / 2);
            int centerY = location.getY() + (size.getHeight() / 2);

            logger.info("Long pressing element at ({}, {}) for {} seconds",
                    centerX, centerY, durationInSeconds);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence longPress = new Sequence(finger, 1);

            longPress.addAction(finger.createPointerMove(Duration.ZERO,
                    PointerInput.Origin.viewport(), centerX, centerY));
            longPress.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            longPress.addAction(finger.createPointerMove(Duration.ofSeconds(durationInSeconds),
                    PointerInput.Origin.viewport(), centerX, centerY));
            longPress.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));

            DriverManager.getDriver().perform(Collections.singletonList(longPress));
            logger.debug("Long press completed successfully");

        } catch (Exception e) {
            logger.error("Failed to long press element: {}", e.getMessage(), e);
            throw e;
        }
    }
}
