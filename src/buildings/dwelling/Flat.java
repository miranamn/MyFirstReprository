package buildings.dwelling;
import buildings.exceptions.InvalidRoomsCountException;
import buildings.exceptions.InvalidSpaceAreaException;
import buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Comparator;

public class Flat implements Space, Serializable, Cloneable, Comparable<Space> {
    private double square;
    private int roomsCount;

    final int FinalCount = 2;
    final int FinalSquare = 50;

    public Flat() { //конструктор по умолчанию
        square = FinalSquare;
        roomsCount = FinalCount;
    }

    public Flat(double square, int flatCount) {//создает квартиру с параметрами
        if (square <= 0) throw new InvalidSpaceAreaException();
        if (flatCount <= 0) throw new InvalidRoomsCountException();
        this.square = square;
        this.roomsCount = flatCount;
    }

    public Flat(double square) {//получает площадь и создает квартиру из 2 комнат
        if (square <= 0) throw new InvalidSpaceAreaException();
        this.square = square;
        roomsCount = FinalCount;
    }

    public double getSquare() {
        return square;
    }

    public int getRoomsCount() {
        return roomsCount;
    }

    public void setRoomsCount(int flatCount) {
        if (flatCount <= 0) throw new InvalidRoomsCountException();
        this.roomsCount = flatCount;
    }

    public void setSquare(double square) {
        if (square <= 0) throw new InvalidSpaceAreaException();
        this.square = square;
    }

    @Override
    public String toString() {
        return "Flat(" + roomsCount + "; " + square + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Flat)) return false;
        Flat temp = (Flat) object;
        return (this.roomsCount == temp.roomsCount) && (this.square == temp.square);
    }

    @Override
    public int hashCode() {
        int res = 1;
        long temp = Double.doubleToLongBits(square);
        res = 31 * res + (int) (temp ^ (temp >>> 32));
        res = 29 * res + roomsCount;
        return res;
    }

    @Override
    public Object clone() {
        Object o;
        try {
            o = super.clone();
            return o;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public int compareTo(Space o) {
        if (this.square < o.getSquare()) return -1;
        if (this.square > o.getSquare()) return 1;
        return 0;
    }

    public static class SpaceComparator implements Comparator<Space> {
        @Override
        public int compare(Space o1, Space o2) {
            if (o1.getRoomsCount() > o2.getRoomsCount()) return -1;
            if (o1.getRoomsCount() < o2.getRoomsCount()) return 1;
            return 0;
        }
    }
}
