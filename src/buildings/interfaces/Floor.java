package buildings.interfaces;

import java.util.Iterator;

public interface Floor extends Comparable<Floor>, Iterable<Space> {

    public int getCount();

    public double getSquare();

    public int getRoomsCount();

    public Space[] getArray();

    public Space getSpace(int index);

    public void setNewSpace(int index, Space space);

    public void addSpace(int index, Space space);

    public void deleteSpace(int index);

    public Space getBestSpace();

    public Object clone();
}
