package kernel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import org.json.JSONObject;



/**
 * Observer class. Contains start and finish times of a task time interval.
 * Generates intervals of time every time a task starts. Keeps them and can
 * return the duration of the interval.
 **/

public class Interval implements Observer {
  private Task fatherTask;
  private LocalDateTime start;
  private LocalDateTime end;
  private String id;

  /**
   * comment.
   **/
  public Interval(Task task) {
    this.start = LocalDateTime.now();
    this.fatherTask = task;
    this.fatherTask.updateStartTime(this.start);
    id = generateUuid();
  }

  /**
   * comment.
   **/
  public Interval(JSONObject jsonObject) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    this.start = LocalDateTime.parse(jsonObject.get("Start").toString(), formatter);
    this.end = LocalDateTime.parse(jsonObject.get("End").toString(), formatter);
    this.id = jsonObject.get("Id").toString();
  }

  /**
   * comment.
   **/
  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Start", this.start);
    jsonObject.put("End", this.end);
    jsonObject.put("Id", this.id);
    return jsonObject;
  }

  public Duration getDuration() {
    return Duration.between(start, end);
  }

  @Override
  public void update(Observable obs, Object arg) {
    LocalDateTime time = (LocalDateTime) arg;
    this.end = time;
    this.fatherTask.updateFinishTime(time);
    printTimes();
  }

  private void printTimes() {
    printIntervalTimes();
    this.fatherTask.printTimes();
    this.fatherTask.printComponentTimes();
  }

  private void printIntervalTimes() {
    System.out.println("-------------------------------------------------");
    System.out.println("Interval -> Start: " + this.start.format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println("            End: " + this.end.format(DateTimeFormatter.ISO_DATE_TIME));
    System.out.println("            Duration: " + getDuration());
  }

  protected String generateUuid() {
    return UUID.randomUUID().toString();
  }
}
