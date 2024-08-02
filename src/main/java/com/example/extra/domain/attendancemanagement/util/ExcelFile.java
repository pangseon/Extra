package com.example.extra.domain.attendancemanagement.util;

import com.example.extra.domain.attendancemanagement.dto.service.AttendanceManagementCreateExcelServiceResponseDto;
import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

@Slf4j
public class ExcelFile {
    private static final int ROW_START_INDEX = 0;
    private static final int COLUMN_START_INDEX = 0;
    private static final List<String> FIELD_NAMES = List.of("이름","은행","계좌번호","출근시각","퇴근시각","식사횟수");
    private final SXSSFWorkbook wb;
    private Sheet sheet;
    private final List<AttendanceManagementCreateExcelServiceResponseDto> data;
    public ExcelFile(List<AttendanceManagementCreateExcelServiceResponseDto> data) {
        this.data = data;
        this.wb = new SXSSFWorkbook();
        renderExcel();
    }

    private void renderExcel() {
        // Create sheet and render headers
        sheet = wb.createSheet();
        sheet.setColumnWidth(COLUMN_START_INDEX, 3500);
        sheet.setColumnWidth(COLUMN_START_INDEX+1, 5000);
        sheet.setColumnWidth(COLUMN_START_INDEX+2, 8000);
        sheet.setColumnWidth(COLUMN_START_INDEX+3, 5000);
        sheet.setColumnWidth(COLUMN_START_INDEX+4, 5000);
        sheet.setColumnWidth(COLUMN_START_INDEX+5, 2000);

        renderHeaders(sheet);
        if (data.isEmpty()) {
            return;
        }
        // Render Body
        int rowIndex = ROW_START_INDEX + 1;
        for (AttendanceManagementCreateExcelServiceResponseDto renderedData : data) {
            renderBody(renderedData, rowIndex);
            rowIndex += 1;
        }
    }

    private void renderHeaders(Sheet sheet) {
        CellStyle blueCellStyle = wb.createCellStyle();
        applyCellStyle(blueCellStyle, new Color(223, 235, 246));
        Row row = sheet.createRow(ExcelFile.ROW_START_INDEX);
        int columnIndex = ExcelFile.COLUMN_START_INDEX;
        for (String dataFieldName : ExcelFile.FIELD_NAMES) {
            Cell cell = row.createCell(columnIndex++);
            cell.setCellValue(dataFieldName);
            cell.setCellStyle(blueCellStyle);
        }
    }

    private void renderBody(
        AttendanceManagementCreateExcelServiceResponseDto data,
        int rowIndex
    ) {
        CellStyle bodyCellStyle = wb.createCellStyle();
        applyCellStyle(bodyCellStyle, new Color(255, 255, 255));

        CreationHelper createHelper = wb.getCreationHelper();
        short format = createHelper.createDataFormat().getFormat("yyyy-mm-dd hh:mm");
        CellStyle dateCellStyle = wb.createCellStyle();
        applyCellStyle(dateCellStyle, new Color(255, 255, 255));
        dateCellStyle.setDataFormat(format);

        Row row = sheet.createRow(rowIndex);
        int columnIndex = ExcelFile.COLUMN_START_INDEX;

        Cell cell = row.createCell(columnIndex++);
        cell.setCellValue(data.memberName());
        cell.setCellStyle(bodyCellStyle);

        cell = row.createCell(columnIndex++);
        cell.setCellValue(data.memberBank());
        cell.setCellStyle(bodyCellStyle);

        cell = row.createCell(columnIndex++);
        cell.setCellValue(data.memberAccountNumber());
        cell.setCellStyle(bodyCellStyle);

        cell = row.createCell(columnIndex++);
        cell.setCellValue(data.clockInTime());
        cell.setCellStyle(dateCellStyle);

        cell = row.createCell(columnIndex++);
        cell.setCellValue(data.clockOutTime());
        cell.setCellStyle(dateCellStyle);

        cell = row.createCell(columnIndex);
        cell.setCellValue(data.mealCount());
        cell.setCellStyle(bodyCellStyle);
    }

    private void applyCellStyle(CellStyle cellStyle, Color color) {
        XSSFCellStyle xssfCellStyle = (XSSFCellStyle) cellStyle;
        xssfCellStyle.setFillForegroundColor(new XSSFColor(color, new DefaultIndexedColorMap()));
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
    }

    public void write(OutputStream stream) throws IOException {
        try (stream) {
            wb.write(stream);
        } catch (Exception e) {
            log.error(String.valueOf(e));
        } finally {
            wb.close();
            wb.dispose();
        }
    }

}