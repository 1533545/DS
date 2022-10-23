package Kernel;

import java.time.Duration;
import java.time.LocalTime;
import java.util.UUID;

public abstract class ProjectComponent {
    protected ProjectComponent _fatherNode;
    protected String Id;
    protected String Name;
    protected String Description;
    protected Duration EstimatedTime;
    protected Duration CompletedWork;
    protected ComponentState State;

    protected ProjectComponent(ProjectComponent fatherNode, String name)
    {
        this._fatherNode = fatherNode;
        this.Id = generateUUID();
        this.Name = name;
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

    protected String generateUUID() {
        return UUID.randomUUID().toString();
    }

    //

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

    public void setDescription(String description)
    {
        this.Description = description;
    }

    public String getDescription()
    {
        return this.Description;
    }
}
