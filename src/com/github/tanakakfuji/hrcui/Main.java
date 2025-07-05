package com.github.tanakakfuji.hrcui;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            gameManager.initRace();
            gameManager.printRaceTable();
            gameManager.startRace();
            gameManager.printRaceResult();
            System.out.print("次のレースを行いますか？続行するにはEnterを押してください。");
            scanner.nextLine();
        }
    }
}
