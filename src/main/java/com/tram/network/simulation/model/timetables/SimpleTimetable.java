package com.tram.network.simulation.model.timetables;

public class SimpleTimetable implements Timetable {
    @Override
    public Boolean isItDepartureTime() {
        return true;
    }

    @Override
    public void tramDeparted() {

    }
}
