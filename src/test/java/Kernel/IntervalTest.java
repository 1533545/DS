package Kernel;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class IntervalTest {
  @Test
  void ObserverTest() throws InterruptedException {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Clock c=new Clock();
    c.startTimer();
    Interval i=new Interval();
    System.out.println("The start time is: "+(i.getStart()).format(formatter));
    System.out.println("The end time is: "+(i.getEnd()).format(formatter));
    c.addObserver(i);
    Task t= new Task();
    LocalDateTime endTime=(i.getStart()).plusSeconds(10);
    t.startTask();
    while(t.State!=ComponentState.DONE)
    {
      c.updateTask();
      Thread.sleep(3000);
      i.update(c,i.getEnd());
      System.out.println("The end time after the update is: "+(i.getEnd()).format(formatter));
      if((i.getEnd()).isAfter(endTime))
      {
        t.State=ComponentState.DONE;
      }
    }
    System.out.println("The period of the task have expired!");
  }
}
