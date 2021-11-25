package buildings.dwelling.hotel;

import buildings.dwelling.DwellingFloor;
import buildings.dwelling.Flat;
import buildings.interfaces.Space;

import java.util.Arrays;

public class HotelFloor extends DwellingFloor{

    public final int DEFAULT_STARS = 1;
    private int stars;

    public HotelFloor(int spacesAmount) {
        super(spacesAmount);
        this.stars = DEFAULT_STARS;
    }

    public HotelFloor(Flat[] spaces) {
        super(spaces);
        this.stars = DEFAULT_STARS;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    @Override
    public String toString(){
        StringBuffer myBuffer = new StringBuffer();
        myBuffer.append("HotelFloor(" + getCount() + ", ");
        for(Space flat : flats){
            myBuffer.append("Flat( ").append(flat.getRoomsCount()).append("; ").append(flat.getSquare()).append(")");
        }
        myBuffer.append(")");
        return myBuffer.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelFloor)) return false;
        HotelFloor temp = (HotelFloor) o;
        if(this.getCount() != temp.getCount()) return  false;
        int i = 0;
        for(Space flat : flats)
            if (!flat.equals(temp.getSpace(i++))) return false;
        return temp.stars == this.stars;
    }

    @Override
    public int hashCode() {
        int res = 31 + getCount();
        res ^= 31 * this.stars;
        res ^= 31 * res + Arrays.hashCode(getArray());
        return res;
    }
}
