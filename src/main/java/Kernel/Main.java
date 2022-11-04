//Class Main, a class made to test the functionality of the code

package Kernel;

import org.json.JSONObject;


public class Main {
    public static void main(String[] args) throws Exception {
        appendixA();
        appendixB();
        JSONObject rootJson = JsonReader.readJson("json.txt");
        ProjectComposite rootRead = new ProjectComposite(rootJson);
        System.out.println("\n");
        rootRead.print(0);
    }
    private static void appendixB() throws Exception {
        //We create the tree of appendixA
        ProjectComposite root = new ProjectComposite(null,"root","father");
        ProjectComposite Software_Design = new ProjectComposite(root, "software design","java,flutter");
        ProjectComposite Software_Testing = new ProjectComposite(root, "software testing","c++,Java,python");
        ProjectComposite databases = new ProjectComposite(root, "databases","SQL,python,C++");
        Task transportation = new Task(root, "transportation","");
        root.addComponent(Software_Design);
        root.addComponent(Software_Testing);
        root.addComponent(databases);
        root.addComponent(transportation);
        ProjectComposite problems = new ProjectComposite(Software_Design, "problems","");
        ProjectComposite time_tracker = new ProjectComposite(Software_Design, "time tracker","");
        Software_Design.addComponent(problems);
        Software_Design.addComponent(time_tracker);
        Task first_list = new Task(problems, "first list","java");
        Task second_list = new Task(problems, "second list","Dart");
        problems.addComponent(first_list);
        problems.addComponent(second_list);
        Task read_handout = new Task(time_tracker, " read handout","task4");
        Task first_milestone = new Task(time_tracker, "first milestone","Java,IntelliJ");
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
        root.print(0);
        JSONObject rootJson = root.toJson();
        JsonWriter.saveJson(rootJson);
        JsonWriter.saveJsonPrettier(rootJson);
    }
    private static void appendixA() throws Exception {
        ProjectComposite root = new ProjectComposite(null,"root","father");
        ProjectComposite Software_Design = new ProjectComposite(root, "software design","java,flutter");
        ProjectComposite Software_Testing = new ProjectComposite(root, "software testing","c++,Java,python");
        ProjectComposite databases = new ProjectComposite(root, "databases","SQL,python,C++");
        Task transportation = new Task(root, "transportation","");
        root.addComponent(Software_Design);
        root.addComponent(Software_Testing);
        root.addComponent(databases);
        root.addComponent(transportation);
        ProjectComposite problems = new ProjectComposite(Software_Design, "problems","");
        ProjectComposite time_tracker = new ProjectComposite(Software_Design, "time tracker","");
        Software_Design.addComponent(problems);
        Software_Design.addComponent(time_tracker);
        Task first_list = new Task(problems, "first list","java");
        Task second_list = new Task(problems, "second list","Dart");
        problems.addComponent(first_list);
        problems.addComponent(second_list);
        Task read_handout = new Task(time_tracker, "read handout","task4");
        Task first_milestone = new Task(time_tracker, "first milestone","Java,IntelliJ");
        time_tracker.addComponent(read_handout);
        time_tracker.addComponent(first_milestone);

        root.print(0);
    }
}
