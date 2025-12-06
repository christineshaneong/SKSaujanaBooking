package com.school.booking;

public class Booking {
    private Long id;
    private String className;
    private int students;
    private String purpose;
    private String equipment;
    private String day;       // NEW: Stores "Monday", "Tuesday"
    private String timeSlot;  // Stores "08:00 - 09:00"
    private String roomType;
    private String status;

    public Booking(Long id, String className, int students, String purpose, String equipment, String day, String timeSlot, String roomType) {
        this.id = id;
        this.className = className;
        this.students = students;
        this.purpose = purpose;
        this.equipment = equipment;
        this.day = day;
        this.timeSlot = timeSlot;
        this.roomType = roomType;
        this.status = "Pending";
    }

    // Getters
    public Long getId() { return id; }
    public String getClassName() { return className; }
    public int getStudents() { return students; }
    public String getPurpose() { return purpose; }
    public String getEquipment() { return equipment; }
    public String getDay() { return day; }
    public String getTimeSlot() { return timeSlot; }
    public String getRoomType() { return roomType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}