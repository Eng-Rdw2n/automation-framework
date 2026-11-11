package com.radwanalbunni.automation.api.clients;

import com.radwanalbunni.automation.core.reporting.AllureAttachments;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static io.restassured.RestAssured.given;

public class ApiClientBase {

    protected final Logger log = LogManager.getLogger(getClass());

    protected Response post(RequestSpecification spec, String endpoint) {
        log.info("[API] POST -> {}", endpoint);
        Response response = given()
                .spec(spec)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
        AllureAttachments.attachResponse(response);
        log.info("[API] POST <- {} | Status: {} | Time: {}ms", endpoint, response.getStatusCode(), response.getTime());
        return response;
    }

    protected Response get(RequestSpecification spec, String endpoint) {
        log.info("[API] GET -> {}", endpoint);
        Response response = given().spec(spec).when().get(endpoint).then().extract().response();
        AllureAttachments.attachResponse(response);
        log.info("[API] GET <- {} | Status: {} | Time: {}ms", endpoint, response.getStatusCode(), response.getTime());
        return response;
    }

    protected Response put(RequestSpecification spec, String endpoint) {
        log.info("[API] PUT -> {}", endpoint);
        Response response = given().spec(spec).when().put(endpoint).then().extract().response();
        AllureAttachments.attachResponse(response);
        log.info("[API] PUT <- {} | Status: {} | Time: {}ms", endpoint, response.getStatusCode(), response.getTime());
        return response;
    }

    protected Response delete(RequestSpecification spec, String endpoint) {
        log.info("[API] DELETE -> {}", endpoint);
        Response response = given()
                .spec(spec)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
        AllureAttachments.attachResponse(response);
        log.info("[API] DELETE <- {} | Status: {} | Time: {}ms", endpoint, response.getStatusCode(), response.getTime());
        return response;
    }
}
