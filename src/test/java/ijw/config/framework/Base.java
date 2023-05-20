package ijw.config.framework;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Base {

	public static Properties prop;
	private WebDriver driver;
	public static final String PROJECT_PATH = System.getProperty("user.dir");

	/* Constructor */
	public Base() 
	{
		prop = readProperty(PROJECT_PATH + "/src/test/java/ijw/properties/framework/data.properties");
	}

	/* Method to select different browser from properties file */
	public WebDriver initializeWebDriver() throws Exception {

		if (prop.getProperty("browser").equals("chrome")) {
			//System.setProperty("webdriver.chrome.driver", prop.getProperty("chromedriverpath"));
			//System.setProperty("webdriver.chrome.silentOutput","true");
			ChromeOptions options = new ChromeOptions();
			WebDriverManager.chromedriver().setup();
			options.addArguments("--process-per-tab");
			options.addArguments("--process-per-site");
			options.addArguments("--disable-plugins");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-notifications");
			options.addArguments("--disable-gpu");
			options.addArguments("--media-cache-size=1 --disk-cache-size=1");
			driver = new ChromeDriver(options);
		}
		else if (prop.getProperty("browser").equals("firefox")) {
			//System.setProperty("webdriver.gecko.driver", prop.getProperty("firefoxdriverpath"));
			WebDriverManager.firefoxdriver().setup();
			driver = new FirefoxDriver();
			
		}
		else if (prop.getProperty("browser").equals("Edge")) {
			//System.setProperty("webdriver.ie.driver", prop.getProperty("IEdriverpath"));
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		return driver;
	}

	/*Assigning the 'driver' from method parameter to class parameter*/ 
	public void getDriver(WebDriver driver) {
		this.driver = driver;
	}

	/*Exiting the browser Instance*/
	public void stopTest()
	{
		driver.close();
		driver.quit();
	}

	/*To read the data from Properties file*/
	public Properties readProperty(String filePath) {
		prop = new Properties();
		
		try {
			FileInputStream fis = new FileInputStream(filePath);
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;
	}
	
		
	
	/*To write the data into properties file*/
	public Properties writeProperty(String key, String value) {
		Properties properties = new Properties();
		try(OutputStream outputStream = new FileOutputStream(PROJECT_PATH + "/src/test/java/ijw/properties/framework/data.properties"))
		{  
		    properties.setProperty(key, value);
		    properties.store(outputStream, null);
		} catch (IOException e) {
		    e.printStackTrace();
		}
		return properties;
	}
	
	/*To Update the data into properties file*/
	public static Properties updateProperty(String key, String value)  {	
		Properties props = new Properties();
	
		try {
		FileInputStream in = new FileInputStream(PROJECT_PATH + "/src/test/java/ijw/properties/framework/data.properties");
		props.load(in);
		in.close();
		FileOutputStream out = new FileOutputStream(PROJECT_PATH + "/src/test/java/ijw/properties/framework/data.properties");
		props.setProperty(key, value);
		props.store(out, null);
		out.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
}
