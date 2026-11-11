package com.radwanalbunni.automation.data.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestDataType {
    String value();  // valid / invalid / edge
}
