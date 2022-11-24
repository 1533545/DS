package kernel;

import json.JsonReader;
import json.JsonWriter;
import org.json.JSONObject;
import visitor.TagExplorer;
import visitor.NameExplorer;
import visitor.Printer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
  public static void main(String[] args) throws Exception {
/*    System.out.println("Appendix A");
    appendixA();
    System.out.println("Appendix B");
    appendixB();*/
    JSONObject rootJson = JsonReader.readJson("json.txt");
    Project rootRead = new Project(rootJson);
    System.out.println("\n");
    System.out.println("Print tree read from json:");

    Printer printer = new Printer(0);
    printer.print(rootRead);

  }

  private static void appendixB() throws Exception {
    Project root = new Project(null, "root", "father", null);
    Project Software_Design = new Project(root, "software design", "java,flutter", Arrays.asList("java", "flutter"));
    Project Software_Testing = new Project(root, "software testing", "c++,Java,python", Arrays.asList("c++", "Java", "python"));
    Project databases = new Project(root, "databases", "SQL,python,C++", Arrays.asList("SQL", "python", "C++"));
    Task transportation = new Task(root, "transportation", "Nothing", null);
    root.addComponent(Software_Design);
    root.addComponent(Software_Testing);
    root.addComponent(databases);
    root.addComponent(transportation);
    Project problems = new Project(Software_Design, "problems", "Nothing", null);
    Project time_tracker = new Project(Software_Design, "time tracker", "Nothing", null);
    Software_Design.addComponent(problems);
    Software_Design.addComponent(time_tracker);
    Task first_list = new Task(problems, "first list", "java", Arrays.asList("java"));
    Task second_list = new Task(problems, "second list", "Dart", Arrays.asList("Dart"));
    problems.addComponent(first_list);
    problems.addComponent(second_list);
    Task read_handout = new Task(time_tracker, "read handout", "Nothing", Arrays.asList("Dart"));
    Task first_milestone = new Task(time_tracker, "first milestone", "Nothing", Arrays.asList("Java", "IntelliJ"));
    time_tracker.addComponent(read_handout);
    time_tracker.addComponent(first_milestone);
    //Start of appendixB

    Clock clock = Clock.getInstance();
    Thread.sleep(1500);
    clock.startClock();

    transportation.startTask();
    Thread.sleep(6005);
    transportation.finishTask();

    Thread.sleep(2005);

    first_list.startTask();

    Thread.sleep(6005);

    second_list.startTask();

    Thread.sleep(4005);

    first_list.finishTask();

    Thread.sleep(2005);

    second_list.finishTask();

    Thread.sleep(2005);
    transportation.startTask();
    Thread.sleep(4005);
    transportation.finishTask();

    clock.stopClock();
    System.out.println("Print tree ready to convert to json:");

    JsonWriter.saveJsonPrettier(root.toJson());
    JsonWriter.saveJson(root.toJson());

    Printer printer = new Printer(0);
    printer.print(root);

  }

  private static void appendixA() throws Exception {
    Project root = new Project(null, "root", "father", null);
    Project Software_Design = new Project(root, "software design", "java,flutter", Arrays.asList("java", "flutter"));
    Project Software_Testing = new Project(root, "software testing", "c++,Java,python", Arrays.asList("c++", "Java", "python"));
    Project databases = new Project(root, "databases", "SQL,python,C++", Arrays.asList("SQL", "python", "C++"));
    Task transportation = new Task(root, "transportation", "Nothing", null);
    root.addComponent(Software_Design);
    root.addComponent(Software_Testing);
    root.addComponent(databases);
    root.addComponent(transportation);
    Project problems = new Project(Software_Design, "problems", "Nothing", new ArrayList<>());
    Project time_tracker = new Project(Software_Design, "time tracker", "Nothing", null);
    Software_Design.addComponent(problems);
    Software_Design.addComponent(time_tracker);
    Task first_list = new Task(problems, "first list", "java", Arrays.asList("java"));
    Task second_list = new Task(problems, "second list", "Dart", Arrays.asList("Dart"));
    problems.addComponent(first_list);
    problems.addComponent(second_list);
    Task read_handout = new Task(time_tracker, "read handout", "Nothing", Arrays.asList("Dart"));
    Task first_milestone = new Task(time_tracker, "first milestone", "Nothing", Arrays.asList("Java", "IntelliJ"));
    time_tracker.addComponent(read_handout);
    time_tracker.addComponent(first_milestone);

    Printer printer = new Printer(0);
    JsonWriter.saveJsonPrettier(root.toJson());
    JsonWriter.saveJson(root.toJson());
    printer.print(root);

    NameExplorer explorerName = new NameExplorer(first_list.getName());
    TagExplorer tagExplorer = new TagExplorer(Software_Testing.getTags().get(0));

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
    tagExplorer.search(root);
    List<Component> cppTag = tagExplorer.getResult();
    if (cppTag != null && !cppTag.isEmpty()) {
      cppTag.stream().forEach(component -> {
        printer.print(component);
      });
    } else {
      System.out.println("Not Found");
    }

    System.out.println("---------------------------------------------------");
    System.out.println("Search Project Name");
    explorerName.setTargetName("time tracker");
    explorerName.search(root);
    Component ProjectName = explorerName.getResult();
    if (ProjectName != null) {
      System.out.println(ProjectName.name);
    } else {
      System.out.println("Not Found");
    }
    printer.print(ProjectName);

    System.out.println("---------------------------------------------------");
    System.out.println("Search Python");
    tagExplorer.setTargetTag(Software_Testing.getTags().get(2));
    tagExplorer.cleanTargetTag();
    tagExplorer.search(root);
    List<Component> pythonTag = tagExplorer.getResult();
    if (pythonTag != null && !pythonTag.isEmpty()) {
      pythonTag.stream().forEach(component -> {
        printer.print(component);
      });
    } else {
      System.out.println("Not Found");
    }
  }
}
