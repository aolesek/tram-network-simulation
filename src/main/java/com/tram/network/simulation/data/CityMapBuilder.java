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
import com.tram.network.simulation.model.timetables.FileConverter;
import com.tram.network.simulation.model.timetables.SimpleTimetable;
import com.tram.network.simulation.model.timetables.Timetable;
import com.tram.network.simulation.model.timetables.TimetableFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;


import java.io.*;
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

    private TimetableFactory timetableFactory;

    public CityMapBuilder(TimetableFactory timetableFactory){
        this.timetableFactory=timetableFactory;
    }

    public void readCSVMapFile(String filename) throws IOException {

        InputStream resourceStream  =
                getClass().getResourceAsStream(filename);

        Reader in = new InputStreamReader(resourceStream, "UTF-8");

//        Reader in = new CharSequenceReader(new String(buffer));

//        targetReader.close();
//
//        Reader in = new InputStreamReader(inputStream);

        //Reader in = new FileReader(filename);
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withDelimiter(',').withAllowMissingColumnNames().withQuote('"').withNullString("").parse(in);

        for (CSVRecord record : records) {
            String type = record.get(0);
            if (type.matches("stop|loop|junction")) {
                addNode(
                        type,
                        record.get(1),
                        record.get(2),
                        record.get(3));
            }
        }
        in.close();
        resourceStream.close();

        resourceStream  =
                getClass().getResourceAsStream(filename);

        in = new InputStreamReader(resourceStream, "UTF-8");

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

    public void addNode(String type, String name, String coordinates, String lines) {

        if (type.equals("stop")) {
            Node node = new StopNode(
                                    new Coords2D(coordinates),
                                    name,
                                    buildTimetable(name, lines)
                        );

            nodesMap.put(name, node);
            nodes.add(node);

        } else if (type.equals("loop")) {
            Node node =  new LoopNode(
                                    new Coords2D(coordinates),
                                    name,
                                    buildTimetable(name, lines)
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

    public Map<Line,Timetable> buildTimetable(String lineName, String rawLines) {

        Map<Line, Timetable> timetables = new HashMap<>();
        FileConverter fileConverter = new FileConverter();
        if ((rawLines != null) && (!rawLines.isEmpty())) {
            String [] lines = rawLines.split(",");
            for (String line : lines) {
                String stringTimetableNE = fileConverter.fileToString(line +"_"+ "ne"+"-"+lineName.replace(" ","_").toLowerCase() );
                String stringTimetableSW = fileConverter.fileToString(line +"_"+ "sw"+"-"+lineName.replace(" ","_").toLowerCase() );

                if (stringTimetableNE.isEmpty()) {
                    timetables.put(new Line(1, LineDirection.NE), new SimpleTimetable());
                } else {
                    timetables.put(new Line(1, LineDirection.NE), timetableFactory.construct(stringTimetableNE));
                }

                if (stringTimetableSW.isEmpty()) {
                    timetables.put(new Line(1, LineDirection.SW), new SimpleTimetable());

                } else {
                    timetables.put(new Line(1, LineDirection.SW), timetableFactory.construct(stringTimetableSW));
                }
            }
            return timetables;
        } else {
            System.out.println("Jedna z liniijek  w pliku mapy określająca jakie linie jeżdżą po danym przystanku jest pusta!");
            return null;
        }

    }
}
