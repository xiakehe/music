package com.example.study_core.timer;

import java.util.TimerTask;

public class BaseTimerTask extends TimerTask {

    private ITimerListener timerListener;

    public BaseTimerTask(ITimerListener timerListener) {
        this.timerListener = timerListener;
    }

    @Override
    public void run() {
        if (timerListener != null) {
            timerListener.onTimer();
        }
    }
}
