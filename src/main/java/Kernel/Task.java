package Kernel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Task extends ProjectComponent{
    private LocalDateTime _startTime;
    private LocalDateTime _finishTime;
    public LocalDateTime CreationTime;
    public List<Interval> IntervalList;

    public Task(ProjectComposite fatherNode, String name, String description) {
        super(fatherNode, name, description);
        fatherNode.addComponent(this);
        this.IntervalList = new ArrayList<>();
        this.CreationTime = LocalDateTime.now();
        this._startTime = LocalDateTime.now();
        this._finishTime = LocalDateTime.now();
    }

    public Task(JSONObject jsonObject) {
        super(jsonObject);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        this._startTime = LocalDateTime.parse(jsonObject.get("StartTime").toString(),formatter);
        this._finishTime = LocalDateTime.parse(jsonObject.get("FinishTime").toString(),formatter);
        this.CreationTime = LocalDateTime.parse(jsonObject.get("CreationTime").toString(),formatter);
        loadIntervalsFromJson(jsonObject.getJSONArray("Intervals"));
    }

    public Task() {}

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = toJsonComponent(new JSONObject());
        jsonObject.put("Class", "Task");
        jsonObject.put("CreationTime",this.CreationTime);
        jsonObject.put("StartTime",this._startTime);
        jsonObject.put("FinishTime",this._finishTime);
        jsonObject.put("Intervals", intervalsToJson());
        return jsonObject;
    }

    private JSONArray intervalsToJson() {
        JSONArray jsonArray = new JSONArray();
        for (Interval interval : IntervalList){
            jsonArray.put(interval.toJson());
        }
        return jsonArray;
    }

    private void loadIntervalsFromJson(JSONArray jsonArray) {
        for(int i = 0; i < jsonArray.length(); i++) {
            JSONObject intervalJson = jsonArray.getJSONObject(i);
            this.IntervalList.add(new Interval(intervalJson));
        }
    }

    public void startTask() {
        updateState(ComponentState.DOING);
        this.IntervalList.add(new Interval());
        this._startTime = this.IntervalList.get(this.IntervalList.size()-1).getStart();
        //TODO: Start intervals
    }

    public void pauseTask() {
        updateState(ComponentState.TODO);
        //TODO: End interval
        this._finishTime = this.IntervalList.get(this.IntervalList.size()-1).getEnd();
    }

    public boolean finishTask(){
        if(this.State == ComponentState.DONE)
        {
            return false;
        }
        updateState(ComponentState.DONE);

        //TODO: End interval
        this._finishTime = this.IntervalList.get(this.IntervalList.size()-1).getEnd();

        for (Interval interval : this.IntervalList ) {
            this.CompletedWork = this.CompletedWork.plus(interval.getDuration());
        }

        return true;
    }


}
