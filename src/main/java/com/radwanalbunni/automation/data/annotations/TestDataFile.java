package com.radwanalbunni.automation.data.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TestDataFile {
    String value(); // login / registration
}
