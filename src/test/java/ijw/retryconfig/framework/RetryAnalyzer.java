package ijw.retryconfig.framework;

import org.testng.ITestResult;

public class RetryAnalyzer {
	
	private int retryCount = 0;
	private static final int maxRetryCount =1;
	

	public boolean retry(ITestResult result) {
		if(retryCount<maxRetryCount)
		{
			System.out.println("Retrying.....");
			retryCount++;
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

}
