package com.tram.network.simulation.application;

import com.tram.network.simulation.model.*;

import java.util.ArrayList;

public class Application {
    public static void main(String [] args)
    {
        Node node = new JunctionNode();

        ArrayList<Line> lines = new ArrayList<>();
        lines.add(new Line(0,LineDirection.NE));

        Path path = new Path(10,3,7,node,node, lines);
        path.setCellState(0,new Cell(TramState.TRAM,0,new Line(0, LineDirection.NE)));

        for (int i=0; i < 30; i++) {
            System.out.println(path);
            path = path.nextState();
        }
    }
}
