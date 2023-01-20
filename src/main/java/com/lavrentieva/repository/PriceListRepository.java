package com.lavrentieva.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PriceListRepository {
    private static List<Map<String, String>> priceList = new ArrayList<>();

    public PriceListRepository() {
    }

    public void save(final Map<String, String> goods) {
        priceList.add(goods);
    }

    public List<Map<String, String>> getPriceList() {
        return priceList;
    }
}
