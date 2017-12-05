package com.tram.network.simulation.model.timetables;

public class DepartureTime implements Comparable<DepartureTime>{

    //TODO: REFACTOR NAME!
    private int time = 0;

    public DepartureTime(int hour, int minute) {
        if ( (hour > 23) | (hour < 0) | (minute < 0) | (minute > 59)) {
            this.time = 0;
        } else {
            this.time = 1000*hour + minute;
        }
    }

    public DepartureTime addMinutes (int minutes) {
        Integer hour = time/1000;
        Integer min = time - (hour*1000);

        min += minutes;

        if (min > 59) {
            hour++;
            min -= 60;
        }

        if (hour > 24) {
            hour = 0;
        }

        return new DepartureTime(hour, min);
    }

    private int getRawTime() {
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

    private boolean isEqual(DepartureTime t) {
        return this.compareTo(t) == 0;
    }

    public boolean isGreaterEqualThen(DepartureTime t) {
        return this.isGreaterThan(t) | this.isEqual(t);
    }

    @Override
    public String toString() {
        Integer hour = time/1000;
        Integer min = time - (hour*1000);
        return hour.toString() + ":" + min.toString() + " ";
    }
}
