package in.vikasrajput.cloudfoundry.simple_rest.task;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Repository
public class TaskRepository {

    // Using a thread-safe list
    static List<Task> tasks = new CopyOnWriteArrayList<>();

    // Add a task
    public Mono<Void> add(Task task) {
        tasks.add(task);
        return Mono.empty();
    }

    // Update a task
    public Mono<Void> update(Task task) {
        return Mono.create(sink -> {
            tasks.stream()
                    .filter(t -> t.id().equals(task.id()))
                    .findFirst()
                    .ifPresent(existingTask -> {
                        int index = tasks.indexOf(existingTask);
                        tasks.set(index, task);
                    });
            sink.success();
        });
    }

    // Delete a task
    public Mono<Void> delete(Task task) {
        tasks.remove(task);
        return Mono.empty();
    }

    // Get all tasks
    public Flux<Task> getAll() {
        return Flux.fromIterable(tasks);
    }

    // Get a task by ID
    public Mono<Task> get(String id) {
        return Mono.justOrEmpty(tasks.stream()
                .filter(t -> t.id().equals(id))
                .findFirst());
    }

}
