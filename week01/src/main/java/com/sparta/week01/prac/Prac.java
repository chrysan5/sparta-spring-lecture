package com.sparta.week01.prac;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class Prac {
    //메서드 종류
    public static void simplePrint() {     // 파라미터 X, 반환값 X
        System.out.println("파라미터도 없고, 반환값도 없어요!");
    }
    public static void simpleSum(int num1, int num2) { // 파라미터 O, 반환값 X
        System.out.println("num1 :" + num1 + ", num2: " + num2);
    }
    public static int simpleReturn() { // 파라미터 X, 반환값 O
        return 3;
    }
    public static int sum(int num1, int num2) { // 파라미터 O, 반환값 O
        return num1 + num2;
    }


    public static void main(String[] args) {
        System.out.println("안녕, 스파르타!");

        //자료형
        int a = 7;
        int b = 3;
        float c = 3.5f;
        Long d = 100000000L;
        boolean isAdult = a < b;
        System.out.println(isAdult);

        //리스트
        List<String> mylist = new ArrayList<>();
        mylist.add("하이");
        mylist.add("바보");
        System.out.println(mylist.get(1));
        mylist.remove(0);

        //메서드 사용법
        simplePrint();

        //반복문, 조건문 사용법
        List<String> fruits = new ArrayList<>();
        fruits.add("감");
        fruits.add("배");
        fruits.add("감");
        fruits.add("딸기");
        fruits.add("수박");
        fruits.add("메론");
        fruits.add("수박");

        for (int i=0; i<fruits.size(); i++) {
            String fruit = fruits.get(i);
            System.out.println(fruit);
        }

        int count = 0;
        for (int i=0; i<fruits.size(); i++) {
            String fruit = fruits.get(i);
            if (fruit == "감") {
                count += 1;
            }
        }
        System.out.println(count);


        //클래스(Course) 연습
//        Course course = new Course();
//        course.title = "웹개발의 봄, Spring";
//        course.tutor = "김병관";
//        System.out.println(course.title);
//        System.out.println(course.tutor);


        //생성자 연습
//        Course course2 = new Course("웹개발의 봄 스프링", "남병관");
//        System.out.println(course2.title);
//        System.out.println(course2.tutor);


        //setter, getter 연습
        Course course3 = new Course();
        course3.setTitle("웹개발의 봄 스프링");
        course3.setTutor("남병관");
        System.out.println(course3.getTitle());
        System.out.println(course3.getTutor());
    }
}

