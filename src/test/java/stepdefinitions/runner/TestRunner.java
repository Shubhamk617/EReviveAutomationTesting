package stepdefinitions.runner;

import io.cucumber.java.Before;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import pagefactory.ReusableMethodUI;
import utils.BaseClass;
import utils.LoggerFile;
import utils.ObjectReader;

import java.io.File;

@CucumberOptions(
        plugin = {"pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
        },
        tags = "@Accessibility and @Desktop",
        monochrome = true,
        glue = {"stepdefinitions"},
        features = {"src/test/resources/FeatureFiles"}
)
public class TestRunner extends AbstractTestNGCucumberTests {
    Logger log = LoggerFile.getLogger(this.getClass());

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
    @BeforeSuite()
    public static void beforeSuitSetup(ITestContext context) {
        int invocationCount=Integer.parseInt(System.getProperty("InvocationCount"));
        if (System.getProperty("BrowserStack").equalsIgnoreCase("yes")){
            invocationCount=1;
        }
        System.out.println("Invocation Count : "+invocationCount);
        context.getCurrentXmlTest().getSuite().setDataProviderThreadCount(invocationCount);
        ObjectReader.initializeObjectRepository();
    }

    @Before(order=0)
    public void extractUrlFromPg() {
        String browser = System.getProperty("BrowserName").toLowerCase();
        if (System.getProperty("ValidationType").equalsIgnoreCase("accessibility")||
                System.getProperty("ValidationType").equalsIgnoreCase("functional")||
        System.getProperty("ValidationType").equalsIgnoreCase("creative")) {
            if (browser.contains("mobile")) {
                System.setProperty("BrowserName", "chrome");
            }
            WebDriver driver = new BaseClass().initiate_driver();
            System.out.println("++++++ ==>" +BaseClass.getDriver());
            //new ReusableMethodUI(BaseClass.getDriver()).launchBrandURL("E-revive");
            System.setProperty("BrowserName", browser);
        }else if (System.getProperty("ValidationType").equalsIgnoreCase("api")) {
            File theDir = new File("./Report/Logs/APILogs");
            if (!theDir.exists()){
                theDir.mkdir();
            }
        }
    }

    /**
     * In aftersuite, all the files generated in temp folder via cucumber run are deleted
     */
    @AfterSuite
    public void deleteFromTemp(){
        String defaultBaseDir = System.getProperty("java.io.tmpdir");
        File directoryToDelete = new File(defaultBaseDir);
        for (File file : directoryToDelete.listFiles()) {
            try {
                if (file.getName().contains("cucumber") || file.getName().contains(".com.google.Chrome")) {
                    if (file.isDirectory()) {
                        ReusableMethodUI.deleteDirectory(file);
                    }
                    file.delete();
                    log.info(file.getName() + " deleted");
                }
            }catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

}
