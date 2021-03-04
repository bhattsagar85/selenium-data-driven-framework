package com.demo.rough;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Xls_parctise {

    public String path;
    public FileInputStream fis;
    public FileOutputStream fos;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private XSSFRow row;
    private XSSFCell cell;

    public Xls_parctise(String path){
        this.path = path;
        try{
            fis = new FileInputStream(path);
            workbook = new XSSFWorkbook(fis);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public int getRowCount(String sheetName){
        int index = workbook.getSheetIndex(sheetName);
        if(index == -1){
            return 0;
        }else{
            sheet = workbook.getSheetAt(index);
            int number = sheet.getLastRowNum() + 1;
            return number;
        }
    }

//    public String getCellData(String sheetName, String colName, int rowNum){
//        try{
//            if(rowNum<=0)
//                return "";
//            int index = workbook.getSheetIndex(sheetName);
//            int col_Num = -1;
//            if (index == -1)
//                return "";
//            sheet = workbook.getSheetAt(index);
//            row = sheet.getRow(0);
//
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//
//    }
}
