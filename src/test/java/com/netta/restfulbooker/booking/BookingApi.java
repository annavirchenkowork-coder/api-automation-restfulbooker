package com.netta.restfulbooker.booking;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class BookingApi {

    private final RequestSpecification requestSpec;

    public BookingApi(RequestSpecification requestSpec) {
        this.requestSpec = requestSpec;
    }

    public Response createBooking(String requestBody) {
        return given()
                .spec(requestSpec)
                .body(requestBody)
                .when()
                .post("/booking")
                .then()
                .extract()
                .response();
    }

    public Response getBooking(int bookingId) {
        return given()
                .spec(requestSpec)
                .when()
                .get("/booking/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response updateBooking(int bookingId, String requestBody, String token) {
        return given()
                .spec(requestSpec)
                .cookie("token", token)
                .body(requestBody)
                .when()
                .put("/booking/" + bookingId)
                .then()
                .extract()
                .response();
    }

    public Response deleteBooking(int bookingId, String token) {
        return given()
                .spec(requestSpec)
                .cookie("token", token)
                .when()
                .delete("/booking/" + bookingId)
                .then()
                .extract()
                .response();
    }
}
