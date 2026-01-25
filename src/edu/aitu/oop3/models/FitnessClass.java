package edu.aitu.oop3.models;

import java.time.LocalDateTime;

public class FitnessClass {
    private int id;
    private String title;
    private String instructorName;
    private int maxCapacity;
    private LocalDateTime scheduleTime;

    public FitnessClass(int id, String title, String instructorName, int maxCapacity, LocalDateTime scheduleTime) {
        this.id = id;
        this.title = title;
        this.instructorName = instructorName;
        this.maxCapacity = maxCapacity;
        this.scheduleTime = scheduleTime;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getInstructorName() { return instructorName; }
    public int getMaxCapacity() { return maxCapacity; }
    public LocalDateTime getScheduleTime() { return scheduleTime; }
}
