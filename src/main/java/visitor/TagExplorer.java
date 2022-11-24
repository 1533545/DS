package visitor;

import java.util.ArrayList;
import java.util.List;
import kernel.Component;
import kernel.Project;
import kernel.Task;

/**
 * Given the root node of a Component tree, search in all children nodes for the targetTag
 * attribute and sets the found component in componentsFound attribute.
 **/
public class TagExplorer implements Visitor {

  private String targetTag;
  private List<Component> componentsFound;

  public TagExplorer(String targetTag) {
    this.targetTag = targetTag;
    this.componentsFound = new ArrayList<>();
  }

  public void setTargetTag(String tag) {
    this.targetTag = tag;
  }

  public String getTargetTag() {
    return this.targetTag;
  }

  public List<Component> getResult() {
    return this.componentsFound;
  }

  /**
   * REQUIRED TO DO A NEW SEARCH.
   * Initialize targetTag attribute as an empty ArrayList for a new search.
   **/
  public void cleanTargetTag() {
    this.componentsFound = new ArrayList<>();
  }

  /**
   * Searches for coincides of targetTag in each Component of the Tree.
   * If a coincidence is found, adds the Component to componentsFound attribute.
   **/
  public void search(Project project) {

    List<String> tagList = project.getTags();

    if (tagList != null && !tagList.isEmpty()) {
      if (tagList.contains(targetTag)) {
        this.componentsFound.add(project);
      }
    } else {
      for (Component component : project.getChildren()) {
        component.acceptVisitor(this);
      }
    }
  }

  /**
   * Searches for coincides of targetTag in each component of a project.
   **/
  @Override
  public void visitProject(Project project) {
    this.search(project);
  }

  /**
   * Searches for coincides of targetTag in Task tag list. If a coincidence is
   * found, adds the Task to componentsFound attribute.
   **/
  @Override
  public void visitTask(Task task) {
    List<String> tagList = task.getTags();
    if (tagList != null && !tagList.isEmpty()) {
      if (tagList.contains(this.targetTag)) {
        this.componentsFound.add(task);
      }
    }
  }
}
