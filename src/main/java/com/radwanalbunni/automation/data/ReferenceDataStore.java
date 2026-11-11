package com.radwanalbunni.automation.data;

import com.radwanalbunni.automation.data.models.domain.PaymentCardDataModel;
import com.radwanalbunni.automation.data.models.domain.ProductDataModel;
import com.radwanalbunni.automation.data.models.domain.UserDataModel;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Provides isolated copies of reusable users, products, and payment cards.
 */
public final class ReferenceDataStore {

    public UserDataModel user(String id) {
        return findById(
                TestDataLoader.load("shared/users", UserDataModel.class),
                id,
                UserDataModel::getId,
                UserDataModel::new,
                "shared/users"
        );
    }

    public ProductDataModel product(String id) {
        return findById(
                TestDataLoader.load("shared/products", ProductDataModel.class),
                id,
                ProductDataModel::getId,
                ProductDataModel::new,
                "shared/products"
        );
    }

    public List<ProductDataModel> products(String[] ids) {
        return Arrays.stream(ids)
                .map(this::product)
                .toList();
    }

    public PaymentCardDataModel paymentCard(String id) {
        return findById(
                TestDataLoader.load("shared/paymentCards", PaymentCardDataModel.class),
                id,
                PaymentCardDataModel::getId,
                PaymentCardDataModel::new,
                "shared/paymentCards"
        );
    }

    private <T> T findById(
            List<T> records,
            String id,
            Function<T, String> idExtractor,
            UnaryOperator<T> copy,
            String source
    ) {
        return records.stream()
                .filter(record -> id.equalsIgnoreCase(idExtractor.apply(record)))
                .findFirst()
                .map(copy)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Reference '" + id + "' was not found in " + source
                ));
    }
}
