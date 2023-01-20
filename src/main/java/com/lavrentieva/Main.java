package com.lavrentieva;

import com.lavrentieva.model.Invoice;
import com.lavrentieva.model.goods.ElectronicsType;
import com.lavrentieva.service.AnalyticsService;
import com.lavrentieva.service.ShopService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        int limit = 1500;
        final ShopService shopService = ShopService.getInstance();
        final AnalyticsService analyticsService = AnalyticsService.getInstance();

        shopService.writeToCsvFileData();

        try {
            shopService.readFromCsvFileData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Invoice invoice = shopService.createInvoice(limit);
        System.out.println(invoice);
        System.out.println();

        shopService.createInvoiceFromAmount(14, limit);
        shopService.printAllInvoices();
        System.out.println();

        final Map<ElectronicsType, Long> amountOfInvoicesByElectronicsTypes =
                analyticsService.amountOfInvoicesByElectronicsTypes();
        System.out.println(amountOfInvoicesByElectronicsTypes);
        System.out.println();

        final int sumOfSales = analyticsService.sumOfSales();
        System.out.println(sumOfSales);
        System.out.println();

        final Map<String, Long> amountOfInvoicesByRetail =
                analyticsService.amountOfInvoicesByRetail();
        System.out.println(amountOfInvoicesByRetail);
        System.out.println();

        List<Invoice> firstThreeInvoicesByDate = analyticsService.firstThreeInvoicesByDate();
        System.out.println(firstThreeInvoicesByDate);
        System.out.println();

        List<Invoice> sortTreeTimes = analyticsService.sortTreeTimes();
        System.out.println(sortTreeTimes);
        System.out.println();

        Invoice withSmallestSum = analyticsService.sumOfSmallestInvoice();
        System.out.println(withSmallestSum);
        System.out.println();

        List<Invoice> oneType = analyticsService.invoicesWithOneElectronicsType();
        System.out.println(oneType);
        System.out.println();

        List<Invoice> infoInvoicesByCustomerYounger18 =
                analyticsService.infoInvoicesByCustomerYounger18();
        System.out.println(infoInvoicesByCustomerYounger18);
        System.out.println();
    }
}