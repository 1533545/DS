package visitor;

import java.util.List;
import kernel.Component;
import kernel.Project;
import kernel.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Prints Component attributes values in the console with a custom style.
 **/
public class Printer implements Visitor {
  private static Logger logger = LoggerFactory.getLogger(Printer.class);

  /**
   * Stores the indentation length.
   **/
  private int indentation;

  /**
   * Default constructor.
   **/
  public Printer() {
    this.indentation = 0;
  }

  /**
   * Parameter constructor.
   **/
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

  /**
   * Prints in console all nodes of the Component.
   **/
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
    logger.info(customIndentation + ">" + "PROJECT: " + project.getName()
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
    logger.info(customIndentation + ">" + "TASK: " + task.getName()
        + " - Start: " + task.getStartTime() + " - Finish: " + task.getFinishTime()
        + " - Duration: " + task.getDuration() + " - Tags: " + customTagList);
  }
}
