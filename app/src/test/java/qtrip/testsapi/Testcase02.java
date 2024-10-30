package qtrip.testsapi;

import static org.hamcrest.Matchers.*;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Testcase02 {
    RequestSpecification apiRequest;
    Response apiResponse;

    @Test(
        description = "Verify that the search city API returns the correct number of results",
        enabled = true,
        priority = 2,
        groups = {"API Test"}
    )
    public void TC02() {
        apiRequest = RestAssured.given()
                                .baseUri("https://content-qtripdynamic-qa-backend.azurewebsites.net")
                                .basePath("/api/v1");

        apiResponse = apiRequest.queryParam("q", "beng")
                                .get("/cities");

        apiResponse.then()
                    .assertThat().statusCode(200)
                    .assertThat().body(JsonSchemaValidator.matchesJsonSchema(new File("src/test/resources/schema.json")))
                    .body("$", hasSize(1))
                    .body("description[0]", equalTo("100+ Places"));
    }
}
