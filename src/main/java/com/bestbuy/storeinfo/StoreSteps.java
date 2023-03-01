package com.bestbuy.storeinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import com.bestbuy.model.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoreSteps {
    @Step("getting all information :{0}")
    public ValidatableResponse getAllStoreInfo() {
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then();
    }

    @Step("creating store with name :{0},type: {1}")
    public ValidatableResponse createStore(String name, String type) {


        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setAddress("1351 Rid Dr");
        pojo.setState("mn");
        pojo.setZip("55378");
        pojo.setCity("London");
        pojo.setHours("45");
        pojo.setAddress2("yuh");
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_STORES)
                .then().log().all().statusCode(201);
    }

    @Step("getting store info by name:{0}")
    public HashMap<String, Object> getStoreInfoByName(String name) {
        String part1 = "data.findAll{it.firstName='";
        String part2 = "'}.get(0)";

        return SerenityRest.given()
                .log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then()
                .statusCode(200)
                .extract().path(part1 + name + part2);
        //findAll{it.firstName=='akshit69136'}.get(0)
    }

    @Step("update store info with name :{0}")
    public ValidatableResponse updateStore(String name,int storesID) {
        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        return SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID",storesID)
                .body(pojo)
                .when()
                .put(EndPoints.UPDATE_STORES_BY_ID)
                .then();

    }
    @Step("deleteing store information with storeId:{0}")
    public ValidatableResponse deleteStoreInfoByID(int storesId){
        return SerenityRest.given()
                .pathParam("storesID",storesId)
                .when()
                .delete(EndPoints.DELETE_STORES_BY_ID)
                .then();
    }

    @Step("getting store info By storesId:{0}")
    public ValidatableResponse getstoreInfoBystoreId(int storesId){
        return SerenityRest.given()
                .pathParam("storesID",storesId)
                .when()
                .get(EndPoints.GET_SINGLE_STORES_BY_ID)
                .then();
    }
}
