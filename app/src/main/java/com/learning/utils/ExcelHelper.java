package com.learning.utils;

/*

import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
*/
import java.io.File;

import java.io.FileOutputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/6/27
 * email:          ccie20079@126.com
 */
/*
public class ExcelHelper {

    @SuppressWarnings("unchecked")
    public static <T> void saveListToExcel(Class clazz,List<T> list, File file){
        Field[] fields = clazz.getDeclaredFields();

        SXSSFWorkbook wb = new SXSSFWorkbook();
        SXSSFSheet sh = wb.createSheet();
        SXSSFRow row = sh.createRow(0);

        for(int i = 0; i<fields.length;i++){
            SXSSFCell cell = row.createCell(i);
            cell.setCellValue(fields[i].getName());
        }

        for(int i=0;i<list.size();i++){
            row = sh.createRow(i+1);
            T t = list.get(i);

            for(int colIndex=0;colIndex<fields.length;colIndex++){
                SXSSFCell cell = row.createCell(colIndex);
                String fieldName = fields[colIndex].getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Class tCls = t.getClass();
                Method getMethod = null;
                Object value = null;
                try {
                    getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    value = getMethod.invoke(t, new Object[] {});
                } catch (Exception e) {
                    e.printStackTrace();
                }
                String content =( null==value?"":value.toString());
                // 鍒ゆ柇鍊肩殑绫诲瀷鍚庤繘琛屽己鍒剁被鍨嬭浆鎹�
                cell.setCellValue(content);
            }
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 */
