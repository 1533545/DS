package Kernel;

import netscape.javascript.JSObject;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectComposite extends ProjectComponent{
    private List<ProjectComponent> Children;

    public ProjectComposite() {
        super();
        Children = new ArrayList<>();
    }

    public ProjectComposite(ProjectComponent fatherNode, String name, String description) {
        super(fatherNode, name, description);
        Children = new ArrayList<>();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = toJsonComponent(new JSONObject());
        JSONArray jsonArray = new JSONArray();

        for (ProjectComponent child : Children){
            jsonArray.put(child.toJson());
        }
        jsonObject.put("Children",jsonArray);

        return jsonObject;
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
    }
}
