package com.github.tanakakfuji.hrcui.horse;

public class CastratedMaleRacrhorse extends Racehorse {
    public CastratedMaleRacrhorse(String name, int id) {
        super(name, id);
        this.penalty = 57.0;
    }

    public String getSex() {
        return "セン馬";
    }
}
