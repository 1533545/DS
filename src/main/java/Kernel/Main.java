package kernel;


import json.JsonReader;
import json.JsonWriter;
import org.json.JSONObject;
import visitor.IdExplorer;
import visitor.NameExplorer;


/**
 * comment.
 */
public class Main {
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
    rootRead.print(0);
  }

  private static void appendixB() throws Exception {
    Project root = new Project(null, "root", "father");
    Project softwareDesign = new Project(root, "software design", "java,flutter");
    Project softwareTesting = new Project(root, "software testing", "c++,Java,python");
    Project databases = new Project(root, "databases", "SQL,python,C++");
    //We create the tree of appendixA
    Task transportation = new Task(root, "transportation", "");
    root.addComponent(softwareDesign);
    root.addComponent(softwareTesting);
    root.addComponent(databases);
    root.addComponent(transportation);
    Project problems = new Project(softwareDesign, "problems", "");
    Project timeTracker = new Project(softwareDesign, "time tracker", "");
    softwareDesign.addComponent(problems);
    softwareDesign.addComponent(timeTracker);
    Task firstList = new Task(problems, "first list", "java");
    Task secondList = new Task(problems, "second list", "Dart");
    problems.addComponent(firstList);
    problems.addComponent(secondList);
    Task readHandout = new Task(timeTracker, " read handout", "task4");
    Task firstMilestone = new Task(timeTracker, "first milestone", "Java,IntelliJ");
    timeTracker.addComponent(readHandout);
    timeTracker.addComponent(firstMilestone);
    //Start of appendixB

    Clock clock = Clock.getInstance();
    Thread.sleep(1500);
    clock.startClock();

    transportation.startTask();
    Thread.sleep(6005);
    transportation.finishTask();

    Thread.sleep(2005);

    firstList.startTask();

    Thread.sleep(6005);

    secondList.startTask();

    Thread.sleep(4005);

    firstList.finishTask();

    Thread.sleep(2005);

    secondList.finishTask();

    Thread.sleep(2005);
    transportation.startTask();
    Thread.sleep(4005);
    transportation.finishTask();

    clock.stopClock();
    System.out.println("Print tree ready to convert to json:");
    root.print(0);
  }

  private static void appendixA() throws Exception {
    Project root = new Project(null, "root", "father");
    Project softwareDesign = new Project(root, "software design", "java,flutter");
    Project softwareTesting = new Project(root, "software testing", "c++,Java,python");
    Project databases = new Project(root, "databases", "SQL,python,C++");
    Task transportation = new Task(root, "transportation", "");
    root.addComponent(softwareDesign);
    root.addComponent(softwareTesting);
    root.addComponent(databases);
    root.addComponent(transportation);
    Project problems = new Project(softwareDesign, "problems", "");
    Project timeTracker = new Project(softwareDesign, "time tracker", "");
    softwareDesign.addComponent(problems);
    softwareDesign.addComponent(timeTracker);
    Task firstList = new Task(problems, "first list", "java");
    Task secondList = new Task(problems, "second list", "Dart");
    problems.addComponent(firstList);
    problems.addComponent(secondList);
    Task readHandout = new Task(timeTracker, "read handout", "task4");
    Task firstMilestone = new Task(timeTracker, "first milestone", "Java,IntelliJ");
    timeTracker.addComponent(readHandout);
    timeTracker.addComponent(firstMilestone);

    JsonWriter.saveJsonPrettier(root.toJson());
    JsonWriter.saveJson(root.toJson());
    root.print(0);

    NameExplorer explorerName = new NameExplorer(firstList.getName());
    explorerName.search(root);
    Component resultName = explorerName.getResult();
    if (resultName != null) {
      resultName.print(0);
      System.out.println(resultName.name);
    }


    IdExplorer explorerId = new IdExplorer(firstList.getId());
    explorerId.search(root);
    Component resultId = explorerId.getResult();
    if (resultId != null) {
      resultId.print(0);
      System.out.println(resultId.id);
    }
  }
}
