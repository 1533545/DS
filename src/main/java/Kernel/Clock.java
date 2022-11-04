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
    //Important:synchronized per gestionar de forma correcta més d'un thread
    //i singleton per crear una única instancia de Clock
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
            this._timer.purge(); //Eliminem totes les tasques cancelades del timer
            this._timer=null;
        }
    }

    public void stopClock() {
        this._cancelTimer = true;
    }
}
