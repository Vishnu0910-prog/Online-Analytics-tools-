package com.grd.online.paper.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class XlsxReaderUtil {

    static public List<List<String>> readExcelFromFile(String filePath, Boolean skipFirstRow) throws IOException {
        try (InputStream inputStream = new FileInputStream(filePath)) {
            return _readXLSX(inputStream, skipFirstRow);
        } catch (IOException e) {
            return null;
        }
    }

    static public List<List<String>> readExcelFromMultipart(MultipartFile multipartFile, Boolean skipFirstRow)
            throws IOException {
        try {
            return _readXLSX(multipartFile.getInputStream(), skipFirstRow);
        } catch (IOException e) {
            return null;
        }
    }

    @SuppressWarnings("resource")
    static List<List<String>> _readXLSX(InputStream inputStream, Boolean skipFirstRow) throws IOException {
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0); // Read the first sheet
        List<List<String>> sheetData = new ArrayList<>();

        boolean firstRowSkipped = !skipFirstRow;
        for (Row row : sheet) {

            if (!firstRowSkipped) {
                firstRowSkipped = true;
                continue;
            }

            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(_cellToString(cell));
            }
            sheetData.add(rowData);
        }
        return sheetData;
    }

    static private String _cellToString(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return Boolean.toString(cell.getBooleanCellValue());
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return Double.toString(cell.getNumericCellValue());
                }
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "Unknown Cell Type";
        }
    }
}