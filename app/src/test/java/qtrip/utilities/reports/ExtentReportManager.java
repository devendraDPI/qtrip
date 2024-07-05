package qtrip.utilities.reports;

import java.io.File;
import com.relevantcodes.extentreports.ExtentReports;

public class ExtentReportManager {
    private static ExtentReports extentReports;
    private static final String REPORT_FILE_PATH = System.getProperty("user.dir") + File.separator + "reports" + File.separator + "qkartTestReport.html";
    private static final String EXTENT_CONFIG_FILE_PATH = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "extentConfig.xml";

    private ExtentReportManager() {}

    public static synchronized ExtentReports getReports() {
        if (extentReports == null) {
            extentReports = new ExtentReports(REPORT_FILE_PATH);
            extentReports.loadConfig(new File(EXTENT_CONFIG_FILE_PATH));
        }
        return extentReports;
    }
}
