package com.github.tanakakfuji.hrcui.horse;

import com.github.tanakakfuji.hrcui.course.Course;
import com.github.tanakakfuji.hrcui.enums.ConditionType;
import com.github.tanakakfuji.hrcui.enums.RunningType;

import java.util.Random;

import static com.github.tanakakfuji.hrcui.GameManager.MAX_HORSE_COUNT;
import static com.github.tanakakfuji.hrcui.course.Course.MAX_COURSE_LENGTH;
import static com.github.tanakakfuji.hrcui.course.Course.MIN_COURSE_LENGTH;

public abstract class Racehorse extends Horse {
    private int id;
    private double baseSpeed;
    protected double penalty;
    private RunningType runningStyle;
    private ConditionType condition;
    private String[] lastThreeRecords = new String[3];
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

    public double run(Course course, double currentPosition) {
        double speed = baseSpeed;
        speed += condition.getSpeed();
        speed += getSpeedByCourseLength(course);
        speed += getSpeedByDirtTrack(course);
        speed += getSpeedByPoorGround(course);
        speed += getSpeedByHealthy();
        return speed;
    }

    public int getId() {
        return id;
    }

    public double getPenalty() {
        return penalty;
    }

    public RunningType getRunningStyle() {
        return runningStyle;
    }

    public String[] getLastThreeRecords() {
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

    private double getSpeedByCourseLength(Course course) {
        int weightDiff = MAX_HORSE_WEIGHT - MIN_HORSE_WEIGHT;
        int lengthDiff = MAX_COURSE_LENGTH - MIN_COURSE_LENGTH;
        double speed = 0.0;
//        短距離の場合、重量級が有利
        if (course.getLength() <= MIN_COURSE_LENGTH + lengthDiff / 3.0) {
            if (MIN_HORSE_WEIGHT + weightDiff * 2.0 / 3.0 <= getWeight()) {
                speed = 0.2;
            }
        }
//        中距離の場合、中量級が有利
        else if (course.getLength() <= MIN_COURSE_LENGTH + lengthDiff * 2.0 / 3.0) {
            if (MIN_HORSE_WEIGHT + weightDiff / 3.0 <= getWeight() && getWeight() <= MIN_HORSE_WEIGHT + weightDiff * 2.0 / 3.0) {
                speed = 0.2;
            }
        }
//        長距離の場合、軽量級が有利
        else if (course.getLength() <= MAX_COURSE_LENGTH) {
            if (getWeight() <= MIN_HORSE_WEIGHT + weightDiff / 3.0) {
                speed = 0.2;
            }
        } else {
            speed = -0.2;
        }
        return speed;
    }

    private double getSpeedByDirtTrack(Course course) {
        double speed = 0.0;
        if (course.isDirtTrack()) {
            if (isGoodOnDirt) {
                speed = 0.2;
            } else {
                speed = -0.2;
            }
        }
        return speed;
    }

    private double getSpeedByPoorGround(Course course) {
        double speed = 0.0;
        if (course.isPoorGround()) {
            if (isGoodOnPoorGround) {
                speed = 0.2;
            } else {
                speed = -0.2;
            }
        }
        return speed;
    }

    private double getSpeedByHealthy() {
        double speed = 0.0;
        if (weightDifference < -5 || 5 < weightDifference) {
            speed = isHealthy ? 0.2 : -0.2;
        } else {
            speed = isHealthy ? 0.1 : -0.1;
        }
        return speed;
    }

    private void setLastThreeRecords() {
        for (int i = 0; i < 3; i++) {
            int rank = condition.generateRank();
            if (baseSpeed > 20.0 && rank >= 2) {
                rank--;
            } else if (baseSpeed < 20.0 && rank <= MAX_HORSE_COUNT - 1) {
                rank++;
            }
            lastThreeRecords[i] = String.valueOf(rank);
        }
    }
}
