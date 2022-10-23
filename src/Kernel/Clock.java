import java.util.Timer;
import java.time.LocalDateTime;
import java.util.Observable;

public class Clock extends Observable{
  private Timer timer;

  public void startTimer()
  {
    this.timer=new Timer();
  }
  private void tick()
  {
    LocalDateTime dateTime=LocalDateTime.now();
    setChanged();
    notifyObservers(this);
  }
}
