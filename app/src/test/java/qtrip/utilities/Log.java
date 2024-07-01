package qtrip.utilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Log {
    public static void log(String methodName, String message) {
        System.out.println(String.format("%s | %s | %s", getDateTime("yyyy-MM-dd HH:mm:ss"), methodName, message));
    }

    public static void log(String testCaseID, String testStep, String testMessage) {
        System.out.println(String.format("\n%s | %s | %s | %s\n", getDateTime("yyyy-MM-dd HH:mm:ss"), testCaseID, testStep, testMessage));
    }

    public static String getDateTime(String formatPattern) {
        LocalDateTime now = LocalDateTime.now();
        return now.format(DateTimeFormatter.ofPattern(formatPattern));
    }
}
