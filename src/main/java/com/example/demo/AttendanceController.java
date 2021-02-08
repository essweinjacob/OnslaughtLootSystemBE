package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("api/")
public class AttendanceController {
    @Autowired
    private AttendanceRepository attendanceRepository;



    @GetMapping("/getAttendance")
    public List<Attendance> getAllAttendanceEntries() {
        return attendanceRepository.getAllAttendanceEntries();
    }

    @GetMapping("/getUniqueRaidDates")
    public List <String> getUniqueRaidDates() { return attendanceRepository.getUniqueRaidDates(); }

    @GetMapping("/getUniquePlayerNames")
    public List <String> getUniquePlayerNames() {
        System.out.println("1");
        return attendanceRepository.getUniquePlayerNames();
    }

    @PutMapping("/postUpdateAttendance")
    public void updateAttendance(@RequestBody List<Attendance> attendance) {
        attendanceRepository.updateAttendance(attendance);
    }
}
