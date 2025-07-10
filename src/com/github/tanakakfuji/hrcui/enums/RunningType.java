package com.github.tanakakfuji.hrcui.enums;

public enum RunningType {
    FRONT_RUNNER("逃げ"),
    STALKER("先行"),
    LOOKER("差し"),
    CLOSER("追い込み");

    String name;

    RunningType(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
