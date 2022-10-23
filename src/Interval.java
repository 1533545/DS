import java.time.Duration;
import java.time.LocalDateTime;

public class Interval {

  LocalDateTime _start = null;
  LocalDateTime _end = null;

  Duration _duration = Duration.between(_start,_end);

  public LocalDateTime getStart() {
    _start = LocalDateTime.now();
    return _start;
  }

  public LocalDateTime getEnd() {
    _end = LocalDateTime.now();
    return _end;
  }

  public Duration getDuration() {
    return _duration;
  }

  public void update() {

  }
}
