package com.demo.rough;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class Rough {
    private static final String rootDirectory = System.getProperty("user.dir");
    private static final String fileSeparator = File.separator;

    public void readJson() throws IOException, ParseException {
        String path = rootDirectory+ fileSeparator +  "src" + fileSeparator + "test" + fileSeparator + "resources" +
                fileSeparator + "testdata.json";
        FileReader fileReader = new FileReader(path);
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(fileReader);
        JSONArray testData = (JSONArray) jsonObject.get("testData");
        for(int i=0;i<testData.size();i++){
            JSONObject testDetails = (JSONObject) testData.get(i);
            String testName = (String) testDetails.get("testName");
            System.out.println(testName);
            JSONArray data = (JSONArray) testDetails.get("data");
            for(int j=0;j<data.size();j++){
                JSONObject testdata_value = (JSONObject) data.get(j);
                Set<String> keys = testdata_value.keySet();
                Iterator<String> it = keys.iterator();
                while (it.hasNext()){
                    String key = it.next();
                    String value = (String) testdata_value.get(key);
                    System.out.println(key+ "--"+ value);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, ParseException {
        Rough rough = new Rough();
        rough.readJson();
    }
}
