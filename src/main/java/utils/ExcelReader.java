package utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

public class ExcelReader {

    public static HashMap<String, HashMap<String, HashMap<String, Object>>> sheetMap = new HashMap<>();

    /**
     * Author: Kundan Kumar & Priya Bharti
     * Date: 3/15/2022(Modified)
     * Description: This method is to Read the entire Excel and create A HashMap on the basis of sheet, row and column
     */
    public void readExcel() {
        try {
            String fileName = "Style.xlsx";
            File file = new File("./TestData/UITestData/ExcelFiles/" + fileName);
            FileInputStream inputStream = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook(inputStream);
            int totalSheet = wb.getNumberOfSheets();
//            XSSFSheet sheet = wb.getSheet(sheetName);
            if (totalSheet > 0) {
//                System.out.println("Total Sheet Found:" + totalSheet);
                for (int k = 0; k < totalSheet; k++) {
//                    System.out.println("Sheet Name:" + wb.getSheetName(k));
                    XSSFSheet Sheet1 = wb.getSheetAt(k);
//                        System.out.println("Sheet Name:" + wb.getSheetName(k));
                    int rowSize = Sheet1.getPhysicalNumberOfRows();
                    int colSize = Sheet1.getRow(0).getPhysicalNumberOfCells();
                    HashMap<String, HashMap<String, Object>> rowMap = new HashMap<>();
                    try {
                        for (int i = 1; i < rowSize; i++) {
                            HashMap<String, Object> colMap = new HashMap<>();
                            for (int j = 1; j < colSize; j++) {
                                String cell = Sheet1.getRow(0).getCell(j).getStringCellValue();
//                                System.out.println(i+"---"+j);
//                                System.out.println(colMap);
                                CellType type = Sheet1.getRow(i).getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).getCellType();
                                if (type == CellType.STRING) {
                                    colMap.put(cell.toLowerCase(), Sheet1.getRow(i).getCell(j).getStringCellValue());
                                } else if (type == CellType.NUMERIC) {
                                    colMap.put(cell.toLowerCase(), Sheet1.getRow(i).getCell(j).getNumericCellValue());
                                } else if (type == CellType.BLANK) {
                                    colMap.put(cell.toLowerCase(), "");
                                }
                            }
                            rowMap.put(Sheet1.getRow(i).getCell(0).getStringCellValue().toLowerCase(), colMap);
                        }
                    } catch (Exception e) {
                        System.err.println("Sheet Map : " + sheetMap.keySet());
                        e.printStackTrace();
                    } finally {
                        sheetMap.put(wb.getSheetName(k), rowMap);
                    }
//                    System.out.println(sheetMap);
                }
            }
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Author: Kundan Kumar & Priya Bharti
     * Date: 3/15/2022(Modified)
     * Description: This is to get the value from the MAP which is created from readExcel() based on the
     * Brand name from the current URL
     * Key provided in the class file
     * and column theme is the combination of the getTheme() from ConfigReader and current Browser from pom.xml
     */
    public String getCSSValueUsingKey(String brand, String key, String columnTheme) {
        return sheetMap.get(brand).get(key).get(columnTheme).toString();
    }

    /**
     * Author: Kundan Kumar & Priya Bharti
     * Date: 3/15/2022(Modified)
     * Description: This is to get the value from the MAP which is created from readExcel() based on the
     * BrandStyle name from the current URL
     * Tag provided in the class file
     * and column Name is the CSS property which we have to validate
     */
    public String getCSSValueUsingTag(String brandStyle, String tag, String columnName) {
        return sheetMap.get(brandStyle).get(tag).get(columnName).toString();
    }

    /**
     * Author: Shubham Kumar
     * Date: 4/11/2022
     * Description: This is to get the mapping of the pageName and pageUrls, based on the brand
     * for accessibility testing parameters.
     */
    public HashMap<String, String> getPageUrlMappingForAccessibility(String brand) throws IOException {
        HashMap<String,String> pageUrlMapping = new HashMap<>();
        String pageUrlSheet = brand;
        String analyticsExcelPath = "./TestData/AccessibilityTestData/ExcelFiles/PageURLs.xlsx";
        for (int i = 2; i <= numberOfRows(analyticsExcelPath, pageUrlSheet) + 1; i++){
            String pageName = readFromExcel(analyticsExcelPath, pageUrlSheet, i, 1);
            String pageUrl = readFromExcel(analyticsExcelPath, pageUrlSheet, i, 2);
            pageUrlMapping.put(pageName,pageUrl);
            pageUrlMapping.remove("");
        }
        return pageUrlMapping;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new ExcelReader().getPageUrlMappingForAccessibility("E-revive"));
        System.out.println(new ExcelReader().getPageUrlMappingForAccessibility("E-revive").size());
    }

    String fileExtension;
    Workbook workbook;
    Sheet iSheet;
    FileInputStream iFile;
    Row oRow;
    Cell oCell;

    /**
     * Author: Shubham Kumar
     * Date: 4/11/2022
     * Description: This is to read from a specific row and column from accessibility excel data
     */

    private String readFromExcel(String analyticsExcelPath, String pageUrlSheet, int iRow, int iCol) throws IOException {
        String cellvalue = null;
        try {
            DataFormatter df = new DataFormatter();
            iFile = new FileInputStream(new File(analyticsExcelPath));
            fileExtension = FilenameUtils.getExtension(analyticsExcelPath);
            workbook = createWorkbook(fileExtension);
            iSheet = workbook.getSheet(pageUrlSheet);
            oRow = iSheet.getRow(iRow - 1);
            oCell = oRow.getCell(iCol - 1);
            cellvalue = df.formatCellValue(oCell);
        } catch (NullPointerException e) {
            System.out.println("No data at Row " + iRow);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            workbook.close();
        }
        return cellvalue;
    }
    /**
     * Author: Shubham Kumar
     * Date: 4/11/2022
     * Description: This returns the total number of rows.
     */
    public int numberOfRows(String analyticsExcelPath, String pageUrlSheet) throws IOException {
        iFile = new FileInputStream(new File(analyticsExcelPath));
        fileExtension = FilenameUtils.getExtension(analyticsExcelPath);
        workbook = createWorkbook(fileExtension);
        iSheet = workbook.getSheet(pageUrlSheet);
        return iSheet.getLastRowNum();
    }
    /**
     * Author: Shubham Kumar
     * Date: 4/11/2022
     * Description: This creates a workbook based on extension.
     */

    private Workbook createWorkbook(String extension) throws IOException {
        switch (extension.toLowerCase()) {
            case "xls":
                workbook = new HSSFWorkbook(iFile);
                break;
            case "xlsx":
                workbook = new XSSFWorkbook(iFile);
                break;
            default:
                workbook = new XSSFWorkbook(iFile);
        }
        return workbook;
    }
}