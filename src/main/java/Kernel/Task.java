package kernel;


import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import visitor.Visitor;

/**
 * Component extension. Leaf of the components tree.
 * Contains the work time intervals for the assignment being working on.
 **/
public class Task extends Component {
  private List<Interval> intervals;
  private Interval runningInterval;

  /**
   * comment.
   */
  public Task(Project fatherNode, String name, String description) {
    super(fatherNode, name, description);
    if ((name.replaceAll("\\s", "")).matches("^[a-zA-Z0-9]*$") && name != null) {
      this.intervals = new ArrayList<>();
    } else {
      throw new IllegalArgumentException("Illegal task name:" + name);
    }

    this.runningInterval = null;
    invariant();
  }

  /**
   * comment.
   */
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

  @Override
  public void acceptVisitor(Visitor visitor) {
    visitor.visitTask(this);
  }

  @Override
  public JSONObject toJson() {
    JSONObject jsonObject = toJsonComponent(new JSONObject());
    jsonObject.put("Class", "Task");
    jsonObject.put("Intervals", intervalsToJson());
    assert (jsonObject != null);
    return jsonObject;
  }

  private JSONArray intervalsToJson() {
    JSONArray jsonArray = new JSONArray();
    for (Interval interval : intervals) {
      jsonArray.put(interval.toJson());
    }
    return jsonArray;
  }

  private void loadIntervalsFromJson(JSONArray jsonArray) {
    for (int i = 0; i < jsonArray.length(); i++) {
      JSONObject intervalJson = jsonArray.getJSONObject(i);
      this.intervals.add(new Interval(intervalJson));
    }
  }

  /**
   * comment.
   */
  public void addInterval(Interval interval) {
    invariant();
    if (interval != null) {
      this.intervals.add(interval);
    }

    assert (this.intervals.get(this.intervals.size() - 1) == interval);
    invariant();
  }

  public void startTask() {
    System.out.println("Starting " + this.name + "\n");
    startObservingClock();
  }

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
   * comment.
   */
  public void finishTask() {
    invariant();
    System.out.println("Finishing " + this.name + "\n");
    stopObservingClock();
    invariant();
  }

  private void stopObservingClock() {
    Clock clock = Clock.getInstance();
    clock.deleteObserver(this.runningInterval);
    this.runningInterval = null;
    assert (clock != null);
    assert (clock.countObservers() == 0);
    assert (this.runningInterval == null);
  }

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
   * comment.
   */
  public void print(int indentation) {
    String customIndentation = generateCustomIndentation(indentation);
    System.out.println(customIndentation + ">" + "TASK: " + this.name + " - Start: "
        + this.getStartTime() + " - Finish: " + this.getFinishTime()
        + " - Duration: " + this.getDuration());
  }

  /**
   * comment.
   */
  public void printTimes() {
    System.out.println(this.name.toUpperCase() + ":");
    System.out.println("Task     -> Start: "
        + this.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));

    System.out.println("            End: "
        + this.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));

    System.out.println("            Duration: " + this.getDuration());
  }

  private boolean invariant() {
    assert (this.intervals != null);
    return true;
  }
}
