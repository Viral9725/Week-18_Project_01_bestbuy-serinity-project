package com.bestbuy.crudTests;

import com.bestbuy.steps.StoresSteps;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;
@RunWith(SerenityRunner.class)
public class StoresCRUDTest extends TestBase {
    static String name = "Jason" + TestUtils.getRandomValue();
    static String type = "CarWash" + TestUtils.getRandomValue();
    static String address = "7 Bond Road";
    static String address2 = "London,UK";
    static String city = "London";
    static String state = "Check";
    static String zip = "nw9 5ot";
    static int lat = 34;
    static int lng = 23;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9;Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storeID;

    @Steps
    StoresSteps storesSteps;

    @Title("This test will create a New Store")
    @Test
    public void test001() {
        HashMap<Object, Object> servicesData = new HashMap<>();
        ValidatableResponse response = storesSteps.createStore(name, type, address, address2, city, state, zip, lat, lng, hours,servicesData);
        response.log().all().statusCode(201);
        storeID = response.log().all().extract().path("id");
        System.out.println(storeID);
    }

    @Title("This test will verify if the Store was added to the application")
    @Test
    public void test002() {
        HashMap<String, ?> storeMap = storesSteps.getStoreInfoById(storeID);
        Assert.assertThat(storeMap, hasValue(name));
        System.out.println(storeMap);
    }

    @Title("This test will Update the services information")
    @Test
    public void test003() {
        name = name + "_updated";
        storesSteps.updateStore(storeID,name);
        HashMap<String, ?> productList = storesSteps.getStoreInfoById(storeID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @Title("This test will Delete the services by ID")
    @Test
    public void test004() {
        storesSteps.deleteStore(storeID).statusCode(200);
        storesSteps.getStoreByID(storeID).statusCode(404);
    }
}