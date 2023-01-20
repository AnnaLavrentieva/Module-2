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

import java.util.*;


class AnalyticsServiceTest {

    private AnalyticsService target;
    private ShopRepository repository;


    @BeforeEach
    void setUpMockRepository() {
        repository = Mockito.mock(ShopRepository.class);
        target = AnalyticsService.getInstance(repository);
    }

    @Test
    void amountOfInvoicesByElectronicsTypes() {
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
        final Invoice invoice1 = new Invoice(listOfGoods1, limit);
        invoice1.getCustomer().setAge(15);
        invoice1.getCustomer().setEmail("a@gmail.com");
        invoice1.getCustomer().setId("wantRest");
        final Invoice invoice2 = new Invoice(listOfGoods2, limit);
        invoice2.getCustomer().setAge(15);
        invoice2.getCustomer().setEmail("b@gmail.com");
        invoice2.getCustomer().setId("wantWine");
        final Invoice invoice3 = new Invoice(listOfGoods3, limit);
        invoice3.getCustomer().setAge(18);
        invoice3.getCustomer().setEmail("c@gmail.com");
        invoice3.getCustomer().setId("wantMovie");
        final Invoice invoice4 = new Invoice(listOfGoods4, limit);
        invoice4.getCustomer().setAge(50);
        invoice4.getCustomer().setEmail("d@gmail.com");
        invoice4.getCustomer().setId("wantCheese");
        final Invoice invoice5 = new Invoice(listOfGoods5, limit);
        invoice5.getCustomer().setAge(35);
        invoice5.getCustomer().setEmail("e@gmail.com");
        invoice5.getCustomer().setId("wantSmoke");

        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);

        final Map<ElectronicsType, Long> expected = new HashMap<>();
        expected.put(ElectronicsType.TELEPHONE, (long) 7);
        expected.put(ElectronicsType.TELEVISION, (long) 4);

        Mockito.when(repository.getInvoices()).thenReturn(invoices);
        final Map<ElectronicsType, Long> actual = target.amountOfInvoicesByElectronicsTypes();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void sumOfSales() {
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
        final Invoice invoice1 = new Invoice(listOfGoods1, limit);
        invoice1.getCustomer().setAge(15);
        invoice1.getCustomer().setEmail("a@gmail.com");
        invoice1.getCustomer().setId("wantRest");
        final Invoice invoice2 = new Invoice(listOfGoods2, limit);
        invoice2.getCustomer().setAge(15);
        invoice2.getCustomer().setEmail("b@gmail.com");
        invoice2.getCustomer().setId("wantWine");
        final Invoice invoice3 = new Invoice(listOfGoods3, limit);
        invoice3.getCustomer().setAge(18);
        invoice3.getCustomer().setEmail("c@gmail.com");
        invoice3.getCustomer().setId("wantMovie");
        final Invoice invoice4 = new Invoice(listOfGoods4, limit);
        invoice4.getCustomer().setAge(50);
        invoice4.getCustomer().setEmail("d@gmail.com");
        invoice4.getCustomer().setId("wantCheese");
        final Invoice invoice5 = new Invoice(listOfGoods5, limit);
        invoice5.getCustomer().setAge(35);
        invoice5.getCustomer().setEmail("e@gmail.com");
        invoice5.getCustomer().setId("wantSmoke");

        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);
        int expected = 600 + 375 + 725 + 425 + 600 + 375 + 600 + 375 + 725 + 425 + 375;

        Mockito.when(repository.getInvoices()).thenReturn(invoices);
        final int actual = target.sumOfSales();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void amountOfInvoicesByRetail() {
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
        final Invoice invoice1 = new Invoice(listOfGoods1, limit);
        invoice1.getCustomer().setAge(15);
        invoice1.getCustomer().setEmail("a@gmail.com");
        invoice1.getCustomer().setId("wantRest");
        final Invoice invoice2 = new Invoice(listOfGoods2, limit);
        invoice2.getCustomer().setAge(15);
        invoice2.getCustomer().setEmail("b@gmail.com");
        invoice2.getCustomer().setId("wantWine");
        final Invoice invoice3 = new Invoice(listOfGoods3, limit);
        invoice3.getCustomer().setAge(18);
        invoice3.getCustomer().setEmail("c@gmail.com");
        invoice3.getCustomer().setId("wantMovie");
        final Invoice invoice4 = new Invoice(listOfGoods4, limit);
        invoice4.getCustomer().setAge(50);
        invoice4.getCustomer().setEmail("d@gmail.com");
        invoice4.getCustomer().setId("wantCheese");
        final Invoice invoice5 = new Invoice(listOfGoods5, limit);
        invoice5.getCustomer().setAge(35);
        invoice5.getCustomer().setEmail("e@gmail.com");
        invoice5.getCustomer().setId("wantSmoke");

        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);

