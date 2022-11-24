//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package kernel;

import java.io.PrintStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import visitor.Visitor;

/**
 * comment.
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

  protected Component(Component fatherNode, String name, String description, List<String> tagList) {
    this.fatherNode = fatherNode;
    this.name = name;
    this.description = description;
    this.tags = tagList;
  }

  protected Component(JSONObject jsonObject) {
    this.name = (String) jsonObject.get("Name");
    this.description = (String) jsonObject.get("Description");
    this.tags = this.generateTagsFromJson(jsonObject.getJSONArray("Tags"));
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    LocalDateTime start = LocalDateTime.parse(jsonObject.get("Start").toString(), formatter);
    LocalDateTime finish = LocalDateTime.parse(jsonObject.get("Finish").toString(), formatter);
    if (start != LocalDateTime.MIN) {
      this.startTime = start;
    }

    if (finish != LocalDateTime.MIN) {
      this.finishTime = finish;
    }

  }

  private List<String> generateTagsFromJson(JSONArray jsonArray) {
    List<String> tags = new ArrayList();
    if (!jsonArray.isEmpty() && jsonArray != null) {
      for (int i = 0; i < jsonArray.length(); ++i) {
        tags.add(jsonArray.get(i).toString());
      }
    }

    return tags;
  }

  protected JSONObject toJsonComponent(JSONObject jsonObject) {
    jsonObject.put("Name", this.name);
    jsonObject.put("Description", this.description);
    jsonObject.put("Duration", this.getDuration());
    jsonObject.put("Tags", this.tagsTojson());
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
      Iterator var2 = this.tags.iterator();

      while (var2.hasNext()) {
        String tag = (String) var2.next();
        jsonArray.put(tag);
      }
    }

    return jsonArray;
  }

  /**
   * comment.
   */
  public void updateStartTime(LocalDateTime time) {
    for (Component node = this; node != null; node = node.fatherNode) {
      if (node.getStartTime() == null) {
        node.setStartTime(time);
      }
    }

  }

  /**
   * comment.
   */
  public void updateFinishTime(LocalDateTime time) {
    for (Component node = this; node != null; node = node.fatherNode) {
      node.setFinishTime(time);
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

  /**
   * comment.
   */
  public void printComponentTimes() {
    for (Component project = this; project != null; project = project.fatherNode) {
      System.out.println(project.name.toUpperCase() + ":");
      PrintStream var10000 = System.out;
      LocalDateTime var10001 = project.getStartTime();
      var10000.println("Component-> Start: " + var10001.format(DateTimeFormatter.ISO_DATE_TIME));
      var10000 = System.out;
      var10001 = project.getFinishTime();
      var10000.println("            End: " + var10001.format(DateTimeFormatter.ISO_DATE_TIME));
      System.out.println("            Duration: " + String.valueOf(project.getDuration()));
    }

    System.out.println("-------------------------------------------------");
  }

  public abstract void acceptVisitor(Visitor var1);
}
