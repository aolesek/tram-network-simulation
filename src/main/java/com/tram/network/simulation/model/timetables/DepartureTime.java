package com.tram.network.simulation.model.timetables;

public class DepartureTime implements Comparable<DepartureTime>{

    //TODO: REFACTOR NAME!
    private int hour = 0;
    private int minute = 0;
    private int second = 0;

    public DepartureTime(int hour, int minute) {
        if ( (hour > 23) | (hour < 0) | (minute < 0) | (minute > 59)) {
            this.minute = 0;
            this.hour = 0;
        } else {
            this.hour = hour;
            this.minute = minute;
        }
        this.second = 0;
    }

    public DepartureTime(int hour, int minute, int second) {
        if ( (hour > 23) | (hour < 0) | (minute < 0) | (minute > 59) | (second < 0) | (second > 59) ) {
            this.minute = 0;
            this.hour = 0;
            this.second = 0;
        } else {
            this.hour = hour;
            this.minute = minute;
            this.second = second;
        }
    }

    public DepartureTime addMinutes (int minutes) {
        int newminute = minute + minutes;

        int min;
        int hou = hour;
        int sec = second;

        if (newminute < 60) {
            min = newminute;
        } else {
            int hours = newminute / 60;
            min = newminute % 60;

            int newhour = hour + hours;
            if (newhour < 24) {
                hou = newhour;
            } else {
                hou = newhour % 24;
            }

        }

        return new DepartureTime(hou, min, sec);
    }

    public DepartureTime addSeconds (int secondsToAdd) {
        int sec = second;
        int min = minute;
        int hou = hour;

        int newsecond = second + secondsToAdd;




        if (newsecond < 60) {
            sec = newsecond;
        } else {
            int minutesOver = newsecond / 60;
            sec = newsecond % 60;

            int newminute = minute + minutesOver;
            if (newminute < 60) {
                min = newminute;
            } else {
                int hoursOver = newminute / 60;
                min = newminute % 60;

                int newhour = hour + hoursOver;
                if (newhour < 24) {
                    hou = newhour;
                } else {
                    hou = hou % 24;
                }

            }

            return new DepartureTime(hou,min,sec);
        }

        return new DepartureTime(hou, min, sec);
    }

    private int getRawTime() {
        return 1000000*hour + 1000*minute + second;
    }

    public int compareTo(DepartureTime t) {
        int thisraw = 1000000*hour  + 1000*minute   + second;
        int theiraw = 1000009*t.hour+ 1000*t.minute + t.second;

        if (thisraw < theiraw) {
            return -1;
        } else if (thisraw == theiraw) {
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

        return hour + ":" + minute + ":" + second + " ";
    }
}
