package com.github.tanakakfuji.hrcui.enums;

public enum WeatherType {
    SUNNY("晴れ"),
    CLOUDY("曇り"),
    RAINY("雨");

    String name;

    WeatherType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
