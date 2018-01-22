package com.tram.network.simulation.model.base;

import com.tram.network.simulation.application.ApplicationUtils;

public class TimerGovernor implements Runnable {

    final private GlobalTimer timer;
    public volatile boolean pause = false;
    public volatile int timeBreak = ApplicationUtils.defaultTimeBreak;
    private volatile BreakType breakType;

    public TimerGovernor(GlobalTimer timer) {
        this.timer = timer;
    }

    public int getTimeBreak() {
        return timeBreak;
    }

    public void setTimeBreak(int timeBreak) {
        this.timeBreak = timeBreak;
    }

    public BreakType getBreakType() {
        return breakType;
    }

    public void setBreakType(BreakType breakType) {
        this.breakType = breakType;
    }

    public void setOneStepTime(int time) {
        this.timer.setOneStepTime(time);
    }

    public void speedUp() {
        if (this.timeBreak > 0) {
            timeBreak -= 3;
            if (timeBreak < 0)
                timeBreak = 0;
        }
    }

    public void speedDown() {
        timeBreak += 3;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (true) {
            timer.nextState();
            waitBeforeNextStep(breakType);
            while (pause) try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }


        }
    }

    public void waitBeforeNextStep(BreakType type) {
        if (type == BreakType.FIXED) {
            try {
                Thread.sleep(timeBreak);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (type == BreakType.REAL) {
            long l = System.currentTimeMillis();

            long oneStepTime = timer.getOneStepTime() * 1000;

            while ((System.currentTimeMillis() - l) < oneStepTime) {
                //wait
            }
        }
    }
}
