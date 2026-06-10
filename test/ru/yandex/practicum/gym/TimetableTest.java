package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTimetable().get(DayOfWeek.MONDAY).size());

        //Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTimetable().get(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTimetable().get(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        Assertions.assertEquals(2, timetable.getTimetable().get(DayOfWeek.THURSDAY).size());
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> timeOfTrainings = timetable.getTimetable().get(DayOfWeek.THURSDAY);
        Assertions.assertEquals("13:0", timeOfTrainings.firstKey().toString());
        Assertions.assertEquals("20:0", timeOfTrainings.lastKey().toString());

        // Проверить, что за вторник не вернулось занятий
        Assertions.assertNull(timetable.getTimetable().get(DayOfWeek.TUESDAY));
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assertions.assertEquals(1, timetable.getTimetable().get(DayOfWeek.MONDAY).size());
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> timeOfTrainings = timetable.getTimetable().get(DayOfWeek.MONDAY);
        Assertions.assertEquals("13:0", timeOfTrainings.firstKey().toString());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assertions.assertNull(timeOfTrainings.get(new TimeOfDay(14, 0)));
    }

    @Test
    void testGetCountByCoaches1() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");

        TrainingSession singleTrainingSession1 = new TrainingSession(group, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));


        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        ArrayList<Map.Entry<Coach, Integer>> countCountByCoaches = new ArrayList<>(timetable.getCountByCoaches().entrySet());

        Assertions.assertEquals(countCountByCoaches.get(0).getKey(), coach1);
        Assertions.assertEquals(countCountByCoaches.get(1).getKey(), coach2);
    }

    @Test
    void testGetCountByCoaches2() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");

        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group1, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));


        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);

        ArrayList<Map.Entry<Coach, Integer>> countCountByCoaches = new ArrayList<>(timetable.getCountByCoaches().entrySet());

        Assertions.assertEquals(countCountByCoaches.get(0).getKey(), coach1);
        Assertions.assertEquals(countCountByCoaches.get(1).getKey(), coach2);
    }

    @Test
    void testGetCountByCoaches3() {
        Timetable timetable = new Timetable();

        Group group1 = new Group("Акробатика для детей", Age.CHILD, 60);
        Group group2 = new Group("Акробатика для взрослых", Age.ADULT, 60);
        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        Coach coach2 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach3 = new Coach("Маринова", "Марина", "Маринович");

        TrainingSession singleTrainingSession1 = new TrainingSession(group1, coach1,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession singleTrainingSession2 = new TrainingSession(group2, coach1,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession3 = new TrainingSession(group1, coach2,
                DayOfWeek.TUESDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession4 = new TrainingSession(group2, coach3,
                DayOfWeek.FRIDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession5 = new TrainingSession(group1, coach3,
                DayOfWeek.THURSDAY, new TimeOfDay(14, 0));
        TrainingSession singleTrainingSession6 = new TrainingSession(group2, coach3,
                DayOfWeek.SUNDAY, new TimeOfDay(14, 0));

        timetable.addNewTrainingSession(singleTrainingSession1);
        timetable.addNewTrainingSession(singleTrainingSession2);
        timetable.addNewTrainingSession(singleTrainingSession3);
        timetable.addNewTrainingSession(singleTrainingSession4);
        timetable.addNewTrainingSession(singleTrainingSession5);
        timetable.addNewTrainingSession(singleTrainingSession6);

        ArrayList<Map.Entry<Coach, Integer>> countCountByCoaches = new ArrayList<>(timetable.getCountByCoaches().entrySet());

        Assertions.assertEquals(countCountByCoaches.get(0).getKey(), coach3);
        Assertions.assertEquals(countCountByCoaches.get(1).getKey(), coach1);
        Assertions.assertEquals(countCountByCoaches.get(2).getKey(), coach2);
    }

}
