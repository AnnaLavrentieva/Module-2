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
    private final Customer customer;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice invoice)) return false;

        if (getListOfGoods() != null ? !getListOfGoods().toString().equals(invoice.getListOfGoods().toString())
                : invoice.getListOfGoods() != null)
            return false;
        if (getCustomer() != null ? !getCustomer().toString().equals(invoice.getCustomer().toString()) : invoice.getCustomer() != null)
            return false;
        if (getInvoiceType() != null ? !getInvoiceType().equals(invoice.getInvoiceType()) : invoice.getInvoiceType() != null)
            return false;
        if (getDate() != null ? !getDate().toString().equals(invoice.getDate().toString())
                : invoice.getDate() != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = getListOfGoods() != null ? getListOfGoods().hashCode() : 0;
        result = 31 * result + (getCustomer() != null ? getCustomer().hashCode() : 0);
        result = 31 * result + (getInvoiceType() != null ? getInvoiceType().hashCode() : 0);
        result = 31 * result + (getDate() != null ? getDate().hashCode() : 0);
        result = 31 * result + (getPersonService() != null ? getPersonService().hashCode() : 0);
        return result;
    }
}








