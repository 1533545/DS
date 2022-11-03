package Kernel;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ProjectComposite extends ProjectComponent{
    private List<ProjectComponent> Children;

    public ProjectComposite() {
        super();
        this.Children = new ArrayList<>();
    }

    public ProjectComposite(ProjectComponent fatherNode, String name, String description) {
        super(fatherNode, name, description);
        this.Children = new ArrayList<>();
    }

    public ProjectComposite(JSONObject jsonObject) throws Exception {
        super(jsonObject);
        JSONArray jsonArray  = jsonObject.getJSONArray("Children");
        this.Children = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject childJson = jsonArray.getJSONObject(i);
            String objectType = childJson.get("Class").toString();

            if(objectType.equals("Task")) {
                this.Children.add(new Task(childJson));
            }
            else if(objectType.equals("Project")){
                this.Children.add(new ProjectComposite(childJson));
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
        for (ProjectComponent child : Children){
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
        this.Children.remove(componentById);
        return true;
    }

    private ProjectComponent getComponentById(String id) {
        for (ProjectComponent projectComponent : this.Children)
        {
            if (Objects.equals(projectComponent.Id, id))
            {
                return projectComponent;
            }
        }
        return null;
    }

    public void addComponent(ProjectComponent projectComponent) {
        this.Children.add(projectComponent);
        projectComponent._fatherNode = this;
    }

    @Override
    public String toString() {
        //:TODO Check if this is correct, which i doubt it is...
            return Arrays.toString(this.Children.toArray());
    }
}
