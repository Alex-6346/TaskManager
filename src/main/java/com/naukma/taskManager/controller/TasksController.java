package com.naukma.taskManager.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.naukma.taskManager.entity.TaskDto;
import com.naukma.taskManager.entity.TaskEntity;
import com.naukma.taskManager.entity.UserEntity;
import com.naukma.taskManager.service.TasksService;
import com.naukma.taskManager.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private TasksService tasksService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<TaskDto> addTask(@Valid @RequestBody TaskDto taskDto) {
        TaskEntity taskEntity = tasksService.createTask(taskDto);
        taskDto.setId(taskEntity.getId());
        return  ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @GetMapping("/{id}")
    public String taskPage(Model model, @PathVariable(value="id") long id){
        TaskEntity taskEntity = tasksService.getTaskById(id);
        TaskDto taskDto= new TaskDto(taskEntity);

        model.addAttribute("taskInfo", taskDto);
        return "task-page";
    }

    @PutMapping("/update")
    @ResponseBody
    public ResponseEntity<TaskDto> updateTask(@Valid @RequestBody TaskDto taskDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        taskDto.setUser(user.getId());
        tasksService.updateTask(taskDto);

        return  ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @DeleteMapping("/delete")
    @ResponseBody
    public ResponseEntity<TaskDto> deleteTask(@RequestBody TaskDto taskDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        taskDto.setUser(user.getId());
        tasksService.deleteTask(taskDto);
        return  ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

    @GetMapping("/view")
    public String viewTasks(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        List<TaskEntity> tasksList = tasksService.findAllTasksOfUser(user);
        model.addAttribute("tasksInfo", tasksList);
        return "tasks-view";
    }

    @ResponseBody
    @GetMapping("/show-tasks")
    public List<TaskDto> showTasks() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return tasksService.findUserNotOverdueTasks(user).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/show-overdue-tasks")
    public List<TaskDto> showOverdueTasks() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return tasksService.findUserOverdueTasks(user).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/completed-tasks")
    public List<TaskDto> completedTasks(@RequestParam("from_date") @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate fromDate,
                                        @RequestParam("to_date")  @DateTimeFormat(pattern="dd-MM-yyyy")
            LocalDate toDate) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return tasksService.findUserCompletedTasksByDates(user, fromDate, toDate).stream().map(this::convertToDto).collect(Collectors.toList());
    }



    @ResponseBody
    @GetMapping("/today-incomplete-tasks")
    public List<TaskDto> showTodayIncompleteTasks() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return tasksService.findUserTodayIncompleteTasks(user).stream().map(this::convertToDto).collect(Collectors.toList());
    }


    @ResponseBody
    @GetMapping("/today-completed-tasks")
    public List<TaskDto> showTodayCompletedTasks() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return tasksService.findUserTodayCompletedTasks(user).stream().map(this::convertToDto).collect(Collectors.toList());
    }


    @ResponseBody
    @GetMapping("/overdue-tasks-by-category")
    public List<TaskDto> showOverdueTasksByCategory(@PathParam("categoryID") Long categoryID){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return tasksService.findUserOverdueTasksByCategory(user, categoryID).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/not-overdue-tasks-by-category")
    public List<TaskDto> showNotOverdueTasksByCategory(@PathParam("categoryID") Long categoryID){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((UserDetails) principal).getUsername();
        UserEntity user = usersService.getUserByEmail(email);
        return tasksService.findUserNotOverdueTasksByCategory(user, categoryID).stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @ResponseBody
    @GetMapping("/get/{id}")
    public TaskDto task(Model model, @PathVariable(value="id") long id){
        TaskEntity taskEntity = tasksService.getTaskById(id);
        TaskDto taskDto = new TaskDto(taskEntity);

        return new TaskDto(taskEntity);
    }

    private TaskDto convertToDto(TaskEntity task) {
        return new TaskDto(task);
    }
}