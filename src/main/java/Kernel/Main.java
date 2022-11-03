package Kernel;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) throws Exception {
        //TODO: Add json interval



    }
    private static void testCountingTime() throws InterruptedException { //test ApendixB
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Thread.sleep(1500);
        ProjectComposite p=new ProjectComposite();
        Task transportation= new Task(p,"transportation","transport");
        transportation.startTask();
        Thread.sleep(6000);
        transportation.pauseTask();

        Thread.sleep(2000);

        Task first_list=new Task(p,"fisrt list","list one");
        first_list.startTask();
        Thread.sleep(6000);
        Task second_list=new Task(p,"second list","list two");
        second_list.startTask();
        Thread.sleep(4000);

        first_list.finishTask();
        Thread.sleep(2000);
        second_list.finishTask();
        Thread.sleep(2000);

        Thread.sleep(2000);

        transportation.startTask();
        Thread.sleep(4000);
        transportation.finishTask();
    }
    private static void Sampletree() throws Exception {
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
        JSONObject jsonReadFromFile = ProjectComponent.readJson("json.txt");
        ProjectComposite projectReadFromJson = new ProjectComposite(jsonReadFromFile);
    }
}