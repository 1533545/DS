package visitor;

import kernel.Project;
import kernel.Task;

/**
 * Visitor Pattern interface.
 **/
public interface Visitor {
  /**
   * Visits a Project object.
   **/
  void visitProject(Project project);

  /**
   * Visits a Task object.
   **/
  void visitTask(Task task);
}
