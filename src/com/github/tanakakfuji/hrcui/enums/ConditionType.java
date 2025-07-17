package com.github.tanakakfuji.hrcui.enums;

import java.util.Random;

import static com.github.tanakakfuji.hrcui.GameManager.MAX_HORSE_COUNT;

public enum ConditionType {
    PERFECT(0.4) {
        protected int evaluateRankByCondition(int i) {
            return MAX_HORSE_COUNT - i;
        }
    },
    FINE(0.2) {
        protected int evaluateRankByCondition(int i) {
            int result;
            int threeQuarterNumber = MAX_HORSE_COUNT * 3 / 4;
            if (i <= threeQuarterNumber) {
                result = MAX_HORSE_COUNT - i;
            } else {
                result = i - threeQuarterNumber;
            }
            return result;
        }
    },
    NORMAL(0.0) {
        protected int evaluateRankByCondition(int i) {
            int result;
            if (i % 2 == 0) {
                result = MAX_HORSE_COUNT - (i / 2);
            } else {
                result = (i + 1) / 2;
            }
            return result;
        }
    },
    WEAK(-0.2) {
        protected int evaluateRankByCondition(int i) {
            int result;
            int threeQuarterNumber = MAX_HORSE_COUNT * 3 / 4;
            if (i <= threeQuarterNumber) {
                result = i + 1;
            } else {
                result = MAX_HORSE_COUNT - (i - 1 - threeQuarterNumber);
            }
            return result;
        }
    },
    AWFUL(-0.4) {
        protected int evaluateRankByCondition(int i) {
            return i + 1;
        }
    };

    private double speed;

    ConditionType(double speed) {
        this.speed = speed;
    }

    public double getSpeed() {
        return speed;
    }

    public int generateRank() {
        Random rand = new Random();
        int rank = 1;
//        無限等比級数の和の公式に基づいた計算式
//        Sn = a / (1 - r) = 1 (100%)において、a = 1 / 3, r = 2 / 3 で考える。
//        一番低い確率を決めてから、徐々に確率を上げる。誤差の発生は一番高い確率で丸める。
        int range = 10000;
        double firstThreshold = 1.0 / 3.0 * Math.pow(2.0 / 3.0, MAX_HORSE_COUNT - 1) * range;
        int r = rand.nextInt(range + 1);
        double threshold = 0;
        for (int i = 0; i < MAX_HORSE_COUNT; i++) {
            threshold += firstThreshold * Math.pow(3.0 / 2.0, i);
            if (r < threshold || i == MAX_HORSE_COUNT - 1) {
                rank = evaluateRankByCondition(i);
                break;
            }
        }
        return rank;
    }

    protected abstract int evaluateRankByCondition(int i);
}
