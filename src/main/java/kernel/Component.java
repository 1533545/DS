package kernel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

/**
 * Abstraction of Project and Task.
 **/
public abstract class Component {
  /**
   * Logger object of the Component.
   **/
  private static Logger logger = LoggerFactory.getLogger(Component.class);

  /**
   * Father node of the Component.
   **/
  protected Component fatherNode;
  /**
   * List of tags identifiers.
   **/
  protected List<String> tags;
  /**
   * Name of the Component.
   **/
  protected String name;
  /**
   * Description of the Component.
   **/
  protected String description;
  /**
   * Start time of the Component.
   **/
  protected LocalDateTime startTime;
  /**
   * Finish time of the Component.
   **/
  protected LocalDateTime finishTime;

  /**
   * Returns a JSONObject of the Component object.
   **/
  protected abstract JSONObject toJson();

  /**
   * Returns the Duration of the Component.
   **/
  public abstract Duration getDuration();

  /**
   * Default constructor.
   **/
  protected Component() {
    //void
  }

  /**
   * Parameter constructor.
   **/
  protected Component(Component fatherNode, String name, String description, List<String> tagList) {
    this.fatherNode = fatherNode;
    this.name = name;
    this.description = description;
    if (tagList != null && !tagList.isEmpty()) {
      this.tags = tagsToLowercase(tagList);
    } else {
      this.tags = null;
    }
  }

  /**
   * Initialize a Component from a JSONObject.
   **/
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

  private List<String> tagsToLowercase(List<String> tags) {
    List<String> lowerCaseTagsList = new ArrayList<>();
    for (String tag : tags) {
      lowerCaseTagsList.add((tag.toLowerCase()));
    }
    return lowerCaseTagsList;
  }

  /**
   * Returns a list of tags generated from a JSONArray.
   **/
  private List<String> generateTagsFromJson(JSONArray jsonArray) {
    List<String> tags = new ArrayList();
    if (!jsonArray.isEmpty() && jsonArray != null) {
      for (int i = 0; i < jsonArray.length(); ++i) {
        tags.add(jsonArray.get(i).toString());
      }
    } else {
      logger.warn("null value or empty list received, careful!");
    }
    return tags;
  }

  /**
   * Returns a JSONObject of the Component object.
   **/
  protected JSONObject toJsonComponent(JSONObject jsonObject) {
    jsonObject.put("Name", this.name);
    jsonObject.put("Description", this.description);
    jsonObject.put("Duration", this.getDuration());
    jsonObject.put("Tags", this.tagsToJson());

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
   * Returns a JSONArray of the tags list of the Component.
   **/
  private JSONArray tagsToJson() {
    JSONArray jsonArray = new JSONArray();
    if (this.tags != null && !this.tags.isEmpty()) {
      Iterator var2 = this.tags.iterator();

      while (var2.hasNext()) {
        String tag = (String) var2.next();
        jsonArray.put(tag);
      }
    } else {
      logger.warn("null value or empty list received, careful!");
    }
    return jsonArray;
  }

  /**
   * Updates the startTime attribute of the Component object.
   */
  public void updateStartTime(LocalDateTime time) {
    for (Component node = this; node != null; node = node.fatherNode) {
      if (node.getStartTime() == null) {
        node.setStartTime(time);
      }
    }
  }

  /**
   * Updates the finishTime attribute of the Component object.
   **/
  public void updateFinishTime(LocalDateTime time) {
    for (Component node = this; node != null; node = node.fatherNode) {
      node.setFinishTime(time);
    }
  }

  /**
   * Returns the Component Name.
   **/
  public String getName() {
    return this.name;
  }

  /**
   * Returns a list of tags.
   **/
  public List<String> getTags() {
    return this.tags;
  }

  /**
   * Returns startTime attribute value of the Component object.
   **/
  public LocalDateTime getStartTime() {
    return this.startTime;
  }

  /**
   * Returns finishTime attribute value of the Component object.
   **/
  public LocalDateTime getFinishTime() {
    return this.finishTime;
  }

  /**
   * Change finishTime attribute value of the Component object.
   **/
  public void setFinishTime(LocalDateTime time) {
    this.finishTime = time;
  }

  /**
   * Change startTime attribute value of the Component object.
   **/
  public void setStartTime(LocalDateTime time) {
    this.startTime = time;
  }

  /**
   * Custom console printer for Component time values.
   **/
  public void printComponentTimes() {
    for (Component project = this; project != null; project = project.fatherNode) {
      logger.info(project.name.toUpperCase() + ":");
      logger.info("Component-> Start: "
          + project.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));
      logger.info("End: "
          + project.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));
      logger.trace("Duration: " + project.getDuration());
    }
  }

  /**
   * Accepts a visitor.
   **/
  public abstract void acceptVisitor(Visitor var1);
}
