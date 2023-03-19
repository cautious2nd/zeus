package com.scaffold.file.excel.util;

import com.scaffold.file.excel.annotation.ExcelColumnName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.scaffold.common.annotation.AnnotationUtils;
import org.scaffold.common.reflect.ReflectUtil;
import org.scaffold.common.string.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExcelUtils {

    public static <T> List<T> parse(File file, Class<T> clazz) {
        String[] columnNames = null;
        List<T> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        boolean isColumn = true;
        try {
            Workbook wb = null;
            if (isExcel2003(file.getName())) {
                wb = new HSSFWorkbook(new FileInputStream(file));
            } else {
                wb = new XSSFWorkbook(file);
            }
            Sheet sheet = null;
            Row row = null;
            Cell cell = null;
            // 循环工作表Sheet
            for (int numSheet = 0; numSheet < wb.getNumberOfSheets(); numSheet++) {
                sheet = wb.getSheetAt(numSheet);
                if (sheet == null) {
                    continue;
                }

                row = sheet.getRow(0);
                columnNames = new String[row.getLastCellNum()];
                for (int cellNum = 0; cellNum < columnNames.length; cellNum++) {
                    cell = row.getCell(cellNum);
                    columnNames[cellNum] = cell == null ? null : getValue(cell);// 获取字段
                }

                T t = null;
                // 循环行Row
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    t = clazz.newInstance();
                    // 循环列Cell
                    for (int cellNum = 0; cellNum < columnNames.length; cellNum++) {
                        cell = row.getCell(cellNum);
                        if (cell == null) {
                            continue;
                        }
                        // 拼装对象
                        for (Field field : fields) {
                            if (field.getAnnotation(ExcelColumnName.class) != null) {
                                Object temp = AnnotationUtils.getAnnotationValue(field, ExcelColumnName.class, "value");
                                if (temp != null && temp.equals(columnNames[cellNum])) {
                                    ReflectUtil.setFieldValue(t, field.getName(), getValue(cell));
                                    break;
                                }
                            }

                            if (field.getName().equals(StringUtils.h2s(columnNames[cellNum]))) {
                                ReflectUtil.setFieldValue(t, field.getName(), getValue(cell));
                                break;
                            }
                        }
                    }
                    if (t != null)
                        list.add(t);
                }
            }
            return list;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

    public static <T> File write(String dirPath, Class<T> clazz, List<T> list) throws IOException {
        File dicFile = new File(dirPath);
        dicFile.mkdirs();
        File file = File.createTempFile("javaTemp" + System.currentTimeMillis(), ".xlsx", dicFile);

        String[] columnNames = null;
        Field[] fields = clazz.getDeclaredFields();
        boolean isColumn = true;
        Workbook wb = null;
        if (isExcel2003(file.getName())) {
            wb = new HSSFWorkbook();
        } else {
            wb = new XSSFWorkbook();
        }
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);

        Font font = wb.createFont();
        font.setFontName("宋体");
        font.setFontHeightInPoints((short) 12);
        cellStyle.setFont(font);

        Sheet sheet = wb.createSheet();
        List<Field> fieldList = Arrays.stream(fields).filter(field -> field.getAnnotation(ExcelColumnName.class) != null).collect(Collectors.toList());
        Row row0 = sheet.createRow(0);
        ;////第一行
        for (int i = 0; i < fieldList.size(); i++) {
            Field field = fields[i];
            Cell cell = row0.createCell(i);
            Object temp = AnnotationUtils.getAnnotationValue(field, ExcelColumnName.class, "value");
            cell.setCellValue((String) temp);
            cell.setCellStyle(cellStyle);
        }

        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);
            Row row = sheet.createRow(i + 1);//第一行
            for (int ii = 0; ii < fieldList.size(); ii++) {
                Field field = fields[ii];
                Cell cell = row.createCell(ii);
                cell.setCellValue(String.valueOf(ReflectUtil.getFieldValue(t, field.getName())));
                cell.setCellStyle(cellStyle);
            }
        }

        OutputStream outputStream = null;
        outputStream = new FileOutputStream(file);
        wb.write(outputStream);
        outputStream.close();

        return file;
    }

    private static String getValue(Cell cell) {
        if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == CellType.NUMERIC) {

            return ((XSSFCell) cell).getRawValue();
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");

    }

}
