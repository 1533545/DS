//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package kernel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import json.JsonReader;
import json.JsonWriter;
import org.json.JSONObject;
import visitor.NameExplorer;
import visitor.Printer;
import visitor.TagExplorer;

/**
 * comment.
 */
public class Main {
  public Main() {
  }

  /**
   * comment.
   */
  public static void main(String[] args) throws Exception {
    System.out.println("Appendix A");
    appendixA();
    System.out.println("Appendix B");
    appendixB();
    JSONObject rootJson = JsonReader.readJson("json.txt");
    Project rootRead = new Project(rootJson);
    System.out.println("\n");
    System.out.println("Print tree read from json:");
    Printer printer = new Printer(0);
    printer.print(rootRead);
  }

  private static void appendixB() throws Exception {
    Project root = new Project((Component) null, "root", "father", (List) null);
    Project softwareDesign = new Project(root, "software design",
        "java,flutter", Arrays.asList("java", "flutter"));
    Project softwareTesting = new Project(root, "software testing",
        "c++,Java,python", Arrays.asList("c++", "Java", "python"));
    Project databases = new Project(root, "databases",
        "SQL,python,C++", Arrays.asList("SQL", "python", "C++"));
    Task transportation = new Task(root, "transportation", "Nothing", (List) null);
    root.addComponent(softwareDesign);
    root.addComponent(softwareTesting);
    root.addComponent(databases);
    root.addComponent(transportation);
    Project problems = new Project(softwareDesign, "problems", "Nothing", (List) null);
    Project timeTracker = new Project(softwareDesign, "time tracker", "Nothing", (List) null);
    softwareDesign.addComponent(problems);
    softwareDesign.addComponent(timeTracker);
    Task firstList = new Task(problems, "first list", "java", Arrays.asList("java"));
    Task secondList = new Task(problems, "second list", "Dart", Arrays.asList("Dart"));
    problems.addComponent(firstList);
    problems.addComponent(secondList);
    Task readHandout = new Task(timeTracker, "read handout", "Nothing", Arrays.asList("Dart"));
    Task firstMilestone = new Task(timeTracker, "first milestone",
        "Nothing", Arrays.asList("Java", "IntelliJ"));
    timeTracker.addComponent(readHandout);
    timeTracker.addComponent(firstMilestone);
    Clock clock = Clock.getInstance();
    Thread.sleep(1500L);
    clock.startClock();
    transportation.startTask();
    Thread.sleep(6005L);
    transportation.finishTask();
    Thread.sleep(2005L);
    firstList.startTask();
    Thread.sleep(6005L);
    secondList.startTask();
    Thread.sleep(4005L);
    firstList.finishTask();
    Thread.sleep(2005L);
    secondList.finishTask();
    Thread.sleep(2005L);
    transportation.startTask();
    Thread.sleep(4005L);
    transportation.finishTask();
    clock.stopClock();
    System.out.println("Print tree ready to convert to json:");
    JsonWriter.saveJsonPrettier(root.toJson());
    JsonWriter.saveJson(root.toJson());
    Printer printer = new Printer(0);
    printer.print(root);
  }

  private static void appendixA() throws Exception {
    Project root = new Project((Component) null, "root", "father", (List) null);
    Project softwareDesign = new Project(root, "software design",
        "java,flutter", Arrays.asList("java", "flutter"));
    Project softwareTesting = new Project(root, "software testing",
        "c++,Java,python", Arrays.asList("c++", "Java", "python"));
    Project databases = new Project(root, "databases",
        "SQL,python,C++", Arrays.asList("SQL", "python", "C++"));
    Task transportation = new Task(root, "transportation", "Nothing", (List) null);
    root.addComponent(softwareDesign);
    root.addComponent(softwareTesting);
    root.addComponent(databases);
    root.addComponent(transportation);
    Project problems = new Project(softwareDesign, "problems", "Nothing", new ArrayList());
    Project timeTracker = new Project(softwareDesign, "time tracker", "Nothing", (List) null);
    softwareDesign.addComponent(problems);
    softwareDesign.addComponent(timeTracker);
    Task firstList = new Task(problems, "first list", "java", Arrays.asList("java"));
    Task secondList = new Task(problems, "second list", "Dart", Arrays.asList("Dart"));
    problems.addComponent(firstList);
    problems.addComponent(secondList);
    Task readHandout = new Task(timeTracker, "read handout", "Nothing", Arrays.asList("Dart"));
    Task firstMilestone = new Task(timeTracker, "first milestone",
        "Nothing", Arrays.asList("Java", "IntelliJ"));
    timeTracker.addComponent(readHandout);
    timeTracker.addComponent(firstMilestone);
    Printer printer = new Printer(0);
    JsonWriter.saveJsonPrettier(root.toJson());
    JsonWriter.saveJson(root.toJson());
    printer.print(root);

    NameExplorer explorerName = new NameExplorer(firstList.getName());
    System.out.println("---------------------------------------------------");
    System.out.println("Search task Name");
    explorerName.search(root);
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
    tagExplorer.search(root);
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
    explorerName.search(root);
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
    tagExplorer.search(root);
    List<Component> pythonTag = tagExplorer.getResult();
    if (pythonTag != null && !pythonTag.isEmpty()) {
      pythonTag.stream().forEach((component) -> {
        printer.print(component);
      });
    } else {
      System.out.println("Not Found");
    }

  }
}
