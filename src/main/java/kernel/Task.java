package kernel;

import visitor.Visitor;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * Component extension. Leaf of the components tree.
 * Contains the work time intervals for the assignment being working on.
 */
public class Task extends Component {
    private static Logger logger = LoggerFactory.getLogger(Task.class);
    private List<Interval> _intervals;
    private Interval _runningInterval;

    public Task(Project fatherNode, String name, String description, List<String> tagList) {
        super(fatherNode, name, description, tagList);
        if((name.replaceAll("\\s","")).matches("^[a-zA-Z0-9]*$") && name!=null)
        {
            this._intervals = new ArrayList<>();
        }
        else
            throw new IllegalArgumentException("Illegal task name:"+name);

        this._runningInterval = null;
        invariant();
    }

    public Task(JSONObject jsonObject) throws Exception {
        super(jsonObject);
        if(jsonObject != null)
        {
            this._intervals = new ArrayList<>();
            loadIntervalsFromJson(jsonObject.getJSONArray("Intervals"));
        }
        else
            throw new Exception("No file to read");
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
        assert(jsonObject!=null);
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
        invariant();
        if(interval != null)
            this._intervals.add(interval);
        assert(this._intervals.get(this._intervals.size()-1)==interval);
        invariant();
    }

    public void startTask() {
        System.out.println("Starting " + this.name + "\n");
        startObservingClock();
    }

    private void startObservingClock() {
        invariant();
        this._runningInterval = new Interval(this);
        addInterval(this._runningInterval);
        Clock clock = Clock.getInstance();
        clock.addObserver(this._runningInterval);
        assert(clock != null);
        assert(clock.countObservers()>0);
        assert(this._runningInterval !=null);
        invariant();
    }

    public void finishTask(){
        invariant();
        System.out.println("Finishing " + this.name + "\n");
        stopObservingClock();
        invariant();
    }

    private void stopObservingClock() {
        Clock clock = Clock.getInstance();
        clock.deleteObserver(this._runningInterval);
        this._runningInterval = null;
        assert(clock != null);
        assert(clock.countObservers()==0);
        assert(this._runningInterval ==null);
    }

    @Override
    public Duration getDuration() {
        invariant();
        Duration duration = Duration.between(LocalTime.NOON,LocalTime.NOON);
        for (Interval interval : _intervals) {
            duration = duration.plus(interval.getDuration());
        }
        assert(duration.isPositive());
        invariant();
        return duration;
    }

    public void printTimes() {
        System.out.println(this.name.toUpperCase() + ":");
        System.out.println("Task     -> Start: " + this.getStartTime().format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println("            End: " + this.getFinishTime().format(DateTimeFormatter.ISO_DATE_TIME));
        System.out.println("            Duration: " + this.getDuration());
    }
    private boolean invariant()
    {
        assert(this._intervals!=null);
        return true;
    }
}
