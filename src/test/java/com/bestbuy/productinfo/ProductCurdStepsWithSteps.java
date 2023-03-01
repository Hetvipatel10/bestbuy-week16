package com.bestbuy.productinfo;

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
public class ProductCurdStepsWithSteps extends TestBase {
    static String name = "Hetvi" + TestUtils.getRandomValue();
    static String type = "standar" + TestUtils.getRandomValue();
    static String model = "ytedghf" + TestUtils.getRandomValue();
    static int productsID;

    @Steps
    ProductSteps productSteps;

    @Title("This will create a new product")
    @Test
    public void test001() {
        ValidatableResponse response = productSteps.createProduct(name, type, model);
        response.statusCode(201);

    }

    @Title("verify if product is created")
    @Test
    public void test002() {
        HashMap<String, Object> productMapData = productSteps.getProductInfoByname(name);
        Assert.assertThat(productMapData, hasValue(name));
        productsID = (int) productMapData.get("id");
        System.out.println(productsID);

    }

    @Title("update the product information")
    @Test
    public void test003() {
        name = name + "test";
        productSteps.updateProduct(name,productsID);
        HashMap<String, Object> productMap = productSteps.getProductInfoByname(name);
        Assert.assertThat(productMap, hasValue(name));
    }
    @Title("Delete student info by StudentID and verify its deleted")
    @Test
    public void test004(){

        productSteps.deleteProductInfoByID(productsID).statusCode(200);
        productSteps.getProductInfoByproductsId(productsID).statusCode(404);

    }
}

