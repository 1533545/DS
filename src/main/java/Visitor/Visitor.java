package Visitor;

import Kernel.Project;
import Kernel.Task;

public interface Visitor {
    void visitProject(Project project);
    void visitTask(Task task);
}
