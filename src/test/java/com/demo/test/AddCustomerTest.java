package com.demo.test;

import com.demo.base.TestBase;
import com.demo.util.TestDataProvider;
import com.demo.util.TestUtil;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.util.HashMap;

import static com.demo.base.Enums.TestMessages.CUSTOMER_ADDED_MESSAGE;

public class AddCustomerTest extends TestBase {

    @Test(dataProvider = "dp", dataProviderClass = TestDataProvider.class)
    public void addCustomerTest(HashMap<String, String> map){
        if(map.get("Runmode").equalsIgnoreCase("N")){
            throw new SkipException("Skipping the test case as Rumode is NO");
        }
        click("btn_bankmanager_login_CSS");
        click("btn_add_customer_CSS");
        type("txtbx_firstname_CSS", map.get("firstname"));
        type("txtbx_lastname_CSS", map.get("lastname"));
        type("txtbx_postcode_CSS", map.get("postcode"));
        click("btn_addcustomer_CSS");
        assertMessage(getAlertMessage(), CUSTOMER_ADDED_MESSAGE);
    }
}
