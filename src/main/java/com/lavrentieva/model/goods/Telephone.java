package com.lavrentieva.model.goods;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Telephone extends Electronics {
    private String model;

    public Telephone(ElectronicsType type) {
        super(type);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s %n", "Type of Electronics: " + type,
                "series: " + series, "model: " + model, "screen type: " + screenType,
                "price: " + price);
    }
}
