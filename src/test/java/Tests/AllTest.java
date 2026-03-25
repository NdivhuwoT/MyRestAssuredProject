package Tests;

import RequestBuilder.ApiRequestBuilder;
import com.github.javafaker.Faker;
import org.testng.annotations.Test;

public class AllTest {

    static String RegisteredEmail;
    static String RegisteredPassword = "P@sswordreg";

    @Test
    public void adminLoginTest() {
        ApiRequestBuilder.userLoginResponse("ndivhuwo.tshiedza@gmail.com", "P@ssword")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test(priority = 1)
    public void userRegistrationTest() {
        RegisteredEmail = Faker.instance().internet().emailAddress();
        ApiRequestBuilder.userRegisterResponse("RegName","RegLastName", RegisteredEmail, RegisteredPassword, RegisteredPassword, "5328c91e-fc40-11f0-8e00-5000e6331276")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201);
    }

    @Test(priority = 2)
    public void userApprovalTest() {
        ApiRequestBuilder.userApprovalResponse()
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test(dependsOnMethods = "userApprovalTest")
    public void userLoginTest() {
        ApiRequestBuilder.userLoginResponse(RegisteredEmail, RegisteredPassword)
                .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
