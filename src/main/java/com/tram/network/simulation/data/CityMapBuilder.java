package com.tram.network.simulation.data;

import com.tram.network.simulation.model.base.Line;
import com.tram.network.simulation.model.base.LineDirection;
import com.tram.network.simulation.model.base.Path;
import com.tram.network.simulation.model.geo.Coords2D;
import com.tram.network.simulation.model.geo.GeoPath;
import com.tram.network.simulation.model.nodes.JunctionNode;
import com.tram.network.simulation.model.nodes.LoopNode;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.nodes.StopNode;
import com.tram.network.simulation.model.timetables.SimpleTimetable;
import com.tram.network.simulation.model.timetables.Timetable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityMapBuilder {
    public List<Node> getNodes() {
        return nodes;
    }

    public List<Path> getPaths() {
        return paths;
    }

    private List<Node> nodes = new ArrayList<>();
    private List<Path> paths = new ArrayList<>();

    public Map<String, Node> getNodesMap() {
        return nodesMap;
    }

    private Map<String,Node> nodesMap = new HashMap<>();

    public void readCSVMapFile(String filename) throws IOException {

        Reader in = new FileReader(filename);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(',').withAllowMissingColumnNames().withQuote('"').withNullString("").parse(in);

        for (CSVRecord record : records) {
            String type = record.get(0);
            if (type.matches("stop|loop|junction")) {
                addNode(
                        type,
                        record.get(1),
                        record.get(2));
            }
        }
        in.close();

        in = new FileReader(filename);
        records = CSVFormat.DEFAULT.withDelimiter(',').withAllowMissingColumnNames().withQuote('"').withNullString("").parse(in);

        for (CSVRecord record2 : records) {
            String type = record2.get(0);
            if (type.matches("path")) {
                addPath(
                        record2.get(1),
                        record2.get(2),
                        record2.get(3),
                        record2.get(4),
                        record2.get(5)
                );
            }
        }
    }

    public void addPath(String source, String destination, String velocity, String lines, String geoPath) {
        paths.add(
                new Path(
                        nodesMap.get(source),
                        nodesMap.get(destination),
                        Integer.parseInt(velocity),
                        buildLines(lines,LineDirection.NE),
                        new GeoPath(geoPath)
                )
        );
        paths.add(
                new Path(
                        nodesMap.get(destination),
                        nodesMap.get(source),
                        Integer.parseInt(velocity),
                        buildLines(lines,LineDirection.SW),
                        new GeoPath(geoPath).reverse()
                )
        );
    }

    private List<Line> buildLines(String linesString, LineDirection direction) {
        List<Line> lines = new ArrayList<>();

        if (linesString.contains(",")) {
            String [] singleLine = linesString.split(",");

            for (String line : singleLine) {
                lines.add(new Line(Integer.parseInt(line),direction));
            }

        } else {
            lines.add(new Line(Integer.parseInt(linesString), direction));
        }

        return lines;
    }

    public void addNode(String type, String name, String coordinates) {
        if (type.equals("stop")) {
            Node node = new StopNode(
                                    new Coords2D(coordinates),
                                    name,
                                    buildTimetable()
                        );

            nodesMap.put(name, node);
            nodes.add(node);

        } else if (type.equals("loop")) {
            Node node =  new LoopNode(
                                    new Coords2D(coordinates),
                                    name,
                                    buildTimetable()
                        );
            nodesMap.put(name,node);
            nodes.add(node);

        } else if (type.equals("junction")) {
            Node node = new JunctionNode(
                                    name,
                                    new Coords2D(coordinates)
                        );
            nodesMap.put(name,node);
            nodes.add(node);
        }
    }

    public Map<Line,Timetable> buildTimetable() {
        Map<Line,Timetable> timetables = new HashMap<>();
        timetables.put(new Line(1, LineDirection.NE), new SimpleTimetable());
        timetables.put(new Line(1, LineDirection.SW), new SimpleTimetable());

        return timetables;
    }
}
