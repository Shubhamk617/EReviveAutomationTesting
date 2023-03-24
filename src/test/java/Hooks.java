import com.aventstack.extentreports.service.ExtentService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;
import pagefactory.ReusableMethodUI;
import utils.BaseClass;
import utils.ExcelReader;

public class Hooks {

    private BaseClass driverFactory;
    private WebDriver driver;
    public  Scenario scenarioName;

    public SoftAssert softAssert;

    static{
        try{
            ExtentService.getInstance().setSystemInfo("Environment", System.getProperty("Env").toUpperCase());
            ExtentService.getInstance().setSystemInfo("Browser", System.getProperty("BrowserName").toUpperCase());
            ExtentService.getInstance().setSystemInfo("OS", System.getProperty("OS_Version"));
            ExtentService.getInstance().setSystemInfo("Release", System.getProperty("ReleaseNumber"));
            ExtentService.getInstance().setSystemInfo("Build", System.getProperty("BuildNumber"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }



	@Before(order = 1)
	public void launchBrowser(Scenario scenario) {
       if(System.getProperty("ValidationType").equalsIgnoreCase("API")){
           driverFactory = new BaseClass();
           scenarioName = driverFactory.setScenario(scenario);
       } else {
           driverFactory = new BaseClass();
           driver = driverFactory.initiate_driver();
           softAssert = driverFactory.setSoftAssert();
       }
    }

    @Before(order = 2)
    public void launchExcel(Scenario scenario) {
        if (System.getProperty("ValidationType").equalsIgnoreCase("creative") || scenario.getName().toLowerCase().contains("creative")) {
            ExcelReader excl = new ExcelReader();
            excl.readExcel();
            ReusableMethodUI.setExcelReaderInstance(excl);
            System.out.println("Excel Object : "+excl);
        }
    }


    @After(order = 2)
    public void softAssertCheck(Scenario scenario) {
        if (System.getProperty("ValidationType").equalsIgnoreCase("creative") ||
                scenario.getName().toLowerCase().contains("creative") || System.getProperty("ValidationType").equalsIgnoreCase("accessibility")
                || System.getProperty("ValidationType").equalsIgnoreCase("analytics") || scenario.getName().toLowerCase().contains("analytics"))  {
            softAssert.assertAll();
        }
    }

    @After(order = 1)
    public void takeScreenshot(Scenario scenario) {
        if(!System.getProperty("ValidationType").equalsIgnoreCase("API"))
        {
            if (scenario.isFailed()) {
                // take screenshot:
                String screenshotName = scenario.getName().replaceAll(" ", "_");
                byte[] sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(sourcePath, "image/png", screenshotName);
            }
        }
    }

    @After(order = 0)
    public void tearDown(Scenario scenario) {
        if(scenario.getName().contains("API")) {

        } else {
            if (driver != null) {
                driver.quit();
                driver = null;
            }
        }
    }


}
