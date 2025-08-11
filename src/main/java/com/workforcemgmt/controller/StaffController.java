package com.workforcemgmt.controller;

import com.workforcemgmt.dto.StaffDto;
import com.workforcemgmt.mapper.TaskMapper;
import com.workforcemgmt.model.Staff;
import com.workforcemgmt.service.TaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/staff")
@CrossOrigin(origins = "*")
public class StaffController {
    
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    
    public StaffController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }
    
    @GetMapping
    public ResponseEntity<List<StaffDto>> getAllStaff() {
        List<Staff> staff = taskService.getAllStaff();
        List<StaffDto> staffDtos = staff.stream()
                .map(taskMapper::staffToStaffDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(staffDtos);
    }
    
    @GetMapping("/{staffId}")
    public ResponseEntity<StaffDto> getStaffById(@PathVariable String staffId) {
        Staff staff = taskService.getStaffById(staffId);
        return ResponseEntity.ok(taskMapper.staffToStaffDto(staff));
    }
}
