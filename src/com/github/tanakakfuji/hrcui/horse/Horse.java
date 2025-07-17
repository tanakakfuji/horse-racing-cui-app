package com.github.tanakakfuji.hrcui.horse;

import java.util.Objects;
import java.util.Random;

public abstract class Horse {
    private String name;
    private int age;
    private int weight;
    public static final int MIN_HORSE_WEIGHT = 450;
    public static final int MAX_HORSE_WEIGHT = 550;

    public Horse(String name) {
        this.name = name;
        Random rand = new Random();
        this.age = rand.nextInt(2, 7);
        this.weight = rand.nextInt(MIN_HORSE_WEIGHT, MAX_HORSE_WEIGHT + 1);
    }

    public abstract String getSex();

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getWeight() {
        return weight;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Horse)) return false;
        Horse h = (Horse) o;
        return this.name.trim().equals(h.name.trim());
    }

    public int hashCode() {
        return Objects.hash(this.name.trim());
    }
}
