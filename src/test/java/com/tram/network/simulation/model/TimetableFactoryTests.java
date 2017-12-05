package com.tram.network.simulation.model;

import com.tram.network.simulation.model.timetables.Timetable;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import org.junit.Test;

public class TimetableFactoryTests {

    @Test
    public void stringInterpreterTest() {
        TimetableFactory factory = new TimetableFactory(null);
        factory.construct("00 0 15 30 45 \n4 00 20 40 50 \n14 5 10 15 20 25 30");
        factory.construct("00 00\n8 00\n16 00");
    }
}
