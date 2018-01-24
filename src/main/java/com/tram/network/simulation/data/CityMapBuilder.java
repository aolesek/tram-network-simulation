package com.tram.network.simulation.data;


import com.tram.network.simulation.application.Application;
import com.tram.network.simulation.application.ApplicationUtils;
import com.tram.network.simulation.model.base.*;
import com.tram.network.simulation.model.geo.Coords2D;
import com.tram.network.simulation.model.geo.GeoPath;
import com.tram.network.simulation.model.nodes.JunctionNode;
import com.tram.network.simulation.model.nodes.LoopNode;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.nodes.StopNode;
import com.tram.network.simulation.model.timetables.FileConverter;
import com.tram.network.simulation.model.timetables.SimpleTimetable;
import com.tram.network.simulation.model.timetables.Timetable;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityMapBuilder {
    private List<Node> nodes = new ArrayList<>();
    private List<Path> paths = new ArrayList<>();
    private Map<String, Node> nodesMap = new HashMap<>();
    private TimetableFactory timetableFactory;

    public CityMapBuilder(TimetableFactory timetableFactory) {
        this.timetableFactory = timetableFactory;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Path> getPaths() {
        return paths;
    }

    public Map<String, Node> getNodesMap() {
        return nodesMap;
    }

    public void readCSVMapFile(String filename) throws IOException {

        InputStream resourceStream =
                getClass().getResourceAsStream(filename);

        Reader in = new InputStreamReader(resourceStream, "UTF-8");

//        Reader in = new CharSequenceReader(new String(buffer));

//        targetReader.close();
//
//        Reader in = new InputStreamReader(inputStream);

        //Reader in = new FileReader(filename);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(';').withAllowMissingColumnNames().withQuote('"').withNullString("").parse(in);

        for (CSVRecord record : records) {
            String type = record.get(0);
            if (type.matches("stop|loop|junction")) {
                addNode(
                        type,
                        record.get(1),
                        record.get(2),
                        record.get(3),
                        record.get(4));
            }
        }
        in.close();
        resourceStream.close();

        resourceStream =
                getClass().getResourceAsStream(filename);

        in = new InputStreamReader(resourceStream, "UTF-8");

        records = CSVFormat.DEFAULT.withDelimiter(';').withAllowMissingColumnNames().withQuote('"').withNullString("").parse(in);

        for (CSVRecord record2 : records) {
            String type = record2.get(0);
            if (type.matches("path")) {
                addPath(
                        record2.get(1),
                        record2.get(2),
                        record2.get(3),
                        record2.get(4),
                        record2.get(5),
                        record2.get(6),
                        record2.get(7)
                );
            }
        }
    }

    public void addPath(String source, String destination, String defaultVelocityStr, String lines, String geoPath, String velocityStr, String travelTimeStr) {

        int defaultVelocity = Integer.parseInt(defaultVelocityStr);
        int velocity = Integer.parseInt(velocityStr);

        defaultVelocity *= 0.278; //to m/s
        velocity *= 0.278; // to m/s

        // attempt to calculate average velocity based on timetable and length
        if ((travelTimeStr != null) && (!travelTimeStr.isEmpty())) {
            int integerLength = new GeoPath(geoPath).getIntegerLength();
            int travelTime = (int) (60.0 * Double.parseDouble(travelTimeStr)); // travel time seconds

            double velocityRatio = ((double) velocity) /((double) defaultVelocity);

            defaultVelocity = (int) ((integerLength / travelTime) * ApplicationUtils.normalTrafficVelocityFactor);
            velocity =  (int) (velocityRatio*defaultVelocity*ApplicationUtils.highTrafficVelocityFactor);

        }





        if ((defaultVelocity < 1) || (velocity < 1)) {
            System.out.println(source + " to " + destination + " prędkośc za niska!");
            defaultVelocity = 1;
            velocity = 1;
        }


        defaultVelocity *= timetableFactory.getOneStepTime(); // m/s * one step time in seconds => meters by iteration
        velocity *= timetableFactory.getOneStepTime();

        Node sourceNode = nodesMap.get(StringUtils.stripAccents(source).toLowerCase());
        Node destinationNode = nodesMap.get(StringUtils.stripAccents(destination).toLowerCase());

        if ((sourceNode == null) || (destinationNode == null))
            System.out.println("Pusty węzeł w ścieżce " + source + " to " + destination);

        paths.add(
                new Path(sourceNode,
                        destinationNode,
                        defaultVelocity,
                        buildLines(lines, LineDirection.NE),
                        new GeoPath(geoPath),
                        velocity
                )
        );
        paths.add(
                new Path(destinationNode,
                        sourceNode,
                        defaultVelocity,
                        buildLines(lines, LineDirection.SW),
                        new GeoPath(geoPath).reverse(),
                        velocity
                )
        );
    }

    private List<Line> buildLines(String linesString, LineDirection direction) {
        List<Line> lines = new ArrayList<>();
        if (linesString == null) {
            return null;
        }

        if (linesString.contains(",")) {
            String[] singleLine = linesString.split(",");

            for (String line : singleLine) {
                line = line.replace(" ", "");

                if (line.contains("!")) {
                    line = line.replace("!","");
                    LineDirection newDirection = (direction == LineDirection.NE) ? LineDirection.SW : LineDirection.NE;
                    lines.add(new Line(Integer.parseInt(line), newDirection));

                } else {
                    lines.add(new Line(Integer.parseInt(line), direction));
                }


            }

        } else {
            linesString = linesString.replace(" ", "");

            if (linesString.contains("!"))
                direction = (direction == LineDirection.NE) ? LineDirection.SW : LineDirection.NE;

            linesString = linesString.replace("!","");
            lines.add(new Line(Integer.parseInt(linesString), direction));
        }

        return lines;
    }

    public void addNode(String type, String name, String coordinates, String lines, String numberOfTrams) {
        name = StringUtils.stripAccents(name).toLowerCase();

        if (type.equals("stop")) {
            Node node = new StopNode(
                    new Coords2D(coordinates),
                    name,
                    buildTimetable(name, lines)
            );
            addInitialTrams(node, numberOfTrams);
            nodesMap.put(name, node);
            nodes.add(node);

        } else if (type.equals("loop")) {
            Node node = new LoopNode(
                    new Coords2D(coordinates),
                    name,
                    buildTimetable(name, lines)
            );
            addInitialTrams(node, numberOfTrams);
            nodesMap.put(name, node);
            nodes.add(node);

        } else if (type.equals("junction")) {
            Node node = new JunctionNode(
                    name,
                    new Coords2D(coordinates)
            );
            nodesMap.put(name, node);
            nodes.add(node);
        }
    }

    private void addInitialTrams(Node node, String numberOfTrams) {
        if ((numberOfTrams == null) || (numberOfTrams.isEmpty()))
            return;

        String[] byLines = numberOfTrams.split(",");

        for (String line : byLines) {
            String[] tokens;
            String directionString = "";

            if (line.contains("NE")) {
                tokens = line.split("NE");
                directionString = "NE";

            } else if (line.contains("SW")) {
                tokens = line.split("SW");
                directionString = "SW";
            } else {
                System.out.println("Błąd przy parsowaniu liczby tramwajów rozpoczynających na przystanku");
                return;
            }


            int linenumber = Integer.parseInt(tokens[0]);
            int quantity = Integer.parseInt(tokens[1]);
            LineDirection direction = (directionString.equals("NE")) ? LineDirection.NE : LineDirection.SW;

            for (int i = 0; i < quantity; i++) {
                Cell tram = new Cell(ApplicationUtils.getId(),TramState.TRAM, 0, new Line(linenumber, direction));
                node.addTramToQueue(tram);
            }
        }
    }

    public Map<Line, Timetable> buildTimetable(String lineName, String rawLines) {

        Map<Line, Timetable> timetables = new HashMap<>();

        if ((rawLines != null) && (!rawLines.isEmpty())) {
            String[] lines = rawLines.split(",");
            for (String line : lines) {
                line = line.replace(" ", "");

                FileConverter fileConverter = new FileConverter();
                String stringTimetableNE = fileConverter.fileToString(line + "_" + "ne" + "-" + lineName.replace(" ", "_").toLowerCase());
                fileConverter = new FileConverter();
                String stringTimetableSW = fileConverter.fileToString(line + "_" + "sw" + "-" + lineName.replace(" ", "_").toLowerCase());

                if (ApplicationUtils.isInverted(Integer.parseInt(line))) {
                    String temporaryTimetable = new String(stringTimetableNE);
                    stringTimetableNE = stringTimetableSW;
                    stringTimetableSW = temporaryTimetable;

                }

                if (stringTimetableNE.isEmpty()) {
                    timetables.put(new Line(Integer.parseInt(line), LineDirection.NE), new SimpleTimetable());
                } else {
                    timetables.put(new Line(Integer.parseInt(line), LineDirection.NE), timetableFactory.construct(stringTimetableNE));
                }

                if (stringTimetableSW.isEmpty()) {
                    timetables.put(new Line(Integer.parseInt(line), LineDirection.SW), new SimpleTimetable());

                } else {
                    timetables.put(new Line(Integer.parseInt(line), LineDirection.SW), timetableFactory.construct(stringTimetableSW));
                }
            }
            return timetables;
        } else {
            System.out.println("Jedna z liniijek  w pliku mapy określająca jakie linie jeżdżą po danym przystanku jest pusta!");
            return null;
        }

    }
}