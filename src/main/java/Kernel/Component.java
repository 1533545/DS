package Kernel;

import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public abstract class Component {
    //TODO: RENAME
    protected Component fatherNode;
    protected String id;
    protected String name;
    protected String description;
    protected LocalDateTime startTime;
    protected LocalDateTime finishTime;

    protected abstract JSONObject toJson();
    public abstract Duration getDuration();
    public abstract void print(int indentation);

    protected Component(Component fatherNode, String name, String Description)
    {
        this.fatherNode = fatherNode;
        this.id = generateUUID();
        this.name = name;
        this.description = Description;
    }

    protected Component(JSONObject jsonObject) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        this.id = (String) jsonObject.get("Id");
        this.name = (String) jsonObject.get("Name");
        this.description = (String) jsonObject.get("Description");
        var start = LocalDateTime.parse(jsonObject.get("Start").toString(),formatter);
        var finish = LocalDateTime.parse(jsonObject.get("Finish").toString(),formatter);

        if(start != LocalDateTime.MIN) {
            this.startTime = start;
        }

        if(finish != LocalDateTime.MIN) {
            this.finishTime = finish;
        }

    }

    protected JSONObject toJsonComponent(JSONObject jsonObject) {
        jsonObject.put("Id",this.id);
        jsonObject.put("Name",this.name);
        jsonObject.put("Description", this.description);
        jsonObject.put("Duration", getDuration());

        if(this.startTime == null) {
            jsonObject.put("Start", LocalDateTime.MIN);
        }
        else {
            jsonObject.put("Start", this.startTime);
        }

        if(this.finishTime == null) {
            jsonObject.put("Finish", LocalDateTime.MIN);
        }
        else {
            jsonObject.put("Finish", this.finishTime);
        }

        return jsonObject;
    }

    protected String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public String getName() { return this.name; }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getFinishTime() {
        return this.finishTime;
    }

    public void setFinishTime(LocalDateTime time) {
        this.finishTime = time;
    }

    public void setStartTime(LocalDateTime time) {
        this.startTime = time;
    }

    public String generateCustomIndentation(int indentation) {
        String customIndentation = "";
        for (int i = 0; i < indentation; i++) {
            customIndentation = customIndentation.concat("-");
        }
        return customIndentation;
    }
}
