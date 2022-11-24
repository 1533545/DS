package kernel;

import visitor.Visitor;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/*
 * Extension of Component. Container of Components, in other words,
 * contains multiple Projects or/and Tasks. Represents a project node of the components tree.
 */
public class Project extends Component {
    private List<Component> _children;

    public Project(Component fatherNode, String name, String description, List<String> tagList ) {
        super(fatherNode, name, description,tagList);
        this._children = new ArrayList<>();
    }

    public Project(JSONObject jsonObject) throws Exception {
        super(jsonObject);
        JSONArray jsonArray  = jsonObject.getJSONArray("Children");
        this._children = new ArrayList<>();
        generateChildrenFromJson(jsonArray);
    }

    @Override
    public void acceptVisitor(Visitor visitor) {
        visitor.visitProject(this);
    }

    private void generateChildrenFromJson(JSONArray jsonArray) throws Exception {
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject childJson = jsonArray.getJSONObject(i);
            String objectType = childJson.get("Class").toString();

            if(objectType.equals("Task")) {
                generateTaskFromJson(childJson);
            }
            else if(objectType.equals("Project")){
                generateProjectFromJson(childJson);
            }
            else {
                throw new Exception("No class match");
            }
        }
    }

    private void generateTaskFromJson(JSONObject childJson) {
        Task task = new Task(childJson);
        task.fatherNode = this;
        this._children.add(task);
    }

    private void generateProjectFromJson(JSONObject childJson) throws Exception {
        Project task = new Project(childJson);
        task.fatherNode = this;
        this._children.add(task);
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = toJsonComponent(new JSONObject());
        jsonObject.put("Class", "Project");
        jsonObject.put("Children", childrenToJson());
        return jsonObject;
    }

    private JSONArray childrenToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Component child : _children){
            jsonArray.put(child.toJson());
        }
        return jsonArray;
    }

    public void addComponent(Component projectComponent) {
        this._children.add(projectComponent);
        projectComponent.fatherNode = this;
    }

    public List<Component> getChildren() {
        return this._children;
    }

    @Override
    public Duration getDuration() {
        Duration duration = Duration.between(LocalTime.NOON,LocalTime.NOON);
        for (Component child : this._children) {
            duration = duration.plus(child.getDuration());
        }
        return duration;
    }

}
