package ijw.utils.framework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;


public class TakeScreenShots {
	
	public static String captureWebScreenshot(WebDriver driver)
	{
		File srcFile =  ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String encodedBase64 = null;
        FileInputStream fileInputStreamReader = null;
        try {
        	fileInputStreamReader = new FileInputStream(srcFile);
        	byte[] bytes = new byte[(int)srcFile.length()];
        	fileInputStreamReader.read(bytes);
        	encodedBase64 = new String(Base64.encodeBase64(bytes));
        }
        catch(FileNotFoundException f)
        {f.printStackTrace();
        }
        catch(IOException e)
        {
        	e.printStackTrace();	
        }
//        return "screenshots/" + imageName + ".png";
//        return newScreenshotFilePath;
        return "data:image/png;base64, "+encodedBase64;
		
		
	}

}
