package com.bestbuy.storeinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductsPojo;
import com.bestbuy.model.StorePojo;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.http.ContentType;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.hasValue;

/**
 * Created by bhavesh
 */
@RunWith(SerenityRunner.class)
public class StoreCURDTest extends TestBase {
    static String name = "Hoover" + TestUtils.getRandomValue();
    static String type = "dyson" + TestUtils.getRandomValue();
    static String add = "abc" + TestUtils.getRandomValue();
    static String state = "united kingdom";
    static String zip = "abc123";
    static String city = "london";
    static Object storesID;
    private Object StorePojo;

    @Title("This is will get all information of all store")
    @Test
    public void test001() {
        SerenityRest.given().log().all()
                .when().get()
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test // post new and retrive id
    public void test002() {

        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType(type);
        pojo.setAddress("1351 Rid Dr");
        pojo.setState("mn");
        pojo.setZip("55378");
        pojo.setCity("London");
        pojo.setHours("45");
        pojo.setAddress2("yuh");


        SerenityRest.given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(pojo)
                .when()
                .post(EndPoints.CREATE_NEW_STORES)
                .then().log().all()
                .statusCode(201);

    }

    @Title("Verify if store was created")
    @Test
    public void test003() {

        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, ?> storeMap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        System.out.println();
        String name1 = "sdcy";
        //Assert.assertThat(productMap,hasValue(name));
        storesID = storeMap.get("id");
        System.out.println("ID =" + storesID);
        System.out.println("name = " + storeMap);
    }

    @Title("update the product and verify the update information")
    @Test
    public void test004() {
       name=name+"sky";

        StorePojo pojo = new StorePojo();
        pojo.setName(name);
        pojo.setType("testing");
        pojo.setAddress("har");
        pojo.setAddress2("ha39rf");
        pojo.setCity("harrow");
        pojo.setState("fyfcy");
        pojo.setZip("1234");
        pojo.setLat(2);
        pojo.setLng(6);
        pojo.setHours("11:00");

        SerenityRest.given().log().all()
                .contentType(ContentType.JSON)
                .pathParam("storesID", storesID)
                .body(pojo)
                .when()
                .patch(EndPoints.UPDATE_STORES_BY_ID)
                .then()
                .statusCode(200);


        String p1 = "data.findAll{it.name='";
        String p2 = "'}.get(0)";
        HashMap<String, Object> storemap = SerenityRest.given().log().all()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then().statusCode(200)
                .extract()
                .path(p1 + name + p2);
        Assert.assertThat(storemap, hasValue(name));
    }

    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test005() {

        SerenityRest.given()
                .pathParam("storesID", storesID)
                .when()
                .delete(EndPoints.DELETE_STORES_BY_ID)
                .then().log().all().statusCode(200);

        SerenityRest.given()
                .pathParam("storesID", storesID)
                .when()
                .get(EndPoints.GET_SINGLE_STORES_BY_ID)
                .then().log().all().statusCode(404);

    }
}







