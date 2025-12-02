package com.appium.pages;

import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

/**
 * CalculatorPage - Sample page object for Android Calculator app
 * Demonstrates POM pattern with Appium page factory
 */
public class CalculatorPage extends BasePage {

    // Page Elements using AndroidFindBy
    @AndroidFindBy(id = "com.android.calculator2:id/digit_1")
    private WebElement digit1;

    @AndroidFindBy(id = "com.android.calculator2:id/digit_2")
    private WebElement digit2;

    @AndroidFindBy(id = "com.android.calculator2:id/digit_3")
    private WebElement digit3;

    @AndroidFindBy(id = "com.android.calculator2:id/digit_4")
    private WebElement digit4;

    @AndroidFindBy(id = "com.android.calculator2:id/digit_5")
    private WebElement digit5;

    @AndroidFindBy(id = "com.android.calculator2:id/op_add")
    private WebElement addButton;

    @AndroidFindBy(id = "com.android.calculator2:id/op_sub")
    private WebElement subtractButton;

    @AndroidFindBy(id = "com.android.calculator2:id/op_mul")
    private WebElement multiplyButton;

    @AndroidFindBy(id = "com.android.calculator2:id/op_div")
    private WebElement divideButton;

    @AndroidFindBy(id = "com.android.calculator2:id/eq")
    private WebElement equalsButton;

    @AndroidFindBy(id = "com.android.calculator2:id/result")
    private WebElement resultField;

    @AndroidFindBy(id = "com.android.calculator2:id/formula")
    private WebElement formulaField;

    @AndroidFindBy(accessibility = "clear")
    private WebElement clearButton;

    /**
     * Click digit 1
     */
    public CalculatorPage clickDigit1() {
        click(digit1);
        logger.info("Clicked digit 1");
        return this;
    }

    /**
     * Click digit 2
     */
    public CalculatorPage clickDigit2() {
        click(digit2);
        logger.info("Clicked digit 2");
        return this;
    }

    /**
     * Click digit 3
     */
    public CalculatorPage clickDigit3() {
        click(digit3);
        logger.info("Clicked digit 3");
        return this;
    }

    /**
     * Click digit 4
     */
    public CalculatorPage clickDigit4() {
        click(digit4);
        logger.info("Clicked digit 4");
        return this;
    }

    /**
     * Click digit 5
     */
    public CalculatorPage clickDigit5() {
        click(digit5);
        logger.info("Clicked digit 5");
        return this;
    }

    /**
     * Click add button
     */
    public CalculatorPage clickAdd() {
        click(addButton);
        logger.info("Clicked add button");
        return this;
    }

    /**
     * Click subtract button
     */
    public CalculatorPage clickSubtract() {
        click(subtractButton);
        logger.info("Clicked subtract button");
        return this;
    }

    /**
     * Click multiply button
     */
    public CalculatorPage clickMultiply() {
        click(multiplyButton);
        logger.info("Clicked multiply button");
        return this;
    }

    /**
     * Click divide button
     */
    public CalculatorPage clickDivide() {
        click(divideButton);
        logger.info("Clicked divide button");
        return this;
    }

    /**
     * Click equals button
     */
    public CalculatorPage clickEquals() {
        click(equalsButton);
        logger.info("Clicked equals button");
        return this;
    }

    /**
     * Get result text
     * 
     * @return result as string
     */
    public String getResult() {
        String result = getText(resultField);
        logger.info("Retrieved result: {}", result);
        return result;
    }

    /**
     * Get formula text
     * 
     * @return formula as string
     */
    public String getFormula() {
        String formula = getText(formulaField);
        logger.info("Retrieved formula: {}", formula);
        return formula;
    }

    /**
     * Clear calculator
     */
    public CalculatorPage clear() {
        click(clearButton);
        logger.info("Cleared calculator");
        return this;
    }

    /**
     * Perform addition
     * 
     * @param num1 first number
     * @param num2 second number
     * @return result of addition
     */
    public String performAddition(int num1, int num2) {
        logger.info("Performing addition: {} + {}", num1, num2);
        enterNumber(num1);
        clickAdd();
        enterNumber(num2);
        clickEquals();
        return getResult();
    }

    /**
     * Enter a number (supports 1-5 for demo)
     * 
     * @param number number to enter
     */
    private void enterNumber(int number) {
        switch (number) {
            case 1:
                clickDigit1();
                break;
            case 2:
                clickDigit2();
                break;
            case 3:
                clickDigit3();
                break;
            case 4:
                clickDigit4();
                break;
            case 5:
                clickDigit5();
                break;
            default:
                logger.warn("Number {} not supported in demo", number);
        }
    }

    @Override
    public String getPageTitle() {
        return "Calculator";
    }

    @Override
    public boolean isPageLoaded() {
        return isDisplayed(digit1) && isDisplayed(equalsButton);
    }
}
