package tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {

	public WebDriver driver;
	String propPath = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\wheelnames\\sauceCredentials.properties";
	String path = "C:\\Users\\zikaz\\OneDrive\\Desktop\\projects\\wheelnames\\geckodriver.exe";
	String downloadDir = "C:\\Users\\zikaz\\OneDrive\\Desktop";

	public String getProperty(String property) throws IOException {
		Properties sauceData = new Properties();
		FileInputStream inputStream = new FileInputStream(propPath);
		sauceData.load(inputStream);
		return sauceData.getProperty(property);
	};

	public String getSauceUrl() throws IOException {
		String sauceURL = "https://" + getProperty("username") + ":" + getProperty("password")
				+ "@ondemand.saucelabs.com:443/wd/hub";
		return sauceURL;
	};

@BeforeTest
	public void setupLocalMozzila() {
    double zoomLevel = 0.8;
    FirefoxProfile profile = new FirefoxProfile();
    profile.setPreference("layout.css.devPixelsPerPx", String.valueOf(zoomLevel));
    
    
	FirefoxOptions options = new FirefoxOptions();
	//options.setProfile(profile);
    options.addPreference("dom.webnotifications.enabled", false);
    options.addPreference("browser.zoom.siteSpecific", false);  
//    options.addPreference("zoom.minPercent", 75);  
//    options.addPreference("zoom.maxPercent", 75);  
    options.addPreference("browser.zoom.full", true);

		System.setProperty("webdriver.gecko.driver", path);
		driver = new FirefoxDriver(options);
		driver.manage().window().maximize();

	}
	
//	  @BeforeTest
	    @Parameters({"browser"})
	    public void setupGrid(String browser) {
	        String gridUrl = "http://localhost:4444/wd/hub";        
	        try {
	            if (browser.equalsIgnoreCase("firefox")) {
	                FirefoxOptions firefoxOptions = new FirefoxOptions();
	                firefoxOptions.addPreference("dom.webnotifications.enabled", false);
	                firefoxOptions.addPreference("browser.zoom.siteSpecific", false);
	                firefoxOptions.addPreference("zoom.minPercent", 75);
	                firefoxOptions.addPreference("zoom.maxPercent", 75);
	                firefoxOptions.addPreference("browser.zoom.full", true);

	                URL hubUrl = new URL(gridUrl);
	                driver = new RemoteWebDriver(hubUrl, firefoxOptions);
	            } 
	            else if (browser.equalsIgnoreCase("edge")) {
	                EdgeOptions edgeOptions = new EdgeOptions();
	                edgeOptions.setExperimentalOption("prefs", Map.of(
	                    "dom.webnotifications.enabled", false,
	                    "browser.zoom.siteSpecific", false,
	                    "zoom.minPercent", 75,
	                    "zoom.maxPercent", 75,
	                    "browser.zoom.full", true
	                ));

	                URL hubUrl = new URL(gridUrl);
	                driver = new RemoteWebDriver(hubUrl, edgeOptions);
	            }
	            else if (browser.equalsIgnoreCase("chrome")) {
	                ChromeOptions chromeOptions = new ChromeOptions();
	                chromeOptions.addArguments("start-maximized");
	                chromeOptions.addArguments("--disable-notifications");
	                chromeOptions.addArguments("--disable-extensions");
	                chromeOptions.addArguments("--disable-gpu");

	                URL hubUrl = new URL(gridUrl);
	                driver = new RemoteWebDriver(hubUrl, chromeOptions);
	            }
	            driver.manage().window().maximize();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	
	
	

	//@AfterTest
	public void shutDown() {
		if (driver != null) {
			driver.quit();
		}
	}

	//@BeforeClass
	//@Parameters({ "platform", "browser" })
	public void setUpForSauceLabs(String platform) throws Exception {
		ChromeOptions browserOptions = new ChromeOptions();
		browserOptions.setPlatformName(platform);
		browserOptions.setBrowserVersion("latest");
		Map<String, Object> sauceOptions = new HashMap<>();
		sauceOptions.put("username", getProperty("username"));
		sauceOptions.put("accessKey", getProperty("password"));
		sauceOptions.put("build", "55");
		sauceOptions.put("name", "myTestOf wheel");
		browserOptions.setCapability("sauce:options", sauceOptions);

		URL url = new URL("https://ondemand.eu-central-1.saucelabs.com:443/wd/hub");
		driver = new RemoteWebDriver(url, browserOptions);
	}

}
