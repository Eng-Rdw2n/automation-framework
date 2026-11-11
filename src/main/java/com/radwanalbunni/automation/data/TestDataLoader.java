package com.radwanalbunni.automation.data;

import com.radwanalbunni.automation.core.config.ConfigReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TestDataLoader {

    private static final Logger log = LogManager.getLogger(TestDataLoader.class);
    private static final ObjectMapper mapper = new ObjectMapper();


    private static final Map<String, List<?>> CACHE = new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    public static <T> List<T> load(String feature, Class<T> clazz) {
        String env = ConfigReader.getInstance().getEnv();
        String cacheKey = feature + "-" + env + "-" + clazz.getSimpleName();

        return (List<T>) CACHE.computeIfAbsent(cacheKey, key -> {
            String filePath = "test-data/" + env + "/" + feature + ".json";
            log.info("[TestDataLoader] Loading: {} for class {}", filePath, clazz.getSimpleName());
            try (InputStream is = TestDataLoader.class.getClassLoader().getResourceAsStream(filePath)) {
                if (is == null) {
                    log.error("[TestDataLoader] Test data file not found: '{}'", filePath);
                    throw new RuntimeException(String.format(
                            "Test data file not found: '%s'. " +
                                    "Make sure the file exists under src/main/resources/test-data/%s/",
                            filePath, env
                    ));
                }
                List<T> data = mapper.readValue(
                        is,
                        mapper.getTypeFactory().constructCollectionType(List.class, clazz)
                );
                log.info("[TestDataLoader] Loaded {} records from {}", data.size(), filePath);
                return data;

            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse test data: " + filePath, e);
            }
        });
    }
}
