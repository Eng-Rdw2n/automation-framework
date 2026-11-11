package com.radwanalbunni.automation.data.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Response returned by the user-details endpoint.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsResponse {

    private int httpStatus;
    private int responseCode;
    private User user;

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class User {
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
