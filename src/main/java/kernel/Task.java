package kernel;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import visitor.Visitor;

/**
 * Component extension. Leaf of the components tree.
 * Contains the work time intervals for the assignment being working on.
 **/
public class Task extends Component {
  /**
   * Logger for Task class.
   **/
  private static Logger logger = LoggerFactory.getLogger(Task.class);
  /**
   * List of intervals.
   **/
  private List<Interval> intervals;
  /**
   * Running Interval.
   **/
  private Interval runningInterval;

  /**
   * Parameter constructor of Task.
   **/
  public Task(Project fatherNode, String name, String description, List<String> tagList) {
    super(fatherNode, name, description, tagList);
    if ((name.replaceAll("\\s", "")).matches("^[a-zA-Z0-9]*$") && name != null) {
      this.intervals = new ArrayList<>();
    } else {
      throw new IllegalArgumentException("Illegal task name:" + name);
    }

    this.runningInterval = null;
    invariant();
  }

  /**
   * Initialize Task object using a JSONObject.
   **/
  public Task(JSONObject jsonObject) throws Exception {
    super(jsonObject);
    if (jsonObject != null) {
      this.intervals = new ArrayList<>();
      loadIntervalsFromJson(jsonObject.getJSONArray("Intervals"));
    } else {
      throw new Exception("No file to read");
    }

    invariant();
  }

  /**
   * Accepts a visitor.
   **/
  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  }

  /**
   * Returns a JSONObject of Task object.
   **/
  @Override
  public JSONObject toJson() {
    JSONObject jsonObject = toJsonComponent(new JSONObject());
    jsonObject.put("Class", "Task");
    jsonObject.put("Intervals", intervalsToJson());
    assert (jsonObject != null);
    return jsonObject;
  }

  /**
   * Returns a JSONArray of intervals attribute.
   **/
  private JSONArray intervalsToJson() {
    JSONArray jsonArray = new JSONArray();
    for (Interval interval : intervals) {
      jsonArray.put(interval.toJson());
    }
    return jsonArray;
  }

  /**
   * Initialize intervals attribute List from a JSONArray.
   **/
  private void loadIntervalsFromJson(JSONArray jsonArray) {
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject intervalJson = jsonArray.getJSONObject(i);
      this.intervals.add(new Interval(intervalJson));
    }
  }

  /**
   * Adds a new interval object to intervals attribute.
   */
  public void addInterval(Interval interval) {
    invariant();
    if (interval != null) {
      this.intervals.add(interval);
    }

    assert (this.intervals.get(this.intervals.size() - 1) == interval);
    invariant();
  }

  /**
   * Starts doing a task.
   **/
  public void startTask() {
    System.out.println("- Starting " + this.name + "\n");
    startObservingClock();
  }

  /**
   * Starts Counting time by observing the Clock instance.
   **/
  private void startObservingClock() {
    invariant();
    this.runningInterval = new Interval(this);
    addInterval(this.runningInterval);
    Clock clock = Clock.getInstance();
    clock.addObserver(this.runningInterval);
    assert (clock != null);
    assert (clock.countObservers() > 0);
    assert (this.runningInterval != null);
    invariant();
  }

  /**
   * Starts Counting time by stop observing the Clock instance.
   */
  public void finishTask() {
    invariant();
    System.out.println("\n" +"- Finishing " + this.name + "\n");
    stopObservingClock();
    invariant();
  }

  /**
   * Ends doings Task.
   */
  private void stopObservingClock() {
    Clock clock = Clock.getInstance();
    clock.deleteObserver(this.runningInterval);
    this.runningInterval = null;
    assert (clock != null);
    assert (this.runningInterval == null);
  }

  /**
   * Returns the Duration of a task.
   **/
  @Override
  public Duration getDuration() {
    invariant();
    Duration duration = Duration.between(LocalTime.NOON, LocalTime.NOON);
    for (Interval interval : intervals) {
      duration = duration.plus(interval.getDuration());
    }
    assert (duration.isPositive());
    invariant();
    return duration;
  }

  /**
   * Custom Task printer for task times.
   **/
  public void printTimes() {
    System.out.println(this.name.toUpperCase() + ":");
    System.out.println("Task     -> Start: "
        + this.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));

    System.out.println("            End: "
        + this.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));

    System.out.println("            Duration: " + this.getDuration());
  }

  /**
   * Checks if intervals attribute is null.
   **/
  private boolean invariant() {
    assert (this.intervals != null);
    return true;
  }
}
