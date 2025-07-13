package com.github.tanakakfuji.hrcui.horse;

public class FemaleRacehorse extends Racehorse {
    public FemaleRacehorse(String name, int id) {
        super(name, id);
        this.penalty = 55.0;
    }

    public String getSex() {
        return "牝馬";
    }
}
