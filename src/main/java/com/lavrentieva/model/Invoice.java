package com.lavrentieva.model;

import com.lavrentieva.model.goods.Electronics;
import com.lavrentieva.service.PersonService;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class Invoice {
    private List<Electronics> listOfGoods;
    private Customer customer;
    private String invoiceType;
    private final Date date;
    private final PersonService personService = new PersonService();

    public Invoice(List<Electronics> listOfGoods, int limit) {
        date = new Date();
        this.listOfGoods = listOfGoods;
        customer = personService.createCustomer();
        invoiceType = typeSaleValue(limit, listOfGoods);
    }

    private String typeSaleValue(int limit, List<Electronics> listOfGoods) {
        Objects.requireNonNull(listOfGoods);
        int totalSum = listOfGoods.stream()
                .mapToInt(Electronics::getPrice)
                .sum();
        if (totalSum >= limit) {
            return "WHOLESALE";
        }
        return "RETAIL";
    }

    @Override
    public String toString() {
        return String.format("%s, %s %n", "[" + date +
                "] [user: " + customer + "] [invoice: " + invoiceType, listOfGoods + "]");
    }


}








