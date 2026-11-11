package com.radwanalbunni.automation.data;

import com.radwanalbunni.automation.data.annotations.TestDataFile;
import com.radwanalbunni.automation.data.annotations.TestDataType;
import com.radwanalbunni.automation.core.config.ConfigReader;
import com.radwanalbunni.automation.data.models.domain.TestDataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.List;

public class GenericDataProvider {

    private static final Logger log = LogManager.getLogger(GenericDataProvider.class);

    @DataProvider(name = "data")
    public Object[][] provide(Method method) {

        TestDataFile featureAnn = method.getAnnotation(TestDataFile.class);
        TestDataType typeAnn = method.getAnnotation(TestDataType.class);

        if (featureAnn == null) {
            throw new RuntimeException("Missing @TestDataFile on: " + method.getName());
        }

        if (typeAnn == null) {
            throw new RuntimeException("Missing @TestDataType on: " + method.getName());
        }

        String feature = featureAnn.value();
        String type = typeAnn.value();
        String env = ConfigReader.getInstance().getEnv();

        Class<?> modelClass = method.getParameterTypes()[0];

        log.info("[DataProvider] feature={} | type={} | env={} | model={}",
                feature, type, env, modelClass.getSimpleName());

        List<?> all = TestDataLoader.load(feature, modelClass);

        List<TestDataModel> result = all.stream()
                .map(d -> (TestDataModel) d)
                .filter(d -> d.getType().equalsIgnoreCase(type))
                .map(DataProcessor::process)
                .toList();

        if (result.isEmpty()) {
            throw new RuntimeException(
                    "No test data found for feature=" + feature +
                            ", type=" + type +
                            ", env=" + env
            );
        }
        return result.stream()
                .map(d -> new Object[]{d})
                .toArray(Object[][]::new);
    }
}
