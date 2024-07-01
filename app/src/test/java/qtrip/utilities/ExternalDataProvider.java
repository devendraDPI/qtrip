package qtrip.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExternalDataProvider {
    @DataProvider(name="qtripTestDataSet")
    public Object[][] externalDataProvider(Method methodName) {
        int rowIndex = 0;
        int cellIndex = 0;
        List<List<String>> testData = new ArrayList<>();
        FileInputStream excelFile = null;
        Workbook workbook = null;

        try {
            // Define the path to the Excel file
            String filePath = System.getProperty("user.dir") + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator + "qtripTestDataSet.xlsx";
            excelFile = new FileInputStream(new File(filePath));

            // Open the Excel workbook
            workbook = new XSSFWorkbook(excelFile);

            // Get the sheet based on the test method name
            Sheet sheet = workbook.getSheet(methodName.getName());
            Iterator<Row> iterator = sheet.iterator();

            // Iterate through each row in the sheet
            while (iterator.hasNext()) {
                Row row = iterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> rowData = new ArrayList<>();

                // Iterate through each cell in the row
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();

                    // Process cell contents based on type
                    if (rowIndex > 0 && cellIndex > 0) {
                        if (cell.getCellType() == CellType.STRING) {
                            rowData.add(cell.getStringCellValue());
                        } else if (cell.getCellType() == CellType.NUMERIC) {
                            rowData.add(String.valueOf(cell.getNumericCellValue()));
                        } else if (cell.getCellType() == CellType.BOOLEAN) {
                            rowData.add(String.valueOf(cell.getBooleanCellValue()));
                        } else {
                            rowData.add("");
                        }
                    }

                    cellIndex += 1;
                }

                // Move to the next row and reset cell index
                rowIndex += 1;
                cellIndex = 0;

                // Add row data to test data list if it contains any values
                if (rowData.size() > 0) {
                    testData.add(rowData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources in finally block to ensure they are always closed
            try {
                if (excelFile != null) {
                    excelFile.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Convert List<List<String>> to Object[][] for TestNG data provider
        String[][] stringArray = testData.stream().map(o -> o.toArray(new String[0])).toArray(String[][]::new);
        return stringArray;
    }
}
