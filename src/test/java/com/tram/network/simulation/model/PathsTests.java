package com.tram.network.simulation.model;

import com.tram.network.simulation.model.base.*;
import com.tram.network.simulation.model.nodes.BasicNode;
import com.tram.network.simulation.model.nodes.Node;
import org.junit.Assert;
import org.junit.Test;

public class PathsTests {

    @Test
    public void pathTest() {
        Node node = new BasicNode();

        Path pathBefore = new Path(10,3,7,node,node, null);
        pathBefore.setCellState(0, new Cell(TramState.TRAM,0,new Line(0, LineDirection.NE)));
        pathBefore = pathBefore.nextState();

        Path pathAfter = new Path(10,3,7,node,node, null);
        pathAfter.setCellState(3, new Cell(TramState.TRAM,3,new Line(0, LineDirection.NE)));

        Path pathOtherVelocity = new Path(10,5,7,node,node, null);
        pathOtherVelocity.setCellState(3, new Cell(TramState.TRAM,3,new Line(0, LineDirection.NE)));
        pathOtherVelocity = pathOtherVelocity.nextState();

        Path pathAfterOtherVelocity = new Path(10,5,7,node,node, null);
        pathAfterOtherVelocity.setCellState(8, new Cell(TramState.TRAM,8,new Line(0, LineDirection.NE)));

        Assert.assertEquals(pathAfter.toString(), pathBefore.toString());
        Assert.assertEquals(pathAfterOtherVelocity.toString(), pathOtherVelocity.toString());

    }
}
