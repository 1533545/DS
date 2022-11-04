package Kernel;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
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

    protected ProjectComponent(JSONObject jsonObject){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        this.Id = (String) jsonObject.get("Id");
        this.Name = (String) jsonObject.get("Name");
        this.Description = (String) jsonObject.get("Description");
        this._startTime = LocalDateTime.parse(jsonObject.get("StartTime").toString(),formatter);
        this._finishTime = LocalDateTime.parse(jsonObject.get("FinishTime").toString(),formatter);
    }

    protected JSONObject toJsonComponent(JSONObject jsonObject) {
        jsonObject.put("Id",this.Id);
        jsonObject.put("Name",this.Name);
        jsonObject.put("Description", this.Description);
        jsonObject.put("Start:", this._startTime);
        jsonObject.put("Finish:", this._finishTime);
        jsonObject.put("Duration", getDuration());

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

    public String generateCustomIndentation(int indentation) {
        String customIndentation = "";
        for (int i = 0; i < indentation; i++) {
            customIndentation = customIndentation.concat("-");
        }
        return customIndentation;
    }
}
