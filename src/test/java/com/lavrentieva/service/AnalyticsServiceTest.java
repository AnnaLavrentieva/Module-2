package com.lavrentieva.service;

import com.lavrentieva.model.Invoice;
import com.lavrentieva.model.goods.Electronics;
import com.lavrentieva.model.goods.ElectronicsType;
import com.lavrentieva.model.goods.Telephone;
import com.lavrentieva.model.goods.Television;
import com.lavrentieva.repository.ShopRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.random.RandomGenerator;

import static org.junit.jupiter.api.Assertions.*;

class AnalyticsServiceTest {

    private AnalyticsService target;
    private ShopRepository repository;

    private PersonService personService = new PersonService();

    @BeforeEach
    void setUpMockRepository() {
        repository = Mockito.mock(ShopRepository.class);
        target = AnalyticsService.getInstance(repository);
    }

    private List<Invoice> listForRepository() {
        final Telephone telephone1 = new Telephone(ElectronicsType.TELEPHONE);
        telephone1.setSeries("X 12");
        telephone1.setModel("Xiaomi");
        telephone1.setScreenType("AMOLED");
        telephone1.setPrice(600);
        final Telephone telephone2 = new Telephone(ElectronicsType.TELEPHONE);
        telephone2.setSeries("S 15");
        telephone2.setModel("Samsung");
        telephone2.setScreenType("AMOLED");
        telephone2.setPrice(375);
        final Television television1 = new Television(ElectronicsType.TELEVISION);
        television1.setSeries("Mi TV Q1E");
        television1.setDiagonal(55);
        television1.setScreenType("QLED");
        television1.setCountry("China");
        television1.setPrice(725);
        final Television television2 = new Television(ElectronicsType.TELEVISION);
        television2.setSeries("UE43AU7100UXUA");
        television2.setDiagonal(43);
        television2.setScreenType("LED");
        television2.setCountry("South Korea");
        television2.setPrice(425);

        final List<Electronics> listOfGoods1 = new ArrayList<>();
        listOfGoods1.add(telephone1);
        listOfGoods1.add(telephone2);
        final List<Electronics> listOfGoods2 = new ArrayList<>();
        listOfGoods2.add(television1);
        final List<Electronics> listOfGoods3 = new ArrayList<>();
        listOfGoods3.add(television2);
        listOfGoods3.add(telephone1);
        listOfGoods3.add(telephone2);
        final List<Electronics> listOfGoods4 = new ArrayList<>();
        listOfGoods4.add(telephone1);
        listOfGoods4.add(telephone2);
        listOfGoods4.add(television1);
        listOfGoods4.add(television2);
        final List<Electronics> listOfGoods5 = new ArrayList<>();
        listOfGoods5.add(telephone2);

        final int limit = 1500;
        final Invoice invoice1 = new Invoice(listOfGoods1,limit);
        invoice1.getCustomer().setAge(15);
        final Invoice invoice2 = new Invoice(listOfGoods2,limit);
        invoice2.getCustomer().setAge(15);
        final Invoice invoice3 = new Invoice(listOfGoods3,limit);
        invoice3.getCustomer().setAge(18);
        final Invoice invoice4 = new Invoice(listOfGoods4,limit);
        invoice4.getCustomer().setAge(35);
        final Invoice invoice5 = new Invoice(listOfGoods5,limit);
        invoice5.getCustomer().setAge(50);

        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);

        return invoices;
    }

    private Map<ElectronicsType, Long> expectedForAmountOfInvoicesByTypes() {
        final Map<ElectronicsType, Long> map = new HashMap<>();
        map.put(ElectronicsType.TELEPHONE,(long)7);
        map.put(ElectronicsType.TELEVISION,(long)4);
        return map;
    }

    @Test
    void amountOfInvoicesByElectronicsTypes() {
        Mockito.when(repository.getInvoices()).thenReturn(listForRepository());
        final Map<ElectronicsType, Long> expected = expectedForAmountOfInvoicesByTypes();
        final Map<ElectronicsType, Long> actual = target.amountOfInvoicesByElectronicsTypes();
        Assertions.assertEquals(expected, actual);
    }

    private int expectedForSumOfSales() {
        int sales = 600+375+725+425+600+375+600+375+725+425+375;
        return sales;
    }

    @Test
    void sumOfSales() {
        Mockito.when(repository.getInvoices()).thenReturn(listForRepository());
        final int expected = expectedForSumOfSales();
        final int actual = target.sumOfSales();
        Assertions.assertEquals(expected, actual);
    }

    private Map<String, Long> expectedForAmountOfInvoicesByRetail() {
        final Map<String, Long> map = new HashMap<>();
        map.put("RETAIL",(long)4);
        return map;
    }

    @Test
    void amountOfInvoicesByRetail() {
        Mockito.when(repository.getInvoices()).thenReturn(listForRepository());
        Map<String, Long> expected = expectedForAmountOfInvoicesByRetail();
        Map<String, Long> actual = target.amountOfInvoicesByRetail();
        Assertions.assertEquals(expected, actual);
    }

    private List<Invoice> expectedForFirstThreeInvoicesByDate() {
        final List<Invoice> list = new ArrayList<>();
        list.add(listForRepository().get(0));
        list.add(listForRepository().get(1));
        list.add(listForRepository().get(2));
        return list;
    }

//    @Test
//    void firstThreeInvoicesByDate() {
//        Mockito.when(repository.getInvoices()).thenReturn(listForRepository());
//        List<Invoice> expected = expectedForFirstThreeInvoicesByDate();
//        List<Invoice> actual = target.firstThreeInvoicesByDate();
//        Assertions.assertEquals(expected, actual);
//    }

    private List<Invoice> expectedForInfoInvoicesByCustomerYounger18() {
        final List<Invoice> list = new ArrayList<>();
        Invoice invoice1 = listForRepository().get(0);
        invoice1.setInvoiceType("LOW_AGE");
        Invoice invoice2 = listForRepository().get(1);
        invoice2.setInvoiceType("LOW_AGE");
        list.add(invoice1);
        list.add(invoice2);
        return list;
    }
//    @Test
//    void infoInvoicesByCustomerYounger18() {
//        Mockito.when(repository.getInvoices()).thenReturn(listForRepository());
//        List<Invoice> expected = expectedForInfoInvoicesByCustomerYounger18();
//        List<Invoice> actual = target.infoInvoicesByCustomerYounger18();
//        Assertions.assertEquals(expected, actual);
//
//    }

    @Test
    void sortTreeTimes() {
    }

    @Test
    void sumOfSmallestInvoice() {
    }

    @Test
    void invoicesWithOneElectronicsType() {
    }
}