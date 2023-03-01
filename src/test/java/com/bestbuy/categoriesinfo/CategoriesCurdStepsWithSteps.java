package com.bestbuy.categoriesinfo;

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
public class CategoriesCurdStepsWithSteps extends TestBase {
    static String name = "TestCategory" + TestUtils.getRandomValue();
    static String category = "20" + TestUtils.getRandomValue();
    static Object categoryId;

    @Steps
    CategoriesSteps categoriesSteps;

    @Title("This will create a new category")
    @Test
    public void test001() {

        ValidatableResponse response =categoriesSteps.createCategories(name,category);
        response.statusCode(201);
    }

    @Title("verify if category is created")
    @Test
    public void test002() {
        HashMap<String,Object> categoryData =categoriesSteps.getCatgoryInfoByName(name);
        Assert.assertThat(categoryData,hasValue(name));
        categoryId =  categoryData.get("id") ;
        System.out.println(categoryId);

    }
    @Title("update the user information")
    @Test
    public void test003() {
        name = name + "categoryTest";

        categoriesSteps.updatecategory(categoryId, name);
        HashMap<String,Object> categoryMap=categoriesSteps.getCatgoryInfoByName(name);
        Assert.assertThat(categoryMap,hasValue(name));

    }
    @Title("Delete category info by categopryID and verify its deleted")
    @Test
    public void test004(){

        categoriesSteps.deletecategoryInfoByID(categoryId).statusCode(200);
        categoriesSteps.getcategoryInfoBycategoryId(categoryId).statusCode(404);

    }

}
