package com.tram.network.simulation.model;

import org.junit.Assert;
import org.junit.Test;

public class DepartureTimeTests {

    @Test
    public void DepartureTimeCreationAndComparationTests() {
        DepartureTime t1 = new DepartureTime(0,0);
        DepartureTime t2 = new DepartureTime(9,33);
        DepartureTime t3 = new DepartureTime(12,30);
        DepartureTime t4 = new DepartureTime(21,30);

        Assert.assertTrue("00:00 should be less than 9:33",t1.isLessThan(t2));
        Assert.assertTrue("9:33 should be less than 12:30",t2.isLessThan(t3));
        Assert.assertTrue("21:30 should be greater than 12:30",t4.isGreaterThan(t3));
    }
}
