package visitor;

import kernel.Project;
import kernel.Task;

public interface Visitor {
    void visitProject(Project project);
    void visitTask(Task task);
}
