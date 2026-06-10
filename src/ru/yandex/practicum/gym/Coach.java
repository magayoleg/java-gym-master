package ru.yandex.practicum.gym;

import java.util.Objects;

public class Coach {

    //фамилия
    private String surname;
    //имя
    private String name;
    //отчество
    private String middleName;

    private int trainingCountByWeek = 0;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public int getTrainingCountByWeek() {
        return trainingCountByWeek;
    }

    public void addTrainingCountByWeek() {
        this.trainingCountByWeek += 1;
    }

    public void resetTrainingCountByWeek() {
        this.trainingCountByWeek = 0;
    }

    @Override
    public String toString() {
        return surname + " " + name.charAt(0) + "." + middleName.charAt(0) + ".";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return trainingCountByWeek == coach.trainingCountByWeek && Objects.equals(surname, coach.surname) && Objects.equals(name, coach.name) && Objects.equals(middleName, coach.middleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(surname, name, middleName, trainingCountByWeek);
    }
}
