package stepdefinitions.ui;

import io.cucumber.java.en.Then;
import pagefactory.accessibility.Accessibility;
import utils.BaseClass;

import java.io.IOException;

public class AccessibilityStepDef {
    private Accessibility accessibility = new Accessibility(BaseClass.getDriver());

    public AccessibilityStepDef() {
    }

    @Then("I validate accessibility on {string} page")
    public void iValidateTheAccessibility(String webPage) throws IOException {
            accessibility.verifyAccessibility(webPage);
        }
}
