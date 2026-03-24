package Basics;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserRegistration {

    static String BaseURI = "https://ndosiautomation.co.za/APIDEV/";
    static String AdminToken;
    static String UserID;
    static String RegisteredEmail;
    static String UserToken;

    @Test()
    public void adminLoginTest() {

        String API = "login";
        String Payload = "{\n" +
                "  \"email\": \"ndivhuwo.tshiedza@gmail.com\",\n" +
                "  \"password\": \"P@ssword\"\n" +
                "}";

        Response response = RestAssured.given()
                .baseUri(BaseURI)
                .basePath(API)
                .header("Content-Type", "application/json")
                .body(Payload)
                .log().all()
                .post().prettyPeek();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        AdminToken = response.jsonPath().getString("data.token");

    }

    @Test(priority = 1)
    public void userRegistrationTest() {
        String API = "register";
        RegisteredEmail = Faker.instance().internet().emailAddress();

        String payload = String.format("{\n" +
                "    \"firstName\": \"NewFirstName\",\n" +
                "    \"lastName\": \"NewLastName\",\n" +
                "    \"email\": \"%s\",\n" +
                "    \"password\": \"P@ssword\",\n" +
                "    \"confirmPassword\": \"P@ssword\",\n" +
                "    \"phone\": \"\",\n" +
                "    \"groupId\": \"5328c91e-fc40-11f0-8e00-5000e6331276\"\n" +
                "}", RegisteredEmail);

        Response response = RestAssured.given()
                .baseUri(BaseURI)
                .basePath(API)
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 201);
        UserID = response.jsonPath().getString("data.id");
    }

    @Test(priority = 2)
    public void userApprovalTest() {
        String API = "admin/users/"+UserID+"/approve";

        Response response = RestAssured.given()
                .baseUri(BaseURI)
                .basePath(API)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + AdminToken)
                .log().all()
                .put().prettyPeek();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test(priority = 3)
    public void userLoginTest() {
        String API = "login";
        String payload = String.format("{\n" +
                "  \"email\": \"%s\",\n" +
                "  \"password\": \"P@ssword\"\n" +
                "}", RegisteredEmail);

        Response response = RestAssured.given()
                .baseUri(BaseURI)
                .basePath(API)
                .header("Content-Type", "application/json")
                .body(payload)
                .log().all()
                .post().prettyPeek();

        int statusCode = response.getStatusCode();
        Assert.assertEquals(statusCode, 200);
        UserToken = response.jsonPath().getString("data.token");
    }
}
