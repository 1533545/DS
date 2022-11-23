package Visitor;

import Kernel.Component;
import Kernel.Project;
import Kernel.Task;

/*
    Given the root node of a tree, search in all children nodes for the target Id
    and sets the found component in resultID attribute.
*/
public class IdExplorer implements Visitor {

    String targetId;
    Component resultId;

    public IdExplorer(String targetId) {
        this.targetId = targetId;
        this.resultId = null;
    }

    public Component getResult() {
        return this.resultId;
    }

    public void search(Project project) {

        String id = project.getID();

        if(id.equals(this.targetId)) {
            this.resultId = project;
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
        String id = task.getID();
        if(id.equals(this.targetId)) {
            this.resultId = task;
        }
    }
}
