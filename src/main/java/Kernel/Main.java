package Kernel;

import org.json.JSONObject;

public class Main {
    public static void main(String[] args) throws Exception {
        appendixA();
        appendixB();
        JSONObject rootJson = JsonReader.readJson("json.txt");
        Project rootRead = new Project(rootJson);
        System.out.println("\n");
        System.out.println("Print tree read from json:");
        rootRead.print(0);
    }
    private static void appendixB() throws Exception {
        Project root = new Project(null,"root","father");
        Project Software_Design = new Project(root, "software design","java,flutter");
        Project Software_Testing = new Project(root, "software testing","c++,Java,python");
        Project databases = new Project(root, "databases","SQL,python,C++");
        //We create the tree of appendixA
        Task transportation = new Task(root, "transportation","");
        root.addComponent(Software_Design);
        root.addComponent(Software_Testing);
        root.addComponent(databases);
        root.addComponent(transportation);
        Project problems = new Project(Software_Design, "problems","");
        Project time_tracker = new Project(Software_Design, "time tracker","");
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
        System.out.println("Print tree ready to convert to json:");
        root.print(0);
    }
    private static void appendixA() throws Exception {
        Project root = new Project(null,"root","father");
        Project Software_Design = new Project(root, "software design","java,flutter");
        Project Software_Testing = new Project(root, "software testing","c++,Java,python");
        Project databases = new Project(root, "databases","SQL,python,C++");
        Task transportation = new Task(root, "transportation","");
        root.addComponent(Software_Design);
        root.addComponent(Software_Testing);
        root.addComponent(databases);
        root.addComponent(transportation);
        Project problems = new Project(Software_Design, "problems","");
        Project time_tracker = new Project(Software_Design, "time tracker","");
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
