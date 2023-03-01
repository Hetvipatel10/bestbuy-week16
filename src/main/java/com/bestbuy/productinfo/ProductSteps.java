package com.bestbuy.productinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

public class ProductSteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllProductInfo() {
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then();
    }

    @Step("creating product with name :{0},type: {1},model:{2}")
    public ValidatableResponse createProduct(String name, String type, String model) {


        ProductsPojo pojo = new ProductsPojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setPrice(1700);
        pojo.setShipping(15);
        pojo.setUpc("af");
        pojo.setDescription("egwe");
        pojo.setManufacturer("regsd");
        pojo.setModel(model);
        pojo.setUrl("aerg");
        pojo.setImage("yjnyu");
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_PRODUCTS)
                .then().log().all().statusCode(201);
    }

    @Step("getting product info by name:{0}")
    public HashMap<String, Object> getProductInfoByname(String name) {
        String part1 = "data.findAll{it.firstName='";
        String part2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_PRODUCTS)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
        //findAll{it.firstName=='akshit69136'}.get(0)
    }

    @Step("update product info with name :{0}")
    public ValidatableResponse updateProduct(String name,int productsID) {
        ProductsPojo pojo = new ProductsPojo();
        pojo.setName(name);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("productsID", productsID)
                .body(pojo)
                .when()
                .put(EndPoints.UPDATE_PRODUCTS_BY_ID)
                .then();

    }
    @Step("deleting product information with productId:{0}")
    public ValidatableResponse deleteProductInfoByID(int productsId){
        return SerenityRest.given()
                .pathParam("productsID",productsId)
                .when()
                .delete(EndPoints.DELETE_PRODUCTS_BY_ID)
                .then();
    }
    @Step("getting product info By productsId:{0}")
    public ValidatableResponse getProductInfoByproductsId(int prodcutsId){
        return SerenityRest.given()
                .pathParam("productsID",prodcutsId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCTS_BY_ID)
                .then();
    }


}