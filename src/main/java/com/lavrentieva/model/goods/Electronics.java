package com.lavrentieva.model.goods;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public abstract class Electronics {
    protected ElectronicsType type;
    protected String series;
    protected String screenType;
    protected int price;

    public Electronics(ElectronicsType type) {
        this.type = type;
    }
}
