package Kernel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProjectComposite extends ProjectComponent{
    private List<ProjectComponent> Children;

    public ProjectComposite() {
        Children = new ArrayList<>();
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
