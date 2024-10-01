package in.vikasrajput.cloudfoundry.simple_rest.task;

public class Task {

    private String id;
    private String desc;
    private String status;

    // Constructor
    public Task(String id, String desc, String status) {
        this.id = id;
        this.desc = desc;
        this.status = status;
    }

    // "Record-like" Getters (no 'get' prefix, using field names directly)
    public String id() {
        return id;
    }

    public String desc() {
        return desc;
    }

    public String status() {
        return status;
    }

    // Setters (if mutability is required)
    public void setId(String id) {
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // equals() and hashCode() for object comparison and hashing
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!id.equals(task.id)) return false;
        if (!desc.equals(task.desc)) return false;
        return status.equals(task.status);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + desc.hashCode();
        result = 31 * result + status.hashCode();
        return result;
    }

    // toString() method for printing
    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", desc='" + desc + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
