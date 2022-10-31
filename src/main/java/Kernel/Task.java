package Kernel;

import org.json.JSONObject;

import java.time.LocalDateTime;
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
        this.IntervalList = new ArrayList<Interval>();
        this.CreationTime = LocalDateTime.now();
        this._startTime = LocalDateTime.now();
        this._finishTime = LocalDateTime.now();
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = toJsonComponent(new JSONObject());
        jsonObject.put("CreationTime",this.CreationTime);
        jsonObject.put("StartTime",this._startTime);
        jsonObject.put("FinishTime",this._finishTime);
        return jsonObject;
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
