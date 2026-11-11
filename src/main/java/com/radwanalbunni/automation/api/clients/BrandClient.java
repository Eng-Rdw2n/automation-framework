package com.radwanalbunni.automation.api.clients;

import com.radwanalbunni.automation.api.endpoints.Endpoints;
import com.radwanalbunni.automation.api.specs.RequestSpecs;
import com.radwanalbunni.automation.api.ResponseParser;
import com.radwanalbunni.automation.core.reporting.AllureAttachments;
import com.radwanalbunni.automation.data.models.response.ApiMessageResponse;
import com.radwanalbunni.automation.data.models.response.BrandsResponse;

public class BrandClient extends ApiClientBase {

    public BrandsResponse getAllBrands() {
        log.info("[API] Get all brands");
        AllureAttachments.attachText("Request", "GET /api/brandsList");
        return ResponseParser.parse(get(RequestSpecs.getSpec(), Endpoints.GET_ALL_BRANDS), BrandsResponse.class, BrandsResponse::setHttpStatus);
    }

    public ApiMessageResponse putToBrands() {
        log.info("[API] Reject unsupported PUT to brands");
        AllureAttachments.attachText("Request", "PUT /api/brandsList");
        return ResponseParser.parse(put(RequestSpecs.getSpec(), Endpoints.GET_ALL_BRANDS), ApiMessageResponse.class, ApiMessageResponse::setHttpStatus);
    }
}
