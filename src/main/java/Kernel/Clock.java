package Kernel;

import java.util.Timer;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.TimerTask;

public class Clock extends Observable {
    private Timer _timer=null;
    private static Clock c=null;

    private boolean cancelTimer=false;
    private Clock()
    {
        this._timer=new Timer();
        cancelTimer=false;
    }
    public synchronized static Clock getInstance() {
        if(c == null)
        {
            c=new Clock();
        }
        return c;
    }
    public void startTimer()
    {
        if(this._timer==null)
        {
            this._timer=new Timer();
            cancelTimer=false;
        }
    }
    public void setCancel()
    {
        this.cancelTimer=true;
    }
    public void updateTask()
    {
        this._timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        },2000,2000);
    }
    private void tick() {
        if(!cancelTimer)
        {
            LocalDateTime dateTime = LocalDateTime.now();
            setChanged();
            notifyObservers(dateTime);
        }
        else
        {
            this._timer.cancel();
            this._timer.purge();
            this._timer=null;
        }
    }
}