package Kernel;

import java.time.LocalDateTime;

public class Task extends ProjectComponent{
    private LocalDateTime _startTime;
    private LocalDateTime _finishTime;

    public Task() {
        _startTime = LocalDateTime.now();
        _finishTime = null;
    }

    public Task(String name, ProjectComposite projectComposite) {

    }
}
