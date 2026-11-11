package com.radwanalbunni.automation.api.clients;

import com.radwanalbunni.automation.api.endpoints.Endpoints;
import com.radwanalbunni.automation.api.specs.RequestSpecs;
import com.radwanalbunni.automation.api.ResponseParser;
import com.radwanalbunni.automation.core.logging.SensitiveDataMasker;
import com.radwanalbunni.automation.core.reporting.AllureAttachments;
import com.radwanalbunni.automation.data.models.domain.RegistrationDataModel;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import com.radwanalbunni.automation.data.models.response.UserDetailsResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AccountClient extends ApiClientBase {

    public ApiMessageResponse verifyLogin(UserDataModel user) {
        log.info("[API] Verify login | email: {}", SensitiveDataMasker.maskEmail(user.getEmail()));
        attachAccountRequest("POST", Endpoints.VERIFY_LOGIN, user.getEmail(), true);

        Response response = post(
                RequestSpecs.getSpec()
                        .formParam("email", user.getEmail())
                        .formParam("password", user.getPassword()),
                Endpoints.VERIFY_LOGIN
        );
        return ResponseParser.parse(response, ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }

    public ApiMessageResponse verifyLoginWithoutEmail(UserDataModel user) {
        log.info("[API] Verify login without email");
        AllureAttachments.attachText("Request", "POST /api/verifyLogin | email parameter omitted");

        Response response = post(
                RequestSpecs.getSpec().formParam("password", user.getPassword()),
                Endpoints.VERIFY_LOGIN
        );
        return ResponseParser.parse(response, ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }

    public ApiMessageResponse verifyLoginWithoutPassword(UserDataModel user) {
        log.info("[API] Verify login without password");
        AllureAttachments.attachText("Request", "POST /api/verifyLogin | password parameter omitted");

        Response response = post(
                RequestSpecs.getSpec().formParam("email", user.getEmail()),
                Endpoints.VERIFY_LOGIN
        );
        return ResponseParser.parse(response, ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }

    public ApiMessageResponse deleteVerifyLogin() {
        log.info("[API] Reject unsupported DELETE to verify login");
        AllureAttachments.attachText("Request", "DELETE /api/verifyLogin");
        return ResponseParser.parse(delete(RequestSpecs.getSpec(), Endpoints.VERIFY_LOGIN), ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }

    public ApiMessageResponse createAccount(RegistrationDataModel data) {
        log.info("[API] Create account | email: {}", SensitiveDataMasker.maskEmail(data.getEmail()));
        attachAccountRequest("POST", Endpoints.CREATE_ACCOUNT, data.getEmail(), true);
        return ResponseParser.parse(post(accountRequestSpec(data), Endpoints.CREATE_ACCOUNT), ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }

    public ApiMessageResponse deleteAccount(RegistrationDataModel data) {
        log.info("[API] Delete account | email: {}", SensitiveDataMasker.maskEmail(data.getEmail()));
        attachAccountRequest("DELETE", Endpoints.DELETE_ACCOUNT, data.getEmail(), true);
        return ResponseParser.parse(
                delete(
                        RequestSpecs.getSpec()
                                .formParam("email", data.getEmail())
                                .formParam("password", data.getPassword()),
                        Endpoints.DELETE_ACCOUNT
                ),
                ApiMessageResponse.class,
                ApiMessageResponse::setHttpStatus
        );
    }

    /**
     * Deletes an account when only its login credentials are needed for cleanup.
     */
    public ApiMessageResponse deleteAccount(UserDataModel user) {
        log.info("[API] Delete account | email: {}", SensitiveDataMasker.maskEmail(user.getEmail()));
        attachAccountRequest("DELETE", Endpoints.DELETE_ACCOUNT, user.getEmail(), true);
        return ResponseParser.parse(
                delete(
                        RequestSpecs.getSpec()
                                .formParam("email", user.getEmail())
                                .formParam("password", user.getPassword()),
                        Endpoints.DELETE_ACCOUNT
                ),
                ApiMessageResponse.class,
                ApiMessageResponse::setHttpStatus
        );
    }

    public ApiMessageResponse updateAccount(RegistrationDataModel data) {
        log.info("[API] Update account | email: {}", SensitiveDataMasker.maskEmail(data.getEmail()));
        attachAccountRequest("PUT", Endpoints.UPDATE_ACCOUNT, data.getEmail(), true);
        return ResponseParser.parse(put(accountRequestSpec(data), Endpoints.UPDATE_ACCOUNT), ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }

    public UserDetailsResponse getUserDetail(String email) {
        log.info("[API] Get account details | email: {}", SensitiveDataMasker.maskEmail(email));
        attachAccountRequest("GET", Endpoints.GET_USER_DETAIL, email, false);
        return ResponseParser.parse(
                get(RequestSpecs.getSpec().queryParam("email", email), Endpoints.GET_USER_DETAIL),
                UserDetailsResponse.class,
                UserDetailsResponse::setHttpStatus
        );
    }

    private RequestSpecification accountRequestSpec(RegistrationDataModel data) {
        return RequestSpecs.getSpec()
                .formParam("name", data.getName())
                .formParam("email", data.getEmail())
                .formParam("password", data.getPassword())
                .formParam("title", data.getTitle())
                .formParam("birth_date", data.getDay())
                .formParam("birth_month", data.getMonth())
                .formParam("birth_year", data.getYear())
                .formParam("firstname", data.getFirstname())
                .formParam("lastname", data.getLastname())
                .formParam("company", data.getCompany())
                .formParam("address1", data.getAddress1())
                .formParam("address2", data.getAddress2())
                .formParam("country", data.getCountry())
                .formParam("zipcode", data.getZipcode())
                .formParam("state", data.getState())
                .formParam("city", data.getCity())
                .formParam("mobile_number", data.getMobileNumber());
    }

    private void attachAccountRequest(String method, String endpoint, String email, boolean includesPassword) {
        String requestSummary = method + " " + endpoint
                + System.lineSeparator() + "email=" + SensitiveDataMasker.maskEmail(email);
        if (includesPassword) {
            requestSummary += System.lineSeparator() + "password=***";
        }
        AllureAttachments.attachText("Request", requestSummary);
    }
}
