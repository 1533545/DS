package Kernel;//package Kernel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observer;
import java.util.Observable;

public class Interval implements Observer{ //Implements Observer
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

  @Override
  public void update(Observable obs,Object arg) {
    this._end=(LocalDateTime) arg;
  }
}
