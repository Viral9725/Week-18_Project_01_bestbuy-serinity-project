package com.bestbuy.steps;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductsSteps {
    @Step
    public ValidatableResponse getAllProducts() {
        return SerenityRest.given().log().all()
                .when()
                .get(EndPoints.PRODUCTS)
                .then();
    }
    @Step("Creating product with name :{0}, type : {1}, price :{2}, shipping:{3},upc:{4},description:{5},manufacturer:{6},model:{7},url:{8},image{9}")
    public ValidatableResponse createANewProduct(String name, String type, int price, int shipping, String upc, String description, String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setShipping(shipping);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .body(productPojo)
                .when()
                .post(EndPoints.PRODUCTS)
                .then();
    }
    @Step("Get product details of id : {0}")
    public HashMap<String, Object> getProductInfoByName(int productID) {
        HashMap<String, Object> productMap = SerenityRest.given().log().all()
                .when()
                .pathParam("productID",productID)
                .get(EndPoints.PRODUCTS_BYID)
                .then()
                .statusCode(200)
                .extract()
                .path("");
        return productMap;
    }
    @Step("Update product details of id: {0}")
    public ValidatableResponse updateProduct(int productID, String name) {
        ProductPojo productPojo = new ProductPojo();
        productPojo.setName(name);
        return SerenityRest.given().log().all()
                .header("Content-Type", "application/json")
                .body(productPojo)
                .pathParam("productID", productID)
                .when()
                .patch(EndPoints.PRODUCTS_BYID)
                .then();
    }
    @Step("Get single product details by id: {0}")
    public ValidatableResponse getSingleProduct(int productID) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .get(EndPoints.PRODUCTS_BYID)
                .then();
    }
    @Step("Get single product details by id: {0}")
    public HashMap<String,Object> getSingleProductData(int productID) {
        HashMap<String,Object> productMap = SerenityRest.given().log().all()
                .when()
                .pathParam("productID", productID)
                .get(EndPoints.PRODUCTS_BYID)
                .then().extract().path("");
        return productMap;
    }
    @Step("Delete product details by id: {0}")
    public ValidatableResponse deleteSingleProduct(int productID) {
        return SerenityRest.given().log().all()
                .pathParam("productID", productID)
                .when()
                .delete(EndPoints.PRODUCTS_BYID)
                .then();
    }
}
