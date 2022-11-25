package kernel;

import json.JsonReader;
import json.JsonWriter;
import org.json.JSONObject;
import visitor.NameExplorer;
import visitor.Printer;
import visitor.TagExplorer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Main entrypoint of execution of the Kernel.
 **/
public class Main {
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
    AppendixJson(root, printer);
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
        "Nothing", Arrays.asList("Dart"));
    Task firstMilestone = new Task(timeTracker, "first milestone",
        "Nothing", Arrays.asList("Java", "IntelliJ"));

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

    /*NameExplorer explorerName = new NameExplorer(firstList.getName());
    System.out.println("---------------------------------------------------");
    System.out.println("Search task Name");
    explorerName.search(this.root);
    Component taskName = explorerName.getResult();
    if (taskName != null) {
      System.out.println(taskName.name);
    } else {
      System.out.println("Not Found");
    }

    printer.print(taskName);
    System.out.println("---------------------------------------------------");
    System.out.println("Search C++");
    TagExplorer tagExplorer = new TagExplorer((String) softwareTesting.getTags().get(0));
    tagExplorer.search(this.root);
    List<Component> cppTag = tagExplorer.getResult();
    if (cppTag != null && !cppTag.isEmpty()) {
      cppTag.stream().forEach((component) -> {
        printer.print(component);
      });
    } else {
      System.out.println("Not Found");
    }

    System.out.println("---------------------------------------------------");
    System.out.println("Search Project Name");
    explorerName.setTargetName("time tracker");
    explorerName.search(this.root);
    Component projectName = explorerName.getResult();
    if (projectName != null) {
      System.out.println(projectName.name);
    } else {
      System.out.println("Not Found");
    }

    printer.print(projectName);
    System.out.println("---------------------------------------------------");
    System.out.println("Search Python");
    tagExplorer.setTargetTag((String) softwareTesting.getTags().get(2));
    tagExplorer.cleanTargetTag();
    tagExplorer.search(this.root);
    List<Component> pythonTag = tagExplorer.getResult();
    if (pythonTag != null && !pythonTag.isEmpty()) {
      pythonTag.stream().forEach((component) -> {
        printer.print(component);
      });
    } else {
      System.out.println("Not Found");
    }*/
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
  public static void AppendixJson(Project root, Printer printer) {
    // Save object in file as json.
    JsonWriter.saveJsonPrettier(root.toJson());
    JsonWriter.saveJson(root.toJson());

    // Read json from file.
    JSONObject rootJson = JsonReader.readJson("json.txt");

    try {
      root = new Project(rootJson);
    }
    catch (Exception exception) {
      System.out.println(exception);
    }

    // Print initialized object from json.
    printer.print(root);
  }
}
