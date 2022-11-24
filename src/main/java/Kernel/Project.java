package kernel;


import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import visitor.Visitor;

/**
 * Extension of Component. Container of Components, in other words,
 * contains multiple Projects or/and Tasks. Represents a project node of the components tree.
 **/
public class Project extends Component {
  private List<Component> children;

  /**
   * comment.
   **/
  public Project(Component fatherNode, String name, String description) {
    super(fatherNode, name, description);
    if ((name.replaceAll("\\s", "")).matches("^[a-zA-Z0-9]*$") && name != null) {
      this.children = new ArrayList<>();
    } else {
      throw new IllegalArgumentException("Illegal project name:" + name);
    }


    invariant();
  }

  /**
   * comment.
   */
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

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitProject(this);
  }

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

  private void generateTaskFromJson(JSONObject childJson) throws Exception {
    Task task = new Task(childJson);
    task.fatherNode = this;
    this.children.add(task);
  }

  private void generateProjectFromJson(JSONObject childJson) throws Exception {
    Project task = new Project(childJson);
    task.fatherNode = this;
    this.children.add(task);
  }

  @Override
  public JSONObject toJson() {
    JSONObject jsonObject = toJsonComponent(new JSONObject());
    jsonObject.put("Class", "Project");
    jsonObject.put("Children", childrenToJson());
    assert (jsonObject != null);
    return jsonObject;
  }

  private JSONArray childrenToJson() {
    JSONArray jsonArray = new JSONArray();
    for (Component child : children) {
      jsonArray.put(child.toJson());
    }
    return jsonArray;
  }

  /**
   *comment.
   */
  public void addComponent(Component projectComponent) {
    invariant();
    if (projectComponent != null) {
      this.children.add(projectComponent);
      projectComponent.fatherNode = this;
    }
    invariant();
  }

  public List<Component> getChildren() {
    return this.children;
  }

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
   * comment.
   */
  public void print(int indentation) {
    String customIndentation = generateCustomIndentation(indentation);
    System.out.println(customIndentation + ">" + "PROJECT: " + this.name
        + " - Start: " +  this.getStartTime() + " - Finish: "
        + this.getFinishTime() + " - Duration: " + this.getDuration());
    for (Component child : this.children) {
      child.print(indentation + 2);
    }
  }

  private boolean invariant() {
    assert (this.children != null);
    return true;
  }
}
