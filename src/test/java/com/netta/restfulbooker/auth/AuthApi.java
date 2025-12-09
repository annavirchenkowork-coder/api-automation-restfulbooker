package com.netta.restfulbooker.auth;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class AuthApi {

    private final RequestSpecification requestSpec;

    public AuthApi(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response createToken(String username, String password) {
        String requestBody = """
                {
                "username" : "%s",
                "password" : "%s"
                }
                """.formatted(username, password);

        return given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/auth")
                .then()
                .extract()
                .response();
    }
}
