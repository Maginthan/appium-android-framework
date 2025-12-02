package com.appium.config;

import com.appium.constants.FrameworkConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Singleton class to read configuration properties
 * Implements thread-safe lazy initialization
 */
public final class ConfigReader {

    private static final Logger logger = LogManager.getLogger(ConfigReader.class);
    private static volatile ConfigReader instance;
    private final Properties properties;

    // Private constructor to prevent instantiation
    private ConfigReader() {
        properties = new Properties();
        loadProperties();
    }

    /**
     * Get singleton instance of ConfigReader (Thread-safe)
     * 
     * @return ConfigReader instance
     */
    public static ConfigReader getInstance() {
        if (instance == null) {
            synchronized (ConfigReader.class) {
                if (instance == null) {
                    instance = new ConfigReader();
                }
            }
        }
        return instance;
    }

    /**
     * Load properties from config file
     */
    private void loadProperties() {
        try (FileInputStream fis = new FileInputStream(FrameworkConstants.CONFIG_FILE_PATH)) {
            properties.load(fis);
            logger.info("Configuration properties loaded successfully from: {}", FrameworkConstants.CONFIG_FILE_PATH);
        } catch (IOException e) {
            logger.error("Failed to load configuration properties: {}", e.getMessage());
            throw new RuntimeException("Configuration file not found at: " + FrameworkConstants.CONFIG_FILE_PATH, e);
        }
    }

    /**
     * Get property value by key
     * 
     * @param key property key
     * @return property value
     */
    private String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            logger.warn("Property '{}' not found in config file", key);
        }
        return value;
    }

    // Appium Server Configuration
    public String getAppiumServerUrl() {
        return getProperty("appium.server.url");
    }

    public String getAppiumServerPort() {
        return getProperty("appium.server.port");
    }

    // Platform Configuration
    public String getPlatformName() {
        return getProperty("platform.name");
    }

    public String getDeviceName() {
        return getProperty("device.name");
    }

    public String getPlatformVersion() {
        return getProperty("platform.version");
    }

    public String getAutomationName() {
        return getProperty("automation.name");
    }

    // App Configuration
    public String getAppPath() {
        return getProperty("app.path");
    }

    public String getAppPackage() {
        return getProperty("app.package");
    }

    public String getAppActivity() {
        return getProperty("app.activity");
    }

    // Additional Capabilities
    public boolean getAutoGrantPermissions() {
        return Boolean.parseBoolean(getProperty("auto.grant.permissions"));
    }

    public boolean getNoReset() {
        return Boolean.parseBoolean(getProperty("no.reset"));
    }

    public boolean getFullReset() {
        return Boolean.parseBoolean(getProperty("full.reset"));
    }

    public boolean getAutoAcceptAlerts() {
        return Boolean.parseBoolean(getProperty("auto.accept.alerts"));
    }

    // Wait Configuration
    public int getImplicitWait() {
        String wait = getProperty("implicit.wait");
        return wait != null ? Integer.parseInt(wait) : FrameworkConstants.IMPLICIT_WAIT;
    }

    public int getExplicitWait() {
        String wait = getProperty("explicit.wait");
        return wait != null ? Integer.parseInt(wait) : FrameworkConstants.EXPLICIT_WAIT;
    }

    public int getPageLoadTimeout() {
        String timeout = getProperty("page.load.timeout");
        return timeout != null ? Integer.parseInt(timeout) : FrameworkConstants.PAGE_LOAD_TIMEOUT;
    }

    // Test Execution Configuration
    public boolean isParallelExecution() {
        return Boolean.parseBoolean(getProperty("parallel.execution"));
    }

    public int getThreadCount() {
        String count = getProperty("thread.count");
        return count != null ? Integer.parseInt(count) : 1;
    }

    // Reporting Configuration
    public String getReportTitle() {
        return getProperty("report.title");
    }

    public String getReportName() {
        return getProperty("report.name");
    }
}
