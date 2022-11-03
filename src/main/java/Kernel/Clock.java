package Kernel;

import java.util.Timer;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.TimerTask;

public class Clock extends Observable {
    private Timer _timer=null;
    private static Clock c=null;
    private Clock()
    {
        this._timer=new Timer();
    }
    public synchronized static Clock startTimer() {
        if(c == null)
        {
            c=new Clock();
        }
        return c;
    }
    public void updateTask()
    {
        this._timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        },2,2000);

    }
    private void tick() {
        LocalDateTime dateTime = LocalDateTime.now();
        setChanged();
        notifyObservers(dateTime);
    }
}
