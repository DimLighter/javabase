package com.hhg.jerry.java8;

/**
 * Created by lining on 2018/10/17.
 */
public class Person {
    public enum Sex {
        MALE, FEMALE
    }

    private String name;

    private Integer age;

    private Sex gender;

    public Person(String name, Integer age, Sex gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Sex getGender() {
        return gender;
    }

    public void setGender(Sex gender) {
        this.gender = gender;
    }
}
