package stepdefinitions.ui;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import pagefactory.ReusableMethodUI;
import utils.BaseClass;

public class CommonStepDef {

    public ReusableMethodUI rm = new ReusableMethodUI(BaseClass.getDriver());

    public CommonStepDef() {
    }

    @Given("I launch Site url with {string} Url")
    public void launchAvoidCaptchaUrl(String webURL) throws Throwable {
        rm.launchBrandURL(webURL);
    }

}
