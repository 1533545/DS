//Class ProjectComponent, abstract class that describes the operations that their composites 
//and tasks can implement, and the common attributes they have.

package Kernel;

import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class ProjectComponent {
    //TODO: RENAME
    protected ProjectComponent _fatherNode;
    protected String Id;
    protected String Name;
    protected String Description;
    protected Duration CompletedWork;
    protected LocalDateTime _startTime;
    protected LocalDateTime _finishTime;

    protected abstract JSONObject toJson();
    public abstract Duration getDuration();
    public abstract void print(int indentation);

    protected ProjectComponent(ProjectComponent fatherNode, String name, String Description)
    {
        this._fatherNode = fatherNode;
        this.Id = generateUUID();
        this.Name = name;
        this.Description = Description;
    }

    protected ProjectComponent(JSONObject jsonObject) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        this.Id = (String) jsonObject.get("Id");
        this.Name = (String) jsonObject.get("Name");
        this.Description = (String) jsonObject.get("Description");
        var start = LocalDateTime.parse(jsonObject.get("Start").toString(),formatter);
        var finish = LocalDateTime.parse(jsonObject.get("Finish").toString(),formatter);

        if(start != LocalDateTime.MIN) {
            this._startTime = start;
        }

        if(finish != LocalDateTime.MIN) {
            this._finishTime = finish;
        }

    }

    protected JSONObject toJsonComponent(JSONObject jsonObject) {
        jsonObject.put("Id",this.Id);
        jsonObject.put("Name",this.Name);
        jsonObject.put("Description", this.Description);
        jsonObject.put("Duration", getDuration());

        if(this._startTime == null) {
            jsonObject.put("Start", LocalDateTime.MIN);
        }
        else {
            jsonObject.put("Start", this._startTime);
        }

        if(this._finishTime == null) {
            jsonObject.put("Finish", LocalDateTime.MIN);
        }
        else {
            jsonObject.put("Finish", this._finishTime);
        }

        return jsonObject;
    }

    protected String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public String getName() { return this.Name; }

    public LocalDateTime getStartTime() {
        return this._startTime;
    }

    public LocalDateTime getFinishTime() {
        return this._finishTime;
    }

    public void setFinishTime(LocalDateTime time) {
        this._finishTime = time;
    }

    public void setStartTime(LocalDateTime time) {
        this._startTime = time;
    }

    //Method that helps visualize the structure of the tree
    public String generateCustomIndentation(int indentation) {
        String customIndentation = "";
        for (int i = 0; i < indentation; i++) {
            customIndentation = customIndentation.concat("-");
        }
        return customIndentation;
    }
}
