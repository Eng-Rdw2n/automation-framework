package com.radwanalbunni.automation.core.reporting;

import com.radwanalbunni.automation.core.logging.SensitiveDataMasker;
import io.qameta.allure.Attachment;
import io.restassured.response.Response;

public class AllureAttachments {

    @Attachment(value = "Response", type = "application/json")
    public static String attachResponse(Response response) {
        return response != null
                ? SensitiveDataMasker.sanitizeJson(response.asPrettyString())
                : "Response is null";
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String attachText(String attachmentName, String content) {
        return content;
    }

    @Attachment(value = "{0}", type = "image/png")
    public static byte[] attachScreenshot(String name, byte[] screenshot) {
        return screenshot != null ? screenshot : new byte[0];
    }

    @Attachment(value = "{0}", type = "text/plain", fileExtension = ".log")
    public static byte[] attachLog(String name, byte[] content) {
        return content != null ? content : new byte[0];
    }
}
