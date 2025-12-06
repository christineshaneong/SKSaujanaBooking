package com.school.booking;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class MainController {

    private List<Booking> bookingList = new ArrayList<>();
    private AtomicLong idCounter = new AtomicLong();

    private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    // Includes 7 AM as earliest time
    private final int[] HOURS = {7, 8, 9, 10, 11, 12, 13, 14, 15, 16};

    @GetMapping("/calendar")
    public String showCalendar(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null) { role = "USER"; session.setAttribute("role", "USER"); }

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

        model.addAttribute("bookings", bookingList);
        model.addAttribute("currentUserRole", role);
        model.addAttribute("todaysDate", today.format(formatter));
        model.addAttribute("days", DAYS);
        model.addAttribute("hours", HOURS);

        return "calendar";
    }

    @PostMapping("/book-room")
    public String submitBooking(
            @RequestParam String className,
            @RequestParam int students,
            @RequestParam String purpose,
            @RequestParam String equipment,
            @RequestParam String day,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam String roomType) {
        
        Booking newBooking = new Booking(idCounter.incrementAndGet(), className, students, purpose, equipment, day, startTime, endTime, roomType);
        bookingList.add(newBooking);
        
        return "redirect:/calendar";
    }

    @PostMapping("/update-status")
    public String updateStatus(
            @RequestParam Long id, 
            @RequestParam String status,
            @RequestParam(required = false) String reason) {
        
        for (Booking b : bookingList) {
            if (b.getId().equals(id)) {
                b.setStatus(status);
                if ("Rejected".equals(status) && reason != null) {
                    b.setRejectionReason(reason);
                }
                break;
            }
        }
        return "redirect:/calendar";
    }
}