package com.appium.driver;

import com.appium.config.ConfigReader;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.MalformedURLException;
import java.net.URI;
import java.time.Duration;

/**
 * DriverManager - Manages AppiumDriver lifecycle with ThreadLocal for parallel
 * execution
 * Implements thread-safe driver initialization and cleanup
 */
public final class DriverManager {

    private static final Logger logger = LogManager.getLogger(DriverManager.class);
    private static final ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    private static final ConfigReader config = ConfigReader.getInstance();

    // Private constructor to prevent instantiation
    private DriverManager() {
        throw new UnsupportedOperationException("DriverManager is a utility class and cannot be instantiated");
    }

    /**
     * Initialize AppiumDriver with capabilities from config
     */
    public static void initializeDriver() {
        if (driver.get() != null) {
            logger.warn("Driver already initialized for thread: {}", Thread.currentThread().threadId());
            return;
        }

        try {
            String platformName = config.getPlatformName();
            logger.info("Initializing driver for platform: {}", platformName);

            if ("Android".equalsIgnoreCase(platformName)) {
                driver.set(createAndroidDriver());
            } else {
                throw new IllegalArgumentException("Unsupported platform: " + platformName);
            }

            configureTimeouts();
            logger.info("Driver initialized successfully for thread: {}", Thread.currentThread().threadId());

        } catch (Exception e) {
            logger.error("Failed to initialize driver: {}", e.getMessage(), e);
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    /**
     * Create Android driver with UiAutomator2 options
     * 
     * @return AndroidDriver instance
     */
    private static AndroidDriver createAndroidDriver() throws MalformedURLException {
        UiAutomator2Options options = new UiAutomator2Options();

        // Set basic capabilities
        options.setPlatformName(config.getPlatformName());
        options.setDeviceName(config.getDeviceName());
        options.setPlatformVersion(config.getPlatformVersion());
        options.setAutomationName(config.getAutomationName());

        // Set app configuration
        String appPath = config.getAppPath();
        if (appPath != null && !appPath.isEmpty()) {
            options.setApp(appPath);
            logger.info("Using app from path: {}", appPath);
        } else {
            // Use app package and activity for installed apps
            options.setAppPackage(config.getAppPackage());
            options.setAppActivity(config.getAppActivity());
            logger.info("Using installed app - Package: {}, Activity: {}",
                    config.getAppPackage(), config.getAppActivity());
        }

        // Set additional capabilities
        options.setAutoGrantPermissions(config.getAutoGrantPermissions());
        options.setNoReset(config.getNoReset());
        options.setFullReset(config.getFullReset());

        // Create driver
        URI serverUri = URI.create(config.getAppiumServerUrl());
        logger.info("Connecting to Appium server at: {}", serverUri);

        return new AndroidDriver(serverUri.toURL(), options);
    }

    /**
     * Configure driver timeouts
     */
    private static void configureTimeouts() {
        if (driver.get() != null) {
            driver.get().manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(config.getImplicitWait()));
            logger.info("Configured implicit wait: {} seconds", config.getImplicitWait());
        }
    }

    /**
     * Get the current thread's driver instance
     * 
     * @return AppiumDriver instance
     */
    public static AppiumDriver getDriver() {
        if (driver.get() == null) {
            logger.error("Driver not initialized for thread: {}", Thread.currentThread().threadId());
            throw new IllegalStateException("Driver not initialized. Call initializeDriver() first.");
        }
        return driver.get();
    }

    /**
     * Quit driver and remove from ThreadLocal
     */
    public static void quitDriver() {
        if (driver.get() != null) {
            try {
                driver.get().quit();
                logger.info("Driver quit successfully for thread: {}", Thread.currentThread().threadId());
            } catch (Exception e) {
                logger.error("Error while quitting driver: {}", e.getMessage(), e);
            } finally {
                driver.remove();
            }
        } else {
            logger.warn("No driver to quit for thread: {}", Thread.currentThread().threadId());
        }
    }

    /**
     * Check if driver is initialized
     * 
     * @return true if driver is initialized, false otherwise
     */
    public static boolean isDriverInitialized() {
        return driver.get() != null;
    }
}
