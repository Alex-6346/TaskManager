package controller;

import entity.TaskDto;
import entity.TaskEntity;
import entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import service.TasksService;
import service.UsersService;

import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TasksController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private TasksService tasksService;

    @GetMapping("/add")
    public String addTaskPage(Model model) {
        return "task-add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<TaskDto> addTask(@RequestBody TaskDto taskDto) {
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

    @PostMapping("/update")
    @ResponseBody
    public ResponseEntity<TaskDto> updateTask(Model model, @RequestBody TaskDto taskDto){
        TaskEntity taskEntity = tasksService.updateTask(taskDto);
        model.addAttribute("taskInfo", taskEntity);
        taskDto.setId(taskEntity.getId());
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


    @PostMapping("/delete")
    @ResponseBody
    public ResponseEntity<TaskDto> deleteTask(Model model, @RequestBody TaskDto taskDto){
        tasksService.deleteTask(taskDto);
        return  ResponseEntity.status(HttpStatus.OK).body(taskDto);
    }

}