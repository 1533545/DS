package kernel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Observer class. Contains start and finish times of a task time interval.
 * Generates intervals of time every time a task starts. Keeps them and can
 * return the duration of the interval.
 **/
public class Interval implements Observer {
  /**
   * Interval Logger.
   */
  private static Logger logger = LoggerFactory.getLogger(Interval.class);
  /**
   * Task that contains this Interval object.
   */
  private Task fatherTask;
  /**
   * Start time of the Interval object.
   */
  private LocalDateTime start;
  /**
   * Finish time of the Interval object..
   */
  private LocalDateTime end;

  /**
   * Parameter constructor.
   */
  public Interval(Task task) {
    this.start = LocalDateTime.now();
    this.fatherTask = task;
    this.fatherTask.updateStartTime(this.start);
  }

  /**
   * Initialize Interval object from a JSONObject.
   */
  public Interval(JSONObject jsonObject) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    this.start = LocalDateTime.parse(jsonObject.get("Start").toString(), formatter);
    this.end = LocalDateTime.parse(jsonObject.get("End").toString(), formatter);
  }

  /**
   * Returns a JSONObject of the Interval object.
   */
  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Start", this.start);
    jsonObject.put("End", this.end);
    return jsonObject;
  }

  /**
   * Returns the Duration of the Interval object.
   */
  public Duration getDuration() {
    return Duration.between(start, end);
  }

  /**
   * Updates interval every time it is notified by an Observable object.
   **/
  @Override
  public void update(Observable obs, Object arg) {
    LocalDateTime time = (LocalDateTime) arg;
    this.end = time;
    this.fatherTask.updateFinishTime(time);
    printTimes();
  }

  /**
   * Custom console printer for time values for every node from this
   * Interval object to root Component.
   **/
  private void printTimes() {
    printIntervalTimes();
    this.fatherTask.printTimes();
    this.fatherTask.printComponentTimes();
  }

  /**
   * Custom console printer for time values of the Interval object.
   **/
  private void printIntervalTimes() {
    logger.info("Interval -> Start: " + this.start.format(DateTimeFormatter.ISO_DATE_TIME));
    logger.info("End: " + this.end.format(DateTimeFormatter.ISO_DATE_TIME));
    logger.info("Duration: " + getDuration());
  }
}
