package visitor;

import kernel.Project;
import kernel.Task;

/**
 * Visitor Pattern interface.
 */
public interface Visitor {
  void visitProject(Project project);

  void visitTask(Task task);
}
