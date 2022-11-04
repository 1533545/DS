package Kernel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

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
        this._runningInterval = new Interval(this);
        addInterval(this._runningInterval);

        Clock clock = Clock.getInstance();
        clock.addObserver(this._runningInterval);

        System.out.println("Starting " + this.name + "\n");
    }

    public void finishTask(){
        Clock clock = Clock.getInstance();
        clock.deleteObserver(this._runningInterval);
        this._runningInterval = null;

        System.out.println("Finishing " + this.name + "\n");
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

}
