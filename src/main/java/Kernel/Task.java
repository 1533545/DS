package Kernel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task extends ProjectComponent{
    private LocalDateTime _startTime;
    private LocalDateTime _finishTime;
    public LocalDateTime CreactionTime;
    public List<Interval> IntervalList;

    public Task() {
        IntervalList = new ArrayList<Interval>();
        CreactionTime = LocalDateTime.now();
        _startTime = null;
        _finishTime = null;
    }

    public Task(String name, ProjectComposite projectComposite) {
        super(projectComposite, name);
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
