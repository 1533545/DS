package kernel;

import java.time.LocalDateTime;
import java.util.Observable;
import java.util.Timer;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Observable class. Is in charge of returning the real time
 * every two seconds to all of his observers.
 **/
public class Clock extends Observable {
  /**
   * Clock logger.
   **/
  private static Logger _logger = LoggerFactory.getLogger(Clock.class);
  /**
   * Task Scheduler.
   **/
  private Timer timer;
  /**
   * Clock object instance.
   **/
  private static Clock clock;
  /**
   * Indicates if notify or not the observers.
   **/
  private boolean cancelTimer;

  private Clock() {
    this.timer = new Timer();
    this.cancelTimer = false;
  }

  /**
   * Returns the instance of the clock object.
   **/
  public static synchronized Clock getInstance() {
    if (clock == null) {
      clock = new Clock();
    }
    return clock;
  }

  /**
   * Starts to schedule a tick task every 2 seconds.
   **/
  public void startClock() {
    this.timer.schedule(new TimerTask() {
      @Override
      public void run() {
        tick();
      }
    }, 2000, 2000);
  }

  /**
   * Notify observers the current time.
   **/
  private void tick() {
    if (!cancelTimer) {
      setChanged();
      notifyObservers(LocalDateTime.now());
    } else {
      stopTimer();
    }
  }

  /**
   * Stops scheduling ticks tasks.
   **/
  private void stopTimer() {
    this.timer.cancel();
    this.timer.purge();
    this.timer = null;
  }

  /**
   * Indicates to stop notifying to all observers.
   **/
  public void stopClock() {
    this.cancelTimer = true;
  }
}