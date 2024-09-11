package exercise.controller;

import java.util.List;

import exercise.dto.TaskCreateDTO;
import exercise.dto.TaskDTO;
import exercise.dto.TaskUpdateDTO;
import exercise.mapper.TaskMapper;
import exercise.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.TaskRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    // BEGIN
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TaskMapper taskMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDTO> index() {
        return  taskRepository.findAll()
                .stream()
                .map(task ->taskMapper.map(task))
                .toList();
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO show(@PathVariable long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        return taskMapper.map(task);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskDTO create(@Valid @RequestBody TaskCreateDTO dto) {
        var task = taskMapper.map(dto);
        taskRepository.save(task);
        return taskMapper.map(task);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TaskDTO update(@Valid @RequestBody TaskUpdateDTO dto, @PathVariable long id) {
        var task = taskRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found"));
        var userId = task.getAssignee().getId();
        var newAssignId = dto.getAssigneeId();
        if(userId != newAssignId) {
            var userLast = userRepository
                    .findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found"));
            userLast.removeTask(task);
            var userUpdated = userRepository.findById(newAssignId)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found"));
            userUpdated.addTask(task);
        }
        taskMapper.update(dto, task);
        taskRepository.save(task);
        return taskMapper.map(task);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        var task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found"));
        task.getAssignee().removeTask(task);
        taskRepository.delete(task);
    }
    // END
}
