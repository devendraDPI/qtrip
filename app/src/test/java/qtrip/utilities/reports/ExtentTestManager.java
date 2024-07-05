package qtrip.utilities.reports;

import java.util.HashMap;
import java.util.Map;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class ExtentTestManager {
    private static ExtentReports extentReports = ExtentReportManager.getReports();
    private static Map<Object, Object> extentMap = new HashMap<>();

    public static ExtentTest startTest(String testName) {
        ExtentTest extentTest = extentReports.startTest(testName);
        extentMap.put((int) (long) Thread.currentThread().threadId(), extentTest);
        return extentTest;
    }

    public static ExtentTest getTest() {
        return (ExtentTest) extentMap.get((int) (long) Thread.currentThread().threadId());
    }

    public static void testLogger(LogStatus status, String description) {
        getTest().log(status, description);
    }

    public static void endTest() {
        extentReports.endTest(getTest());
    }
}
