package com.github.tanakakfuji.hrcui.factory;

import com.github.tanakakfuji.hrcui.course.Course;

import java.util.Random;

public class CourseFactory {
    private static String[] nameList = {
            "札幌", "函館", "福島", "中山", "東京",
            "新潟", "中京", "京都", "兵庫", "福岡",
            "帯広", "門別", "盛岡", "水沢", "浦和",
            "船橋", "大井", "川崎", "金沢", "笠松",
            "名古屋", "姫路", "園田", "高知", "佐賀",
    };

    public static Course createCourse() {
        Random rand = new Random();
        String courseName = nameList[rand.nextInt(nameList.length)];
        return new Course(courseName);
    }
}
