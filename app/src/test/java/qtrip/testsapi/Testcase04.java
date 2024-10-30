package qtrip.testsapi;

import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Testcase04 {
    String email;
    String password;
    JSONObject jsonObject;
    RequestSpecification apiRequest;
    Response apiResponse;

    @Test(
        description = "Verify that a duplicate user account cannot be created on the Qtrip Website",
        enabled = true,
        priority = 4,
        groups = {"API Test"}
    )
    public void TC04() {
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

        // Re-register with same credentials
        apiResponse = apiRequest.when()
                                .post("/register");

        apiResponse.then()
                    .assertThat().statusCode(400)
                    .body("message", equalTo("Email already exists"));
    }
}
