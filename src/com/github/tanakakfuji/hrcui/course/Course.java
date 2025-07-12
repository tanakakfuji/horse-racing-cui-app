package com.github.tanakakfuji.hrcui.course;

import com.github.tanakakfuji.hrcui.enums.WeatherType;

import java.util.Objects;
import java.util.Random;

public class Course {
    private String name;
    private int length;
    private boolean isDirtTrack;
    private WeatherType weather;
    private final int MIN_COURSE_LENGTH = 1000;
    private final int MAX_COURSE_LENGTH = 3600;

    public Course(String name) {
        this.name = name;
        Random rand = new Random();
        int lr = rand.nextInt(MIN_COURSE_LENGTH / 100, MAX_COURSE_LENGTH / 100 + 1);
        this.length = lr * 100;

        int dr = rand.nextInt(2);
        this.isDirtTrack = dr == 0;

        int wr = rand.nextInt(3);
        this.weather = switch (wr) {
            case 0 -> WeatherType.SUNNY;
            case 1 -> WeatherType.CLOUDY;
            case 2 -> WeatherType.RAINY;
            default -> throw new IllegalStateException("予期しない値: " + wr);
        };
    }

    public String getName() {
        return name;
    }

    public int getLength() {
        return length;
    }

    public boolean isDirtTrack() {
        return isDirtTrack;
    }

    public WeatherType getWeather() {
        return weather;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (o == null) return false;
        if (!(o instanceof Course)) return false;
        Course c = (Course) o;
        if (!(this.name.trim().equals(c.name.trim()) && this.length == c.length
                && this.isDirtTrack == c.isDirtTrack && this.weather == this.weather)) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.name.trim(), this.length, this.isDirtTrack, this.weather);
    }
}
