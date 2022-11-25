package visitor;

import kernel.Component;
import kernel.Project;
import kernel.Task;

/**
 *  Given the root node of a Component tree, search in all children nodes for the targetName
 *  and sets the found component in componentFound attribute.
**/
public class NameExplorer implements Visitor {

  /**
   * Tag to be found.
   **/
  String targetName;
  /**
   * Found Component.
   **/
  Component componentFound;

  /**
   * Default constructor.
   **/
  public NameExplorer() {
    /* Void */
  }

  /**
   * Parameter constructor.
   **/
  public NameExplorer(String targetName) {
    this.targetName = targetName;
    this.componentFound = null;
  }

  /**
   * Change targetName attribute value.
   **/
  public void setTargetName(String name) {
    this.targetName = name;
  }

  /**
   * Returns targetName value.
   **/
  public String getTargetName() {
    return this.targetName;
  }

  /**
   * Returns the found Component.
   **/
  public Component getResult() {
    return this.componentFound;
  }

  /**
   * Searches for coincides of targetName in each Component of the Tree.
   * If a coincidence is found, initialize componentFound with the found Component.
   */
  public void search(Project project) {

    String name = project.getName();

    if (name.equals(this.targetName)) {
      this.componentFound = project;
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
      this.componentFound = task;
    }
  }
}
