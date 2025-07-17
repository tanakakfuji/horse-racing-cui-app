package com.github.tanakakfuji.hrcui.enums;

public enum RunningType {
    FRONT_RUNNER("逃げ", 1.2) {
        public double calcBoostRate(double progress) {
            double rate = 1.0;
            if (progress <= 0.5) {
                rate = getBoostRate();
            }
            return rate;
        }
    },
    STALKER("先行", 1.125) {
        public double calcBoostRate(double progress) {
            double rate = 1.0;
            if (progress <= 0.75) {
                rate = getBoostRate();
            }
            return rate;
        }
    },
    LOOKER("差し", 1.125) {
        public double calcBoostRate(double progress) {
            double rate = 1.0;
            if (0.25 <= progress) {
                rate = getBoostRate();
            }
            return rate;
        }
    },
    CLOSER("追い込み", 1.2) {
        public double calcBoostRate(double progress) {
            double rate = 1.0;
            if (0.5 <= progress) {
                rate = getBoostRate();
            }
            return rate;
        }
    };

    private String name;
    private double boostRate;

    RunningType(String name, double boostRate) {
        this.name = name;
        this.boostRate = boostRate;
    }

    public String getName() {
        return name;
    }

    public double getBoostRate() {
        return boostRate;
    }

    public abstract double calcBoostRate(double progress);
}
