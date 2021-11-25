package buildings.office;
import buildings.exceptions.InvalidRoomsCountException;
import buildings.exceptions.InvalidSpaceAreaException;
import buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Comparator;

public class Office implements Space, Serializable, Cloneable, Comparable <Space> {
    private double square;
    private int roomsCount;

    final int FinalCount = 1;
    final int FinalSquare = 250;

    public Office(){ //конструктор по умолчанию
        square = FinalSquare;
        roomsCount = FinalCount;
    }
    public Office(double square, int roomsCount){//создает квартиру с параметрами
        if(square <= 0) throw new InvalidSpaceAreaException();
        if(roomsCount <= 0) throw new InvalidRoomsCountException();
        this.square = square;
        this.roomsCount = roomsCount;
    }
    public Office(double square){//получает площадь и создает квартиру из 2 комнат
        if(square <= 0) throw new InvalidSpaceAreaException();
        this.square = square;
        roomsCount = FinalCount;
    }
    public double getSquare() { return square; }
    public int getRoomsCount() { return roomsCount; }
    public void setRoomsCount(int roomsCount) {
        if(roomsCount <= 0) throw new InvalidRoomsCountException();
        this.roomsCount = roomsCount; }
    public void setSquare(double square) {
        if(square <= 0) throw new InvalidSpaceAreaException();
        this.square = square; }

    @Override
    public String toString(){
        return "Office(" + roomsCount + "; " + square + ")";
    }

    @Override
    public boolean equals(Object object){
        if (object == this) return true;
        if (!(object instanceof Office)) return false;
        Office temp = (Office) object;
        return (this.roomsCount == temp.roomsCount) && (this.square == temp.square);
    }

    @Override
    public int hashCode(){
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
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
        return o;
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
