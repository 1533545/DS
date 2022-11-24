package visitor;

import kernel.Component;
import kernel.Project;
import kernel.Task;

/*
    Given the root node of a tree, search in all children nodes for the target name
    and sets the found component in resultName attribute.
*/
public class NameExplorer implements Visitor {

  String targetName;
  Component resultName;

  public NameExplorer(String targetName) {
    this.targetName = targetName;
    this.resultName = null;
  }

  public void setTargetName(String name) {
    this.targetName = name;
  }

  public String getTargetName() {
    return this.targetName;
  }

  public Component getResult() {
    return this.resultName;
  }

  public void search(Project project) {

    String name = project.getName();

    if (name.equals(this.targetName)) {
      this.resultName = project;
    } else {
      for (Component component : project.getChildren()) {
        component.acceptVisitor(this);
      }
    }
  }

  @Override
  public void visitProject(Project project) {
    this.search(project);
  }

  @Override
  public void visitTask(Task task) {
    String name = task.getName();
    if (name.equals(this.targetName)) {
      this.resultName = task;
    }
  }
}
