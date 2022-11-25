package kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import json.JsonReader;
import json.JsonWriter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.NameExplorer;
import visitor.Printer;
import visitor.TagExplorer;

/**
 * Entrypoint for the execution of Kernel tests.
 **/
public class Kernel {
  /**
   * Executes Appendix tests.
   **/
  public static void main(String[] args) throws Exception {
    // Initialize required objects.
    final Project root = generateComponentTreeTest();
    final NameExplorer nameExplorer = new NameExplorer();
    final TagExplorer tagExplorer = new TagExplorer();
    final Printer printer = new Printer();

    // Appendix A test.
    System.out.println("\n");
    System.out.println("Appendix A:");
    System.out.println("--------------------------------------"
        + "---------------------------------------------");
    appendixA(root, printer);

    // Appendix B test.
    System.out.println("\n");
    System.out.println("Appendix B:");
    System.out.println("--------------------------------------"
        + "---------------------------------------------");
    appendixB(root, nameExplorer);

    // Appendix JSON test.
    System.out.println("\n");
    System.out.println("Appendix JSON:");
    System.out.println("--------------------------------------"
        + "---------------------------------------------");
    appendixJson(root, printer);
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
   * Executes Appendix A tests.
   **/
  private static void appendixA(Project root, Printer printer) {
    printer.print(root);
  }

  /**
   * Executes Appendix B tests.
   **/
  private static void appendixB(Project root, NameExplorer nameExplorer) throws Exception {
    Clock clock = Clock.getInstance();

    Thread.sleep(1500L);
    clock.startClock();

    // Search transportation Component by name
    nameExplorer.setTargetName("transportation");
    nameExplorer.search(root);
    final Task transportation = (Task) nameExplorer.getResult();

    // Search firstList Component by name
    nameExplorer.setTargetName("first list");
    nameExplorer.search(root);
    final Task firstList = (Task) nameExplorer.getResult();

    // Search secondList Component by name
    nameExplorer.setTargetName("second list");
    nameExplorer.search(root);
    final Task secondList = (Task) nameExplorer.getResult();

    // Start transportation Task
    transportation.startTask();
    Thread.sleep(6005L);
    // Finish transportation Task
    transportation.finishTask();
    Thread.sleep(2005L);

    // Test working on 2 task at the sametime.
    firstList.startTask();
    Thread.sleep(6005L);
    secondList.startTask();
    Thread.sleep(4005L);
    firstList.finishTask();
    Thread.sleep(2005L);
    secondList.finishTask();

    // Test start working again on the same task.
    Thread.sleep(2005L);
    transportation.startTask();
    Thread.sleep(4005L);
    transportation.finishTask();

    clock.stopClock();
  }

  /**
   * Test write and read json objects.
   **/
  public static void appendixJson(Project root, Printer printer) {
    // Save object in file as json.
    JsonWriter.saveJsonPrettier(root.toJson());
    JsonWriter.saveJson(root.toJson());

    // Read json from file.
    JSONObject rootJson = JsonReader.readJson("json.txt");

    // Initialize Project from JSONObject.
    Project projectLoadFromJson = new Project(rootJson);

    // Print initialized Project object from json.
    printer.print(projectLoadFromJson);
  }
}
