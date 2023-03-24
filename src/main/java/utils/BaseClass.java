package utils;

import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.asserts.SoftAssert;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class BaseClass {

    public static ThreadLocal<WebDriver> tl = new ThreadLocal<>();
    public static ThreadLocal<String> feature = new ThreadLocal<>();
    public static ThreadLocal<String> resourceUrl = new ThreadLocal<>();
    public static ThreadLocal<Scenario> scenario = new ThreadLocal<>();
    public static ThreadLocal<SoftAssert> softAssert = new ThreadLocal<>();
    ConfigReader config = new ConfigReader();

    public WebDriver initiate_driver() {
        try {
            String browser = System.getProperty("BrowserName").toLowerCase();
            String device = System.getProperty("DeviceName");
            String osVersion = System.getProperty("OS_Version");
            Map<String, String> mobileEmulation = new HashMap<>();
            ChromeOptions chromeOptions = new ChromeOptions();
            DesiredCapabilities caps = new DesiredCapabilities();
            LoggingPreferences logPrefs = new LoggingPreferences();
            logPrefs.enable( LogType.PERFORMANCE, Level.ALL );

            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    chromeOptions.setCapability( "goog:loggingPrefs", logPrefs );
                    tl.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    tl.set(new FirefoxDriver());
                    break;

                case "edge":
                    WebDriverManager.edgedriver().setup();
                    tl.set(new EdgeDriver());
                    break;

                case "mobileheadless":
                    WebDriverManager.chromedriver().setup();
                    mobileEmulation.put("deviceName", device);
                    chromeOptions.addArguments("--headless");
                    chromeOptions.addArguments("--disable-dev-shm-using");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("disable-infobars");
                    chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                    tl.set(new ChromeDriver(chromeOptions));
                    break;

                case "mobile":
                        WebDriverManager.chromedriver().setup();
                        mobileEmulation.put("deviceName", device);
                        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
                        chromeOptions.setCapability( "goog:loggingPrefs", logPrefs );
                        tl.set(new ChromeDriver(chromeOptions));
                    break;

                case "headless":
                    WebDriverManager.chromedriver().setup();
                    chromeOptions.addArguments("--no-sandbox");
                    chromeOptions.addArguments("--disable-setuid-sandbox");
                    chromeOptions.addArguments("--remote-debugging-port=9222");
                    chromeOptions.addArguments("--disable-dev-shm-using");
                    chromeOptions.addArguments("--disable-extensions");
                    chromeOptions.addArguments("--disable-gpu");
                    chromeOptions.addArguments("start-maximized");
                    chromeOptions.addArguments("disable-infobars");
                    tl.set(new ChromeDriver(chromeOptions));
                    break;
            }
            getDriver().manage().deleteAllCookies();
            getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
            getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            if (!System.getProperty("BrowserResolution").equalsIgnoreCase("null")) {
                String[] resolution = System.getProperty("BrowserResolution").split("\\*");
                Dimension d = new Dimension(Integer.parseInt(resolution[0]), Integer.parseInt(resolution[1]));
                getDriver().manage().window().setSize(d);
            } else {
                getDriver().manage().window().maximize();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getDriver();
    }

    public static String setFeature(String featureFile) {
        feature.set(featureFile);
        return getFeature();

    }

    public static String setResourceUrl(String resUrl) {
        resourceUrl.set(resUrl);
        return getResoureUrl();

    }

    public Scenario setScenario(Scenario scen) {
        scenario.set(scen);
        return getScenario();

    }


    public SoftAssert setSoftAssert() {
        SoftAssert sa = new SoftAssert();
        softAssert.set(sa);
        return getSoftAssert();
    }


    public static synchronized WebDriver getDriver() {
        return tl.get();
    }
    public static synchronized Scenario getScenario() {
        return scenario.get();
    }

    public static synchronized String getFeature() {
        return feature.get();
    }
    public static synchronized String getResoureUrl() {
        return resourceUrl.get();
    }

    public static synchronized SoftAssert getSoftAssert()
    {
        return softAssert.get();
    }

}
