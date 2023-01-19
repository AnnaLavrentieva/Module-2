package com.lavrentieva.service;

import com.lavrentieva.model.Invoice;
import com.lavrentieva.model.goods.Electronics;
import com.lavrentieva.model.goods.ElectronicsType;
import com.lavrentieva.repository.ShopRepository;

import java.util.*;
import java.util.stream.Collectors;

public class AnalyticsService {
    private static AnalyticsService instance;
    private final ShopRepository shopRepository;

    public AnalyticsService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public static AnalyticsService getInstance() {
        if (instance == null) {
            instance = new AnalyticsService(ShopRepository.getInstance());
        }
        return instance;
    }

    public static AnalyticsService getInstance(final ShopRepository shopRepository) {
        if (instance == null) {
            instance = new AnalyticsService(shopRepository);
        }
        return instance;
    }

    public Map<ElectronicsType, Long> amountOfInvoicesByElectronicsTypes() {
        final Map<ElectronicsType, Long> mapAmount = shopRepository.getInvoices()
                .stream()
                .filter(Objects::nonNull)
                .map(Invoice::getListOfGoods)
                .filter(Objects::nonNull)
                .flatMap(List::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(Electronics::getType,
                        Collectors.counting()));
        return mapAmount;
    }

    public int sumOfSales() {
        final int sum = shopRepository.getInvoices()
                .stream()
                .filter(Objects::nonNull)
                .map(Invoice::getListOfGoods)
                .flatMap(List::stream)
                .mapToInt(Electronics::getPrice)
                .sum();
        return sum;
    }

    public Map<String, Long> amountOfInvoicesByRetail() {
        final Map<String, Long> mapAmount = shopRepository.getInvoices()
                .stream()
                .filter(Objects::nonNull)
                .filter(invoice -> invoice.getInvoiceType().equals("RETAIL"))
                .collect(Collectors.groupingBy(Invoice::getInvoiceType,
                        Collectors.counting()));
        return mapAmount;
    }

    public List<Invoice> firstThreeInvoicesByDate() {
        List<Invoice> invoices = shopRepository.getInvoices()
                .stream()
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(Invoice::getDate))
                .filter(Objects::nonNull)
                .limit(3)
                .collect(Collectors.toList());
        return invoices;
    }

    public List<Invoice> infoInvoicesByCustomerYounger18() {
        List<Invoice> infoFromInvoices = shopRepository.getInvoices()
                .stream()
                .filter(Objects::nonNull)
                .filter(invoice -> invoice.getCustomer().getAge() < 18)
                .peek(invoice -> invoice.setInvoiceType("LOW_AGE"))
                .collect(Collectors.toList());
        return infoFromInvoices;
    }

    public List<Invoice> sortTreeTimes() {
        final Comparator<Invoice> invoiceComparator = customerAgeComparator.
                thenComparing(goodsCountComparator).thenComparing(goodsSumComparator);
        List<Invoice> sort = shopRepository.getInvoices()
                .stream()
                .filter(Objects::nonNull)
                .sorted(invoiceComparator)
                .collect(Collectors.toList());
        return sort;
    }

    private final Comparator<Invoice> customerAgeComparator = (i1, i2) -> Integer.compare(
            i1.getCustomer().getAge(), i2.getCustomer().getAge());

    private final Comparator<Invoice> goodsCountComparator = (i1, i2) -> Long.compare(
            i1.getListOfGoods().stream().count(), i2.getListOfGoods().stream().count());

    private final Comparator<Invoice> goodsSumComparator = (i1, i2) -> Integer.compare(
            i1.getListOfGoods().stream().mapToInt(Electronics::getPrice).sum(),
            i2.getListOfGoods().stream().mapToInt(Electronics::getPrice).sum());


    public Invoice sumOfSmallestInvoice() {
        Invoice invoice = null;
        Optional<Invoice> mapSum = shopRepository.getInvoices()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.minBy(goodsSumComparator));

        if (mapSum.isPresent()) {
            invoice = mapSum.get();
        }
        return invoice;
    }

    //    Чеки, які містять тільки один тип товару
    public List<Invoice> invoicesWithOneElectronicsType() {
        final List<Invoice> withOneType = shopRepository.getInvoices()
                .stream()
                .filter(Objects::nonNull)
                .filter(invoice -> checkIfOneType(invoice))
                .collect(Collectors.toList());
        return withOneType;
    }

    private boolean checkIfOneType(Invoice invoice) {
        boolean result = false;
        final List<Electronics> withOneType = invoice.getListOfGoods();
        if (withOneType.size() == 1) {
            return true;
        }
        for (int i = 0; i < withOneType.size() - 1; i++) {
            if (withOneType.get(i).getType().equals(withOneType.get(i+1).getType())) {
                result = true;
            } else {
                result = false;
                break;
            }
        }
        return result;
    }

}






