package pagefactory.accessibility;

import com.deque.axe.AXE;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.asserts.SoftAssert;
import pagefactory.ReusableMethodUI;
import utils.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class Accessibility {
    Logger log = Logger.getLogger(this.getClass());
    ReusableMethodUI rm = new ReusableMethodUI(BaseClass.getDriver());

    ExcelReader excl = new ExcelReader();
    ConfigReader cr = new ConfigReader();
    //    SoftAssert aAssert = new SoftAssert();
    CreateReport createReport = new CreateReport();
    String outPutFolder;

    SoftAssert softAssert = BaseClass.getSoftAssert();


    private static final String scriptPath = "./TestData/AccessibilityTestData/AxeJs/axe.min.js";
    private static URL scriptUrl = null;

    static {
        try {
            File file = new File(scriptPath);
            scriptUrl = file.toURI().toURL();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private WebDriver driver;
    public Accessibility(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyAccessibility(String webPage) throws IOException {
        try {
            outPutFolder = createReport.createOutputFolder(webPage);
            HashMap<String, String> pageUrlMapping = excl.getPageUrlMappingForAccessibility(webPage);
            pageUrlMapping.forEach((pageName, pageUrl)->{
                log.info("Validating Accessibility on page :: "+ pageName + " and Page Url ::" + pageUrl);
                if(!(pageUrl.isEmpty() || pageName.isEmpty())){
                    String url= pageUrl;
                    driver.navigate().to(url);
                    String axeRunParams = cr.getAxeRunParams();

                    JSONObject responseJsonObject = new AXE.Builder(driver, scriptUrl).options(axeRunParams).analyze();

                    responseJsonObject.put("id", pageName.toString());
                    responseJsonObject.put("title", driver.getTitle().toString());
                    Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
                    responseJsonObject.put("dimension", ((RemoteWebDriver) driver).manage().window().getSize().toString());
                    responseJsonObject.put("device", cap.getPlatform().toString());
                    responseJsonObject.put("browser",cap.getBrowserName() + " " + cap.getVersion());
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    String strDate = sdf.format(cal.getTime());
                    responseJsonObject.put("date", strDate);

                    JSONArray violationsArray = responseJsonObject.getJSONArray("violations");
                    if (violationsArray.length() == 0) {
                        log.info("No Error Is Present...!!!");
                    } else {
                        AXE.writeResults(outPutFolder + pageName, responseJsonObject);
                        //softAssert.assertTrue(false, AXE.report(violationsArray));
                    }
                }

                log.info("Validatiion completed on page :: "+ pageName + " and Page Url ::" + pageUrl);
            });

        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            //Assert.assertTrue("" + e, false);
        } finally {
            CreateReport.createHTMLReport(webPage);
        }
    }

}
