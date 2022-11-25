package visitor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kernel.Component;
import kernel.Project;
import kernel.Task;

/**
 * Entrypoint for the execution of the Visitor tests.
 **/
public class Explorer {
  /**
   * Executes Appendix tests.
   **/
  public static void main(String[] args) throws Exception {
    // Initialize required objects.
    final Project root = generateComponentTreeTest();
    final NameExplorer nameExplorer = new NameExplorer();
    final TagExplorer tagExplorer = new TagExplorer();
    final Printer printer = new Printer();

    // Appendix Visitor test.
    System.out.println("\n");
    System.out.println("Appendix Visitor:");
    System.out.println("--------------------------------------"
        + "---------------------------------------------");
    appendixVisitor(root, tagExplorer);
  }

  /**
   * Generates a Component tree for testing.
   **/
  private static Project generateComponentTreeTest() {
    // Create Root
    Project root = new Project((Component) null, "root",
        "father", (List) null);

    // Root Children
    Project softwareDesign = new Project(root, "software design",
        "java,flutter", Arrays.asList("java", "flutter"));
    Project softwareTesting = new Project(root, "software testing",
        "c++,Java,python", Arrays.asList("c++", "Java", "python"));
    Project databases = new Project(root, "databases",
        "SQL,python,C++", Arrays.asList("SQL", "python", "C++"));
    Task transportation = new Task(root, "transportation",
        "Nothing", (List) null);

    // Add children to root
    root.addComponent(softwareDesign);
    root.addComponent(softwareTesting);
    root.addComponent(databases);
    root.addComponent(transportation);

    // softwareDesign children
    Project problems = new Project(softwareDesign, "problems",
        "Nothing", new ArrayList());
    Project timeTracker = new Project(softwareDesign, "time tracker",
        "Nothing", (List) null);

    // Add children to softwareDesign
    softwareDesign.addComponent(problems);
    softwareDesign.addComponent(timeTracker);

    // problems children
    Task firstList = new Task(problems, "first list",
        "java", Arrays.asList("java"));
    Task secondList = new Task(problems, "second list",
        "Dart", Arrays.asList("Dart"));

    // Add children to problems
    problems.addComponent(firstList);
    problems.addComponent(secondList);

    // Add children to timeTracker
    Task readHandout = new Task(timeTracker, "read handout",
        "Dart", Arrays.asList("Dart"));
    Task firstMilestone = new Task(timeTracker, "first milestone",
        "Java,IntelliJ", Arrays.asList("Java", "IntelliJ"));

    // Add children to timeTracker
    timeTracker.addComponent(readHandout);
    timeTracker.addComponent(firstMilestone);

    return root;
  }

  /**
   * Test search by tags.
   **/
  public static void appendixVisitor(Project root, TagExplorer tagExplorer) {
    String[] tagsToSearch = new String[] {
        "java", "Java", "IntelliJ", "c++", "python"
    };

    for (String tag : tagsToSearch) {
      List<Component> foundComponents = searchByTagList(root, tagExplorer, tag);
      if (foundComponents != null && !foundComponents.isEmpty()) {
        System.out.println("-" + tag + " found in: "
            + generateCustomComponentsNameLog(foundComponents));
      } else {
        System.out.println("-" + tag + " not found :(");
      }
    }
  }

  /**
   * Given a Component tree search for a specific tag.
   **/
  private static List<Component> searchByTagList(Project root,
                                                 TagExplorer tagExplorer, String tag) {
    tagExplorer.cleanTargetTag();
    tagExplorer.setTargetTag(tag);
    tagExplorer.search(root);
    return tagExplorer.getResult();
  }

  /**
   * Given a Component list search generate a custom string of concatenated Components names.
   **/
  private static String generateCustomComponentsNameLog(List<Component> components) {
    String customLog = "";
    for (Component component : components) {
      customLog += (component.getName() + ", ");
    }
    return customLog;
  }
}
