package kernel;

import java.util.Timer;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.TimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
* Observable class. Is in charge of returning the real time
* every two seconds to all of his observers.
*/
public class Clock extends Observable {
    private static Logger logger = LoggerFactory.getLogger(Clock.class);
    private Timer _timer;
    private static Clock _clock;
    private boolean _cancelTimer;

    private Clock() {
        this._timer = new Timer();
        this._cancelTimer = false;
    }

    /* The synchronized is used in order to manage more than one thread */
    public synchronized static Clock getInstance() {
        if(_clock == null)
        {
            _clock = new Clock();
        }
        return _clock;
    }

    public void startClock() {
        this._timer.schedule(new TimerTask() { @Override public void run() { tick(); } },2000,2000);
    }

    private void tick() {
        if(!_cancelTimer) {
            setChanged();
            notifyObservers(LocalDateTime.now());
        }
        else {
            stopTimer();
        }
    }

    private void stopTimer() {
        this._timer.cancel();
        this._timer.purge();
        this._timer=null;
    }

    public void stopClock() {
        this._cancelTimer = true;
    }
}