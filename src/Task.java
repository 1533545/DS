import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Task {

    LocalDateTime startTime = LocalDateTime.now();
    LocalDateTime endTime = null;
    private List<Interval> intervals;
    Duration total_duration = null;

    public Task (){
      intervals = new ArrayList<Interval>();
    }

    public void StartTask() {
      Interval interval = new Interval();
      interval.getStart();
      intervals.add(interval);
  }

    public void PauseTask() {
      intervals.get(intervals.size()-1).getEnd();
  }

    public void FinishTask() {
      endTime = intervals.get(intervals.size()-1).getEnd();
      for (Interval interval : intervals) {
        total_duration = total_duration.plus(interval.getDuration());
      }
    }

}

