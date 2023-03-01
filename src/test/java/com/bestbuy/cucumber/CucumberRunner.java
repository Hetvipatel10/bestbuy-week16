package com.bestbuy.cucumber;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.testbase.TestBase;
import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * Created by bhavesh
 */

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/java/resources/feature"
        //tags = {"SMOKE"}
        )
public class CucumberRunner extends TestBase {

}
