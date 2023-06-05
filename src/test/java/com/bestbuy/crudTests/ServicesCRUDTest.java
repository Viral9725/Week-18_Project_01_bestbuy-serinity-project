package com.bestbuy.crudTests;

import com.bestbuy.steps.ServicesSteps;
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
public class ServicesCRUDTest extends TestBase {
    static String name = "Jason's services" + TestUtils.getRandomValue();
    static int serviceID;

    @Steps
    ServicesSteps servicesSteps;

    @Title("This test will create a New Service")
    @Test
    public void test001() {
        ValidatableResponse response = servicesSteps.createService(name);
        response.log().all().statusCode(201);
        serviceID = response.log().all().extract().path("id");
        System.out.println(serviceID);
    }

    @Title("This test will Verify if the services was added to the application")
    @Test
    public void test002() {
        HashMap<String, ?> storeMap = servicesSteps.getServiceInfoByName(serviceID);
        Assert.assertThat(storeMap, hasValue(name));
        System.out.println(storeMap);
    }

    @Title("This test will Update the Services information")
    @Test
    public void test003() {
        name = name + "_updated";
        servicesSteps.updateService(serviceID,name).statusCode(200);
        HashMap<String, ?> productList = servicesSteps.getServiceInfoByName(serviceID);
        Assert.assertThat(productList, hasValue(name));
        System.out.println(productList);
    }

    @Title("This test will Delete the service by ID")
    @Test
    public void test004() {
        servicesSteps.deleteService(serviceID).statusCode(200);
        servicesSteps.getServiceByID(serviceID).statusCode(404);
    }
}