package buildings.dwelling.hotel;

import buildings.dwelling.Dwelling;
import buildings.interfaces.*;
import java.util.Arrays;

public class Hotel extends Dwelling {

    public Hotel(int[] FlatCount, int FloorCount) {
        super(FlatCount, FloorCount);
    }
    public Hotel(Floor[] dwell){
        super(dwell);
    }

    public int getStars(){
        int res = 0;
        for(Floor floor : floors) {
            if (floor instanceof HotelFloor) {
                if (res < ((HotelFloor) floor).getStars()) {
                    res = ((HotelFloor) floor).getStars();
                }
            }
        }
        return res;
    }

    @Override
    public Space getBestSpace() {
        Space bestFlat = null;
        double[] mas = {0.25, 0.5, 1, 1.25, 1.5};
        double res = 0;
        double tempRes;
        for(Floor floor : floors) {
            if (floor instanceof HotelFloor) {
                for (Space flat : floor.getArray()) {
                    tempRes = mas[((HotelFloor)floor).getStars() - 1] * flat.getSquare();
                    if (res < tempRes) {
                        res = tempRes;
                        bestFlat = flat;
                    }
                }
            }
        }
        return bestFlat;
    }

    @Override
    public String toString(){
        StringBuffer myBuffer = new StringBuffer();
        myBuffer.append("Hotel(" + getCount() + ", ");
        for(Floor floor : floors) {
            myBuffer.append("HotelFloor(" + floor.getCount() + ", ");
            for (Space flat : floor.getArray()) {
                myBuffer.append("Flat( ").append(flat.getRoomsCount()).append("; ").append(flat.getSquare()).append(") ");
            }
            myBuffer.append(")");
        }
        myBuffer.append(")");
        return myBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel temp = (Hotel) object;
        if(this.getCount() != temp.getCount())
            return false;
        int i = 0;
        for(Floor floor : floors)
            if (!floor.equals(temp.getFloor(i++))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int res = 31 + getCount();
        res ^= 31 * Arrays.hashCode(getArray());
        return res;
    }

}
