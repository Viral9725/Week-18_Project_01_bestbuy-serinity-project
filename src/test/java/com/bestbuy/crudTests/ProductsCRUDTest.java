package com.bestbuy.crudTests;

import com.bestbuy.steps.ProductsSteps;
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
public class ProductsCRUDTest extends TestBase {
    static String name = "Prime_Battery" + TestUtils.getRandomValue();
    static String type = "lithium";
    static int price = 4;
    static Integer shipping = 12;
    static String upc = "DHL";
    static String description = "Solar charge";
    static String manufacturer = "TeslaBattery";
    static String model = "MAKE2019";
    static String url = "jay_battery.com";
    static String image = "jay.png";
    static int productID;


    @Steps
    ProductsSteps productsStep;


    @Title("This test will Create a new Product")
    @Test
    public void test001() {

        ValidatableResponse response = productsStep.createANewProduct(name, type, price, shipping, upc, description, manufacturer, model, url, image).statusCode(201);
        productID = response.log().all().extract().path("id");
    }

    @Title("This test will verify product was added to the application")
    @Test
    public void test002(){
        HashMap<String, Object> productMap =  productsStep.getProductInfoByName(productID);
        /* System.out.println("Product map"+productMap.get("name"));*/
        Assert.assertThat(productMap, hasValue(name));
    }
    @Title("This test will Update the product information")
    @Test
    public void test003() {
        name = name + "_updated";
        productsStep.updateProduct(productID,name).statusCode(200).log().all();

        HashMap<String,Object>  productMapData = productsStep.getSingleProductData(productID);
        Assert.assertThat(productMapData,hasValue(name));

    }

    @Title("This test will Get all the products details")
    @Test
    public void test004() {
        productsStep.getAllProducts().statusCode(200).log().all();
    }

    @Title("This test will Get single product details")
    @Test
    public void test005() {
        productsStep.getSingleProduct(productID).statusCode(200).log().all();
    }
    @Title("This test will Delete product details")
    @Test
    public void test006() {

        productsStep.deleteSingleProduct(productID).statusCode(200).log().all();
        productsStep.getSingleProduct(productID).statusCode(404).log().all();
    }

}