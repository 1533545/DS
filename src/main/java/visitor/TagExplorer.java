package visitor;

import java.util.ArrayList;
import java.util.List;
import kernel.Component;
import kernel.Project;
import kernel.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Given the root node of a Component tree, search in all children nodes for the targetTag
 * attribute and sets the found component in componentsFound attribute.
 **/
public class TagExplorer implements Visitor {
  private static Logger logger = LoggerFactory.getLogger(TagExplorer.class);

  /**
   * Tag to be found.
   **/
  private String targetTag;

  /**
   * List of Components containing targetTag in their Tag list.
   **/
  private List<Component> componentsFound;

  /**
   * Default constructor.
   **/
  public TagExplorer() {
    /* Void */
  }

  /**
   * Parameter contructor.
   **/
  public TagExplorer(String targetTag) {
    this.targetTag = targetTag.toLowerCase();
    this.componentsFound = new ArrayList<>();
  }

  /**
   * Sets targetTag attribute a new value.
   **/
  public void setTargetTag(String tag) {
    this.targetTag = tag.toLowerCase();
  }

  /**
   * Returns the value of targetTag attribute.
   **/
  public String getTargetTag() {
    return this.targetTag;
  }

  /**
   * Returns the list of components matching targetTag.
   **/
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
        logger.debug("Project "+ project.getTags()+" was found");
        this.componentsFound.add(project);
      }
    }
    for (Component component : project.getChildren()) {
      component.acceptVisitor(this);
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
        logger.debug("Task "+ task.getTags()+" was found");
        this.componentsFound.add(task);
      }
    }
  }
}
