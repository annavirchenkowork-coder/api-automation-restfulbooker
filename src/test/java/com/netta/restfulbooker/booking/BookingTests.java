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
}
