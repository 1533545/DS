package Kernel;

import Visitor.Visitor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/*
 * Component extension. Leaf of the components tree.
 * Contains the work time intervals for the assignment being working on.
 */
public class Task extends Component {
    private List<Interval> _intervals;
    private Interval _runningInterval;

    public Task(Project fatherNode, String name, String description) {
        super(fatherNode, name, description);
        this._intervals = new ArrayList<>();
        _runningInterval = null;
    }

    public Task(JSONObject jsonObject) {
        super(jsonObject);
        this._intervals = new ArrayList<>();
        loadIntervalsFromJson(jsonObject.getJSONArray("Intervals"));
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
        return jsonObject;
    }

    private JSONArray intervalsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Interval interval : _intervals){
            jsonArray.put(interval.toJson());
        }
        return jsonArray;
    }

    private void loadIntervalsFromJson(JSONArray jsonArray) {
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject intervalJson = jsonArray.getJSONObject(i);
            this._intervals.add(new Interval(intervalJson));
        }
    }

    public void addInterval(Interval interval) {
        this._intervals.add(interval);
    }

    public void startTask() {
        System.out.println("Starting " + this.name + "\n");
        startObservingClock();
    }

    private void startObservingClock() {
        this._runningInterval = new Interval(this);
        addInterval(this._runningInterval);
        Clock clock = Clock.getInstance();
        clock.addObserver(this._runningInterval);
    }

    public void finishTask(){
        System.out.println("Finishing " + this.name + "\n");
        stopObservingClock();
    }

    private void stopObservingClock() {
        Clock clock = Clock.getInstance();
        clock.deleteObserver(this._runningInterval);
        this._runningInterval = null;
    }

    @Override
    public Duration getDuration() {
        Duration duration = Duration.between(LocalTime.NOON,LocalTime.NOON);
        for (Interval interval : _intervals) {
            duration = duration.plus(interval.getDuration());
        }
        return duration;
    }

    public void print(int indentation) {
        String customIndentation = generateCustomIndentation(indentation);
        System.out.println(customIndentation + ">" + "TASK: " + this.name + " - Start: " +
                this.getStartTime() + " - Finish: " + this.getFinishTime() + " - Duration: " + this.getDuration());
    }

    public void printTimes() {
        System.out.println(this.name.toUpperCase() + ":");
        System.out.println("Task     -> Start: " + this.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println("            End: " + this.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println("            Duration: " + this.getDuration());
    }
}
