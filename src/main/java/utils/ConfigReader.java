package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import pagefactory.ReusableMethodUI;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class ConfigReader {

    public static String captchaUrl(String brand) throws FileNotFoundException {
        String Value = null;
        try {
            String Env = System.getProperty("Env").toUpperCase();
            if (Env.equalsIgnoreCase("PROD")) {
                FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/Url.properties");
                Properties p = new Properties();
                p.load(reader);
                Value = p.getProperty(brand + "_" + Env + "_Cookie");
            } else {
                FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/Url.properties");
                Properties p = new Properties();
                p.load(reader);
                Value = p.getProperty(brand + "_" + "Cookie");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public static String getURL(String brand) throws FileNotFoundException {
        String Value = null;
        try {
            String Env = System.getProperty("Env").toUpperCase();
            FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/Url.properties");
            Properties p = new Properties();
            p.load(reader);
            if(Env.equals("PROD")){
                Value = p.getProperty(brand + "_" + Env + "_Url");
            }else {
                Value = p.getProperty(brand + "_Url");
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }
    public static String getURL(String brandFrom, String brandTo) throws FileNotFoundException {
        String Value = null;
        try {
            FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/Url.properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty("Deeplink_" + brandFrom + "To" + brandTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public static String getAuthInstCompURL(String component, String brand) throws FileNotFoundException {
        String Value = null;
        try {
            System.out.println(component + " URL will be launched");
            FileReader reader;
            if (component.contains("-")) {
                String ComponentName[] = component.split("-");
                reader = new FileReader("./TestData/UITestData/AEMComponents/" + ComponentName[0] + ".properties");
            } else
                reader = new FileReader("./TestData/UITestData/AEMComponents/" + component + ".properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty("Auth_" + brand + "_" + component);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public static String getAuthorURL(String AuthorPage, String brand) throws FileNotFoundException {
        String Value = null;
        try {
            System.out.println(AuthorPage + " URL will be launched");
            String Env = System.getProperty("Env").toUpperCase();
            FileReader reader;
            reader = new FileReader("./TestData/UITestData/AEMComponents/AuthorURL.properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty(brand + "_" + Env + "_" + AuthorPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public static String getPublishComponentURL(String component, String brand) throws FileNotFoundException {
        String Value = null;
        try {
            FileReader reader;
            if (component.contains("-")) {
                String ComponentName[] = component.split("-");
                reader = new FileReader("./TestData/UITestData/AEMComponents/" + ComponentName[0] + ".properties");
            } else
                reader = new FileReader("./TestData/UITestData/AEMComponents/" + component + ".properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty("Pub_" + brand + "_" + component);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public static String getAEMURL() throws FileNotFoundException {
        String Value = null;
        try {
            String Env = System.getProperty("Env").toUpperCase();
            FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/AEM.properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty("AEM_URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public String getUserDetails(String brand, String userType) throws FileNotFoundException {
        String Value = null;
        try {
            String Env = System.getProperty("Env").toUpperCase();
            if (Env.equalsIgnoreCase("PROD")) {
                FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/UserCredential.properties");
                Properties p = new Properties();
                p.load(reader);
                Value = p.getProperty(brand + "_" + Env + "_" + userType);
            } else {
                FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/UserCredential.properties");
                Properties p = new Properties();
                p.load(reader);
                Value = p.getProperty(brand + "_" + userType);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public String getOktaUserDetails() throws FileNotFoundException {
        String Value = null;
        try {
            String userName = System.getProperty("UserName");
            FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/AEM.properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty("OKTA" + "_" + userName);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return Value;
    }


    public static String getPageURL(String pageName) {
        String Value = null;
        try {
            FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/AEM.properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty(pageName + "_URL");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public String getSystemEnv() {
        String Env = System.getProperty("Env").toUpperCase();
        if (Env.equalsIgnoreCase("Prod"))
            Env = "PROD";
        else if (Env.equalsIgnoreCase("PreProd"))
            Env = "PREPROD";
        else if (Env.equalsIgnoreCase("DEV"))
            Env = "DEV";
        else if (Env.equalsIgnoreCase("STAGE"))
            Env = "STAGE";
        else
            Env = "QA";
        return Env;
    }

    public String getBrowser() {
        return System.getProperty("BrowserName").toLowerCase();
    }


    public String getTheme(String brand) {
        String Value = null;
        try {
            FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/Theme.properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty(brand + "_" + "Theme");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public String getValidationType() {
        return System.getProperty("ValidationType").toLowerCase();
    }

    public String getTestData(String Component, String key, String Brand) throws FileNotFoundException {
        String Value = null;
        try {
            FileReader reader;
            reader = new FileReader("./TestData/UITestData/AEMComponents/" + Component + ".properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty(key + "_" + Brand);
        } catch (IOException e) {

            e.printStackTrace();
        }
        return Value;
    }

    public String getBSUserDetails(String credType) {
        String Value = null;
        try {
            String user = System.getProperty("UserName").substring(0, 1).toUpperCase() + System.getProperty("UserName").substring(1);
            FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/BSCredential.properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty(user + "_" + credType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public String getAxeRunParams() {
        String content = "";
        try {
            content = new String(Files.readAllBytes(Paths.get("./TestData/AccessibilityTestData/AxeRunParams/axeRunParams.yaml")));
            String json = convertYamlToJson(content);
            return json;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String convertYamlToJson(String yaml) {
        try {
            ObjectMapper yamlReader = new ObjectMapper(new YAMLFactory());
            Object obj = yamlReader.readValue(yaml, Object.class);
            ObjectMapper jsonWriter = new ObjectMapper();
            return jsonWriter.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String getProperty(String path, String key) throws FileNotFoundException {
        String Value = null;
        try {
            FileReader reader = new FileReader(path);
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public static void setProperty(String path, String key, String value) throws FileNotFoundException {
        try {
            FileOutputStream out = new FileOutputStream(path);
            Properties props = new Properties();
            props.setProperty(key, value);
            props.store(out, null);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getComponentURL(String component, String brand) {
        String Value = null;
        try {
            FileReader reader;
            if (component.contains("-")) {
                String ComponentName[] = component.split("-");
                reader = new FileReader("./TestData/UITestData/AEMComponents/" + ComponentName[0] + ".properties");
            } else
                reader = new FileReader("./TestData/UITestData/AEMComponents/" + component + ".properties");
            Properties p = new Properties();
            p.load(reader);
            Value = p.getProperty(brand + "_" + component);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Value;
    }

    public static void main(String[] args) throws IOException {
        FileReader reader = new FileReader("./TestData/UITestData/EnvironmentTestData/Url.properties");
        Properties p = new Properties();
        p.load(reader);
        System.out.println(p.getProperty("E-revive_PROD_Url"));

    }

}

