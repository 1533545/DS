package Kernel;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void addTaskToProject() {
        ProjectComposite project = new ProjectComposite();
        Task task = new Task("New Task", project);

        assertEquals(task._fatherNode, project);
        assertEquals(task.Name, "New Task");
    }
}