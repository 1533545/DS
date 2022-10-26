package Kernel;

import java.util.Timer;
import java.time.LocalDateTime;
import java.util.Observable;

public class Clock extends Observable {
    private Timer _timer;

    public void startTimer() {
        this._timer = new Timer();
    }

    private void tick() {
        LocalDateTime dateTime = LocalDateTime.now();
        setChanged();
        notifyObservers(this);
    }
}
