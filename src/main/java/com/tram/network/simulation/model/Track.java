package com.tram.network.simulation.model;

public class Track {
    private ArrayList<TramState> cells;
    private Direction direction;
    private int length;

    CellIterator cellIterator = new CellIterator();

    public Track nextState()
    {
        return null;
    }

    public CellIterator cellIterator()
    {
       return null;
    }

    protected Boolean hasNextCoordinates(TramCoordinates coords)
    {
        return null;
    }

    protected TramCoordinates nextCoordinates(TramCoordinates coords)
    {
        return null;
    }

    protected TramCoordinates initialCoordinates()
    {
        return null;
    }


    private class CellIterator
    {
        private TramCoordinates currentCoordinates;

        CellIterator()
        {
            currentCoordinates = initialCoordinates();
        }

        public Boolean hasNext()
        {
            return hasNextCoordinates(currentCoordinates);
        }

        public Cell next()
        {
            currentCoordinates = nextCoordinates(currentCoordinates);
            Cell cell = new Cell();
            cell.state = cells.get(currentCoordinates);
            cell.coords = currentCoordinates;

            return cell;
        }

        public void setState(TramState state)
        {

        }

        public void resetCoords()
        {
            currentCoordinates = initialCoordinates();
        }
    }
}
