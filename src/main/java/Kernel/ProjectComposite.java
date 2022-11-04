package Kernel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectComposite extends ProjectComponent{
    private List<ProjectComponent> _children;

    public ProjectComposite(ProjectComponent fatherNode, String name, String description) {
        super(fatherNode, name, description);
        this._children = new ArrayList<>();
    }

    public ProjectComposite(JSONObject jsonObject) throws Exception {
        super(jsonObject);
        JSONArray jsonArray  = jsonObject.getJSONArray("Children");
        this._children = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject childJson = jsonArray.getJSONObject(i);
            String objectType = childJson.get("Class").toString();

            if(objectType.equals("Task")) {
                Task task = new Task(childJson);
                task._fatherNode = this;
                this._children.add(task);
            }
            else if(objectType.equals("Project")){
                ProjectComposite project = new ProjectComposite(childJson);
                project._fatherNode = this;
                this._children.add(project);
            }
            else {
                throw new Exception("No class match");
            }
        }
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
        for (ProjectComponent child : _children){
            jsonArray.put(child.toJson());
        }
        return jsonArray;
    }

    public boolean RemoveComponent(String id) {
        ProjectComponent componentById = getComponentById(id);
        if(componentById == null)
        {
            return false;
        }
        this._children.remove(componentById);
        return true;
    }

    private ProjectComponent getComponentById(String id) {
        for (ProjectComponent projectComponent : this._children)
        {
            if (Objects.equals(projectComponent.Id, id))
            {
                return projectComponent;
            }
        }
        return null;
    }

    public void addComponent(ProjectComponent projectComponent) {
        this._children.add(projectComponent);
        projectComponent._fatherNode = this;
    }

    @Override
    public Duration getDuration() {
        Duration duration = Duration.between(LocalTime.NOON,LocalTime.NOON);
        for (ProjectComponent child : this._children) {
            duration = duration.plus(child.getDuration());
        }
        return duration;
    }

    public void print(int indentation) {
        String customIndentation = generateCustomIndentation(indentation);
        System.out.println(customIndentation + ">" + "PROJECT: " + this.Name + " - Start: " +
                this.getStartTime() + " - Finish: " + this.getFinishTime() + " - Duration: " + this.getDuration());
        for (ProjectComponent child : this._children) {
            child.print(indentation + 2);
        }
    }

}
