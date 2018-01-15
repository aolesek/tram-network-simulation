package com.tram.network.simulation.application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tram.network.simulation.data.CityMap;
import com.tram.network.simulation.data.CityMapBuilder;
import com.tram.network.simulation.model.base.*;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import spark.ResponseTransformer;

import java.io.IOException;
import java.sql.Time;
import java.util.Map;

import static spark.Spark.*;

public class Application {
    public static void main(String[] args) {


        GlobalTimer timer = new GlobalTimer();
//
//        List<Path> paths = new ArrayList<>();
//        List<Node> stops = new ArrayList<>();

//        // Loop 1
//        TimetableFactory f1 = new TimetableFactory(timer);
//        Timetable l1t1 = f1.construct("00 00\n8 00\n16 00");
//        Timetable l1t2 = f1.construct("00 00\n8 00\n16 00");
//
//        Map<Line, Timetable> timetables = new HashMap<>();
//        timetables.put(new Line(1, LineDirection.NE), l1t1); //l1t1
//        timetables.put(new Line(2, LineDirection.NE), l1t2); //l1t2
//
//        Node l1 = new LoopNode(new Coords2D("50.064012574757506, 19.916539341205638"), "L1", timetables);
//        stops.add(l1);
//
//        // Junction 1
//
//        Node j1 = new JunctionNode("J1", new Coords2D("50.0640263491082, 19.916539341205638"));
//        stops.add(j1);
//
//        // Stop 1
//        TimetableFactory f2 = new TimetableFactory(timer);
//        Timetable s1t1 = f2.construct("5 00\n13 00\n21 00");
//        Timetable s1t2 = f2.construct("3 00\n11 00\n19 00");
//
//        Map<Line, Timetable> timetables2 = new HashMap<>();
//        timetables2.put(new Line(1, LineDirection.NE), s1t1);
//        timetables2.put(new Line(1, LineDirection.SW), s1t2);
//
//        Node s1 = new StopNode(new Coords2D("50.06277975435237, 19.91771951317219"), "S1", timetables2);
//        stops.add(s1);
//
//        // Stop 2
//        TimetableFactory f3 = new TimetableFactory(timer);
//        Timetable s2t1 = f3.construct("5 00\n13 00\n21 00");
//        Timetable s2t2 = f3.construct("3 00\n11 00\n19 00");
//
//        Map<Line, Timetable> timetables3 = new HashMap<>();
//        timetables3.put(new Line(1, LineDirection.NE), s2t1);
//        timetables3.put(new Line(1, LineDirection.SW), s2t2);
//
//        Node s2 = new StopNode(new Coords2D("50.062118563875416, 19.917547851795238"), "S2", timetables3);
//        stops.add(s2);
//
//        // Stop 3
//        TimetableFactory f4 = new TimetableFactory(timer);
//        Timetable s3t1 = f4.construct("4 00\n12 00\n20 00");
//        Timetable s3t2 = f4.construct("4 00\n12 00\n20 00");
//
//        Map<Line, Timetable> timetables4 = new HashMap<>();
//        timetables4.put(new Line(2, LineDirection.NE), s3t1);
//        timetables4.put(new Line(2, LineDirection.SW), s3t2);
//
//        Node s3 = new StopNode(new Coords2D("50.06204968934332, 19.916592985385936"), "S3", timetables4);
//        stops.add(s3);
//
//        // Junction 2
//
//        Node j2 = new JunctionNode("J2", new Coords2D("50.06087191954098, 19.91774097084431"));
//        stops.add(j2);
//
//        // Loop 2
//        TimetableFactory f5 = new TimetableFactory(timer);
//        Timetable l2t1 = f5.construct("00 00\n8 00\n16 00");
//        Timetable l2t2 = f5.construct("00 00\n8 00\n16 00");
//
//        Map<Line, Timetable> timetables5 = new HashMap<>();
//        timetables5.put(new Line(1, LineDirection.SW), l2t1);
//        timetables5.put(new Line(2, LineDirection.SW), l2t2);
//
//        Node l2 = new LoopNode(new Coords2D("50.060823706126, 19.91778388618855"), "L2", timetables5);
//        stops.add(l2);
//
//        // PATHS
//
//        //L1J1 Paths
//
//        GeoPath l1j1g = new GeoPath(
//                "50.0643432059077, 19.917832091447853; 50.064277778138916, 19.917579963800453; 50.06423645529159, 19.917327836153053; 50.064167583800284, 19.916458800432228; 50.064033284107836, 19.916539266702674"
//        );
//        ArrayList<Line> l1j1lines = new ArrayList<>();
//        l1j1lines.add(new Line(1, LineDirection.NE));
//        l1j1lines.add(new Line(2, LineDirection.NE));
//        paths.add(
//                new Path(100, 2, 7, l1, j1, l1j1lines, l1j1g)
//        );
//        ArrayList<Line> j1l1lines = new ArrayList<>();
//        j1l1lines.add(new Line(1, LineDirection.SW));
//        j1l1lines.add(new Line(2, LineDirection.SW));
//        paths.add(
//                new Path(100, 2, 7, j1, l1, j1l1lines, l1j1g.reverse())
//        );
//
//        //J1S1 PATHS
//        GeoPath j1s1g = new GeoPath(
//                "50.06403672769439, 19.916539266702674; 50.063637270005096, 19.91702742874338; 50.06334800546384, 19.917327836153053; 50.06321714712212, 19.91739757358744; 50.06309661938633, 19.91741903125956; 50.06291754904821, 19.917445853349705; 50.06290721804675, 19.917553141710304; 50.06287278135923, 19.917638972398777; 50.062821126281584, 19.917692616579075; 50.06277635850261, 19.917703345415134"
//        );
//        ArrayList<Line> j1s1lines = new ArrayList<>();
//        j1s1lines.add(new Line(1, LineDirection.NE));
//        paths.add(
//                new Path(80, 2, 7, j1, s1, j1s1lines, j1s1g)
//        );
//
//        ArrayList<Line> s1j1lines = new ArrayList<>();
//        s1j1lines.add(new Line(1, LineDirection.SW));
//        paths.add(
//                new Path(80, 2, 7, s1, j1, s1j1lines, j1s1g.reverse())
//        );
//
//        //J1S3 PATHS
//        GeoPath j1s3g = new GeoPath(
//                "50.064040171280695, 19.916533902284645; 50.06380256324548, 19.91656072437479; 50.063013971621004, 19.91657681762888; 50.06275225275815, 19.916539266702674; 50.06248020137397, 19.916485622522373; 50.0622288107487, 19.916485622522373; 50.06217371125755, 19.916490986940403; 50.06203596225279, 19.91657145321085"
//        );
//        ArrayList<Line> j1s3lines = new ArrayList<>();
//        j1s3lines.add(new Line(2, LineDirection.NE));
//        paths.add(
//                new Path(100, 2, 7, j1, s3, j1s3lines, j1s3g)
//        );
//
//        ArrayList<Line> s3j1lines = new ArrayList<>();
//        s3j1lines.add(new Line(2, LineDirection.SW));
//        paths.add(
//                new Path(100, 2, 7, s3, j1, s3j1lines, j1s3g.reverse())
//        );
//
//        //s1s2 PATHS
//        GeoPath s1s2g = new GeoPath(
//                "50.06277635850261, 19.917692616579075; 50.06271781595939, 19.91768725216105; 50.06266960439957, 19.91765506565287; 50.06262828016684, 19.917574599382423; 50.062614505414686, 19.917494133111976; 50.06242165846932, 19.91751559078409; 50.062104836804615, 19.917558506128334"
//        );
//        ArrayList<Line> s1s2lines = new ArrayList<>();
//        s1s2lines.add(new Line(1, LineDirection.NE));
//        paths.add(
//                new Path(40, 2, 7, s1, s2, s1s2lines, s1s2g)
//        );
//
//        ArrayList<Line> s2s1lines = new ArrayList<>();
//        s2s1lines.add(new Line(1, LineDirection.SW));
//        paths.add(
//                new Path(40, 2, 7, s2, s1, s2s1lines, s1s2g.reverse())
//        );
//
//        //s2j2 PATHS
//        GeoPath s2j2g = new GeoPath(
//                "50.06210139307936, 19.917553141710304; 50.06108203954093, 19.917719438669224; 50.06080309104802, 19.917773082849525"
//        );
//        ArrayList<Line> s2j2lines = new ArrayList<>();
//        s2j2lines.add(new Line(1, LineDirection.NE));
//        paths.add(
//                new Path(80, 2, 7, s2, j2, s2j2lines, s2j2g)
//        );
//
//        ArrayList<Line> j2s2lines = new ArrayList<>();
//        j2s2lines.add(new Line(1, LineDirection.SW));
//        paths.add(
//                new Path(80, 2, 7, j2, s2, j2s2lines, s2j2g.reverse())
//        );
//
//        //s3j2 PATHS
//        GeoPath s3j2g = new GeoPath(
//                "50.06203596225279, 19.91658754646494; 50.06189476911242, 19.916694834825538; 50.06165026293518, 19.916710928079628; 50.0614884060478, 19.916769936677955; 50.061350655075195, 19.91687722503855; 50.06119912854848, 19.917070344087623; 50.06106482054512, 19.91728492080881; 50.06087885499666, 19.917714074251194; 50.06080653486645, 19.917773082849525"
//        );
//        ArrayList<Line> s3j2lines = new ArrayList<>();
//        s3j2lines.add(new Line(2, LineDirection.NE));
//        paths.add(
//                new Path(100, 2, 7, s3, j2, s3j2lines, s3j2g)
//        );
//
//        ArrayList<Line> j2s3lines = new ArrayList<>();
//        j2s3lines.add(new Line(2, LineDirection.SW));
//        paths.add(
//                new Path(100, 2, 7, j2, s3, j2s3lines, s3j2g.reverse())
//        );
//
//        //j2l2 PATHS
//        GeoPath j2l2g = new GeoPath(
//                "50.06082031013774, 19.917773082849525; 50.06043460104614, 19.917832091447853; 50.060369167946135, 19.917869642374058; 50.06009710304709, 19.919586256143592"
//        );
//        ArrayList<Line> j2l2lines = new ArrayList<>();
//        j2l2lines.add(new Line(1, LineDirection.NE));
//        j2l2lines.add(new Line(2, LineDirection.NE));
//        paths.add(
//                new Path(100, 2, 7, j2, l2, j2l2lines, j2l2g)
//        );
//
//        ArrayList<Line> l2j2lines = new ArrayList<>();
//        l2j2lines.add(new Line(1, LineDirection.SW));
//        l2j2lines.add(new Line(2, LineDirection.SW));
//
//        paths.add(
//                new Path(100, 2, 7, l2, j2, l2j2lines, j2l2g.reverse())
//        );
//
//        //Simple network is now initialized.
//        //Adding initial trams
//
//        l1.addTramToQueue(
//                new Cell(TramState.TRAM, 0, new Line(1, LineDirection.NE))
//        );
//
//        l1.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
//        );
//        l1.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
//        );
//
//        l1.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(2, LineDirection.NE))
//        );
//        l1.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(2, LineDirection.NE))
//        );
//        l1.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(2, LineDirection.NE))
//        );
//
//        l2.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
//        );
//        l2.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
//        );
//        l2.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
//        );
//
//        l2.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(2, LineDirection.SW))
//        );
//        l2.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(2, LineDirection.SW))
//        );
//        l2.addTramToQueue(
//                new Cell(TramState.TRAM,0,new Line(2, LineDirection.SW))
//        );
//
////
////        ONE PATH EXAMPLE
////
////        CityMap<Line,Timetable> timetables = new HashMap<>();
////        timetables.put(new Line(1, LineDirection.NE), new SimpleTimetable()); //l1t1
////        timetables.put(new Line(2, LineDirection.NE), new SimpleTimetable() ); //l1t2
////
////        Node l1 = new LoopNode("L1",timetables);
////
////
////        CityMap<Line,Timetable> timetables2 = new HashMap<>();
////        timetables2.put(new Line(1, LineDirection.SW), new SimpleTimetable()); //l1t1
////        timetables2.put(new Line(2, LineDirection.SW), new SimpleTimetable() ); //l1t2
////
////        Node l2 = new LoopNode("L2",timetables2);
////
////
////        GeoPath l1j1g = new GeoPath(
////                "50.06439829692669, 19.91359957493842; 50.06123700424441, 19.91235502995551; 50.06046558966059, 19.917376125231385"
////        );
////        ArrayList<Line> l1j1lines = new ArrayList<>();
////        l1j1lines.add(new Line(1, LineDirection.NE));
////        l1j1lines.add(new Line(2, LineDirection.NE));
////        paths.add(
////                new Path("a-b",100,1,7,l1,l2, l1j1lines, l1j1g)
////        );
////        ArrayList<Line> j1l1lines = new ArrayList<>();
////        j1l1lines.add(new Line(1, LineDirection.SW));
////        j1l1lines.add(new Line(2, LineDirection.SW));
////        paths.add(
////                new Path("b-c",100,1,7,l2,l1, j1l1lines, l1j1g.reverse())
////        );
////
////        l1.addTramToQueue(
////                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
////        );
////
//        timer.setPathNetwork(paths);
//        timer.setNodeNetwork(stops);


        CityMap citymap = null;

        try {
            TimetableFactory timetableFactory = new TimetableFactory(timer);
            CityMapBuilder builder = new CityMapBuilder(timetableFactory);
            builder.readCSVMapFile("/tramnet.csv");
            citymap = new CityMap(
                    builder
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        timer.setCityMap(citymap);

        Map<String,Node> nodesMap = citymap.getNodesMap();
/*
        nodesMap.get("Darwina").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
        );

        nodesMap.get("Cienista").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
        );

        nodesMap.get("Poczta Główna").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
        );

        nodesMap.get("Plaza").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
        );

        nodesMap.get("Filharmonia").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.SW))
        );


        //sdf

        nodesMap.get("Salwator").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
        );

        nodesMap.get("Starowiślna").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
        );

        nodesMap.get("Fabryczna").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
        );

        nodesMap.get("Teatr Ludowy").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
        );
*/
        nodesMap.get("Wzgorza Krzeslawickie").addTramToQueue(
                new Cell(TramState.TRAM,0,new Line(1, LineDirection.NE))
        );



        Thread thread = new Thread(new TimerGovernor(timer));
        thread.start();

        //externalStaticFileLocation("/public");
        externalStaticFileLocation("src/main/resources/public/");
        //staticFileLocation("/public");

        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        final ResponseTransformer json = gson::toJson;

        port(4567);
        get("/trams", (req, res) -> timer.getTrams(), json);
        get("/time", (req, res) -> timer.getCurrentTime(), json);
        get("/step", (req, res) -> timer.getOneStepTime(), json);

    }
}