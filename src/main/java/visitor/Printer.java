package visitor;

import java.util.List;
import kernel.Component;
import kernel.Project;
import kernel.Task;

/**
 * Prints Component attributes values in the console with a custom style.
 **/
public class Printer implements Visitor {
  
  private int indentation;

  public Printer(int indentation) {
    this.indentation = indentation;
  }

  /**
   * Generates a custom indentation.
   **/
  private String generateCustomIndentation() {
    String customIndentation = "";
    for (int i = 0; i < this.indentation; i++) {
      customIndentation = customIndentation.concat("-");
    }
    return customIndentation;
  }

  /**
   * Generates a custom String with all tags concatenated.
   **/
  private String generateCustomTagListString(List<String> tagList) {
    String customTagList = "";

    if (tagList != null && !tagList.isEmpty()) {
      for (String tag : tagList) {
        customTagList = customTagList.concat(tag + " ");
      }
    } else {
      customTagList = "No tags";
    }

    return customTagList;
  }

  public void print(Component component) {
    component.acceptVisitor(this);
  }

  /**
   * Prints in console all nodes of the Component tree.
   **/
  @Override
  public void visitProject(Project project) {
    String customIndentation = generateCustomIndentation();
    String customTagList = generateCustomTagListString(project.getTags());
    System.out.println(customIndentation + ">" + "PROJECT: " + project.getName()
        + " - Start: " + project.getStartTime() + " - Finish: " + project.getFinishTime()
        + " - Duration: " + project.getDuration() + " - Tags: " + customTagList);
    int auxiliaryIndentation = this.indentation;
    this.indentation += 2;
    for (Component child : project.getChildren()) {
      child.acceptVisitor(this);
    }
    this.indentation = auxiliaryIndentation;
  }

  /**
   * Prints in console the attributes of a Task.
   **/
  @Override
  public void visitTask(Task task) {
    String customIndentation = generateCustomIndentation();
    String customTagList = generateCustomTagListString(task.getTags());
    System.out.println(customIndentation + ">" + "TASK: " + task.getName()
        + " - Start: " + task.getStartTime() + " - Finish: " + task.getFinishTime()
        + " - Duration: " + task.getDuration() + " - Tags: " + customTagList);
  }
}
