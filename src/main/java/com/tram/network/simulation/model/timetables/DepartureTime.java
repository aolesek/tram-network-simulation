package com.tram.network.simulation.model.timetables;

public class DepartureTime implements Comparable<DepartureTime>{

    //TODO: REFACTOR NAME!
    public int time = 0;

    public DepartureTime(int hour, int minute) {
        if ( (hour > 23) | (hour < 0) | (minute < 0) | (minute > 59))
            throw new IllegalArgumentException();
        this.time = 1000*hour + minute;
    }

    public int getRawTime() {
        return this.time;
    }

    public int compareTo(DepartureTime t) {
        if ( this.getRawTime() < t.getRawTime() ) {
            return -1;
        } else if ( this.getRawTime() == t.getRawTime() ) {
            return 0;
        } else {
            return 1;
        }
    }

    public boolean isGreaterThan(DepartureTime t) {
        return this.compareTo(t) > 0;
    }

    public boolean isLessThan(DepartureTime t) {
        return this.compareTo(t) < 0;
    }

    public boolean isEqual(DepartureTime t) {
        return this.compareTo(t) == 0;
    }

    public boolean isGreaterEqualThen(DepartureTime t) {
        return this.isGreaterThan(t) | this.isEqual(t);
    }
}
