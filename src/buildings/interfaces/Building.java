package buildings.interfaces;

import java.util.Iterator;

public interface Building extends Iterable<Floor> {
    public int getCount();

    public int getCountSpaces();

    public double getSquare();

    public int getRoomsCount();

    public Floor[] getArray();

    public Floor getFloor(int index);

    public void setFloor(int index, Floor oneFloor);

    public Space getSpace(int index);

    public void setSpace(int index, Space oneSpace);

    public void addSpace(int index, Space oneSpace);

    public void deleteSpace(int index);

    public Space getBestSpace();

    public Space[] getSpaceArraySorted();

    public Object clone();

}
