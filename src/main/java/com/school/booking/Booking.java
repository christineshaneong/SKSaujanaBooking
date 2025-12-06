package com.school.booking;

public class Booking {
    private Long id;
    private String className;
    private int students;
    private String purpose;
    private String equipment;
    private String day;
    private String startTime; 
    private String endTime;   
    private String roomType;
    private String status;
    private String rejectionReason;

    // CONSTANT: This must match the CSS height of 'td' in calendar.html
    private static final int ROW_HEIGHT_PX = 80;

    public Booking(Long id, String className, int students, String purpose, String equipment, String day, String startTime, String endTime, String roomType) {
        this.id = id;
        this.className = className;
        this.students = students;
        this.purpose = purpose;
        this.equipment = equipment;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.roomType = roomType;
        this.status = "Pending";
        this.rejectionReason = "";
    }

    // --- LOGIC 1: Check if booking belongs in this hour ---
    public boolean covers(int currentHour) {
        int startH = getStartHour();
        int endH = getEndHour();
        int endM = getEndMinute();

        if (currentHour < startH) return false;
        if (currentHour > endH) return false;
        if (currentHour == endH && endM == 0) return false; 
        return true;
    }

    // --- LOGIC 2: Calculate Top Spacing (Pixels) ---
    public int getMarginTop(int currentHour) {
        if (currentHour == getStartHour()) {
            double minutes = getStartMinute();
            return (int) ((minutes / 60.0) * ROW_HEIGHT_PX);
        }
        return 0;
    }

    // --- LOGIC 3: Calculate Height (Pixels) ---
    public int getHeight(int currentHour) {
        int startH = getStartHour();
        int endH = getEndHour();
        double startM = getStartMinute();
        double endM = getEndMinute();
        
        if (currentHour == startH && currentHour == endH) {
             double duration = endM - startM;
             return (int) ((duration / 60.0) * ROW_HEIGHT_PX);
        }
        if (currentHour == startH) {
            double remainingMinutes = 60 - startM;
            return (int) ((remainingMinutes / 60.0) * ROW_HEIGHT_PX);
        }
        if (currentHour == endH) {
            return (int) ((endM / 60.0) * ROW_HEIGHT_PX);
        }
        return ROW_HEIGHT_PX;
    }

    // Helpers
    private int getStartHour() { return Integer.parseInt(startTime.split(":")[0]); }
    private int getStartMinute() { return Integer.parseInt(startTime.split(":")[1]); }
    private int getEndHour() { return Integer.parseInt(endTime.split(":")[0]); }
    private int getEndMinute() { return Integer.parseInt(endTime.split(":")[1]); }

    public String getTimeSlot() { return startTime + " - " + endTime; }

    // Getters and Setters
    public Long getId() { return id; }
    public String getClassName() { return className; }
    public int getStudents() { return students; }
    public String getPurpose() { return purpose; }
    public String getEquipment() { return equipment; }
    public String getDay() { return day; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getRoomType() { return roomType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }
}