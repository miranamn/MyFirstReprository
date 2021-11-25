package buildings;

import buildings.exceptions.InexchangeableFloorsException;
import buildings.exceptions.InexchangeableSpacesException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

public class PlacementExchanger {
    public static boolean ChangedIt(Space one, Space two){ //Можно ли обменяться помещениями
        return( one.getSquare() == two.getSquare() && one.getRoomsCount() == two.getRoomsCount());
    }
    public static boolean ChangedIt(Floor one, Floor two){ //можно ли обменяться этажами
        return( one.getSquare() == two.getSquare() && one.getRoomsCount() == two.getRoomsCount());
    }
    //обмен помещениями
    public static void exchangeFloorRooms(Floor floor1, int index1, Floor floor2, int index2) throws InexchangeableSpacesException {
        if (index1 >= floor1.getCount() || index1 < 0 || index2 >= floor2.getCount())
            throw new SpaceIndexOutOfBoundsException();
        else if (!(ChangedIt(floor1.getSpace(index1), floor2.getSpace(index2))))
            throw new InexchangeableSpacesException();
        else {
            Space temp = floor1.getSpace(index1);
            floor1.setNewSpace(index1, floor2.getSpace(index2));
            floor2.setNewSpace(index2, temp);
        }
    }
    //обмен этажами
    public static void exchangeBuildingFloors(Building building1, int index1, Building building2, int index2) throws InexchangeableFloorsException {
        if (index1 >= building1.getCount() || index1 < 0 || index2 >= building2.getCount())
            throw new SpaceIndexOutOfBoundsException();
        else if (!(ChangedIt(building1.getFloor(index1), building2.getFloor(index2))))
            throw new InexchangeableFloorsException();
        else {
            Floor temp = building1.getFloor(index1);
            building1.setFloor(index1, building2.getFloor(index2));
            building2.setFloor(index2, temp);
        }
    }
}
