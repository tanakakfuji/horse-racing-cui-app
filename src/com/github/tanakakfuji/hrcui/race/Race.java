package com.github.tanakakfuji.hrcui.race;

import com.github.tanakakfuji.hrcui.course.Course;
import com.github.tanakakfuji.hrcui.factory.CourseFactory;
import com.github.tanakakfuji.hrcui.factory.RacehorseFactory;
import com.github.tanakakfuji.hrcui.horse.Racehorse;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Race {
    private Course course;
    private Map<Racehorse, Double> racehorsePositionMap = new LinkedHashMap<>();
    private List<Racehorse> ranking = new ArrayList<>();

    public Race(int numOfHorses) {
        this.course = CourseFactory.createCourse();
        setRacehorses(numOfHorses);
    }

    public Course getCourse() {
        return course;
    }

    public Racehorse[] getRacehorses() {
        return racehorsePositionMap.keySet().toArray(new Racehorse[0]);
    }

    public List<Racehorse> getRanking() {
        return ranking;
    }

    public void play(int xSpeed) {
        for (int i = 3; i > 0; i--) {
            for (int j = 0; j < 50; j++) System.out.println();
            System.out.println(i);
            printProgress();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        while (!(ranking.size() == racehorsePositionMap.size())) {
            List<Racehorse> racehorsesAtFinish = updateRacehorsePositions();
            if (!racehorsesAtFinish.isEmpty()) {
                rankRacehorses(racehorsesAtFinish);
            }
            for (int i = 0; i < 50; i++) System.out.println();
            printProgress();
            try {
                Thread.sleep(1000 / xSpeed);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void setRacehorses(int numOfHorses) {
        for (int i = 0; i < numOfHorses; i++) {
            Racehorse createdRacehorse;
            while (true) {
                createdRacehorse = RacehorseFactory.createRacehorse(i + 1);
                if (!racehorsePositionMap.containsKey(createdRacehorse)) {
                    break;
                }
            }
            racehorsePositionMap.put(createdRacehorse, 0.0);
        }
    }

    private List<Racehorse> updateRacehorsePositions() {
        ArrayList<Racehorse> racehorsesAtFinish = new ArrayList<>();
        for (Racehorse r : racehorsePositionMap.keySet()) {
            double currentPosition = racehorsePositionMap.get(r);
            if (course.getLength() <= currentPosition) {
                continue;
            }
            double updatedPosition = currentPosition + r.run(this.course, currentPosition);
            racehorsePositionMap.put(r, updatedPosition);
            if (course.getLength() <= updatedPosition) {
                racehorsesAtFinish.add(r);
            }
        }
        return racehorsesAtFinish;
    }

    private void rankRacehorses(List<Racehorse> racehorsesAtFinish) {
        while (!racehorsesAtFinish.isEmpty()) {
            Racehorse fasterRacehorse = racehorsesAtFinish.get(0);
            double maxPosition = racehorsePositionMap.get(fasterRacehorse);
            for (int i = 1; i < racehorsesAtFinish.size(); i++) {
                Racehorse r = racehorsesAtFinish.get(i);
                if (maxPosition < racehorsePositionMap.get(r)) {
                    fasterRacehorse = r;
                    maxPosition = racehorsePositionMap.get(r);
                }
            }
            ranking.add(fasterRacehorse);
            racehorsePositionMap.put(fasterRacehorse, (double) course.getLength());
            racehorsesAtFinish.remove(racehorsesAtFinish.indexOf(fasterRacehorse));
        }
    }

    private void printProgress() {
        final int displaySize = 100;
        System.out.printf("""
                        %s
                        %s %d        %s
                        
                        """,
                course.getName() + " 競馬場", course.isDirtTrack() ? "ダート" : "芝", course.getLength(), course.getWeather().getName());
        StringBuilder sb = new StringBuilder();
        sb.append("Ｓ");
        for (int i = 0; i < displaySize - 1; i++) sb.append("ー");
        sb.append("Ｇ");
        System.out.println(sb.toString());
        for (Racehorse r : racehorsePositionMap.keySet()) {
            double position = racehorsePositionMap.get(r);
            double unitLength = course.getLength() / (double) displaySize;
            int consolePos = (int) (Math.ceil(position / unitLength));
            sb.setLength(0);
            for (int i = 0; i < consolePos; i++) {
                sb.append("　");
            }
            char bib = (char) (0x2460 + (r.getId() - 1));
            sb.append(bib);
            System.out.println(sb.toString());
        }
        sb.setLength(0);
        sb.append("Ｓ");
        for (int i = 0; i < displaySize - 1; i++) sb.append("ー");
        sb.append("Ｇ");
        System.out.println(sb.toString());
    }
}
