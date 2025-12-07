package com.netta.restfulbooker.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;

public class BaseTest {

    //Shared request specification for all tests
    protected static RequestSpecification requestSpec;
    @BeforeAll
    static void setup(){
        //Base URL for all API tests
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";

        //A reusable request specification
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }
}
