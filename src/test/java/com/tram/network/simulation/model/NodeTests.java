package com.tram.network.simulation.model;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

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
        }

        Assert.assertEquals(original.toString(),path.toString());
    }

    @Test
    public void loopNodeTests() {
        Node loopA = new LoopNode();
        Node loopB = new LoopNode();

        ArrayList<Line> linesNE = new ArrayList<>();
        linesNE.add(new Line(0, LineDirection.NE));

        ArrayList<Line> linesSW = new ArrayList<>();
        linesSW.add(new Line(0, LineDirection.SW));

        Path pathNE = new Path(5,2,5,loopA,loopB, linesNE);
        pathNE.setCellState(0,new Cell(TramState.TRAM,0,new Line(0, LineDirection.NE)));
        pathNE.setCellState(2,new Cell(TramState.TRAM,2,new Line(0, LineDirection.NE)));

        Path pathSW = new Path(5,2,5,loopB,loopA, linesSW);

        for (int i = 0; i < 10; i++) {
            System.out.println(pathNE);
            System.out.println(pathSW);
            System.out.println("---------------------- NEXT STATE ----------------------");
            pathNE = pathNE.nextState();
            pathSW = pathSW.nextState();
        }
    }


}
