package visitor;

import kernel.Component;
import kernel.Project;
import kernel.Task;

/**
 *  Given the root node of a Component tree, search in all children nodes for the targetName
 *  and sets the found component in ComponentFound attribute.
**/
public class NameExplorer implements Visitor {

  String targetName;
  Component ComponentFound;

  public NameExplorer(String targetName) {
    this.targetName = targetName;
    this.ComponentFound = null;
  }

  public void setTargetName(String name) {
    this.targetName = name;
  }

  public String getTargetName() {
    return this.targetName;
  }

  public Component getResult() {
    return this.ComponentFound;
  }

  /**
   * Searches for coincides of targetName in each Component of the Tree.
   * If a coincidence is found, initialize ComponentFound with the found Component.
   */
  public void search(Project project) {

    String name = project.getName();

    if (name.equals(this.targetName)) {
      this.ComponentFound = project;
    } else {
      for (Component component : project.getChildren()) {
        component.acceptVisitor(this);
      }
    }
  }

  /**
   * Searches for coincides of targetName in each component of a project.
   **/
  @Override
  public void visitProject(Project project) {
    this.search(project);
  }

  /**
   * Checks if the name of a Task is equal to the TargetName.
   **/
  @Override
  public void visitTask(Task task) {
    String name = task.getName();
    if (name.equals(this.targetName)) {
      this.ComponentFound = task;
    }
  }
}
