/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scrape.common;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author KeyLove
 */
public class ExcelUtil {

    public XSSFWorkbook workbook;
    public CellStyle currency;
    public CellStyle date;
    public CellStyle number;

    public ExcelUtil(XSSFWorkbook workbook) {
        this.workbook = workbook;
        init();
    }

    private void init() {
        currency = workbook.createCellStyle();
        date = workbook.createCellStyle();
        number = workbook.createCellStyle();
        currency.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));
        date.setDataFormat(workbook.createDataFormat().getFormat("dd/mm/yyyy"));
        number.setDataFormat(workbook.createDataFormat().getFormat("#,###"));
    }

    public void formatNumber(Row row, int column, Object data, CellStyle style) {
        Cell cell = row.createCell(column);
        if (style != null) {
            cell.setCellStyle(style);
        }
        cell.setCellValue(s2d(data));
    }

    public void formatDate(Row row, int column, Date data, CellStyle style) {
        Cell cell = row.createCell(column);
        if (style != null) {
            cell.setCellStyle(style);
        }
        cell.setCellValue(data);
    }

    public Double s2d(Object decimal) {
        if (decimal == null) {
            return 0D;
        } else if (decimal instanceof BigDecimal) {
            return ((BigDecimal) decimal).doubleValue();
        } else if (decimal instanceof Integer) {
            return ((Integer) decimal).doubleValue();
        } else if (decimal instanceof BigInteger) {
            return ((BigInteger) decimal).doubleValue();
        } else if (decimal instanceof Double) {
            return (Double) decimal;
        } else if (decimal instanceof Long) {
            return ((Long) decimal).doubleValue();
        } else {
            return 0D;
        }
    }
}
