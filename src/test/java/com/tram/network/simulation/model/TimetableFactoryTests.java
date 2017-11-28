package com.tram.network.simulation.model;

import com.tram.network.simulation.model.timetables.Timetable;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import org.junit.Test;

public class TimetableFactoryTests {

    @Test
    public void stringInterpreterTest() {
        TimetableFactory factory = new TimetableFactory();
        factory.construct("3 15 30 45 \n4 20 40 50 \n14 5 10 15 20 25 30");
    }
}
