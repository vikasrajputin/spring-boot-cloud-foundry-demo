package in.vikasrajput.cloudfoundry.simple_rest.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

public class TaskRepositoryTest {

    private TaskRepository taskRepository;

    @BeforeEach
    public void setUp() {
        taskRepository = new TaskRepository();
        TaskRepository.tasks.clear();  // Clear the static list before each test
    }

    @Test
    public void testAddTask() {
        Task task = new Task("1", "Sample Task","Done");

        Mono<Void> result = taskRepository.add(task);

        // Verify that the task was added correctly
        StepVerifier.create(result)
                .verifyComplete();

        // Verify that the task list contains the task
        StepVerifier.create(Flux.fromIterable(TaskRepository.tasks))
                .expectNext(task)
                .verifyComplete();
    }

    @Test
    public void testUpdateTask() {
        Task task = new Task("1", "Original Task","Done");
        taskRepository.add(task).block();  // Add the task

        Task updatedTask = new Task("1", "Updated Task","Done");
        Mono<Void> result = taskRepository.update(updatedTask);

        // Verify that the task was updated correctly
        StepVerifier.create(result)
                .verifyComplete();

        // Verify that the updated task is in the list
        StepVerifier.create(Flux.fromIterable(TaskRepository.tasks))
                .expectNext(updatedTask)  // Should contain the updated task
                .verifyComplete();
    }

    @Test
    public void testDeleteTask() {
        Task task = new Task("1", "Sample Task","Done");
        taskRepository.add(task).block();  // Add the task

        Mono<Void> result = taskRepository.delete(task);

        // Verify that the task was deleted correctly
        StepVerifier.create(result)
                .verifyComplete();

        // Verify that the task list is empty
        StepVerifier.create(Flux.fromIterable(TaskRepository.tasks))
                .verifyComplete();
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task("1", "Task 1","Done");
        Task task2 = new Task("2", "Task 2","Done");
        taskRepository.add(task1).block();
        taskRepository.add(task2).block();

        Flux<Task> result = taskRepository.getAll();

        // Verify that both tasks are returned
        StepVerifier.create(result)
                .expectNext(task1)
                .expectNext(task2)
                .verifyComplete();
    }

    @Test
    public void testGetTaskById() {
        Task task = new Task("1", "Sample Task","Done");
        taskRepository.add(task).block();

        Mono<Task> result = taskRepository.get("1");

        // Verify that the correct task is returned
        StepVerifier.create(result)
                .expectNext(task)
                .verifyComplete();
    }

    @Test
    public void testGetTaskByIdNotFound() {
        Mono<Task> result = taskRepository.get("1");

        // Verify that Mono is empty when the task is not found
        StepVerifier.create(result)
                .verifyComplete();  // No value should be emitted
    }
}
