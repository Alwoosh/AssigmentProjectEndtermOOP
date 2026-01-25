package edu.aitu.oop3.models;

import java.time.LocalDateTime;

public class ClassBooking {
    private int id;
    private int memberId;
    private int classId;
    private LocalDateTime bookingTime;

    public ClassBooking(int id, int memberId, int classId, LocalDateTime bookingTime) {
        this.id = id;
        this.memberId = memberId;
        this.classId = classId;
        this.bookingTime = bookingTime;
    }

    public int getId() { return id; }
    public int getMemberId() { return memberId; }
    public int getClassId() { return classId; }
    public LocalDateTime getBookingTime() { return bookingTime; }
}
