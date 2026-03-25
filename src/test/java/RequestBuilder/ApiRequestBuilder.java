package RequestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static Common.BaseURIs.BaseURI;
import static PayloadBuilder.PayloadBuilder.*;

public class ApiRequestBuilder {

    static String AdminToken;
    static String UserID;

    public static Response userLoginResponse(String email, String password) {

        String API = "login";
        Response response = RestAssured.given()
                .baseUri(BaseURI)
                .basePath(API)
                .header("Content-Type", "application/json")
                .body(userLoginPayload(email, password))
                .post().prettyPeek();
        AdminToken = response.jsonPath().getString("data.token");
        return response;
    }

    public static Response userRegisterResponse(String firstName, String lastName, String email, String password, String confirmPassword, String groupId) {
        String API = "register";
        Response response = RestAssured.given()
                .baseUri(BaseURI)
                .basePath(API)
                .header("Content-Type", "application/json")
                .body(userRegistrationPayload(firstName, lastName, email, password, confirmPassword, groupId))
                .post().prettyPeek();
        UserID = response.jsonPath().getString("data.id");
        return response;
    }

    public static Response userApprovalResponse(){
        String API = "admin/users/"+UserID+"/approve";
        Response response = RestAssured.given()
                .baseUri(BaseURI)
                .basePath(API)
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + AdminToken)
                .put().prettyPeek();
        return response;
    }
}
