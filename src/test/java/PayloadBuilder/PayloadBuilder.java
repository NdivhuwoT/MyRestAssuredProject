package PayloadBuilder;

import org.json.simple.JSONObject;

public class PayloadBuilder {
    public static JSONObject userLoginPayload(String email, String password) {
        JSONObject loginUser = new JSONObject();
        loginUser.put("email", email);
        loginUser.put("password", password);
        return loginUser;
    }

    public static JSONObject userRegistrationPayload(String firstName, String lastName, String email, String password, String confirmPassword, String phone, String groupId) {
        JSONObject registerUser = new JSONObject();
        registerUser.put("firstName", firstName);
        registerUser.put("lastName", lastName);
        registerUser.put("email", email);
        registerUser.put("password", password);
        registerUser.put("confirmPassword", confirmPassword);
        registerUser.put("phone", phone);
        registerUser.put("groupId", groupId);
        return registerUser;

    }
}
