package com.github.tanakakfuji.hrcui.horse;

public class MaleRacehorse extends Racehorse {
    public MaleRacehorse(String name, int id) {
        super(name, id);
        this.penalty = 57;
    }

    public String getSex() {
        return "牡馬";
    }
}
