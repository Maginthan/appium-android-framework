package com.appium.tests;

import com.appium.base.BaseTest;
import com.appium.pages.CalculatorPage;
import com.appium.reports.ExtentReportManager;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * CalculatorTests - Sample test class demonstrating calculator app automation
 * Extends BaseTest for driver and reporting setup
 */
public class CalculatorTests extends BaseTest {

    /**
     * Test addition operation
     */
    @Test(description = "Verify addition operation in calculator", priority = 1)
    public void testAddition() {
        ExtentReportManager.logInfo("Starting addition test");

        CalculatorPage calculatorPage = new CalculatorPage();

        // Verify calculator page is loaded
        Assert.assertTrue(calculatorPage.isPageLoaded(), "Calculator page should be loaded");
        ExtentReportManager.logPass("Calculator page loaded successfully");

        // Perform addition: 2 + 3
        ExtentReportManager.logInfo("Performing calculation: 2 + 3");
        String result = calculatorPage.performAddition(2, 3);

        // Verify result
        Assert.assertEquals(result, "5", "Addition result should be 5");
        ExtentReportManager.logPass("Addition test passed: 2 + 3 = " + result);

        logger.info("Addition test completed successfully");
    }

    /**
     * Test individual digit clicks
     */
    @Test(description = "Verify individual digit clicks work correctly", priority = 2)
    public void testDigitClicks() {
        ExtentReportManager.logInfo("Starting digit clicks test");

        CalculatorPage calculatorPage = new CalculatorPage();

        // Click digits 1, 2, 3
        ExtentReportManager.logInfo("Clicking digits 1, 2, 3");
        calculatorPage.clickDigit1()
                .clickDigit2()
                .clickDigit3();

        // Get formula
        String formula = calculatorPage.getFormula();
        ExtentReportManager.logInfo("Formula displayed: " + formula);

        // Verify formula contains the digits
        Assert.assertTrue(formula.contains("123") || formula.equals("123"),
                "Formula should contain 123");
        ExtentReportManager.logPass("Digit clicks test passed: " + formula);

        logger.info("Digit clicks test completed successfully");
    }

    /**
     * Test clear functionality
     */
    @Test(description = "Verify clear button clears the calculator", priority = 3)
    public void testClearFunction() {
        ExtentReportManager.logInfo("Starting clear function test");

        CalculatorPage calculatorPage = new CalculatorPage();

        // Enter some digits
        ExtentReportManager.logInfo("Entering digits 4 and 5");
        calculatorPage.clickDigit4().clickDigit5();

        String formulaBefore = calculatorPage.getFormula();
        ExtentReportManager.logInfo("Formula before clear: " + formulaBefore);

        // Clear calculator
        ExtentReportManager.logInfo("Clicking clear button");
        calculatorPage.clear();

        // Verify calculator is cleared (formula should be empty or "0")
        String formulaAfter = calculatorPage.getFormula();
        ExtentReportManager.logInfo("Formula after clear: " + formulaAfter);

        Assert.assertTrue(formulaAfter.isEmpty() || formulaAfter.equals("0"),
                "Calculator should be cleared");
        ExtentReportManager.logPass("Clear function test passed");

        logger.info("Clear function test completed successfully");
    }

    /**
     * Test calculator page verification
     */
    @Test(description = "Verify calculator page elements are displayed", priority = 4)
    public void testPageVerification() {
        ExtentReportManager.logInfo("Starting page verification test");

        CalculatorPage calculatorPage = new CalculatorPage();

        // Verify page is loaded
        boolean isLoaded = calculatorPage.isPageLoaded();
        ExtentReportManager.logInfo("Page loaded status: " + isLoaded);

        Assert.assertTrue(isLoaded, "Calculator page should be loaded with all elements");
        ExtentReportManager.logPass("Page verification test passed");

        // Verify page title
        String pageTitle = calculatorPage.getPageTitle();
        ExtentReportManager.logInfo("Page title: " + pageTitle);

        Assert.assertEquals(pageTitle, "Calculator", "Page title should be Calculator");
        ExtentReportManager.logPass("Page title verification passed");

        logger.info("Page verification test completed successfully");
    }
}
