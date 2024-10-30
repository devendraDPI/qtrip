package qtrip.testsapi;

import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Testcase01 {
    String email;
    String password;
    JSONObject jsonObject;
    RequestSpecification apiRequest;
    Response apiResponse;

    @Test(
        description = "Verify that a new user can be registered and login using APIs of QTrip",
        enabled = true,
        priority = 1,
        groups = {"API Test"}
    )
    public void TC01() {
        // Register user
        apiRequest = RestAssured.given()
                                .baseUri("https://content-qtripdynamic-qa-backend.azurewebsites.net")
                                .basePath("/api/v1");

        jsonObject = new JSONObject();
        email = String.format("user%s@qtrip.com", UUID.randomUUID());
        password = UUID.randomUUID().toString();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("confirmpassword", password);

        apiResponse = apiRequest.contentType(ContentType.JSON)
                                .body(jsonObject.toString())
                                .when()
                                .post("/register");

        apiResponse.then()
                    .assertThat().statusCode(201);

        // Login user
        jsonObject.remove("confirmpassword");

        apiResponse = apiRequest.contentType(ContentType.JSON)
                                .body(jsonObject.toString())
                                .when()
                                .post("/login");

        apiResponse.then()
                    .assertThat().statusCode(201)
                    .body("success", equalTo(true))
                    .body("data.token", notNullValue())
                    .body("data.id", notNullValue());
    }
}
