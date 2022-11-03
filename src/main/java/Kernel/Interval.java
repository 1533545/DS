package Kernel;

import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observer;
import java.util.Observable;

public class Interval implements Observer{ //Implements Observer
  public Task fatherTask;
  private LocalDateTime _start;
  private LocalDateTime _end;


  public Interval(Task task) {
    this._start = LocalDateTime.now();
    this._end = LocalDateTime.now();
    this.fatherTask = task;
  }

  public Interval() {
    this._start = LocalDateTime.now();
    this._end = LocalDateTime.now();
  }

  public Interval(JSONObject jsonObject) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    this._start = LocalDateTime.parse(jsonObject.get("Start").toString(),formatter);
    this._end = LocalDateTime.parse(jsonObject.get("End").toString(),formatter);
  }

  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Start", this._start);
    jsonObject.put("End", this._end);
    return jsonObject;
  }

  public LocalDateTime getStart() {
    return this._start;
  }

  public LocalDateTime getEnd() {
    return this._end;
  }

  public Duration getDuration() {
    return Duration.between(_start,_end);
  }
  /*public void showTaskInfo()
  {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    System.out.println("Interval: Initial date: "+this._start.format(formatter)+", Final date: "
        +this._end.format(formatter)+", Duration: "+this.getDuration());

  }*/

  public void showProjectInfo()
  {
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    System.out.println("Task: Initial date: "+this._start.format(formatter)+", Final date: "
//        +this._end.format(formatter)+", Duration: "+this.getDuration());
  }

  @Override
  public void update(Observable obs,Object arg) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    this._end=(LocalDateTime) arg;
    this.fatherTask.updateTime(getDuration());

    System.out.println("Interval: Initial date: "+ this._start.format(formatter)+", Final date: "
        +this._end.format(formatter)+", Duration: "+ this.getDuration());
    System.out.println("Task: "+ this.fatherTask.Name + " Initial date: "+this._start.format(formatter)+", Final date: "
        +this._end.format(formatter)+", Duration: "+ this.fatherTask.CompletedWork.plus(this.getDuration()));

    ProjectComponent projectNode = this.fatherTask._fatherNode;
    while(projectNode != null) {
      System.out.println("Project: " + this.fatherTask._fatherNode.Name + " Initial date: "+this._start.format(formatter)+", Final date: "
          + this._end.format(formatter) + ", Duration: "+ projectNode.CompletedWork.plus(this.getDuration()) + "\n");
      projectNode = projectNode._fatherNode;
    }
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
