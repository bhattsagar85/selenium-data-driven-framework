package com.demo.test;

import com.demo.base.TestBase;
import com.demo.util.TestUtil;
import org.testng.SkipException;
import org.testng.annotations.Test;

public class LoginTest extends TestBase {

    @Test(priority = 1)
    public void customerLogin(){
        click("btn_customer_login_CSS");
        selectDropDownValue("dropdown_name_ID", "Harry Potter");
        click("btn_login_CSS");
        verifyTrue(isElementPresent("link_logout_CSS"));
    }

    @Test(priority = 2)
    public void bankManagerLogin() throws InterruptedException {
        click("btn_bankmanager_login_CSS");
        verifyTrue(isElementPresent("btn_add_customer_CSS"));
    }
}
