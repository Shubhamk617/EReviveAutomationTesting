package pagefactory;

import org.apache.log4j.Logger;
import org.assertj.core.api.SoftAssertions;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ReusableMethodUI {
    private WebDriver driver;
    Logger log = LoggerFile.getLogger(this.getClass());
    private static SoftAssertions softAssert = new SoftAssertions();
    private static ExcelReader excl;
    private ObjectReader or;

    public List<WebElement> totalRows;

    public ReusableMethodUI(WebDriver driver) {
        this.driver = driver;
    }

    public ReusableMethodUI() {
    }

    public void verifyPageNavigationbyTab(WebElement element, String tab) throws InterruptedException {
        if (tab.equalsIgnoreCase("Same Tab") || tab.equalsIgnoreCase("Default")) {
            Thread.sleep(5000);
            String href = element.getAttribute("href");
            Assert.assertTrue("Element is not displayed", Waits.checkElementDisplayed(driver, 5, element));
            log.info("Element is Clickable");
            clickWithJS(element);
            log.info("Clicked on the element");
            String currentURL = driver.getCurrentUrl();
            Assert.assertEquals(href, currentURL);
            //org.testng.Assert.assertEquals(href,currentURL);
            driver.navigate().back();
        } else if (tab.equalsIgnoreCase("New Tab")) {
            Thread.sleep(5000);
            String href = element.getAttribute("href");
            Assert.assertTrue("Element is not displayed", Waits.checkElementDisplayed(driver, 5, element));
            log.info("Element is Clickable");
            clickWithJS(element);
            log.info("Clicked on the element");
            focusOnNewTab();
            String currentURL = driver.getCurrentUrl();
            org.testng.Assert.assertEquals(href, currentURL);
            closeNewlyOpenTab();
        }
    }


    public void verifyPageNavigationbyTab(By element, String tab) {
        if (tab.equalsIgnoreCase("Same Tab") || tab.equalsIgnoreCase("Default")) {
            if (Waits.visibilityOfElement(driver, 30, element) != null) {
                String href = driver.findElement(element).getAttribute("href");
                clickWithJS(element);
                String currentURL = driver.getCurrentUrl();
                Assert.assertEquals(href + " is not same with " + currentURL, href, currentURL);
                log.info("Navigated to the page " + currentURL);
                driver.navigate().back();
                currentURL = driver.getCurrentUrl();
                log.info("Navigated back to the page " + currentURL);
            } else {
                Assert.fail(element + " is not visible");
            }
        } else if (tab.equalsIgnoreCase("New Tab")) {
            if (Waits.visibilityOfElement(driver, 30, element) != null) {
                String href = driver.findElement(element).getAttribute("href");
                clickWithJS(element);
                focusOnNewTab();
                String currentURL = driver.getCurrentUrl();
                Assert.assertEquals(href + " is not same with " + currentURL, href, currentURL);
                closeNewlyOpenTab();
            } else {
                Assert.fail(element + " is not visible");
            }
        }
    }

    public WebElement selectByIndex(By locator, int index) {
        if (Waits.visibilityOfElement(driver, 20, locator) != null) {
            Select sel = new Select(driver.findElement(locator));
            sel.selectByIndex(index);
            log.info(sel.getFirstSelectedOption().getText() + " Has been selected from the drop down");
            return sel.getFirstSelectedOption();
        } else {
            Assert.fail(locator + " is not visible");
            return null;
        }
    }

    public void selectByValue(By locator, String value) {
        if (Waits.visibilityOfElement(driver, 20, locator) != null) {
            Select sel = new Select(driver.findElement(locator));
            sel.selectByValue(value);
            log.info(value + "Has been selected from the drop down");
        } else {
            Assert.fail(locator + " is not visible");
        }
    }


    public void mouseHover(By locator) {
        try {
            if (Waits.visibilityOfElement(driver, 20, locator) != null) {
                Actions act = new Actions(driver);
                act.moveToElement(driver.findElement(locator)).build().perform();
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }


    public void mouseHover(WebElement element) {
        try {
            if (Waits.visibilityOfElement(driver, 20, element) != null) {
                Actions act = new Actions(driver);
                act.moveToElement(element).perform();
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public WebElement selectByVisibleText(By locator, String text) {
        WebElement webElement = null;
        try {
            Waits.waitTillPageIsReady(driver);
            if (Waits.visibilityOfElement(driver, 30, locator) != null) {
                Select sel = new Select(driver.findElement(locator));
                sel.selectByVisibleText(text);
                log.info(text + "Has been selected from the drop down");
                webElement = sel.getFirstSelectedOption();
                return webElement;
            } else {
                Assert.fail(locator + " is not visible");
                return webElement;
            }
        } catch (Exception e) {
            log.error(e);
            Assert.fail("Failed in selectByVisibleText " + e);
        }
        return webElement;
    }


    public void scrollInToViewIfNeeded(WebElement ele) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            js.executeScript("arguments[0].scrollIntoViewIfNeeded(true)", ele);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }


    public void scrollInToViewIfNeeded(By locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", driver.findElement(locator));
    }

    public void scrollInToView(By locator) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(locator));
    }


    public void clickWithActionClass(By locator) {
        new Actions(driver).moveToElement(driver.findElement(locator)).click().build().perform();
    }


    public void clickWithActionClass(WebElement ele) {
        new Actions(driver).moveToElement(ele).click().build().perform();
    }

    public void clickWithJS(By locator) {
        try {
            if (Waits.checkElementDisplayed(driver, 20, locator)) {
                JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
                javascriptExecutor.executeScript("arguments[0].click();", driver.findElement(locator));
            } else {
                Assert.fail(locator + " is not displayed");
            }
        } catch (Exception e) {
            Assert.fail(locator + " is not clicked " + e);
        }
    }

    public void click(By locator) {
        if (Waits.checkElementDisplayed(driver, 20, locator)) {
            if (Waits.checkElementClickable(driver, 20, locator))
                driver.findElement(locator).click();
            else
                Assert.fail(locator + " Element is not clickable");
        } else {
            Assert.fail(locator + " Element is not displayed");
        }
    }

    public void click(WebElement element) {
        if (Waits.checkElementDisplayed(driver, 20, element)) {
            if (Waits.checkElementClickable(driver, 20, element))
                element.click();
            else
                Assert.fail(element + " Element is not clickable");
        } else {
            Assert.fail(element + " Element is not displayed");
        }
    }

    public void clickWithJS(WebElement ele) {
        try {
            if (Waits.checkElementDisplayed(driver, 20, ele)) {
                JavascriptExecutor js = (JavascriptExecutor) driver;
                js.executeScript("arguments[0].click();", ele);
            } else {
                Assert.fail(ele + " not displayed");
            }
        } catch (Exception e) {
            Assert.fail("Failed in clickWithJS " + e);
        }
    }


    public String executeJS(String script) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js.executeScript(script).toString();
    }

    public void refreshPageWithJS() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.location.reload();");
    }

    public void launchCaptchaURL(String brand) throws FileNotFoundException, InterruptedException {
        try {
            log.info("Launching captcha URL::  " + ConfigReader.captchaUrl(brand));
            driver.get(ConfigReader.captchaUrl(brand));
            Waits.waitTillPageIsReady(driver);
            Assert.assertTrue("Failed in Captcha challenge launch", Waits.checkElementDisplayed(driver, 30, or.getLocator("CommonMethodUI.CaptchaError")));
            log.info("captcha challenge launched Successfully");
        } catch (Exception e) {
            log.info("Failed in Launch Captcha Url " + e);
            Assert.fail("Failed in Launch Captcha Url " + e);
        }
    }

    public void launchBrandURL(String webURL) {
        try {
            String url = ConfigReader.getURL(webURL);
            System.out.println("Navigating to brand URL : " + url);
            driver.get(url);
            Waits.waitTillPageIsReady(driver);

        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void launchGivenPgURL(String pageName) {
        try {
            String url = ConfigReader.getPageURL(pageName);
            log.info("Navigating to " + pageName + " Page URL : " + url);
            driver.navigate().to(url);
//            Assert.assertTrue(pageName+" URL is not launched properly", Waits.checkElementDisplayed(driver, 20, or.getLocator("LoginPage.OktaEmail")));
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void verifyPage(String Page) throws InterruptedException {
        Thread.sleep(4000);
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        String url = driver.getCurrentUrl().toLowerCase();
        try {
            Assert.assertTrue("Verification of " + Page + " Page is failed. Actual URL is ::" + url, url.contains(Page.toLowerCase()));
            log.info("Verification of " + Page + " Page is Done");
        } catch (Exception e) {
            log.error("Verification of" + Page + " Page is failed");
            log.error(e);
            e.printStackTrace();
        }
    }

    public void navigateBack() {
        try {
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to previous page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }


    public void closePreviousTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(0));
            driver.close();
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(2000);
            log.info("Closed newly Previous tab and focus on new tab");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }


    public void closeOriginalTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(2000);
            log.info("Closed Original Tab tab and focus on new tab");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }


    public void focusOnNewTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));
            Thread.sleep(2000);
            log.info("Focused on new tab");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }


    public void closeNewlyOpenTab() {
        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.close();
            driver.switchTo().window(tabs.get(0));
            Thread.sleep(2000);
            log.info("Closed newly opened tab");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void uploadImage(By locator, String path) {
        try {
            String pathArray[]=path.split("\\\\");
            String finalPath="";
            for (int i = 0; i < pathArray.length ; i++) {
                if (i<pathArray.length-1) {
                    finalPath += pathArray[i] + File.separator;
                }else {
                    finalPath += pathArray[i];
                }
            }
            path=new File(finalPath).getAbsolutePath();
            driver.findElement(locator).sendKeys(path);
            Thread.sleep(5000);
            log.info("Uploaded the photo from path " + path);
        } catch (Exception exp) {
            exp.printStackTrace();
        }
    }

    public void sendValue(By locator, String key) {
        try {
            Assert.assertTrue(locator + " is not available", Waits.checkElementDisplayed(driver, 20, locator));
            driver.findElement(locator).clear();
            log.info("Text Field has been cleared");
            driver.findElement(locator).sendKeys(key);
            log.info("Input value has been Provided for the text Field");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void verifyPageNavigation(By element, String tab) {
        try {
            if (tab.equalsIgnoreCase("Same Tab") || tab.equalsIgnoreCase("Default")) {
                String cta = driver.findElement(element).getAttribute("href");
                clickWithJS(driver.findElement(element));
                Thread.sleep(5000);
                String[] testData = cta.split("/");
                int size = testData.length;
                if (testData[size - 1].contains("qsId")) {
                    testData = testData[size - 1].split("qsId");
                    size = testData.length;
                }
                log.info(testData[size - 1] + " - " + "clicked");
                if (testData[size - 1].equalsIgnoreCase("coupons")) {
                    Assert.assertTrue(driver.getCurrentUrl().contains("dvp"));
                    log.info("Verified Navigating to dvp Page is Pass");
                } else {
                    Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                    log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
                }
            } else if (tab.equalsIgnoreCase("New Tab")) {
                String cta = driver.findElement(element).getAttribute("href");
                clickWithJS(driver.findElement(element));
                focusOnNewTab();
                Thread.sleep(5000);
                String[] testData = cta.split("/");
                int size = testData.length;
                if (testData[size - 1].contains("qsId")) {
                    testData = testData[size - 1].split("qsId");
                    size = testData.length;
                }
                log.info(testData[size - 1] + " - " + "clicked");
                if (testData[size - 1].equalsIgnoreCase("coupons")) {
                    Assert.assertTrue(driver.getCurrentUrl().contains("dvp"));
                    log.info("Verified Navigating to dvp Page is Pass");
                } else {
                    Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                    log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
                }
                closeNewlyOpenTab();
            }
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to previous page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }


    public void verifyPageNavigation(WebElement element, String tab) {
        try {
            if (tab.equalsIgnoreCase("Same Tab") || tab.equalsIgnoreCase("Default")) {
                String cta = element.getAttribute("href");
                clickWithJS(element);
                Thread.sleep(5000);
                String[] testData = cta.split("/");
                int size = testData.length;
                if (testData[size - 1].contains("qsId")) {
                    testData = testData[size - 1].split("qsId");
                    size = testData.length;
                }
                log.info(testData[size - 1] + " - " + "clicked");
                if (testData[size - 1].equalsIgnoreCase("coupons")) {
                    Assert.assertTrue(driver.getCurrentUrl().contains("dvp"));
                    log.info("Verified Navigating to dvp Page is Pass");
                } else {
                    Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                    log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
                }
            } else if
            (tab.equalsIgnoreCase("New Tab")) {
                String cta = element.getAttribute("href");
                clickWithJS(element);
                focusOnNewTab();
                Thread.sleep(5000);
                String[] testData = cta.split("/");
                int size = testData.length;
                if (testData[size - 1].contains("qsId")) {
                    testData = testData[size - 1].split("qsId");
                    size = testData.length;
                }
                log.info(testData[size - 1] + " - " + "clicked");
                if (testData[size - 1].equalsIgnoreCase("coupons")) {
                    Assert.assertTrue(driver.getCurrentUrl().contains("dvp"));
                    log.info("Verified Navigating to dvp Page is Pass");
                } else {
                    Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                    log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
                }
                closeNewlyOpenTab();
            }
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to previous page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public void verifyPageNavigation(By element) {
        try {
            String cta = driver.findElement(element).getAttribute("href");
            System.out.println("href is" + driver.findElement(element).getAttribute("href"));
            clickWithJS(driver.findElement(element));
            Thread.sleep(5000);
            String[] testData = cta.split("/");
            int size = testData.length;
            log.info(testData[size - 1] + " - " + "clicked");
            try {
                System.out.println("driver.getCurrentUrl()" + driver.getCurrentUrl());
                System.out.println("testData[size - 1]" + testData[size - 1]);
                Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
            } catch (Exception e) {
                log.error("Verified Navigating to " + testData[size - 1] + " Page is failed");
            }
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to Header page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }


    public void verifyPageNavigation(WebElement element) {
        try {
            String cta = element.getAttribute("href");
            System.out.println("href is " + element.getAttribute("href"));
            clickWithJS(element);
            Thread.sleep(5000);
            String[] testData = cta.split("/");
            int size = testData.length;
            log.info(testData[size - 1] + " - " + "clicked");
            try {
                System.out.println("driver.getCurrentUrl()" + driver.getCurrentUrl());
                System.out.println("testData[size - 1]" + testData[size - 1]);
                Assert.assertTrue(driver.getCurrentUrl().contains(testData[size - 1]));
                log.info("Verified Navigating to " + testData[size - 1] + " Page is Pass");
            } catch (Exception e) {
                log.error("Verified Navigating to " + testData[size - 1] + " Page is failed");
            }
            driver.navigate().back();
            Thread.sleep(2000);
            log.info("Navigated back to Header page");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public static SoftAssertions getSoftAssertInstance() {
        return softAssert;
    }


    public static void setExcelReaderInstance(ExcelReader exl) {
        excl = exl;
    }


    public static ExcelReader getExcelReaderInstance() {
        return excl;
    }


    public static void doAssertAll() {
        softAssert.assertAll();
    }

    public String getText(By locator) {
        String text = "";
        try {
            Assert.assertTrue(locator + " is not displayed", Waits.checkElementDisplayed(driver, 20, locator));
            text = driver.findElement(locator).getText();
            Thread.sleep(200);
            log.info(text + " is the text of the element");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return text;
    }

    public String getText(WebElement ele) {
        String text = "";
        try {
            Assert.assertTrue(ele + " is not displayed", Waits.checkElementDisplayed(driver, 20, ele));
            text = ele.getText();
            Thread.sleep(200);
            log.info(text + " is the text of the element");
        } catch (AssertionError | Exception e) {
            log.error(e);
            e.printStackTrace();
            Assert.fail("Failed to return the text of the element" + e);
        }
        return text;
    }

    public String getTextUsingJS(By locator) {
        String text = "";
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            text = js.executeScript("return arguments[0].innerHTML;", driver.findElement(locator)).toString();
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return text;
    }


    public String getDate(String dateFormat, int NumDaysFromToday) {
        DateFormat df = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, NumDaysFromToday);
        return df.format(c.getTime());
    }


    public void switchToFrame(By locator) {
        driver.switchTo().frame(driver.findElement(locator));
    }

    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }


    public void selectByVisibleText(WebElement webElement, String value) {
        if (Waits.visibilityOfElement(driver, 10, webElement) != null) {
            Select sel = new Select(webElement);
            sel.selectByVisibleText(value);
            log.info(value + "Has been selected from the drop down");
        } else {
            Assert.fail(webElement + " is not available");
        }
    }

    public void sendValue(WebElement webElement, String key) {
        try {
            if (Waits.visibilityOfElement(driver, 20, webElement) != null) {
                log.info("Text Field is displayed");
                webElement.clear();
                log.info("Text Field has been cleared");
                webElement.sendKeys(key);
                Thread.sleep(200);
                log.info("Input value has been Provided for the text Field");
            } else {
                Assert.fail(webElement + " is not available");
            }
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
    }

    public static void deleteDirectory(File file) {
        for (File subfile : file.listFiles()) {
            if (subfile.isDirectory()) {
                deleteDirectory(subfile);
            }
            subfile.delete();
        }
    }

    public String getAttribute(By locator, String attr) {
        String str = "";
        try {
            str = driver.findElement(locator).getAttribute(attr);
        } catch (Exception e) {
            return "";
        }
        if (str == null)
            return "";
        else
            return str;
    }

    /* Description: This method will return the attribute value of passed attribute*/
    public String getAttribute(WebElement ele, String attr) {
        String str = "";
        try {
            str = ele.getAttribute(attr);
        } catch (Exception e) {
            return "";
        }

        if (str == null)
            return "";
        else
            return str;
    }

    /* Description: This method will return the attribute value of passed attribute*/
    public boolean isSelected(By locator) {
        boolean flag = false;
        try {
            flag = driver.findElement(locator).isSelected();
        } catch (Exception e) {
            log.error(e);
            Assert.fail("Failed in isSelected " + e);
        }
        return flag;
    }

    public void clear(By locator) {
        try {
            if (Waits.checkElementDisplayed(driver, 10, locator)) {
                driver.findElement(locator).clear();
            } else {
                Assert.fail(locator + " Element is not displayed");
            }
        } catch (Exception e) {
            Assert.fail(locator + " Failed While clearing the field");
        }
    }

    public WebElement getElement(By locator) {
        WebElement webElement = null;
        if (Waits.checkElementDisplayed(driver, 20, locator)) {
            webElement = driver.findElement(locator);
        } else {
            Assert.fail(locator + " is not displayed");
        }
        return webElement;
    }

    public String getDateForWf(int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, +hour);
        calendar.add(Calendar.MINUTE, min);
        Format f = new SimpleDateFormat("MM-dd-yyyy hh:mm");
        String str = f.format(calendar.getTime());
        return str;
    }


    public void click(String pageElementName) {
        if (Waits.checkElementDisplayed(driver, 20, or.getLocator(pageElementName))) {
            if (Waits.checkElementClickable(driver, 20, or.getLocator(pageElementName)))
                driver.findElement(or.getLocator(pageElementName)).click();
            else
                Assert.fail(pageElementName + " Element is not clickable");
        } else {
            Assert.fail(pageElementName + " Element is not displayed");
        }
    }

    public void select(String pageElementName, int index) {
        selectByIndex(or.getLocator(pageElementName), index);

    }

    public WebElement getDefaultSelectedOption(By locator) {
        WebElement element = null;
        try {
            if (Waits.visibilityOfElement(driver, 20, locator) != null) {
                Select sel = new Select(driver.findElement(locator));
                element = sel.getFirstSelectedOption();
                log.info("Default selected option in the dropdown : " + element.getText());
            } else {
                Assert.fail(locator + " is not visible");
            }
        } catch (Exception e) {
            log.error(e);
            Assert.fail("Failed to get the default selected option " + e);
        }
        return element;
    }

    public void clear(String ele) {
        By locator = or.getLocator(ele);
        try {
            if (Waits.checkElementDisplayed(driver, 10, locator)) {
                driver.findElement(locator).clear();
            } else {
                Assert.fail(locator + " Element is not displayed");
            }
        } catch (Exception e) {
            Assert.fail(locator + " Failed While clearing the field");
        }
    }

    public void moveToElementAndDoubleClick(WebElement webElement) {
        log.info("Moving to element..");
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).doubleClick().build().perform();
        log.info("Clicked the given element " + webElement + " successfully..");
    }

    public List<String> handlingWebTable(List<WebElement> elements) {
        ArrayList<String> rowData = new ArrayList<>();
        for (WebElement row : elements) {
            totalRows = row.findElements(By.tagName("td"));
            for (WebElement column : totalRows) {
                rowData.add(column.getText());
            }
        }
        return rowData;
    }

    public void refreshWebPage() {
        driver.navigate().refresh();
    }

    public List<WebElement> getElements(By locator) {
        List<WebElement> elements = null;
        try {
            elements = driver.findElements(locator);

        } catch (Exception e) {
            log.info("Could not find the elements");
            Assert.fail("Could not find the elements");

        }
        return elements;
    }

    public void accessiblityAssertion( String currentUrl, String pageUrl, String pageType)
    {
        softAssert.assertThat(currentUrl).contains(pageUrl).describedAs("Failed url is : "+pageUrl+" Page type is : "+pageType);
    }

    public String getCSSValue(By by, String attribute)
    {
        String CssValue = driver.findElement(by).getCssValue(attribute);
        return CssValue;
    }

}

