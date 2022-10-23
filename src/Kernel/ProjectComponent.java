package Kernel;

import java.time.Duration;
import java.time.LocalTime;

public abstract class ProjectComponent {
    private ProjectComponent _fatherNode;
    public String Id;
    public String Name;
    public String Description;
    public Duration EstimatedTime;
    public Duration CompletedWork;
    public ComponentState State;

    protected ProjectComponent(ProjectComponent fatherNode, String id, String name,
                               String description,Duration estimatedTime)
    {
        this._fatherNode = fatherNode;
        this.Id = id;
        this.Name = name;
        this.Description = description;
        this.EstimatedTime = estimatedTime;
        this.State = ComponentState.TODO;
        this.CompletedWork = Duration.between(LocalTime.NOON,LocalTime.NOON);
    }

    protected ProjectComponent() {
        this._fatherNode = null;
        this.State = ComponentState.TODO;
        this.EstimatedTime = Duration.between(LocalTime.NOON,LocalTime.NOON);
        this.CompletedWork = Duration.between(LocalTime.NOON,LocalTime.NOON);
    }

    protected void updateState(ComponentState state)
    {
        this.State = state;
        if(this._fatherNode != null)
        {
            this._fatherNode.updateState(state);
        }
    }

    protected void updateTime(Duration completedWork)
    {
        this.CompletedWork = this.CompletedWork.plus(completedWork);
        if(this._fatherNode != null) {
            this._fatherNode.updateTime(completedWork);
        }
    }


    public void setCompletedWork()
    {
        //TODO: Map simple date format ("yyyy-MM-dd:HH:mm:ss") to Duration type
    }

    public void getCompletedWork()
    {
        //TODO: Map Duration to simple date format "yyyy-MM-dd:HH:mm:ss"
    }


    public void setEstimatedTime()
    {
        //TODO: Map simple date format ("yyyy-MM-dd:HH:mm:ss") to Duration type
    }

    public void getEstimatedTime()
    {
        //TODO: Map Duration to simple date format "yyyy-MM-dd:HH:mm:ss"
    }
}
