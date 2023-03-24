package utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;

/**
 * Author: Kundan Kumar
 * Date: 09/06/2022
 * Description: This class has two methods saveURL() to fetch Urls from Confluence page and store in a Map and
 * getUrls() to fetch Urls from Map
 */
public class ExtractURL {

    Logger log = LoggerFile.getLogger(this.getClass());
    private ObjectReader or;
    public static HashMap<String, HashMap<String, String>> brandMap = new HashMap<>();
    HashMap<String, String> pageMap = new HashMap<>();
    String[] brandAndPage;

    /**
     * Author: Kundan Kumar
     * Date: 09/06/2022
     * Description: This method is implemented to fetch Urls from Confluence page and store in a Map
     *
     * @param : Web driver
     */
    public void saveURL(WebDriver driver) {
        try {
            List<WebElement> brandPage = driver.findElements(or.getLocator("ExtractURL.BrandAndPgName"));
            List<WebElement> urls = driver.findElements(or.getLocator("ExtractURL.URLs"));
            if (brandPage.size() != 0) {
                for (int i = 1; i < brandPage.size(); i++) {
                    brandAndPage = brandPage.get(i).getText().split("\\.");
                    String url = urls.get(i).getText();
                    pageMap.put(brandAndPage[1], url);
                    if (!brandPage.get(i + 1).getText().split("\\.")[0].equals(brandAndPage[0])) {
                        brandMap.put(brandAndPage[0], pageMap);
                        pageMap = new HashMap<>();
                    }
                }
            } else {
                brandMap = null;
                log.warn("Unable to access Confluence Page. Url Map contains null value");
            }
        } catch (IndexOutOfBoundsException e) {
            brandMap.put(brandAndPage[0], pageMap);
            log.info("Urls fetched from Confluence Page and Stored in Map");
        }
    }

    /**
     * Author: Kundan Kumar
     * Date: 09/06/2022
     * Description: This method is implemented to fetch Urls from Map
     *
     * @return : HashMap(Map of Map)
     */
    public static HashMap<String, HashMap<String, String>> getUrls() {
        return brandMap;
    }
}
