package Kernel;

import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observer;
import java.util.Observable;
import java.util.UUID;

/*
* Observer class. Contains start and finish times of a task time interval.
* Generates intervals of time every time a task starts. Keeps them and can
* return the duration of the interval.
*/

public class Interval implements Observer {
  private Task _fatherTask;
  private LocalDateTime _start;
  private LocalDateTime _end;
  private String _id;

  public Interval(Task task) {
    this._start = LocalDateTime.now();
    this._fatherTask = task;
    this._fatherTask.updateStartTime(this._start);
    _id = generateUUID();
  }

  public Interval(JSONObject jsonObject) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    this._start = LocalDateTime.parse(jsonObject.get("Start").toString(),formatter);
    this._end = LocalDateTime.parse(jsonObject.get("End").toString(),formatter);
    this._id = jsonObject.get("Id").toString();
  }

  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Start", this._start);
    jsonObject.put("End", this._end);
    jsonObject.put("Id", this._id);
    return jsonObject;
  }

  public Duration getDuration() {
    return Duration.between(_start,_end);
  }

  @Override
  public void update(Observable obs,Object arg) {
    LocalDateTime time = (LocalDateTime) arg;
    this._end = time;
    this._fatherTask.updateFinishTime(time);
    printTimes();
  }

  private void printTimes() {
    printIntervalTimes();
    this._fatherTask.printTimes();
    this._fatherTask.printComponentTimes();
  }

  private void printIntervalTimes() {
    System.out.println("-------------------------------------------------");
    System.out.println("Interval -> Start: " + this._start.format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println("            End: " + this._end.format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println("            Duration: " + getDuration());
  }

  protected String generateUUID() {
    return UUID.randomUUID().toString();
  }
}
