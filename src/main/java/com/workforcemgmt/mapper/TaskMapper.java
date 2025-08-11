package com.workforcemgmt.mapper;

import com.workforcemgmt.dto.StaffDto;
import com.workforcemgmt.dto.TaskDto;
import com.workforcemgmt.model.Staff;
import com.workforcemgmt.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    
    @Mapping(target = "assignedTo", source = "assignedTo")
    TaskDto taskToTaskDto(Task task);
    
    List<TaskDto> tasksToTaskDtos(List<Task> tasks);
    
    StaffDto staffToStaffDto(Staff staff);
}
