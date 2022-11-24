package kernel;

import org.json.JSONArray;
import visitor.Visitor;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
 * Abstract class. Represents The generalization of the nodes belonging to a
 * Project - Task tree. Contains all shared attributes and methods.
 */
public abstract class Component {
  protected Component fatherNode;
  protected List<String> tags;
  protected String name;
  protected String description;
  protected LocalDateTime startTime;
  protected LocalDateTime finishTime;

  protected abstract JSONObject toJson();

  public abstract Duration getDuration();

  protected Component(Component fatherNode, String name, String Description, List<String> tagList) {
    this.fatherNode = fatherNode;
    this.name = name;
    this.description = Description;
    this.tags = tagList;
  }

  protected Component(JSONObject jsonObject) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    this.name = (String) jsonObject.get("Name");
    this.description = (String) jsonObject.get("Description");
    this.tags = generateTagsFromJson(jsonObject.getJSONArray("Tags"));
    var start = LocalDateTime.parse(jsonObject.get("Start").toString(), formatter);
    var finish = LocalDateTime.parse(jsonObject.get("Finish").toString(), formatter);

    if (start != LocalDateTime.MIN) {
      this.startTime = start;
    }

    if (finish != LocalDateTime.MIN) {
      this.finishTime = finish;
    }
  }

  private List<String> generateTagsFromJson(JSONArray jsonArray) {
    List<String> tags = new ArrayList<>();
    if (!jsonArray.isEmpty() && jsonArray != null) {
      for (int i = 0; i < jsonArray.length(); i++) {
        tags.add(jsonArray.get(i).toString());
      }
    }
    return tags;
  }

  protected JSONObject toJsonComponent(JSONObject jsonObject) {
    jsonObject.put("Name", this.name);
    jsonObject.put("Description", this.description);
    jsonObject.put("Duration", getDuration());
    jsonObject.put("Tags", tagsTojson());

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

  private JSONArray tagsTojson() {
    JSONArray jsonArray = new JSONArray();

    if (this.tags != null && !this.tags.isEmpty()) {
      for (String tag : this.tags) {
        jsonArray.put(tag);
      }
    }

    return jsonArray;
  }

  public void updateStartTime(LocalDateTime time) {
    Component node = this;
    while (node != null) {
      if (node.getStartTime() == null) {
        node.setStartTime(time);
      }
      node = node.fatherNode;
    }
  }

  public void updateFinishTime(LocalDateTime time) {
    Component node = this;
    while (node != null) {
      node.setFinishTime(time);
      node = node.fatherNode;
    }
  }

  public String getName() {
    return this.name;
  }

  public List<String> getTags() {
    return this.tags;
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

  public void printComponentTimes() {
    Component project = this;
    while (project != null) {
      System.out.println(project.name.toUpperCase() + ":");
      System.out.println("Component-> Start: " + project.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));
      System.out.println("            End: " + project.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));
      System.out.println("            Duration: " + project.getDuration());
      project = project.fatherNode;
    }
    System.out.println("-------------------------------------------------");
  }

  public abstract void acceptVisitor(Visitor visitor);
}
