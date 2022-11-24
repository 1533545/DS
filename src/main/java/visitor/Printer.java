package visitor;

import kernel.Component;
import kernel.Project;
import kernel.Task;

import java.util.List;

public class Printer implements Visitor {

  //TODO: MOVE PROJECT PRINTER TO THIS CLASS
  //TODO: ADD CLASS HEADER
  private int indentation;

  public Printer(int indentation) {
    this.indentation = indentation;
  }

  private String generateCustomIndentation() {
    String customIndentation = "";
    for (int i = 0; i < this.indentation; i++) {
      customIndentation = customIndentation.concat("-");
    }
    return customIndentation;
  }

  private String generateCustomTagListString(List<String> tagList) {
    String customTagList = "";

    if(tagList != null && !tagList.isEmpty()) {
      for (String tag : tagList) {
        customTagList = customTagList.concat(tag + " ");
      }
    }
    else {
      customTagList = "No tags";
    }

    return customTagList;
  }

  public void print(Component component) {
    component.acceptVisitor(this);
  }

  @Override
  public void visitProject(Project project) {
    String customIndentation = generateCustomIndentation();
    String customTagList = generateCustomTagListString(project.getTags());
    System.out.println(customIndentation + ">" + "PROJECT: " + project.getName() +
        " - Start: " + project.getStartTime() + " - Finish: " + project.getFinishTime() +
        " - Duration: " + project.getDuration() + " - Tags: " + customTagList);
    int auxiliaryIndentation = this.indentation;
    this.indentation += 2;
      for (Component child : project.getChildren()) {
      child.acceptVisitor(this);
    }
    this.indentation = auxiliaryIndentation;
  }

  @Override
  public void visitTask(Task task) {
    String customIndentation = generateCustomIndentation();
    String customTagList = generateCustomTagListString(task.getTags());
    System.out.println(customIndentation + ">" + "TASK: " + task.getName() +
        " - Start: " + task.getStartTime() + " - Finish: " + task.getFinishTime() +
        " - Duration: " + task.getDuration() + " - Tags: " + customTagList);
  }
}
