package ijw.testrunner.framework;

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import ijw.testrunner.framework.SuiteFilePath;
import ijw.utils.framework.SuiteFileGenerator;
import ijw.config.framework.Base;

public class TestRunner extends Base {

	public static void main(String[] args) {

		// Create object of TestNG Class
		TestNG runner = new TestNG();
		SuiteFileGenerator.createSuiteXMLFile(SuiteFilePath.IJWWebBrowser.getSuiteName(),SuiteFilePath.IJWWebBrowser.getTestName(),SuiteFilePath.IJWWebBrowser.getClassPath(),PROJECT_PATH+SuiteFilePath.IJWWebBrowser.getXmlFilePath());
		

		// Create a list of String
		List<String> suitefiles = new ArrayList<String>();

		/* Add xml file which you have to execute*/
		suitefiles.add(PROJECT_PATH + SuiteFilePath.IJWWebBrowser.getXmlFilePath());
		

		/* now set xml file for execution*/
		runner.setTestSuites(suitefiles);
		
		/* finally execute the runner using run method*/
		runner.run();
	}

}
