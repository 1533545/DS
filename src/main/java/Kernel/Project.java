package Kernel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Extension of Component. Container of Components, in other words,
 * contains multiple Projects or/and Tasks. Represents a project node of the components tree.
 */
public class Project extends Component {
    private List<Component> _children;

    public Project(Component fatherNode, String name, String description) {
        super(fatherNode, name, description);
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

    public boolean RemoveComponent(String id) {
        Component componentById = getComponentById(id);
        if(componentById == null)
        {
            return false;
        }
        this._children.remove(componentById);
        return true;
    }

    private Component getComponentById(String id) {
        for (Component projectComponent : this._children)
        {
            if (Objects.equals(projectComponent.id, id))
            {
                return projectComponent;
            }
        }
        return null;
    }

    public void addComponent(Component projectComponent) {
        this._children.add(projectComponent);
        projectComponent.fatherNode = this;
    }
    public List<Component> getChildren(){
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

    public void print(int indentation) {
        String customIndentation = generateCustomIndentation(indentation);
        System.out.println(customIndentation + ">" + "PROJECT: " + this.name + " - Start: " +
                this.getStartTime() + " - Finish: " + this.getFinishTime() + " - Duration: " + this.getDuration());
        for (Component child : this._children) {
            child.print(indentation + 2);
        }
    }

}
