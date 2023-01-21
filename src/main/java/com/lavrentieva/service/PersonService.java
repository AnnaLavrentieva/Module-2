package com.lavrentieva.service;

import com.lavrentieva.model.Customer;

import java.util.Random;

public class PersonService {
    private final Random random = new Random();

    public Customer createCustomer() {
        return new Customer(getRandomEmail(), getRandomAge());
    }

    private String getRandomEmail() {
        return "Shop's_customer" + random.nextInt(1000) + "@gmail.com";
    }

    private int getRandomAge() {
        return random.nextInt(14, 56);
    }

}
