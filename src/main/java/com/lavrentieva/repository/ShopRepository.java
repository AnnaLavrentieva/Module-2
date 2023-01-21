package com.lavrentieva.repository;

import com.lavrentieva.model.Invoice;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ShopRepository {
    private static ShopRepository instance;
    private final List<Invoice> invoices = new ArrayList<>();

    public static ShopRepository getInstance() {
        if (instance == null) {
            instance = new ShopRepository();
        }
        return instance;
    }

    public void save(final Invoice invoice) {
        invoices.add(invoice);
    }
}
