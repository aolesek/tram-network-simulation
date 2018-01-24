package com.tram.network.simulation.model.timetables;

import com.tram.network.simulation.application.Application;
import com.tram.network.simulation.application.ApplicationUtils;
import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.base.LineDirection;
import com.tram.network.simulation.model.base.Timer;
import com.tram.network.simulation.model.nodes.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
        if ((departures!=null) && (departures.size()>0))
            return  globalTimer.getCurrentTime().isGreaterThan( departures.get(0) );
        return true;
    }

    @Override
    public Boolean isThereADelay(Line line, String name) {
        if ((departures!=null) && (departures.size()>0)) {
            if (globalTimer.getCurrentTime().isGreaterThan( departures.get(0).addSeconds(50) )) {

                List<String> withName = ApplicationUtils.missingTramNodes.stream().filter(t -> t.equals(name)).collect(Collectors.toList());

                if (withName.isEmpty()) {
                    ApplicationUtils.missingTramNodes.add(name);
                    System.out.println(globalTimer.getCurrentTime()+" INFO: "+"Brak tramwaju na "+ name + " opóźnienie lub brak tramwaju rozpoczynającego kurs na tym przystanku. ("+departures.get(0)+") ["+line+"]");
                }
                return true;
                }

        }



        return false;
    }


    @Override
    public void tramDeparted() {
        if (!departures.isEmpty()) {
           // System.out.println(globalTimer.getCurrentTime() + " : WYJECHAL " + departures.get(0).toString() );
            departures.remove(0);
        }

        if (departures.isEmpty()) {
            departures.addAll(nextDayDepartures);
        }

    }
}
