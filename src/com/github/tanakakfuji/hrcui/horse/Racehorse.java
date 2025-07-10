package com.github.tanakakfuji.hrcui.horse;

import static com.github.tanakakfuji.hrcui.GameManager.MAX_HORSE_COUNT;
import com.github.tanakakfuji.hrcui.enums.ConditionType;
import com.github.tanakakfuji.hrcui.enums.RunningType;

import java.util.Random;

public abstract class Racehorse extends Horse {
    private int id;
    private double baseSpeed;
    protected int penalty;
    private RunningType runningStyle;
    private ConditionType condition;
    private int[] lastThreeRecords = new int[3];
    private boolean isGoodOnDirt;
    private boolean isGoodOnPoorGround;
    private int weightDifference;
    private boolean isHealthy;

    public Racehorse(String name, int id) {
        super(name);
        this.id = id;
        Random rand = new Random();
        double delta = rand.nextInt(-2, 3) / 10.0;
        this.baseSpeed = 20 + delta;

        int rr = rand.nextInt(4);
        this.runningStyle = switch (rr) {
            case 0 -> RunningType.FRONT_RUNNER;
            case 1 -> RunningType.STALKER;
            case 2 -> RunningType.LOOKER;
            case 3 -> RunningType.CLOSER;
            default -> throw new IllegalStateException("予期しない値: " + rr);
        };

        int cr = rand.nextInt(5);
        this.condition = switch (cr) {
            case 0 -> ConditionType.PERFECT;
            case 1 -> ConditionType.FINE;
            case 2 -> ConditionType.NORMAL;
            case 3 -> ConditionType.WEAK;
            case 4 -> ConditionType.AWFUL;
            default -> throw new IllegalStateException("予期しない値: " + cr);
        };

        setLastThreeRecords();

        int dr = rand.nextInt(2);
        this.isGoodOnDirt = dr == 0;

        int pr = rand.nextInt(2);
        this.isGoodOnPoorGround = pr == 0;

        int wr = rand.nextInt(-10, 11);
        this.weightDifference = wr;

        int hr = rand.nextInt(2);
        this.isHealthy = hr == 0;
    }

    public abstract String getSex();

    public int getId() {
        return id;
    }

    public int getPenalty() {
        return penalty;
    }

    public RunningType getRunningStyle() {
        return runningStyle;
    }

    public int[] getLastThreeRecords() {
        return lastThreeRecords;
    }

    public boolean isGoodOnDirt() {
        return isGoodOnDirt;
    }

    public boolean isGoodOnPoorGround() {
        return isGoodOnPoorGround;
    }

    public int getWeightDifference() {
        return weightDifference;
    }

    private void setLastThreeRecords() {
        for (int i = 0; i < 3; i++) {
            int result = rankBasedOnCondition();
            if (baseSpeed > 20.0 && result >= 2) {
                result--;
            } else if (baseSpeed < 20.0 && result <= MAX_HORSE_COUNT - 1) {
                result++;
            }
            this.lastThreeRecords[i] = result;
        }
    }

    private int rankBasedOnCondition() {
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
                switch (this.condition) {
                    case ConditionType.PERFECT -> {
                        rank = MAX_HORSE_COUNT - i;
                    }
                    case ConditionType.FINE -> {
                        int threeQuarterNumber = MAX_HORSE_COUNT * 3 / 4;
                        if (i <= threeQuarterNumber) {
                            rank = MAX_HORSE_COUNT - i;
                        } else {
                            rank = i - threeQuarterNumber;
                        }
                    }
                    case ConditionType.NORMAL -> {
                        if (i % 2 == 0) {
                            rank = MAX_HORSE_COUNT - (i * 2);
                        } else {
                            rank = (i + 1) * 2;
                        }
                    }
                    case ConditionType.WEAK -> {
                        int threeQuarterNumber = MAX_HORSE_COUNT * 3 / 4;
                        if (i <= threeQuarterNumber) {
                            rank = i + 1;
                        } else {
                            rank = MAX_HORSE_COUNT - (i - 1 - threeQuarterNumber);
                        }
                    }
                    case ConditionType.AWFUL -> {
                        rank = i + 1;
                    }
                }
            }
        }
        return rank;
    }
}
