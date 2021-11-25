package buildings.factory;

import buildings.dwelling.Dwelling;
import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.*;

public class DwellingFactory implements BuildingFactory {

    public Space createSpace(double area){
        return new Flat(area);
    }

    public Space createSpace(double area, int roomsCount){
        return new Flat(area, roomsCount);
    }

    public Floor createFloor(int spacesCount){
        return new DwellingFloor(spacesCount);
    }

    public Floor createFloor(Space[] spaces){
        return new DwellingFloor(spaces);
    }

    public Building createBuilding(int floorsCount, int[] spacesCounts){
        return new Dwelling(spacesCounts, floorsCount);
    }

    public Building createBuilding(Floor[] floors){
        return new Dwelling(floors);
    }
}
