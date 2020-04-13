package listeners;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import udemy.driverconfig.Base;


public class Listeners implements ITestListener {/*
	public static Logger log=LogManager.getLogger(Listeners.class.getName());
	@Override
	public void onTestStart(ITestResult result) {
		
		
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String resName=result.getName();
		try {
			Base.takeScreenShot(resName);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Unable to capture screenshot");
		}
	
		
	}

	@Override
	public void onTestFailure(ITestResult result) {
		String resName=result.getName();
		try {
			Base.takeScreenShot(resName);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Unable to capture screenshot");
		}

	}

	@Override
	public void onTestSkipped(ITestResult result) {
		
		
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		
		
	}

	@Override
	public void onStart(ITestContext context) {
		
		
	}

	@Override
	public void onFinish(ITestContext context) {
		
		
	}
*/}
