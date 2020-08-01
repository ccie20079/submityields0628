package com.learning.utils;

/**
 * Package_name:   com.learning.utils
 * user:           Administrator
 * date:           2020/6/27
 * email:          ccie20079@126.com
 */
import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.learning.gson.BeanFileAnnotation;
import com.learning.gson.V_Overtime_Detail;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author dmrfcoder
 * @date 2018/8/9
 */
public class JxlExcelHelper {

    private static WritableFont arial14font = null;

    private static WritableCellFormat arial14format = null;
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private final static String UTF8_ENCODING = "UTF-8";


    private File xlsFile;
    private WritableWorkbook wb;
    public File getXlsFile() {
        return xlsFile;
    }

    public void setXlsFile(File xlsFile) {
        this.xlsFile = xlsFile;
    }

    public JxlExcelHelper(File xlsfile) {
      this.xlsFile = xlsfile;
    }

    public WritableWorkbook getWb() {
        return wb;
    }

    public void setWb(WritableWorkbook wb) {
        this.wb = wb;
    }

    /**
     * 单元格的格式设置 字体大小 颜色 对齐方式、背景颜色等...
     */

    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(jxl.format.Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(jxl.format.Alignment.CENTRE);
            arial14format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial14format.setBackground(jxl.format.Colour.VERY_LIGHT_YELLOW);

            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            arial10format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);

            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            //对齐格式
            arial10format.setAlignment(jxl.format.Alignment.CENTRE);
            //设置边框
            arial12format.setBorder(jxl.format.Border.ALL, jxl.format.BorderLineStyle.THIN);

        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param xlsFile
     * @param fields
     */

