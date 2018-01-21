package com.tram.network.simulation.model.base;

public class TimerGovernor implements Runnable {

    final private GlobalTimer timer;
    public volatile boolean pause = false;

    public TimerGovernor(GlobalTimer timer) {
        this.timer = timer;
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
            try {
                Thread.sleep(50);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while(pause) try {Thread.sleep(1000); } catch (InterruptedException ignore) {}

        }
    }
}
