package Kernel;//package Kernel;

import org.json.JSONObject;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observer;
import java.util.Observable;

public class Interval implements Observer{ //Implements Observer
  private LocalDateTime _start;
  private LocalDateTime _end;

  public Interval() {
    this._start = LocalDateTime.now();
    this._end = LocalDateTime.now();
  }

  public Interval(JSONObject jsonObject) {
    DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
    this._start = LocalDateTime.parse(jsonObject.get("Start").toString(),formatter);
    this._end = LocalDateTime.parse(jsonObject.get("End").toString(),formatter);
  }

  public JSONObject toJson() {
    JSONObject jsonObject = new JSONObject();
    jsonObject.put("Start", this._start);
    jsonObject.put("End", this._end);
    return jsonObject;
  }

  public LocalDateTime getStart() {
    return this._start;
  }

  public LocalDateTime getEnd() {
    return this._end;
  }

  public Duration getDuration() {
    return Duration.between(_start,_end);
  }

  @Override
  public void update(Observable obs,Object arg) {
    this._end=(LocalDateTime) arg;
  }
}
