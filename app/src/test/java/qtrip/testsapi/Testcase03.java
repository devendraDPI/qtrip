package qtrip.testsapi;

import static org.hamcrest.Matchers.*;

import java.util.UUID;

import org.json.JSONObject;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Testcase03 {
    String email;
    String password;
    String userId;
    String token;
    JSONObject jsonObject;
    RequestSpecification apiRequest;
    Response apiResponse;

    @Test(
        description = "Verify that a reservation can be made using the QTrip API",
        enabled = true,
        priority = 3,
        groups = {"API Test"}
    )
    public void TC03() {
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

        userId = apiResponse.body()
                            .jsonPath()
                            .get("data.id");

        token = apiResponse.body()
                            .jsonPath()
                            .get("data.token");

        // Reservation
        jsonObject.clear();
        jsonObject.put("userId", userId);
        jsonObject.put("name","Testuser");
        jsonObject.put("date","2025-20-12");
        jsonObject.put("person","2");
        jsonObject.put("adventure", "2447910730");

        apiResponse = apiRequest.header("Authorization", "Bearer " + token)
                                .header("Content-Type", ContentType.JSON)
                                .body(jsonObject.toString())
                                .when()
                                .post("/reservations/new");

        apiResponse.then()
                    .assertThat()
                    .statusCode(200);

        // Verify reservation
        apiResponse = apiRequest.queryParam("id", userId)
                                .when()
                                .get("/reservations");

        apiResponse.then()
                    .body("isCancelled[0]", equalTo(false))
                    .body("userId[0]", equalTo(userId))
                    .body("name[0]", equalTo("Testuser"));
    }
}
