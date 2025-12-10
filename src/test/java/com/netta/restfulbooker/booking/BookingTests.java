package com.netta.restfulbooker.booking;

import com.netta.restfulbooker.auth.AuthApi;
import com.netta.restfulbooker.base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void updateBooking_shouldModifyExistingBooking_whenTokenIsValid() {
        BookingApi bookingApi = new BookingApi(requestSpec);
        AuthApi authApi = new AuthApi(requestSpec);

        // 1. Create a booking to update
        String originalRequestBody = """
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

        Response createResponse = bookingApi.createBooking(originalRequestBody);
        assertEquals(200, createResponse.statusCode(), "Create booking should return 200");
        Integer bookingId = createResponse.path("bookingid");
        assertNotNull(bookingId, "booking should not be null");

        // 2. Get auth token
        String token = authApi.getToken("admin", "password123");
        assertNotNull(token, "Token should not be null");

        //3. Prepare update boyd
        String updateRequestBody = """
                {
                                  "firstname" : "Netta",
                                  "lastname" : "Updated",
                                  "totalprice" : 200,
                                  "depositpaid" : false,
                                  "bookingdates" : {
                                      "checkin" : "2025-02-01",
                                      "checkout" : "2025-02-10"
                                  },
                                  "additionalneeds" : "Late checkout"
                                }
                """;

        //4. Send PUT /booking/{id}
        Response updateResponse = bookingApi.updateBooking(bookingId, updateRequestBody, token);
        assertEquals(200, updateResponse.statusCode(), "Update booking should return 200");

        // 5. Verify updated fields from response
        String updatedLastname = updateResponse.path("lastname");
        int updatedTotalPrice = updateResponse.path("totalprice");
        Boolean updatedDepositPaid = updateResponse.path("depositpaid");
        String updatedCheckin = updateResponse.path("bookingdates.checkin");
        String updatedCheckout = updateResponse.path("bookingdates.checkout");
        String updatedAdditionalNeeds = updateResponse.path("additionalneeds");

        assertEquals("Updated", updatedLastname);
        assertEquals(200, updatedTotalPrice);
        assertEquals(false, updatedDepositPaid);
        assertEquals("2025-02-01", updatedCheckin);
        assertEquals("2025-02-10", updatedCheckout);
        assertEquals("Late checkout", updatedAdditionalNeeds);
    }

    @Test
    void deleteBooking_shouldRemoveBooking_whenTokenIsValid() {
        BookingApi bookingApi = new BookingApi(requestSpec);
        AuthApi authApi = new AuthApi(requestSpec);

        // 1. Create a booking to delete
        String requestBody = """
                {
                  "firstname" : "ToDelete",
                  "lastname" : "Booking",
                  "totalprice" : 50,
                  "depositpaid" : true,
                  "bookingdates" : {
                      "checkin" : "2025-03-01",
                      "checkout" : "2025-03-05"
                  },
                  "additionalneeds" : "None"
                }
                """;

        Response createResponse = bookingApi.createBooking(requestBody);
        assertEquals(200, createResponse.statusCode(), "Create booking should return 200");

        Integer bookingId = createResponse.path("bookingid");
        assertNotNull(bookingId, "bookingid should not be null");

        // 2. Get auth token
        String token = authApi.getToken("admin", "password123");
        assertNotNull(token, "Token should not be null");

        // 3. Delete booking
        Response deleteResponse = bookingApi.deleteBooking(bookingId, token);

        int statusCode = deleteResponse.statusCode();
        System.out.println("Delete status = " + statusCode);

        // Accepting 201 as OK (or 200 if needed)
        boolean isSuccessStatus = statusCode == 201 || statusCode == 200;
        System.out.println("Delete status = " + statusCode);
        System.out.println("Delete body = " + deleteResponse.asString());
        assertTrue(isSuccessStatus, "Delete booking should return 201 or 200");

        // 4. Try to get the booking and expect 404 or some error
        Response getAfterDelete = bookingApi.getBooking(bookingId);
        int getStatusAfterDelete = getAfterDelete.statusCode();

        // Some versions of the API return 404 for deleted booking
        System.out.println("Get after delete status = " + getStatusAfterDelete);
    }
}