        final Map<String, Long> expected = new HashMap<>();
        expected.put("RETAIL", (long) 4);

        Mockito.when(repository.getInvoices()).thenReturn(invoices);
        Map<String, Long> actual = target.amountOfInvoicesByRetail();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void firstThreeInvoicesByDate() {
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
        final Invoice invoice1 = new Invoice(listOfGoods1, limit);
        invoice1.getCustomer().setAge(15);
        invoice1.getCustomer().setEmail("a@gmail.com");
        invoice1.getCustomer().setId("wantRest");
        final Invoice invoice2 = new Invoice(listOfGoods2, limit);
        invoice2.getCustomer().setAge(15);
        invoice2.getCustomer().setEmail("b@gmail.com");
        invoice2.getCustomer().setId("wantWine");
        final Invoice invoice3 = new Invoice(listOfGoods3, limit);
        invoice3.getCustomer().setAge(18);
        invoice3.getCustomer().setEmail("c@gmail.com");
        invoice3.getCustomer().setId("wantMovie");
        final Invoice invoice4 = new Invoice(listOfGoods4, limit);
        invoice4.getCustomer().setAge(50);
        invoice4.getCustomer().setEmail("d@gmail.com");
        invoice4.getCustomer().setId("wantCheese");
        final Invoice invoice5 = new Invoice(listOfGoods5, limit);
        invoice5.getCustomer().setAge(35);
        invoice5.getCustomer().setEmail("e@gmail.com");
        invoice5.getCustomer().setId("wantSmoke");

        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);
        final List<Invoice> expected = new ArrayList<>();
        expected.add(invoices.get(0));
        expected.add(invoices.get(1));
        expected.add(invoices.get(2));

        Mockito.when(repository.getInvoices()).thenReturn(invoices);
        List<Invoice> actual = target.firstThreeInvoicesByDate();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void sortTreeTimes() {
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
        final Invoice invoice1 = new Invoice(listOfGoods1, limit);
        invoice1.getCustomer().setAge(15);
        invoice1.getCustomer().setEmail("a@gmail.com");
        invoice1.getCustomer().setId("wantRest");
        final Invoice invoice2 = new Invoice(listOfGoods2, limit);
        invoice2.getCustomer().setAge(15);
        invoice2.getCustomer().setEmail("b@gmail.com");
        invoice2.getCustomer().setId("wantWine");
        final Invoice invoice3 = new Invoice(listOfGoods3, limit);
        invoice3.getCustomer().setAge(18);
        invoice3.getCustomer().setEmail("c@gmail.com");
        invoice3.getCustomer().setId("wantMovie");
        final Invoice invoice4 = new Invoice(listOfGoods4, limit);
        invoice4.getCustomer().setAge(50);
        invoice4.getCustomer().setEmail("d@gmail.com");
        invoice4.getCustomer().setId("wantCheese");
        final Invoice invoice5 = new Invoice(listOfGoods5, limit);
        invoice5.getCustomer().setAge(35);
        invoice5.getCustomer().setEmail("e@gmail.com");
        invoice5.getCustomer().setId("wantSmoke");

        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);

        final List<Invoice> expected = new ArrayList<>();
        expected.add(invoices.get(1));
        expected.add(invoices.get(0));
        expected.add(invoices.get(2));
        expected.add(invoices.get(4));
        expected.add(invoices.get(3));

