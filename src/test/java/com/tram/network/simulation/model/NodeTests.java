package com.tram.network.simulation.model;

import com.tram.network.simulation.model.base.*;
import com.tram.network.simulation.model.nodes.JunctionNode;
import com.tram.network.simulation.model.nodes.LoopNode;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.timetables.SimpleTimetable;
import com.tram.network.simulation.model.timetables.Timetable;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NodeTests {

    @Test
    public void oneNodeLoopTest() {
        Node node = new JunctionNode();

        ArrayList<Line> lines = new ArrayList<>();
        lines.add(new Line(0, LineDirection.NE));
        lines.add(new Line(2, LineDirection.NE));

        Path path = new Path(10,2,7,node,node, lines);
        path.setCellState(0,new Cell(TramState.TRAM,0,new Line(0, LineDirection.NE)));
        path.setCellState(2,new Cell(TramState.TRAM,2,new Line(0, LineDirection.NE)));

        Path original = new Path(10,2,7,node,node, lines);
        original.setCellState(0,new Cell(TramState.TRAM,0,new Line(0, LineDirection.NE)));
        original.setCellState(2,new Cell(TramState.TRAM,2,new Line(0, LineDirection.NE)));


        //After 18 steps (3 loops:) positions should be the same. Note that waiting at the junction takes always at least one step.
        for (int i=0; i < 18; i++) {
            path = path.nextState();
            System.out.println(path);
        }

        System.out.println("Result:");
        System.out.println(original);
        System.out.println(path);
        Assert.assertEquals(original.toString(),path.toString());
    }

    @Test
    public void loopNodeTests() {
        ArrayList<Line> linesNE = new ArrayList<>();
        linesNE.add(new Line(0, LineDirection.NE));

        ArrayList<Line> linesSW = new ArrayList<>();
        linesSW.add(new Line(0, LineDirection.SW));

        Map<Line,Timetable> timetables = new HashMap<>();
        timetables.put(new Line(0, LineDirection.NE), new SimpleTimetable());
        timetables.put(new Line(0, LineDirection.SW), new SimpleTimetable());

        Node loopA = new LoopNode(timetables);
        Node loopB = new LoopNode(timetables);

        Path pathNE = new Path(5,2,5,loopA,loopB, linesNE);
        pathNE.setCellState(0,new Cell(TramState.TRAM,0,new Line(0, LineDirection.NE)));
        pathNE.setCellState(2,new Cell(TramState.TRAM,2,new Line(0, LineDirection.NE)));

        Path pathSW = new Path(5,2,5,loopB,loopA, linesSW);

        String initialPathString = pathNE.toString();

        for (int i = 0; i < 7; i++) {
            System.out.println(pathNE);
            System.out.println(pathSW);
            System.out.println("---------------------- NEXT STATE ----------------------");
            pathNE = pathNE.nextState();
            pathSW = pathSW.nextState();
        }
        System.out.println(pathNE);
        System.out.println(pathSW);
        System.out.println("---------------------- LAST STATE ----------------------");

        Assert.assertEquals(initialPathString, pathNE.toString());
    }


}
