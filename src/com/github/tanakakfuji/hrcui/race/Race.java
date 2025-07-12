package com.github.tanakakfuji.hrcui.race;

import com.github.tanakakfuji.hrcui.course.Course;
import com.github.tanakakfuji.hrcui.factory.CourseFactory;
import com.github.tanakakfuji.hrcui.factory.RacehorseFactory;
import com.github.tanakakfuji.hrcui.horse.Racehorse;

import java.util.LinkedHashMap;
import java.util.Map;

public class Race {
    private Course course;
    private Map<Racehorse, Double> racehorsePositionMap = new LinkedHashMap<>();
    private Racehorse[] ranking;

    public Race(int numOfHorses) {
        this.course = CourseFactory.createCourse();
        setRacehorses(numOfHorses);
        this.ranking = new Racehorse[numOfHorses];
    }

    public Course getCourse() {
        return course;
    }

    public Racehorse[] getRacehorses() {
        return racehorsePositionMap.keySet().toArray(new Racehorse[0]);
    }

    public Racehorse[] getRanking() {
        return ranking;
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
}
