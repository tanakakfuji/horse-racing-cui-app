package com.github.tanakakfuji.hrcui;

import com.github.tanakakfuji.hrcui.course.Course;
import com.github.tanakakfuji.hrcui.horse.Racehorse;
import com.github.tanakakfuji.hrcui.race.Race;

import java.util.List;
import java.util.Scanner;

public class GameManager {
    private Race race;
    public static final int MIN_HORSE_COUNT = 5;
    public static final int MAX_HORSE_COUNT = 18; // 21以上は丸文字の対応不可
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
        if (this.race == null) throw new IllegalStateException("レースが初期化されていない");
        Course c = race.getCourse();
        Racehorse[] racehorses = race.getRacehorses();
        for (int i = 0; i < 50; i++) System.out.println();

        System.out.printf("""
                        %s
                        %s %d        %s
                        ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
                        """,
                c.getName() + " 競馬場", c.isDirtTrack() ? "ダート" : "芝", c.getLength(), c.getWeather().getName());
        System.out.println("馬番         馬名            性齢      斤量     体重(増減量)    脚質      過去3戦    ダート   道悪");
        for (Racehorse r : racehorses) {
            String lastThreeRecords = String.join("-", r.getLastThreeRecords());
            System.out.printf("""
                            |%2d|  %-12s  |  %s%d  |  %.1f  |  %dkg(%d)  |  %-4s  |  %s  |  %s  |  %s  |
                            """,
                    r.getId(), r.getName(), r.getSex().charAt(0), r.getAge(), r.getPenalty(), r.getWeight(), r.getWeightDifference(), r.getRunningStyle().getName(), lastThreeRecords, r.isGoodOnDirt() ? "○" : " ", r.isGoodOnPoorGround() ? "○" : " ");
        }
        System.out.println("ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー");
        System.out.println();
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
        race.play(xSpeed);
    }

    public void printRaceResult() {
        Course c = race.getCourse();
        List<Racehorse> ranking = race.getRanking();
        if (ranking.isEmpty()) throw new IllegalStateException("レース結果が存在しない");
        for (int i = 0; i < 50; i++) System.out.println();

        System.out.printf("""
                        %s
                        %s %d        %s
                        ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー
                        """,
                c.getName() + " 競馬場", c.isDirtTrack() ? "ダート" : "芝", c.getLength(), c.getWeather().getName());
        System.out.println("レース結果");
        System.out.println("着順 馬番         馬名            性齢      斤量     体重(増減量)    脚質      過去3戦    ダート   道悪");
        for (int i = 0; i < ranking.size(); i++) {
            Racehorse r = ranking.get(i);
            String lastThreeRecords = String.join("-", r.getLastThreeRecords());
            System.out.printf("""
                            |%2d|%2s|  %-12s  |  %s%d  |  %.1f  |  %dkg(%d)  |  %-4s  |  %s  |  %s  |  %s  |
                            """,
                    i + 1, (char) (0x2460 + (r.getId() - 1)), r.getName(), r.getSex().charAt(0), r.getAge(), r.getPenalty(), r.getWeight(), r.getWeightDifference(), r.getRunningStyle().getName(), lastThreeRecords, r.isGoodOnDirt() ? "○" : " ", r.isGoodOnPoorGround() ? "○" : " ");
        }
        System.out.println("ーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーーー");
        System.out.println();
    }
}