        Mockito.when(repository.getInvoices()).thenReturn(invoices);
        List<Invoice> actual = target.sortTreeTimes();
        Assertions.assertEquals(expected, actual);
    }


    @Test
    void sumOfSmallestInvoice() {
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
        final Invoice invoice1 = new Invoice(listOfGoods1, limit);
        invoice1.getCustomer().setAge(15);
        invoice1.getCustomer().setEmail("a@gmail.com");
        invoice1.getCustomer().setId("wantRest");
        final Invoice invoice2 = new Invoice(listOfGoods2, limit);
        invoice2.getCustomer().setAge(15);
        invoice2.getCustomer().setEmail("b@gmail.com");
        invoice2.getCustomer().setId("wantWine");
        final Invoice invoice3 = new Invoice(listOfGoods3, limit);
        invoice3.getCustomer().setAge(18);
        invoice3.getCustomer().setEmail("c@gmail.com");
        invoice3.getCustomer().setId("wantMovie");
        final Invoice invoice4 = new Invoice(listOfGoods4, limit);
        invoice4.getCustomer().setAge(50);
        invoice4.getCustomer().setEmail("d@gmail.com");
        invoice4.getCustomer().setId("wantCheese");
        final Invoice invoice5 = new Invoice(listOfGoods5, limit);
        invoice5.getCustomer().setAge(35);
        invoice5.getCustomer().setEmail("e@gmail.com");
        invoice5.getCustomer().setId("wantSmoke");

        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);
        Invoice expected = invoices.get(4);

        Mockito.when(repository.getInvoices()).thenReturn(invoices);
        Invoice actual = target.sumOfSmallestInvoice();
        Assertions.assertTrue(expected.equals(actual));
    }

    @Test
    void invoicesWithOneElectronicsType() {
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
        final Invoice invoice1 = new Invoice(listOfGoods1, limit);
        invoice1.getCustomer().setAge(15);
        invoice1.getCustomer().setEmail("a@gmail.com");
        invoice1.getCustomer().setId("wantRest");
        final Invoice invoice2 = new Invoice(listOfGoods2, limit);
        invoice2.getCustomer().setAge(15);
        invoice2.getCustomer().setEmail("b@gmail.com");
        invoice2.getCustomer().setId("wantWine");
        final Invoice invoice3 = new Invoice(listOfGoods3, limit);
        invoice3.getCustomer().setAge(18);
        invoice3.getCustomer().setEmail("c@gmail.com");
        invoice3.getCustomer().setId("wantMovie");
        final Invoice invoice4 = new Invoice(listOfGoods4, limit);
        invoice4.getCustomer().setAge(50);
        invoice4.getCustomer().setEmail("d@gmail.com");
        invoice4.getCustomer().setId("wantCheese");
        final Invoice invoice5 = new Invoice(listOfGoods5, limit);
        invoice5.getCustomer().setAge(35);
        invoice5.getCustomer().setEmail("e@gmail.com");
        invoice5.getCustomer().setId("wantSmoke");

        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);
        invoices.add(invoice4);
        invoices.add(invoice5);

        final List<Invoice> expected = new ArrayList<>();
        expected.add(invoices.get(0));
        expected.add(invoices.get(1));
        expected.add(invoices.get(4));

        Mockito.when(repository.getInvoices()).thenReturn(invoices);
        List<Invoice> actual = target.invoicesWithOneElectronicsType();
        Assertions.assertTrue(expected.size() == actual.size()
                && expected.containsAll(actual));
    }

    @Test
    void infoInvoicesByCustomerYounger18() {
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

        final int limit = 1500;
        final Invoice invoice1 = new Invoice(listOfGoods1, limit);
        invoice1.getCustomer().setAge(15);
        invoice1.getCustomer().setEmail("a@gmail.com");
        invoice1.getCustomer().setId("wantRest");
        final Invoice invoice2 = new Invoice(listOfGoods2, limit);
        invoice2.getCustomer().setAge(18);
        invoice2.getCustomer().setEmail("b@gmail.com");
        invoice2.getCustomer().setId("wantWine");
        final Invoice invoice3 = new Invoice(listOfGoods3, limit);
        invoice3.getCustomer().setAge(45);
        invoice3.getCustomer().setEmail("c@gmail.com");
        invoice3.getCustomer().setId("wantMovie");
        final List<Invoice> invoices = new ArrayList<>();
        invoices.add(invoice1);
        invoices.add(invoice2);
        invoices.add(invoice3);

        final List<Invoice> expected = new ArrayList<>();
        final Invoice invoiceAnotherType1 = invoices.get(0);
        invoiceAnotherType1.setInvoiceType("LOW_AGE");
        expected.add(invoiceAnotherType1);

        Mockito.when(repository.getInvoices()).thenReturn(invoices);
        List<Invoice> actual = target.infoInvoicesByCustomerYounger18();
        Assertions.assertEquals(expected, actual);
    }

}



