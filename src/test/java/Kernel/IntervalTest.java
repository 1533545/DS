package Kernel;

import org.junit.jupiter.api.Test;
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
    Task t=new Task();
    t.startTask();

    int counter=0;
    while(t.State!=ComponentState.DONE)
    {

      c.updateTask();
      Thread.sleep(3000);
      i.update(c,i.getEnd());
      System.out.println("The end time after the update is: "+(i.getEnd()).format(formatter));
      if(counter==3)
      {
        t.State=ComponentState.DONE;
      }
      counter=counter+1;
    }

  }
}
