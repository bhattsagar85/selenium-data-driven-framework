package com.demo.base;

/**
 * Project Name    : selenium-data-driven-framework
 * Developer       : Sagar Bhatt
 * Version         : 1.0.0
 * Date            : 03-March-2021
 * Time            : 15:00
 * Description     : All the application messages will be added here
 **/
public class Enums {

    public enum TestMessages{
        CUSTOMER_ADDED_MESSAGE("Customer added successfully with customer id");
        public final String message;
        TestMessages(String message){
            this.message = message;
        }
        public String getMessage(){
            return message;
        }
    }
}
