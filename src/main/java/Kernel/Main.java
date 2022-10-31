package Kernel;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        ProjectComposite root = new ProjectComposite();

        ProjectComposite scheduler = new ProjectComposite(root, "Scheduler", "Do a job Scheduler");
        ProjectComposite mapper = new ProjectComposite(root, "Mapper", "Do a model Mapper");

        root.addComponent(scheduler);
        root.addComponent(mapper);

        Task jobQueue = new Task(scheduler,"Job Queue", "Add a job queue");
        Task schedulingAlgorithm = new Task(scheduler,"Scheduling Algorithm", "Implement a scheduling algorithm");
        Task errorHandler = new Task(scheduler,"Error Handler", "Implement an error handler");

        System.out.println(root.toJson().toString(2));
        ProjectComponent.saveJson(root.toJson());
        ProjectComponent.saveJsonPrettier(root.toJson());
    }
}