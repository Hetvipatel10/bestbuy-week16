package com.bestbuy.storeinfo;

import com.bestbuy.productinfo.ProductSteps;
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
public class StoreCurdStepsWithSteps extends TestBase {
    static String name = "Hoover" + TestUtils.getRandomValue();
    static String type = "dyson" + TestUtils.getRandomValue();
    static String add = "abc" + TestUtils.getRandomValue();
    static String state = "united kingdom";
    static String zip = "abc123";
    static String city = "london";
    static int storesID;

   // private Object StorePojo;

    @Steps
    StoreSteps storeSteps;
    @Title("This will create a new store")
    @Test
    public void test001() {
        ValidatableResponse response =storeSteps.createStore(name, type);
        response.statusCode(201);

    }

    @Title("verify if store is created")
    @Test
    public void test002() {
        HashMap<String, Object> storeMapData = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storeMapData, hasValue(name));
        storesID = (int) storeMapData.get("id");
        System.out.println(storesID);

    }

    @Title("update the product information")
    @Test
    public void test003() {
        name = name + "test";
        storeSteps.updateStore(name,storesID);
        HashMap<String, Object> productMap = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(productMap, hasValue(name));
    }
    @Title("Delete store info by storeID and verify its deleted")
    @Test
    public void test004(){
        storeSteps.deleteStoreInfoByID(storesID).statusCode(200);
        storeSteps.getstoreInfoBystoreId(storesID).statusCode(404);


    }


}
