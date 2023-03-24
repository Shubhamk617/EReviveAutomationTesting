package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Waits {

    private WebDriver driver;

    /**
     * Author: Priya Bharti
     * Date: 30/5/22(Modified by Ditimoni and added the try and catch block to handle the error incase of element not available)
     * Description: This method is implemented for visibility Of Element, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static WebElement visibilityOfElement(WebDriver driver, int time, WebElement element) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, time);
            return webDriverWait.until(ExpectedConditions.visibilityOf(element));
        }catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Author: Priya Bharti
     * Date: 30/5/22(Modified by Ditimoni and added the try and catch block to handle the error incase of element not available)
     * Description: This method is implement for visibility Of Element, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static WebElement visibilityOfElement(WebDriver driver, int time, By locator) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, time);
            return webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        }catch (Exception e)
        {
            return null;
        }
    }

    /**
     * Author: Kundan Kumar
     * Description: This method is implemented to check Element Clickable, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static WebElement elementToBEClickable(WebDriver driver, int time, By locator) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, time);
            return webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
        }catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Author: Kundan Kumar
     * Description: This method is implemented to check Element Clickable, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static WebElement elementToBEClickable(WebDriver driver, int time, WebElement webElement) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, time);
            return webDriverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        }catch(Exception e)
        {
            return null;
        }
    }

    /**
     * Author: Priya Bharti
     * Description: This method is implemented for inVisibility Of Element, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static boolean inVisibilityOfElement(WebDriver driver, int time, By locator) {
        try {
            WebDriverWait webDriverWait = new WebDriverWait(driver, time);
            return webDriverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        }catch(Exception e)
        {
            return true;
        }
    }

    /**
     * Author: Kundan Kumar
     * Description: This method is implemented to wait For Title Contains some string, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static boolean waitForTitleContains(WebDriver driver, int time, String title) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        return webDriverWait.until(ExpectedConditions.titleContains(title));
    }

    /**
     * Author: Kundan Kumar
     * Description: This method is implemented to check text Present in the web page, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static boolean textPresent(WebDriver driver, int time, By locator, String text) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        return webDriverWait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Author: Kundan Kumar
     * Description: This method is implemented to check url Contain the string provided, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static boolean urlContain(WebDriver driver, int time, String urlSubString) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        return webDriverWait.until(ExpectedConditions.urlContains(urlSubString));
    }

    /**
     * Author: Kundan Kumar
     * Description: This method is implemented check Element Displayed or not, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static boolean checkElementDisplayed(WebDriver driver, int time, By locator) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        boolean flag;
        try {
            flag = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isDisplayed();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Author: A Tejaswini
     * Description: This method is implemented check Element is clickable or not, which can be used throughout the framework
     * Date: 30/5/22
     * @param time will be provided in second
     * @return
     */
    public static boolean checkElementClickable(WebDriver driver, int time, By locator) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        boolean flag;
        try {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(locator));
            flag = true;
        } catch (TimeoutException | NoSuchElementException | ElementNotInteractableException| StaleElementReferenceException e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Author: A Tejaswini
     * Description: This method is implemented check Element is clickable or not, which can be used throughout the framework
     ** Date: 30/5/22
     * @param time will be provided in second
     * @return
     */
    public static boolean checkElementClickable(WebDriver driver, int time, WebElement element) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        boolean flag;
        try {
            webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
            flag = true;
        } catch (TimeoutException | NoSuchElementException | ElementNotInteractableException| StaleElementReferenceException e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Author: Kundan Kumar
     * Description: This method is implemented to check Element is enabled, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static boolean checkElementEnabled(WebDriver driver, int time, By locator) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        boolean flag;
        try {
            flag= webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(locator)).isEnabled();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Author: Kundan Kumar
     * Description: This method is implemented to check Element Displayed, which can be used throughout the framework
     *
     * @param time will be provided in second
     * @return
     */
    public static boolean checkElementDisplayed(WebDriver driver, int time, WebElement element) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        boolean flag;
        try {
            flag =  webDriverWait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Author: Priya Bharti
     * Description: This method is implemented to wait Till Page Is Ready, which can be used throughout the framework
     */
    public static void waitTillPageIsReady(WebDriver driver) {
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public static boolean checkElementEnabled(WebDriver driver, int time, WebElement element) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, time);
        boolean flag;
        try {
            flag= webDriverWait.until(ExpectedConditions.visibilityOf(element)).isEnabled();
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            flag = false;
        }
        return flag;
    }

}