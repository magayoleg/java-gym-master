package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {
    private HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable = new HashMap<>();

    public HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> getTimetable() {
        return timetable;
    }

    public Comparator<TimeOfDay> comparator = new Comparator<TimeOfDay>() {
        @Override
        public int compare(TimeOfDay t1, TimeOfDay t2) {
            if (t1.getHours() == t2.getHours()) {
                return t1.getMinutes() - t2.getMinutes();
            }
            return t1.getHours() - t2.getHours();
        }
    };

    Set<Coach> coachesByWeek = new HashSet<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        //сохраняем занятие в расписании
        trainingSession.getCoach().addTrainingCountByWeek();
        coachesByWeek.add(trainingSession.getCoach());

        if (timetable.containsKey(trainingSession.getDayOfWeek())) {
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingOfDay = timetable.get(trainingSession.getDayOfWeek());

            ArrayList<TrainingSession> trainingSessions = trainingOfDay.getOrDefault(trainingSession.getTimeOfDay(), new ArrayList<>());
            trainingSessions.add(trainingSession);

            trainingOfDay.put(trainingSession.getTimeOfDay(), trainingSessions);
        } else {
            ArrayList<TrainingSession> trainingSessions = new ArrayList<>();
            trainingSessions.add(trainingSession);
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingOfWeek = new TreeMap<>(comparator);
            trainingOfWeek.put(trainingSession.getTimeOfDay(), trainingSessions);
            timetable.put(trainingSession.getDayOfWeek(), trainingOfWeek);
        }
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        return timetable.getOrDefault(dayOfWeek, new TreeMap<>());
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        //как реализовать, тоже непонятно, но сложность должна быть О(1)
        if (timetable.containsKey(dayOfWeek)) {
            return timetable.get(dayOfWeek).getOrDefault(timeOfDay, new ArrayList<>());
        }

        return new ArrayList<>();
    }

    public LinkedHashMap<Coach, Integer> getCountByCoaches() {
        List<Coach> coachList = new ArrayList<>(coachesByWeek);
        coachList.sort((coach1, coach2) -> coach2.getTrainingCountByWeek() - coach1.getTrainingCountByWeek());

        LinkedHashMap<Coach, Integer> countTrainingByCoaches = new LinkedHashMap<>();
        for (Coach coach : coachList) {
            countTrainingByCoaches.put(coach, coach.getTrainingCountByWeek());
        }

        return countTrainingByCoaches;
    }

    @Override
    public String toString() {
        String timeTableString = "";

        for (DayOfWeek day : timetable.keySet()) {
            String dayTrainingString = "";
            TreeMap<TimeOfDay, ArrayList<TrainingSession>> trainingsOfDay = timetable.get(day);
            for (TimeOfDay timeOfDay : trainingsOfDay.keySet()) {
                dayTrainingString += "\t" + timeOfDay.getHours() + ":" + timeOfDay.getMinutes() + ": " + trainingsOfDay.get(timeOfDay) + ";\n";
            }
            timeTableString += day + ":" + " {" + "\n" + dayTrainingString + "}";
        }

        return timeTableString;
    }
}
