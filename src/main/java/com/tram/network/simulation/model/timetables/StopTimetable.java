package com.tram.network.simulation.model.timetables;

import com.tram.network.simulation.model.base.Timer;

import java.util.ArrayList;
import java.util.List;

public class StopTimetable implements Timetable {


    private List<DepartureTime> departures = new ArrayList<>();
    private List<DepartureTime> nextDayDepartures = new ArrayList<>();
    private Timer globalTimer;

    StopTimetable(List<DepartureTime> departures, Timer timer) {
        this.departures = departures;
        this.globalTimer = timer;
        nextDayDepartures.addAll(departures);
    }

    @Override
    public Boolean isItDepartureTime() {
        return  globalTimer.getCurrentTime().isGreaterThan( departures.get(0) );
    }

    @Override
    public void tramDeparted() {
        if (!departures.isEmpty()) {
            departures.remove(0);
        }

        if (departures.isEmpty()) {
            departures.addAll(nextDayDepartures);
        }

    }
}
