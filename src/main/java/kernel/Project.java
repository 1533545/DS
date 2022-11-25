package kernel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

/**
 * Extension of Component. Container of Components, in other words,
 * contains multiple Projects or/and Tasks. Represents a project node of the components tree.
 **/
public class Project extends Component {
  /**
   * Logger for Task class.
   **/
  private static Logger logger = LoggerFactory.getLogger(Project.class);
  /**
   * List of all child Components nodes.
   **/
  private List<Component> children;

  /**
   * Default Project Constructor
   **/
  public Project() {
    super();

  }

  /**
   * Parameter constructor for Project.
   **/
  public Project(Component fatherNode, String name, String description, List<String> tagList) {
    super(fatherNode, name, description, tagList);
    if ((name.replaceAll("\\s", "")).matches("^[a-zA-Z0-9]*$") && name != null) {
      this.children = new ArrayList<>();
      logger.debug("Project " + name + " has been created");
    } else {
      throw new IllegalArgumentException("Illegal project name:" + name);
    }
    invariant();
  }

  /**
   * Initialize a Project object from a JSONObject.
   **/
  public Project(JSONObject jsonObject) throws Exception {
    super(jsonObject);
    if (jsonObject != null) {
      JSONArray jsonArray = jsonObject.getJSONArray("Children");
      this.children = new ArrayList<>();
      generateChildrenFromJson(jsonArray);
    } else {
      throw new Exception("No file to read");
    }

    invariant();
  }

  /**
   * Accepts Visitor.
   **/
  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitProject(this);
    logger.debug("Project " + this.name + " visited");
  }

  /**
   * Initialize a Children attribute from a JSONArray .
   **/
  private void generateChildrenFromJson(JSONArray jsonArray) throws Exception {
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject childJson = jsonArray.getJSONObject(i);
      String objectType = childJson.get("Class").toString();

      if (objectType.equals("Task")) {
        generateTaskFromJson(childJson);
      } else if (objectType.equals("Project")) {
        generateProjectFromJson(childJson);
      } else {
        throw new Exception("No class match");
      }
    }
  }

  /**
   * Initialize a Task object from a JSONObject and adds it to Children attribute.
   **/
  private void generateTaskFromJson(JSONObject childJson) throws Exception {
    Task task = new Task(childJson);
    task.fatherNode = this;
    this.children.add(task);
  }

  /**
   * Initialize a Project object from a JSONObject and adds it to Children attribute.
   **/
  private void generateProjectFromJson(JSONObject childJson) throws Exception {
    Project task = new Project(childJson);
    task.fatherNode = this;
    this.children.add(task);
  }

  /**
   * Initialize a Project object from a JSONObject and adds it to Children attribute.
   **/
  @Override
  public JSONObject toJson() {
    JSONObject jsonObject = toJsonComponent(new JSONObject());
    jsonObject.put("Class", "Project");
    jsonObject.put("Children", childrenToJson());
    assert (jsonObject != null);
    return jsonObject;
  }

  /**
   * Returns a JSONArray of all child's of children attribute.
   **/
  private JSONArray childrenToJson() {
    JSONArray jsonArray = new JSONArray();
    for (Component child : children) {
      jsonArray.put(child.toJson());
    }
    return jsonArray;
  }

  /**
   * Add a component to children Attribute.
   */
  public void addComponent(Component projectComponent) {
    invariant();
    if (projectComponent != null) {
      this.children.add(projectComponent);
      projectComponent.fatherNode = this;
      logger.info(projectComponent.name + " has been added to Project " + this.name);
    }
    invariant();
  }

  /**
   * Returns a list of Components.
   **/
  public List<Component> getChildren() {
    return this.children;
  }

  /**
   * Returns the Duration of the project.
   **/
  @Override
  public Duration getDuration() {
    invariant();
    Duration duration = Duration.between(LocalTime.NOON, LocalTime.NOON);
    for (Component child : this.children) {
      duration = duration.plus(child.getDuration());
    }
    assert (duration.isPositive());
    invariant();
    return duration;
  }

  /**
   * Checks if children attribute is null.
   **/
  private boolean invariant() {
    assert (this.children != null);
    return true;
  }

}
