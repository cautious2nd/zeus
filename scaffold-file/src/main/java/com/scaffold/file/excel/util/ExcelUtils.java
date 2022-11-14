package com.scaffold.file.excel.util;

import com.scaffold.file.excel.annotation.ExcelColumnName;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.scaffold.common.annotation.AnnotationUtils;
import org.scaffold.common.reflect.ReflectUtil;
import org.scaffold.common.string.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
                                if (temp != null && temp.equals(StringUtils.h2s(columnNames[cellNum]))) {
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InvalidFormatException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    private static String getValue(Cell cell) {
        if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }

    private static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");

    }

}
