package com.tram.network.simulation.model.base;

public class TimerGovernor implements Runnable{

    private GlobalTimer timer;

    public TimerGovernor(GlobalTimer timer) {
        this.timer = timer;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while(true) {
            timer.nextState();
            try {
                Thread.sleep(timer.getOneStepTime()*10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}