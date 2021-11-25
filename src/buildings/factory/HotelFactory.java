package buildings.factory;

import buildings.dwelling.Flat;
import buildings.dwelling.hotel.Hotel;
import buildings.dwelling.hotel.HotelFloor;
import buildings.interfaces.*;

public class HotelFactory implements BuildingFactory {

    public Space createSpace(double area){
        return new Flat(area);
    }

    public Space createSpace(double area, int roomsCount){
        return new Flat(area, roomsCount);
    }

    public Floor createFloor(int spacesCount){
        return new HotelFloor(spacesCount);
    }

    public Floor createFloor(Space[] spaces){
        return new HotelFloor((Flat[]) spaces);
    }

    public Building createBuilding(int floorsCount, int[] spacesCounts){
        return  new Hotel(spacesCounts, floorsCount);
    }

    public Building createBuilding(Floor[] floors){
        return new Hotel(floors);
    }

}
