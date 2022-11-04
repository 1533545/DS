//Class Clock, observable object that will be constantly updating the time in intervals of 2 seconds.
//Implements the singleton pattern so, there's only one Clock instance
package Kernel;

import java.util.Timer;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.TimerTask;

public class Clock extends Observable {
    private Timer _timer;
    private static Clock _clock;
    private boolean _cancelTimer;

    private Clock()
    {
        this._timer = new Timer();
        this._cancelTimer = false;
    }
    //The synchronized is used in order to manage more than one thread
    public synchronized static Clock getInstance() {
        if(_clock == null)
        {
            _clock = new Clock();
        }
        return _clock;
    }

    public void startClock()
    {
        this._timer.schedule(new TimerTask() { @Override public void run() { tick(); } },2000,2000);
    }

    private void tick() {
        if(!_cancelTimer)
        {
            setChanged();
            notifyObservers(LocalDateTime.now());
        }
        else
        {
            this._timer.cancel();
            //The purge method deletes all the canceled tasks of timer
            this._timer.purge();
            this._timer=null;
        }
    }

    public void stopClock() {
        this._cancelTimer = true;
    }
}
