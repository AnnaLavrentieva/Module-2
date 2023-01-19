package com.lavrentieva.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Setter
@Getter
public class Customer {
    private final String id;
    private String email;
    private int age;

    public Customer(String email, int age) {
        this.id = UUID.randomUUID().toString();
        this.email = email;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s %n", "Customer's id: " + id,
                "email: " + email, "age: " + age);
    }
}
