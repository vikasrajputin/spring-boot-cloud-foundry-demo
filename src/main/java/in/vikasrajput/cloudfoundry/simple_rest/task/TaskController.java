package in.vikasrajput.cloudfoundry.simple_rest.task;

import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskRepository taskRepository;

    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    // Add a task
    @PostMapping
    public Mono<Void> addTask(@RequestBody Task task) {
        return taskRepository.add(task);
    }

    // Update an existing task
    @PutMapping("/{id}")
    public Mono<Void> updateTask(@PathVariable String id, @RequestBody Task task) {
        var t = new Task(id, task.desc(),task.status());  // set the id in the task from the path variable
        return taskRepository.update(t);
    }

    // Delete a task
    @DeleteMapping("/{id}")
    public Mono<Void> deleteTask(@PathVariable String id) {
        return taskRepository.get(id)
            .flatMap(task -> taskRepository.delete(task));
    }

    // Get all tasks
    @GetMapping
    public Flux<Task> getAllTasks() {
        return taskRepository.getAll();
    }

    // Get a task by ID
    @GetMapping("/{id}")
    public Mono<Task> getTaskById(@PathVariable String id) {
        return taskRepository.get(id);
    }
}
