package com.tram.network.simulation.model.base;

import com.tram.network.simulation.data.CityMap;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.timetables.DepartureTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalTimer implements Timer {
    private List<Path> pathNetwork;
    private List<Node> nodeNetwork;
    private DepartureTime currentTime = new DepartureTime(0, 0);
    private Integer oneStepTime = 5;

    public List<TramStatus> getTrams() {
        List<TramStatus> trams = new ArrayList<>();

        for (Path path : pathNetwork) {
            Map<Integer, Cell> cells = path.getCells();
            List<Cell> cellList = new ArrayList<>(cells.values());

            for (Cell cell : cellList) {
                double progress = cell.getCoords().doubleValue() / ((double) path.getLength());

                if (cell.getState() == TramState.TRAM) {
                    trams.add(
                            new TramStatus(cell.getLine(), path.getId(), path.getCoordsByProgress(progress), progress)
                    );
                }
            }
        }

        for (Node n : nodeNetwork) {
            trams.addAll(n.getTrams());
        }

        return trams;
    }

    public void setNodeNetwork(List<Node> nnetwork) {
        this.nodeNetwork = nnetwork;
    }

    public void setPathNetwork(List<Path> pnetwork) {
        this.pathNetwork = pnetwork;
    }

    public void setCityMap(CityMap map) {
        this.pathNetwork = map.getPaths();
        this.nodeNetwork = map.getNodes();
    }

    public void nextState() {
        System.out.println(getCurrentTime());
        for (int i = 0; i < pathNetwork.size(); i++) {
            pathNetwork.set(i, pathNetwork.get(i).nextState());
        }
        currentTime = currentTime.addMinutes(oneStepTime);
    }

    public int getOneStepTime() {
        return oneStepTime;
    }

//    public void setOneStepTime(Integer oneStepTime) {
//        this.oneStepTime = oneStepTime;
//    }

    @Override
    public DepartureTime getCurrentTime() {
        return currentTime;
    }


}
