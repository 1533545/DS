package Kernel;

import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;


class ClockTest {
  @Test
  void clockTesting(){
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    Clock c=new Clock();
    Interval i=new Interval();
    c.startTimer();
    c.addObserver(i);
    c.updateTask();
    i.update(c,i.getEnd());
    System.out.println("The time is: "+(i.getEnd()).format(formatter));
  }
}
