package kernel;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.json.JSONObject;
import visitor.Visitor;


/**
 * Abstract class. Represents The generalization of the nodes belonging to a
 * Project - Task tree. Contains all shared attributes and methods.
 **/
public abstract class Component {
  protected Component fatherNode;
  protected String id;
  protected String name;
  protected String description;
  protected LocalDateTime startTime;
  protected LocalDateTime finishTime;

  protected abstract JSONObject toJson();

  public abstract Duration getDuration();

  public abstract void print(int indentation);

  protected Component(Component fatherNode, String name, String description) {
    this.fatherNode = fatherNode;
    this.id = generateUuiD();
    this.name = name;
    this.description = description;
  }

  protected Component(JSONObject jsonObject) {
    this.id = (String) jsonObject.get("Id");
    this.name = (String) jsonObject.get("Name");
    this.description = (String) jsonObject.get("Description");
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    var start = LocalDateTime.parse(jsonObject.get("Start").toString(), formatter);
    var finish = LocalDateTime.parse(jsonObject.get("Finish").toString(), formatter);

    if (start != LocalDateTime.MIN) {
      this.startTime = start;
    }

    if (finish != LocalDateTime.MIN) {
      this.finishTime = finish;
    }

  }

  protected JSONObject toJsonComponent(JSONObject jsonObject) {
    jsonObject.put("Id", this.id);
    jsonObject.put("Name", this.name);
    jsonObject.put("Description", this.description);
    jsonObject.put("Duration", getDuration());

    if (this.startTime == null) {
      jsonObject.put("Start", LocalDateTime.MIN);
    } else {
      jsonObject.put("Start", this.startTime);
    }

    if (this.finishTime == null) {
      jsonObject.put("Finish", LocalDateTime.MIN);
    } else {
      jsonObject.put("Finish", this.finishTime);
    }

    return jsonObject;
  }

  /**
   * comment.
   **/
  public void updateStartTime(LocalDateTime time) {
    Component node = this;
    while (node != null) {
      if (node.getStartTime() == null) {
        node.setStartTime(time);
      }
      node = node.fatherNode;
    }
  }

  /**
   * comment.
   **/
  public void updateFinishTime(LocalDateTime time) {
    Component node = this;
    while (node != null) {
      node.setFinishTime(time);
      node = node.fatherNode;
    }
  }

  protected String generateUuiD() {
    return UUID.randomUUID().toString();
  }

  /**
   *  Generates custom indentation and returns it as string.
   **/
  public String generateCustomIndentation(int indentation) {
    String customIndentation = "";
    for (int i = 0; i < indentation; i++) {
      customIndentation = customIndentation.concat("-");
    }
    return customIndentation;
  }

  public String getName() {
    return this.name;
  }

  public String getId() {
    return this.id;
  }

  public LocalDateTime getStartTime() {
    return this.startTime;
  }

  public LocalDateTime getFinishTime() {
    return this.finishTime;
  }

  public void setFinishTime(LocalDateTime time) {
    this.finishTime = time;
  }

  public void setStartTime(LocalDateTime time) {
    this.startTime = time;
  }

  /**
   * comment.
   */
  public void printComponentTimes() {
    Component project = this;
    while (project != null) {
      System.out.println(project.name.toUpperCase() + ":");
      System.out.println("Component-> Start: "
          + project.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));

      System.out.println("            End: "
          + project.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));

      System.out.println("            Duration: " + project.getDuration());
      project = project.fatherNode;
    }
    System.out.println("-------------------------------------------------");
  }

  public abstract void acceptVisitor(Visitor visitor);
}
