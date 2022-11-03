package Kernel;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.UUID;

public abstract class ProjectComponent {
    //TODO: RENAME
    protected ProjectComponent _fatherNode;
    protected String Id;
    protected String Name;
    protected String Description;
    protected Duration EstimatedTime;
    protected Duration CompletedWork;
    protected ComponentState State;

    protected ProjectComponent(ProjectComponent fatherNode, String name, String Description)
    {
        this._fatherNode = fatherNode;
        this.Id = generateUUID();
        this.Name = name;
        this.Description = Description;
        this.State = ComponentState.TODO;
    }

    protected ProjectComponent(JSONObject jsonObject){
        this.Id = (String) jsonObject.get("Id");
        this.Name = (String) jsonObject.get("Name");
        this.Description = (String) jsonObject.get("Description");
        this.State = ComponentState.valueOf(jsonObject.get("State").toString());
    }

    protected JSONObject toJsonComponent(JSONObject jsonObject) {
        jsonObject.put("Id",this.Id);
        jsonObject.put("Name",this.Name);
        jsonObject.put("Description", this.Description);
        jsonObject.put("State",this.State);
        jsonObject.put("CompletedWork",this.CompletedWork);
        jsonObject.put("EstimatedTime", this.EstimatedTime);
        return jsonObject;
    }

    protected ProjectComponent() {
        this._fatherNode = null;
        this.Id = generateUUID();
        this.State = ComponentState.TODO;
        this.Description = "";
        this.Name = "";
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

    protected abstract JSONObject toJson();
}
