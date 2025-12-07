package com.netta.restfulbooker.health;

import com.netta.restfulbooker.base.BaseTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthCheckTest extends BaseTest {
    @Test
    void ping_shouldReturn201() {
        int statusCode =
                given()
                        .spec(requestSpec)
                        .when()
                        .get("/ping")
                        .then()
                        .extract()
                        .statusCode();
        assertEquals(201, statusCode, "Ping endpoint should return HTTP 201");
    }
}
