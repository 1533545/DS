package Kernel;

import java.util.Timer;
import java.time.LocalDateTime;
import java.util.Observable;
import java.util.TimerTask;

public class Clock extends Observable {
    private Timer _timer=null;

    public void startTimer() {
        if(this._timer==null)
        {
            this._timer = new Timer();
        }
    }
    public void updateTask()
    {
        this._timer.schedule(new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        },0,3000);

    }
    private void tick() {
        LocalDateTime dateTime = LocalDateTime.now();
        setChanged();
        notifyObservers(dateTime);
    }
}
