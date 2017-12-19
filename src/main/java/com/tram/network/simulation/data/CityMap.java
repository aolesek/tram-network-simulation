package com.tram.network.simulation.data;

import com.tram.network.simulation.model.base.Path;
import com.tram.network.simulation.model.nodes.Node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityMap {
    public List<Path> getPaths() {
        return paths;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    private List<Path> paths;
    private List<Node> nodes;

    public Map<String, Node> getNodesMap() {
        return nodesMap;
    }

    private Map<String,Node> nodesMap;


    public CityMap(CityMapBuilder builder) {
        this.paths = builder.getPaths();
        this.nodes = builder.getNodes();
        this.nodesMap = builder.getNodesMap();
    }
}
