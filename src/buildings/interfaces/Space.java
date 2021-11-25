package buildings.interfaces;

import java.util.Comparator;

public interface Space extends Comparable<Space>{

    public int getRoomsCount();

    public double getSquare();

    public void setRoomsCount(int roomsAmount);

    public void setSquare(double area);

    public Object clone();
}
