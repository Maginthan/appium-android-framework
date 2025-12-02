package com.appium.tests;

import com.appium.base.BaseTest;
import com.appium.pages.SettingsPage;
import com.appium.reports.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * SettingsTests - Demo test class showing framework working on emulator
 */
public class SettingsTests extends BaseTest {

    @Test(description = "Verify Settings app launches successfully", priority = 1)
    public void testSettingsAppLaunches() {
        ExtentReportManager.logInfo("Starting Settings app launch test");

        SettingsPage settingsPage = new SettingsPage();

        // Verify Settings page is loaded
        boolean isLoaded = settingsPage.isPageLoaded();
        ExtentReportManager.logInfo("Settings page loaded: " + isLoaded);

        Assert.assertTrue(isLoaded, "Settings app should launch successfully");
        ExtentReportManager.logPass("Settings app launched successfully on emulator!");

        logger.info("Settings app launch test completed successfully");
    }

    @Test(description = "Verify page title", priority = 2)
    public void testPageTitle() {
        ExtentReportManager.logInfo("Verifying page title");

        SettingsPage settingsPage = new SettingsPage();
        String pageTitle = settingsPage.getPageTitle();

        ExtentReportManager.logInfo("Page title: " + pageTitle);
        Assert.assertEquals(pageTitle, "Settings", "Page title should be Settings");
        ExtentReportManager.logPass("Page title verification passed");

        logger.info("Page title test completed");
    }
}
