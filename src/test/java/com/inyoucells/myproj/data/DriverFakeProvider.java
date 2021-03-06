package com.inyoucells.myproj.data;

import com.inyoucells.myproj.service.driver.models.Driver;

public class DriverFakeProvider {

    private final int startIndex;
    private int index;

    public DriverFakeProvider(int startIndex) {
        this.startIndex = startIndex;
        index = startIndex;
    }

    public Driver generateDriver() {
        index++;
        return new Driver("name:" + index, "licence:" + index);
    }

    public void reset() {
        index = startIndex;
    }
}
