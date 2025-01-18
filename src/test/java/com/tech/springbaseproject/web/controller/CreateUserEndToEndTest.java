package com.tech.springbaseproject.web.controller;

import com.tech.springbaseproject.web.dto.CreateUserRequest;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Value;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CreateUserEndToEndTest {

    @Value("${local.server.port}")
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    @Test
    void createUserAndRetrieveIt() {
        CreateUserRequest request = new CreateUserRequest("eve", "eve@example.com");

        ValidatableResponse createResp =
                given()
                        .contentType("application/json")
                        .body(request)
                        .when()
                        .post("/v1/users")
                        .then()
                        .statusCode(201)
                        .body("data.username", equalTo("eve"))
                        .body("data.id", notNullValue());

        // Extract user ID
        Long userId = createResp.extract().jsonPath().getLong("data.id");

        // Retrieve the user
        given()
                .contentType("application/json")
                .when()
                .get("/v1/users/{id}", userId)
                .then()
                .statusCode(200)
                .body("data.username", equalTo("eve"))
                .body("data.email", equalTo("eve@example.com"));
    }
}
