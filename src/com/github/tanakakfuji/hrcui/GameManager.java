package com.github.tanakakfuji.hrcui;

import com.github.tanakakfuji.hrcui.race.Race;

import java.util.Scanner;

public class GameManager {
    private Race race;
    public static final int MIN_HORSE_COUNT = 5;
    public static final int MAX_HORSE_COUNT = 18;
    private final int[] X_SPEEDS = {1, 2, 3};

    public void initRace() {
        Scanner scanner = new Scanner(System.in);
        System.out.printf("レースを作成します。出走馬数(%d以上%d以下の値)を入力してください。:", MIN_HORSE_COUNT, MAX_HORSE_COUNT);
        int numOfHorses;
        while (true) {
            if (scanner.hasNextInt()) {
                numOfHorses = scanner.nextInt();
                if (MIN_HORSE_COUNT <= numOfHorses && numOfHorses <= MAX_HORSE_COUNT) {
                    break;
                }
            } else {
                scanner.next();
            }
            System.out.printf("不正な値です。%d以上%d以下の値を入力してください。:", MIN_HORSE_COUNT, MAX_HORSE_COUNT);
        }
        this.race = new Race(numOfHorses);
    }

    public void printRaceTable() {
        System.out.println("レースを表示");
    }

    public void startRace() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("レースの再生速度を次の値から選択してください。");
        for (int x: X_SPEEDS) {
            System.out.printf("[%d]", x);
        }
        System.out.print(":");

        int xSpeed;
        infiniteLoop: while (true) {
            if (scanner.hasNextInt()) {
                xSpeed = scanner.nextInt();
                for (int x: X_SPEEDS) {
                    if (xSpeed == x) {
                        break infiniteLoop;
                    }
                }
            } else {
                scanner.next();
            }
            System.out.print("不正な値です。次の値から選択してください。");
            for (int x: X_SPEEDS) {
                System.out.printf("[%d]", x);
            }
            System.out.print(":");
        }
        System.out.println("レース開始");
    }

    public void printRaceResult() {
        System.out.println("結果を表示");
    }
}
