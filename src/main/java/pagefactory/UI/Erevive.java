package pagefactory.UI;

import pagefactory.ReusableMethodUI;
import utils.BaseClass;

public class Erevive {

    public ReusableMethodUI rm = new ReusableMethodUI(BaseClass.getDriver());
    public void lauchSite(String webURL) {
        rm.launchBrandURL(webURL);
    }
}
