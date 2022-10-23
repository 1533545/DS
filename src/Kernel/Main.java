package Kernel;

import java.time.Duration;
import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {
        Duration duration = Duration.between(LocalTime.NOON,LocalTime.NOON);
        System.out.println(duration.getSeconds());
    }
}