package visitor;

import kernel.Component;
import kernel.Project;
import kernel.Task;

import java.util.ArrayList;
import java.util.List;

/*
    Given the root node of a tree, search in all children nodes for the target Id
    and sets the found component in resultID attribute.
*/
public class TagExplorer implements Visitor {

    private String targetTag;
    private List<Component> resultTag;

    public TagExplorer(String targetTag) {
        this.targetTag = targetTag;
        this.resultTag = new ArrayList<>();
    }

    public void setTargetTag(String tag) {
        this.targetTag = tag;
    }

    public String getTargetTag() {
        return this.targetTag;
    }

    public void cleanTargetTag() {
        this.resultTag = new ArrayList<>();
    }

    public List<Component> getResult() {
        return this.resultTag;
    }

    public void search(Project project) {

        List<String> tagList = project.getTags();

        if(tagList != null && !tagList.isEmpty()) {
            if(tagList.contains(targetTag)) {
                this.resultTag.add(project);
            }
        }
        else {
            for(Component component : project.getChildren()) {
                component.acceptVisitor(this);
            }
        }
    }

    @Override
    public void visitProject(Project project) {
        this.search(project);
    }

    @Override
    public void visitTask(Task task) {
        List<String> tagList = task.getTags();
        if(tagList != null && !tagList.isEmpty()) {
            if(tagList.contains(this.targetTag)) {
                this.resultTag.add(task);
            }
        }
    }
}
