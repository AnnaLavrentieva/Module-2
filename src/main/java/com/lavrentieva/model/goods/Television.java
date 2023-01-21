package com.lavrentieva.model.goods;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Television extends Electronics {
    private int diagonal;
    private String country;

    public Television(ElectronicsType type) {
        super(type);
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s %n", "Type of Electronics: " + type,
                "series: " + series, "diagonal: " + diagonal, "screen type: " + screenType,
                "country: " + country, "price: " + price);
    }
}
