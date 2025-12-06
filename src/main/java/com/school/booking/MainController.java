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

    // Define the exact schedule structure
    private final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final String[] TIMES = {
        "08:00 - 09:00", "09:00 - 10:00", "10:00 - 11:00", 
        "11:00 - 12:00", "12:00 - 01:00", "02:00 - 03:00"
    };

    @GetMapping("/calendar")
    public String showCalendar(Model model, HttpSession session) {
        String role = (String) session.getAttribute("role");
        if (role == null) { role = "USER"; session.setAttribute("role", "USER"); }

        // Date Display
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy");

        model.addAttribute("bookings", bookingList);
        model.addAttribute("currentUserRole", role);
        model.addAttribute("todaysDate", today.format(formatter));
        
        // NEW: Send definitions to HTML so we can build the grid
        model.addAttribute("days", DAYS);
        model.addAttribute("timeSlots", TIMES);

        return "calendar";
    }

    @PostMapping("/book-room")
    public String submitBooking(
            @RequestParam String className,
            @RequestParam int students,
            @RequestParam String purpose,
            @RequestParam String equipment,
            @RequestParam String day,       // NEW INPUT
            @RequestParam String timeSlot,
            @RequestParam String roomType) {
        
        Booking newBooking = new Booking(idCounter.incrementAndGet(), className, students, purpose, equipment, day, timeSlot, roomType);
        bookingList.add(newBooking);
        
        return "redirect:/calendar";
    }

    @PostMapping("/update-status")
    public String updateStatus(@RequestParam Long id, @RequestParam String status) {
        for (Booking b : bookingList) {
            if (b.getId().equals(id)) {
                b.setStatus(status);
                break;
            }
        }
        return "redirect:/calendar";
    }
}