    public static void initExcel(File xlsFile, Field[] fields) {
        format();
        WritableWorkbook workbook = null;
        try {
            workbook = Workbook.createWorkbook(xlsFile);

            //设置表格的名字
            WritableSheet sheet = workbook.createSheet("账单", 0);
            //创建标题栏
            sheet.addCell((WritableCell) new Label(0, 0, "test", arial14format));
            for (int col = 0; col < fields.length; col++  ) {
                sheet.addCell(new Label(col, 0, fields[col].getName(), arial10format));

            }
            //设置行高
            sheet.setRowView(0, 340);
            workbook.write();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressWarnings("unchecked")
    public static <T> void writeObjListToExcel(Class<?> clazz,List<T> objList, File file,String sheetName, Context c) {
        format();
        if (objList != null && objList.size() > 0) {
            WritableWorkbook writebook = null;
           //InputStream in = null;
            try {
                WorkbookSettings setEncode = new WorkbookSettings();
                setEncode.setEncoding(UTF8_ENCODING);
                //in = new FileInputStream(file);
                //Workbook workbook = Workbook.getWorkbook(in);
                writebook = Workbook.createWorkbook(file,setEncode);
                //writebook = Workbook.createWorkbook(file, workbook);
                //WritableSheet sheet = writebook.getSheet(0);
                WritableSheet sheet = writebook.createSheet(sheetName, 0);
                /*
                String fieldName = fields[i].getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Class tCls = t.getClass();
                Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                Object value = getMethod.invoke(t, new Object[] {});
                 */
                Field[] fields =clazz.getDeclaredFields();
                List<Field> fieldList =GetOrderedFiled.getOrderedField(fields);
                Map<Integer, Integer> mapColMaxWidth = new HashMap<Integer,Integer>();
                //创建标题栏
                sheet.addCell((WritableCell) new Label(0, 0, "test", arial14format));
                for (int col = 0; col < fieldList.size(); col++  ) {
                    String fieldName =fieldList.get(col).getDeclaredAnnotation(BeanFileAnnotation.class).aliasName();
                    fieldName = "".equals(fieldName)?fieldList.get(col).getName():fieldName;
                    mapColMaxWidth.put(col,fieldName.length());
                    sheet.addCell(new Label(col, 0,fieldName , arial10format));
                }
                //设置行高
                sheet.setRowView(0, 340);
                for (int rowIndex = 0; rowIndex < objList.size(); rowIndex ++) {
                    T t = objList.get(rowIndex);

                    for(int colIndex = 0;colIndex<fieldList.size();colIndex++){    // 列 从第0列开始

                        String content = "";
                        String fieldName = fieldList.get(colIndex).getName();
                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method getMethod = clazz.getMethod(getMethodName, new Class[] {});
                        Object value = getMethod.invoke(t, new Object[] {});
                        content = null==value?"": value.toString();
                        sheet.addCell(new Label(colIndex+0, rowIndex+   1, content, arial12format));
                        if(mapColMaxWidth.get(colIndex)==null){
                            mapColMaxWidth.put(colIndex,content.length());
                        }
                        int contentWidth = content.length();
                        if(contentWidth>mapColMaxWidth.get(colIndex)){
                            mapColMaxWidth.put(colIndex,content.length());
                            //设置列宽
                            sheet.setColumnView(colIndex, content.length()+ 5);
                        }
                     }
                    //设置行高
                    sheet.setRowView(rowIndex+   1, 350);
                }
                writebook.write();
                Toast.makeText(c, "导出Excel成功: " + file.getAbsolutePath().toString(), Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (writebook != null) {
                    try {
                        writebook.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                /*
                if (in != null) {
                    try {
                        in.close();
                    } catch ( IOException e) {
                        e.printStackTrace();
                    }
                }
                */
            }
        }
    }

    /**
     * 首次创建文档
     */
    public void create()  {
        WorkbookSettings setEncode = new WorkbookSettings();
        setEncode.setEncoding(UTF8_ENCODING);
        //in = new FileInputStream(file);
        //Workbook workbook = Workbook.getWorkbook(in);
        try {
            wb = Workbook.createWorkbook(this.xlsFile,setEncode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void open() throws Exception{
       /*
        WorkbookSettings setEncode = new WorkbookSettings();
        setEncode.setEncoding(UTF8_ENCODING);
        wb = Workbook.getWorkbook(xlsFile,setEncode);
        */
    }
    public void close()  {
        try {
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }
    public WritableSheet getFirstSheet(String sheetName){
         WritableSheet[] sheets = wb.getSheets();
        if(sheets.length<1){
            return wb.createSheet(sheetName,0);
        }
        return sheets[0];
    }
    public WritableSheet getSecondSheet(String sheetName){
        WritableSheet[] sheets = wb.getSheets();
        if(sheets.length<2){
            return wb.createSheet(sheetName,1);
        }
        return sheets[1];
    }
    public WritableSheet getThirdSheet(String sheetName){
        WritableSheet[] sheets = wb.getSheets();
        if(sheets.length<3){
            return wb.createSheet(sheetName,2);
        }
        return sheets[2];
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public <T> void writeObjListToExcel(WritableSheet sheet,Class<?> clazz, List<T> objList,  Context c) {
        format();
        if (objList != null && objList.size() > 0) {
            //InputStream in = null;
            try {
                           /*
                String fieldName = fields[i].getName();
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Class tCls = t.getClass();
                Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                Object value = getMethod.invoke(t, new Object[] {});
                 */
                Field[] fields =clazz.getDeclaredFields();
                List<Field> fieldList =GetOrderedFiled.getOrderedField(fields);
                Map<Integer, Integer> mapColMaxWidth = new HashMap<Integer,Integer>();
                //创建标题栏
                sheet.addCell((WritableCell) new Label(0, 0, "test", arial14format));
                for (int col = 0; col < fieldList.size(); col++  ) {
                    String fieldName =fieldList.get(col).getDeclaredAnnotation(BeanFileAnnotation.class).aliasName();
                    fieldName = "".equals(fieldName)?fieldList.get(col).getName():fieldName;
                    mapColMaxWidth.put(col,fieldName.length());
                    sheet.addCell(new Label(col, 0,fieldName , arial10format));
                }
                //设置行高
                sheet.setRowView(0, 340);
                for (int rowIndex = 0; rowIndex < objList.size(); rowIndex ++) {
                    T t = objList.get(rowIndex);
                    for(int colIndex = 0;colIndex<fieldList.size();colIndex++){    // 列 从第0列开始
                        String content = "";
                        String fieldName = fieldList.get(colIndex).getName();
                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                        Method getMethod = clazz.getMethod(getMethodName, new Class[] {});
                        Object value = getMethod.invoke(t, new Object[] {});
                        content = null==value?"": value.toString();
                        sheet.addCell(new Label(colIndex+0, rowIndex+   1, content, arial12format));
                        if(mapColMaxWidth.get(colIndex)==null){
                            mapColMaxWidth.put(colIndex,content.length());
                        }
                        int contentWidth = content.length();
                        if(contentWidth>mapColMaxWidth.get(colIndex)){
                            mapColMaxWidth.put(colIndex,content.length());
                            //设置列宽
                            sheet.setColumnView(colIndex, content.length()+ 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(rowIndex+   1, 350);
                }
                wb.write();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public <T> void writeMultiListsToFirstExcel(String firstSheetName, Context c, List<T>... lists) {
        format();
        WritableSheet sheet = this.getFirstSheet(firstSheetName);
        if (lists == null || lists.length ==0 ){ return;}
        int currRowIndex = 0;
        Map<Integer, Integer> mapColMaxWidth = new HashMap<Integer, Integer>();
        for(int i =0;i<lists.length;i++){
            List<T> list = lists[i];
            if(list.size()==0) continue;
            T t = list.get(0);
            try {
                Field[] fields = t.getClass().getDeclaredFields();
                List<Field> fieldList = GetOrderedFiled.getOrderedField(fields);
                //写标题
                for (int col = 0; col < fieldList.size(); col++) {

                    String fieldName = fieldList.get(col).getDeclaredAnnotation(BeanFileAnnotation.class).aliasName();
                    fieldName = "".equals(fieldName) ? fieldList.get(col).getName() : fieldName;
                    if (mapColMaxWidth.get(col) == null) {
                        mapColMaxWidth.put(col, fieldName.length());
                    }
                    int contentWidth = fieldName.length();
                    if (contentWidth > mapColMaxWidth.get(col)) {
                        mapColMaxWidth.put(col, fieldName.length());
                        //设置列宽
                        sheet.setColumnView(col, fieldName.length() + 5);
                    }
                    sheet.addCell(new Label(col, currRowIndex, fieldName, arial10format));
                }
                //设置行高
                sheet.setRowView(currRowIndex, 340);
                currRowIndex++;
                //写列表的每一行。
                for (int j = 0; j < list.size(); j++, currRowIndex++) {
                    t = list.get(j);
                    for (int colIndex = 0; colIndex < fieldList.size(); colIndex++) {    // 列 从第0列开始
                        String content = "";
//                        String fieldName = fieldList.get(colIndex).getName();
//                        String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
//                        Method getMethod = t.getClass().getMethod(getMethodName, new Class[]{});
//                        Object value = getMethod.invoke(t, new Object[]{});
                        Field field = fieldList.get(colIndex);
                        Object value = GetOrderedFiled.getValueOfField(field,t);
                        content = null == value ? "" : value.toString();
                        sheet.addCell(new Label(colIndex + 0, currRowIndex, content, arial12format));
                        if (mapColMaxWidth.get(colIndex) == null) {
                            mapColMaxWidth.put(colIndex, content.length());
                        }
                        int contentWidth = content.length();
                        if (contentWidth > mapColMaxWidth.get(colIndex)) {
                            mapColMaxWidth.put(colIndex, content.length());
                            //设置列宽
                            sheet.setColumnView(colIndex, content.length() + 5);
                        }
                    }
                    //设置行高
                    sheet.setRowView(currRowIndex, 350);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            currRowIndex +=2;
        }
        try {
            wb.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(c, "已经完成了 " + lists.length + "个列表的保存。",Toast.LENGTH_SHORT).show();
    }
}
