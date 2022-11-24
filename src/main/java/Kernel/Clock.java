package kernel;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Observable class. Is in charge of returning the real time
 * every two seconds to all of his observers.
 **/
public class Clock extends Observable {
  private Timer timer;
  private static Clock clock;
  private boolean cancelTimer;

  private Clock() {
    this.timer = new Timer();
    this.cancelTimer = false;
  }

  /**
   *  The synchronized is used in order to manage more than one thread.
   **/
  public static synchronized Clock getInstance() {
    if (clock == null) {
      clock = new Clock();
    }
    return clock;
  }

  /**
   * comment.
   **/
  public void startClock() {
    this.timer.schedule(new TimerTask() {
      @Override
      public void run() {
        tick();
      }
    }, 2000, 2000);
  }

  private void tick() {
    if (!cancelTimer) {
      setChanged();
      notifyObservers(LocalDateTime.now());
    } else {
      stopTimer();
    }
  }

  private void stopTimer() {
    this.timer.cancel();
    this.timer.purge();
    this.timer = null;
  }

  public void stopClock() {
    this.cancelTimer = true;
  }
}
