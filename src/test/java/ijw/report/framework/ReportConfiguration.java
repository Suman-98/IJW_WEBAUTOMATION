package ijw.report.framework;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import ijw.config.framework.Base;

@SuppressWarnings("deprecation")
public class ReportConfiguration extends Base {

	private static ExtentReports extent;
	static Properties reportDetails;

	public ExtentReports getReportObjectWeb()

	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		LocalDateTime now = LocalDateTime.now();
		String date=dtf.format(now);
		String path = System.getProperty("user.dir") + "/reports/WebApp/summary-" + date + ".html";
		reportDetails = updateProperty("Reportpath", path);
		ExtentHtmlReporter reporter = new ExtentHtmlReporter(path);
		reporter.config().setReportName("Web Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		reporter.config().setTheme(Theme.STANDARD);
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		extent.setSystemInfo("Tester", "QA");
		extent.setSystemInfo("Environment", "QA");
		extent.setSystemInfo("Platform", "Web App");
		return extent;

	}

}
