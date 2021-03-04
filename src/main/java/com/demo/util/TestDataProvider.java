package com.demo.util;

import com.demo.base.TestBase;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
/**
 * Project Name    : selenium-data-driven-framework
 * Developer       : Sagar Bhatt
 * Version         : 1.0.0
 * Date            : 03-March-2021
 * Time            : 13:00
 * Description     : TestNG data-provider for all the tests
 **/

public class TestDataProvider {

    @DataProvider(name="dp")
    public Object[][] getData(Method method){
        String sheetName = method.getName();
        int rows = TestBase.xls_reader.getRowCount(sheetName);
        int cols = TestBase.xls_reader.getColumnCount(sheetName);
        Object [][] data = new Object[rows-1][1];
        HashMap<String,String> map = null;

        for(int rowNum = 2; rowNum<=rows; rowNum++){
            map = new HashMap<>();
            for(int colNum = 0; colNum<cols; colNum++){
                map.put(TestBase.xls_reader.getCellData(sheetName, colNum, 1), TestBase.xls_reader.getCellData(sheetName, colNum, rowNum));
                data[rowNum-2][0] = map;
            }
        }
        return data;
    }
}
