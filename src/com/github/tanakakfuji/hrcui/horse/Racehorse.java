package com.github.tanakakfuji.hrcui.horse;

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

    private void setLastThreeRecords() {
//        TODO: ベーススピードと調子に基づいて、過去3戦の成績をセットする。
    }
}
