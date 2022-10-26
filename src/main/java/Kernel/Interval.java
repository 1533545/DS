package Kernel;

import java.time.Duration;
import java.time.LocalDateTime;

public class Interval { //Implements Observer
  private LocalDateTime _start;
  private LocalDateTime _end;

  public Interval() {
    this._start = LocalDateTime.now();
    this._end = LocalDateTime.now();
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

  public void update() {
      //TODO: Change this._end with value sent by observable
      //TODO: Clock as an extension of observable
      //TODO: Implement Observer
  }
}
