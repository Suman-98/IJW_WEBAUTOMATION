package ijw.listener.framework;

import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import ijw.report.framework.ReportConfiguration;
import ijw.utils.framework.TakeScreenShots;
import ijw.utils.framework.TestResult;
import ijw.pageaction.framework.FunctionalComponents;

public class Listeners extends TestListenerAdapter implements ITestListener {

	ExtentTest test;
	ExtentReports extent;
	public int testPassed = 1;
	public int testFailed = 1;
	public int testSkipped = 1;
	public int testExecuted = 1;
	public WebDriver driver;
	public static Properties prop;
	public static long startTime;
	public static long endTime;

	public static void main(String[] args) {

	}

	public String caculateTestDuration(long end, long start) {

		String timeDifferenceDetailed;

		long timeDifferenceMiliseconds = (Math.subtractExact(end, start));
		long timeDifferenceSeconds = TimeUnit.MILLISECONDS.toSeconds(timeDifferenceMiliseconds);
		long timeDifferenceMinutes = TimeUnit.SECONDS.toMinutes(timeDifferenceSeconds);

		System.out.println("This is time difference in Mililis " + timeDifferenceMiliseconds);
		System.out.println("This is time difference in Minutes " + timeDifferenceMinutes);
		System.out.println("This is time difference in Seconds " + timeDifferenceSeconds);

		if (timeDifferenceMinutes >= 60) {
			long timeDifferenceHours = timeDifferenceMinutes / 60;

			timeDifferenceDetailed = Long.toString(timeDifferenceHours) + " hour(s), "
					+ Long.toString(timeDifferenceMinutes % 60) + " minute(s), "
					+ Long.toString(timeDifferenceSeconds % 60) + " second(s)";
		} else {
			timeDifferenceDetailed = Long.toString(timeDifferenceMinutes) + " minute(s), "
					+ Long.toString(timeDifferenceSeconds % 60) + " second(s)";
		}

		System.out.println("This is time difference " + timeDifferenceDetailed);
		return timeDifferenceDetailed;

	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		test = extent.createTest(result.getMethod().getMethodName());
		FunctionalComponents.getExtentTest(test);
		TestResult.Total_Test_runs = testExecuted++;

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

		test.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " - Test Case PASSED", ExtentColor.GREEN));
		TestResult.Passed = testPassed++;
	}

	@Override
	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
			test.info("Failure Screenshot: ", MediaEntityBuilder
					.createScreenCaptureFromBase64String(TakeScreenShots.captureWebScreenshot(driver)).build());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		test.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " - Test Case Failed", ExtentColor.RED));

		TestResult.Failed = testFailed++;
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub

		if (result.getThrowable().getMessage() == null) {
			extent.removeTest(test);
			testExecuted--;
		} else {
			test.log(Status.SKIP,
					MarkupHelper.createLabel(result.getName() + " - Test Case Skipped", ExtentColor.ORANGE));
			TestResult.Skipped = testSkipped++;
		}
	}

	@Override
	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub
		ReportConfiguration rc = new ReportConfiguration();
		extent = rc.getReportObjectWeb();
		Calendar calendar = Calendar.getInstance();
		startTime = calendar.getTimeInMillis();
		System.out.println("This is start time " + startTime);
	}

	@Override
	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		Iterator<ITestResult> skippedTestCases = context.getSkippedTests().getAllResults().iterator();
		while (skippedTestCases.hasNext()) {
			ITestResult skippedTestCase = skippedTestCases.next();
			ITestNGMethod method = skippedTestCase.getMethod();
			if (context.getSkippedTests().getResults(method).size() > 0) {
				System.out.println("Removing:" + skippedTestCase.getTestClass().toString());
				skippedTestCases.remove();
			}
		}

		Calendar calendar = Calendar.getInstance();
		endTime = calendar.getTimeInMillis();
		System.out.println("This is end time " + endTime);
		extent.flush();
		TestResult.Automation_Effort = caculateTestDuration(endTime, startTime);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub

	}

}
