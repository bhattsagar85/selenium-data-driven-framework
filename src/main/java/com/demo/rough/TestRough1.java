package com.demo.rough;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

/**
 * Project Name    : selenium-data-driven-framework
 * Developer       : Sagar Bhatt
 * Version         : 1.0.0
 * Date            :
 * Time            :
 * Description     :
 **/
public class TestRough1 {

    @BeforeTest
    public void before(){
        System.out.println("In Before Test");
    }

    @Test
    public void test1(){
        System.out.println("In Test 1");
    }

    @Test
    public void test2(){
        System.out.println("In Test 2");
    }
}
