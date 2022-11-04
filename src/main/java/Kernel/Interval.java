package Kernel;

import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observer;
import java.util.Observable;

public class Interval implements Observer {
  private Task _fatherTask;
  private LocalDateTime _start;
  private LocalDateTime _end;

  public Interval(Task task) {
    this._fatherTask = task;
    updateStartTime(LocalDateTime.now());
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

  public Duration getDuration() {
    return Duration.between(_start,_end);
  }

  @Override
  public void update(Observable obs,Object arg) {
    updateFinishTime((LocalDateTime) arg);
    printTimes();
  }

  public void updateFinishTime(LocalDateTime time) {
    this._end = time;
    ProjectComponent node = this._fatherTask;
    while (node != null) {
      node.setFinishTime(time);
      node = node._fatherNode;
    }
  }

  public void updateStartTime(LocalDateTime time) {
    this._start = time;
    ProjectComponent node = this._fatherTask;
    while (node != null) {
      if(node.getStartTime() == null) {
        node.setStartTime(time);
      }
      node = node._fatherNode;
    }
  }

  private void printTimes() {
    printIntervalTimes();
    printTaskTimes();
    printProjectTimes();
  }

  private void printIntervalTimes() {
    System.out.println("-------------------------------------------------");
    System.out.println("Interval -> Start: " + this._start.format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println("            End: " + this._end.format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println("            Duration: " + getDuration());
  }

  private void printTaskTimes() {
    System.out.println(this._fatherTask.Name.toUpperCase() + ":");
    System.out.println("Task     -> Start: " + this._fatherTask.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println("            End: " + this._fatherTask.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println("            Duration: " + this._fatherTask.getDuration());
  }


  //Important: Aquí estem recorrent cada pare desde el node en el que ens trobem fisn arribat al root
  // del arbre i printem la seva info.
  private void printProjectTimes() {
    ProjectComponent project = this._fatherTask._fatherNode;

    while (project != null) {
      System.out.println(project.Name.toUpperCase() + ":");
      System.out.println("Project  -> Start: " + project.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));
      System.out.println("            End: " + project.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));
      System.out.println("            Duration: " + project.getDuration());
      project = project._fatherNode;
    }
    System.out.println("-------------------------------------------------");
  }

}
