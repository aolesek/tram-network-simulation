package com.tram.network.simulation.model.base;

import com.tram.network.simulation.data.CityMap;
import com.tram.network.simulation.model.nodes.Node;
import com.tram.network.simulation.model.timetables.DepartureTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GlobalTimer implements Timer {
    private List<Path> pathNetwork;
    private List<Node> nodeNetwork;
    private DepartureTime currentTime = new DepartureTime(0, 0);
    private Integer oneStepTime = 5;
    private Boolean[] randomEventOnTram;
    private int[] durationOfWaiting;
    private static final int chance = 50000;
    Random generator = new Random();


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
        initializeRandomEvents();
    }

    public void nextState() {
        randomEvent();
        System.out.println(getCurrentTime());
        for (int i = 0; i < pathNetwork.size(); i++) {
            pathNetwork.set(i, pathNetwork.get(i).nextState(randomEventOnTram[i] == true, getCurrentTime().toString()));
        }
        currentTime = currentTime.addSeconds(oneStepTime);
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

    private void initializeRandomEvents(){
        randomEventOnTram = new Boolean[pathNetwork.size()];
        durationOfWaiting = new int[pathNetwork.size()];
        for(int i = 0; i<pathNetwork.size(); i++){
            randomEventOnTram[i]=false;
            durationOfWaiting[i]=0;
        }
    }

    private void randomEvent(){
        for (int i = 0; i < pathNetwork.size(); i++){
            if(randomEventOnTram[i] == false) {
                if(generator.nextInt(chance) == 1){
                    randomEventOnTram[i]=true;
                    durationOfWaiting[i]=4+generator.nextInt(8);
                    System.out.println("Zdarzenie losowe na tramwaju o id ["+i+"].");
                }
            }
            else{
                durationOfWaiting[i]--;
                if(durationOfWaiting[i] == 0){
                    randomEventOnTram[i]=false;
                }
            }
        }
    }


    public void setCurrentTime(DepartureTime currentTime) {
        this.currentTime = currentTime;
    }
}
