package buildings.dwelling;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;
import buildings.exceptions.SpaceIndexOutOfBoundsException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class DwellingFloor implements Floor, Serializable, Cloneable{

    protected Space[] flats;

    public DwellingFloor(int n) {  //конструктор принимает на вход количество квартир на этаже
        this.flats = new Space[n];
        for (int i = 0; i < n; i++)
            flats[i] = new Flat();
    }
    public DwellingFloor(Space[] apart){  //конструктор принимает массив квартир
        this.flats = apart;
    } //конструктор принимает на вход массив квартир
    public int getCount(){  //количество квартир на этаже
        return flats.length;
    } //КОЛИЧЕСТВО КВАРТИР
    public double getSquare() { //суммарная площадь квартир на этаже
        double res = 0;
        for (Space flat : flats)
            res += flat.getSquare();
        return res;
    }
    public int getRoomsCount(){  //суммарное количество комнат квартир на этаже
        int res = 0;
        for (Space flat : flats)
            res += flat.getRoomsCount();
        return res;
    }
    public Space[] getArray() { //возвращает массив квартир на этаже
        return flats;
    }
    public Space getSpace(int index){ //возвращает объект квартиры по индексу
        if ((index >= flats.length) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        return flats[index];
    }
    public void setNewSpace(int index, Space NewFlat){ //изменяет квартиру по номеру
        if ((index >= flats.length) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        NewFlat = new Flat(NewFlat.getSquare(),NewFlat.getRoomsCount()); //реализация работы с ссылками типа Space
        this.flats[index] = NewFlat;
    }
    public Space getBestSpace(){ //самая большая по площади квартира
        Space max = flats[0];
        for(Space flat : flats) {
            if (flat.getSquare() > max.getSquare())
                max = flat;
        }
        return max;
    }
    public void addSpace(int index, Space oneFlat) {// добавить квартиру
        if ((index >= flats.length) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Space[] newFlats = new Space[flats.length + 1];
        int i = 0;
        for (Space flat : flats) {
            newFlats[i] = flat;
        }
        oneFlat = new Flat(oneFlat.getSquare(),oneFlat.getRoomsCount());
        newFlats[index] = oneFlat;
        for ( i = index + 1; i < newFlats.length; i++)
            newFlats[i] = flats[i - 1];
        flats = newFlats;
    }
    public void deleteSpace(int index) { //удалить квартиру
        if ((index >= flats.length) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Space[] newFlats = new Space[flats.length - 1];
        int i = 0;
        for (Space flat : flats) {
            newFlats[i] = flat;
        }
        for (i = index + 1; i < flats.length; i++)
            newFlats[i - 1] = flats[i];
        flats = newFlats;
    }

    @Override
    public String toString(){
        StringBuffer myBuffer = new StringBuffer();
        myBuffer.append("DwellingFloor(" + getCount() + ", ");
        for(Space flat : flats){
            myBuffer.append("Flat( ").append(flat.getRoomsCount()).append("; ").append(flat.getSquare()).append(") ");
        }
        return myBuffer.toString();
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof DwellingFloor)) {
            return false;
        }
        DwellingFloor temp = (DwellingFloor) object;
        if(this.getCount() != temp.getCount())
            return  false;
        int i = 0;
        for(Space flat : flats)
            if (!flat.equals(temp.getSpace(i++))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int res = 1;
        res = 31 * res + Arrays.hashCode(flats);
        return res;
    }

    @Override
    public Object clone() {
        Object o;
        try {
            o = super.clone();
            ((DwellingFloor) o).flats = flats.clone();
            int i = 0;
            for(Space flat : flats){
                ((DwellingFloor) o).flats[i++] = (Space) flat.clone();
            }
            return o;
        } catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    @Override
    public int compareTo(Floor o) {
        if (this.getCount() < o.getCount()) return -1;
        if (this.getCount() > o.getCount()) return 1;
        return 0;
    }

    public static class FloorComparator implements Comparator<Floor> {
        @Override
        public int compare(Floor o1, Floor o2) {
            if (o1.getSquare() > o2.getSquare()) return -1;
            if (o1.getSquare() < o2.getSquare()) return 1;
            return 0;
        }
    }

   @Override
    public Iterator<Space> iterator() {
       return new getIterator();
   }

   private class getIterator implements Iterator<Space> {
       int index;

       @Override
       public boolean hasNext() {
           return index < flats.length;
       }

       @Override
       public Space next() {
           return flats[index++];
       }
   }



}
