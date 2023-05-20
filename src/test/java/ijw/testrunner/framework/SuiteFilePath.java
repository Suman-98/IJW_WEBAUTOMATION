package ijw.testrunner.framework;

public enum SuiteFilePath {

	IJWWebBrowser("IJWWeb","Test IJW In Chrome Browser","ijw.testscripts.framework.TestIJW","/IJWBrowserSuite.xml");
	
	private String suiteName;
	private String testName;
	private String classPath;
	private String xmlFilePath;
	
	SuiteFilePath(String suiteName, String testName, String classPath, String xmlFilePath)
	{
		this.suiteName = suiteName;
		this.testName = testName;
		this.classPath = classPath;
		this.xmlFilePath = xmlFilePath;
	}
	
	public String getSuiteName()
	{
		return suiteName;
	}

	public String getTestName()
	{
		return testName;
	}
	public String getClassPath()
	{
		return classPath;
	}
	public String getXmlFilePath()
	{
		return xmlFilePath;
	}
}

