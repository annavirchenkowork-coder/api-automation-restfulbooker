package com.netta.restfulbooker.booking;

import com.netta.restfulbooker.base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookingTests extends BaseTest {
    @Test
    void createBooking_shouldReturn200_andBookingId() {
        BookingApi bookingApi = new BookingApi(requestSpec);

        // Request body for creating a booking
        String requestBody = """
                {
                  "firstname" : "Netta",
                  "lastname" : "QA",
                  "totalprice" : 123,
                  "depositpaid" : true,
                  "bookingdates" : {
                      "checkin" : "2025-01-01",
                      "checkout" : "2025-01-05"
                  },
                  "additionalneeds" : "Breakfast"
                }
                """;

        // Call the API through helper
        Response response = bookingApi.createBooking(requestBody);

        // Basic assertions
        assertEquals(200, response.statusCode(), "Status code should be 200 for successful booking creation");

        Integer bookingId = response.path("bookingid");
        String firstname = response.path("booking.firstname");

        assertNotNull(bookingId, "bookingid should not be null");
        assertEquals("Netta", firstname, "Firstname in the response should match request");
    }

    @Test
    void createAndGetBooking_shouldReturnSameData() {
        BookingApi bookingApi = new BookingApi(requestSpec);

        // 1. Create booking
        String requestBody = """
                {
                  "firstname" : "Netta",
                  "lastname" : "QA",
                  "totalprice" : 123,
                  "depositpaid" : true,
                  "bookingdates" : {
                      "checkin" : "2025-01-01",
                      "checkout" : "2025-01-05"
                  },
                  "additionalneeds" : "Breakfast"
                }
                """;

        Response createResponse = bookingApi.createBooking(requestBody);

        assertEquals(200, createResponse.statusCode(), "Create booking should return 200");

        Integer bookingId = createResponse.path("bookingid");
        assertNotNull(bookingId, "bookingid should not be null");

        // 2. Get booking by id
        Response getResponse = bookingApi.getBooking(bookingId);

        assertEquals(200, getResponse.statusCode(), "Get booking should return 200");

        // 3. Validate that the data matches
        String actualFirstname = getResponse.path("firstname");
        String actualLastname = getResponse.path("lastname");
        int actualTotalPrice = getResponse.path("totalprice");
        Boolean actualDepositPaid = getResponse.path("depositpaid");
        String actualCheckin = getResponse.path("bookingdates.checkin");
        String actualCheckout = getResponse.path("bookingdates.checkout");
        String actualAdditionalNeeds = getResponse.path("additionalneeds");

        assertEquals("Netta", actualFirstname);
        assertEquals("QA", actualLastname);
        assertEquals(123, actualTotalPrice);
        assertEquals(true, actualDepositPaid);
        assertEquals("2025-01-01", actualCheckin);
        assertEquals("2025-01-05", actualCheckout);
        assertEquals("Breakfast", actualAdditionalNeeds);
    }
}
