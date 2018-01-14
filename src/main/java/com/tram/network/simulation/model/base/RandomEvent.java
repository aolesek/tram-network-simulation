package com.tram.network.simulation.model.base;

import java.util.Random;

public class RandomEvent {
    private int eventLifeTime;
    private int eventDuration; //count of nextStates
    Random generator = new Random();

    RandomEvent(){
        this.eventDuration = 2 + generator.nextInt(3); //2*10minutes+..
        this.eventLifeTime = 0;
    }

    Boolean tryRandomEvent(int velocity){
        return (generator.nextInt(50*velocity)) == 13 ? true : false;
    }

    Boolean eventIsKeepAlive(){
        eventLifeTime++;
        return eventLifeTime == eventDuration || eventLifeTime == 0 ? false : true;
    }
}
