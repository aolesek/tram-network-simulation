package com.tram.network.simulation.model;

import com.tram.network.simulation.data.CityMapBuilder;
import com.tram.network.simulation.model.nodes.Node;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class CityMapTests {
    @Test
    public void cityMapTest() {
        CityMapBuilder builder = new CityMapBuilder();

        try {
            builder.readCSVMapFile("C:\\Users\\arek\\Desktop\\tramnet.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Node> nodes = builder.getNodes();

        System.out.println(nodes);
    }
}
