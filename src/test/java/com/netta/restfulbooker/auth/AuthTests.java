package com.netta.restfulbooker.auth;

import com.netta.restfulbooker.base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AuthTests extends BaseTest {

    @Test
    void createToken_shouldReturnTokenAnd200() {
        AuthApi authApi = new AuthApi(requestSpec);

        Response response = authApi.createToken("admin", "password123");

        assertEquals(200, response.statusCode(), "Auth should return 200 for valid credentials");

        String token = response.path("token");
        assertNotNull(token, "Token should not be null");
        System.out.println("TOKEN = " + token);
    }

}
