package Kernel;

import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws Exception {
//        appendixA();
        appendixB();
    }
    private static void appendixB() throws InterruptedException {
            Clock c=Clock.getInstance();
            Thread.sleep(1500);
            ProjectComposite root = new ProjectComposite(null,"root","father");
            ProjectComposite sideProject = new ProjectComposite(root,"Side project","project");
            Task transportation= new Task(sideProject,"transportation","transport");
            transportation.startTask();
            Thread.sleep(6005);
            transportation.pauseTask();

            Thread.sleep(2000);

            Task first_list=new Task(root,"first list","list one");
            first_list.startTask();

            Thread.sleep(6005);

            Task second_list=new Task(root,"second list","list two");
            second_list.startTask();

            Thread.sleep(4000);

            first_list.finishTask();

            Thread.sleep(2000);

            second_list.finishTask();

            Thread.sleep(2000);
            transportation.startTask();
            Thread.sleep(4005);
            transportation.finishTask();
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
        Task read_handout = new Task(time_tracker, " read handout","task4");
        Task first_milestone = new Task(time_tracker, "first milestone","Java,IntelliJ");
        time_tracker.addComponent(read_handout);
        time_tracker.addComponent(first_milestone);

        System.out.println(root.toString());
    }
}