package buildings.dwelling;
import buildings.exceptions.FloorIndexOutOfBoundsException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class Dwelling implements Building, Serializable, Cloneable {
    protected Floor[] floors;

    public Dwelling(int[] FlatCount, int FloorCount){
        this.floors = new Floor[FloorCount];
        for (int i = 0; i < FloorCount; i++)
            this.floors[i] = new DwellingFloor(FlatCount[i]);
    }
    public Dwelling(Floor[] dwell){
        this.floors = dwell;
    }
    public int getCount(){ //количество этажей в здании
        return floors.length;
    } //количество этажей
    public int getCountSpaces(){ //количество квартир в здании
        int sum = 0;
        for (Floor floor : floors)
            sum += floor.getCount();
        return sum;
    }
    public double getSquare(){ //площадь квартир в здании
        double sum = 0;
        for (Floor floor : floors)
            sum += floor.getSquare();
        return sum;
    }
    public int getRoomsCount(){ //количество комнат в здании
        int sum = 0;
        for (Floor floor : floors)
            sum += floor.getRoomsCount();
        return sum;
    }
    public Floor[] getArray() { //возвращает массив этажей
        return floors;
    }
    public Floor getFloor(int index) { //возвращает объект этажа по индексу
        if ((index >= floors.length) || (index < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        return floors[index];
    }
    public void setFloor(int index, Floor newFloor){ //изменяет этаж по номеру
        if ((index >= floors.length) || (index < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        //реализация интерфейса
        Space[] temp = new Space[newFloor.getCount()];
        for(int i = 0; i < newFloor.getCount(); i++){
            Space a = new Flat(newFloor.getSpace(i).getSquare(), newFloor.getSpace(i).getRoomsCount());
            temp[i] =  a;
        }
        newFloor = new DwellingFloor(temp);
        this.floors[index] = newFloor;
    }
    public Space getSpace(int index){//метод получения объекта квартиры по ее номеру в доме.
        if ((index >= getCountSpaces()) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        int sum = 0;
        for (Floor floor : floors) {
            int flatsAmount = floor.getCount();
            sum += flatsAmount;
            if (sum >= index)
                return floor.getSpace(flatsAmount - (sum - index));
        }
        return null;
    }
    public void setSpace(int index, Space thisFlat) {
        if ((index >= getCountSpaces()) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        int sum = 0;
        int i = 0;
        Floor ourFloor = floors[i];
        while(sum <= index){
            ourFloor = floors[i];
            sum += floors[i].getCount();
            i++;
        }
        ourFloor.setNewSpace(ourFloor.getCount() - (sum - index), thisFlat);
    }
    public void addSpace(int index, Space newFlat) {
        if ((index >= getCountSpaces()) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        int sum = 0;
        int i = 0;
        Floor ourFloor = floors[i];
        while(sum <= index){
            ourFloor = floors[i];
            sum += floors[i].getCount();
            i++;
        }
        ourFloor.addSpace(ourFloor.getCount() - (sum - index), newFlat);
    }
    public void deleteSpace(int index) {
        if ((index >= getCountSpaces()) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        int sum = 0;
        int i = 0;
        Floor ourFloor = floors[i];
        while(sum <= index){
            ourFloor = floors[i];
            sum += floors[i].getCount();
            i++;
        }
        ourFloor.deleteSpace(ourFloor.getCount() - (sum - index));
    }
    public Space getBestSpace() { //квартира с максимальной площадью
        Space bestFlat = floors[0].getBestSpace();
        for (int i = 1; i < floors.length; i++) {
            Space tempFlat = floors[i].getBestSpace();
            if (tempFlat.getSquare() > bestFlat.getSquare()) {
                bestFlat = tempFlat;
            }
        }
        return bestFlat;
    }
    public void sort(Space[] newFlats) { //сортировочка пузырьком))
        int n = newFlats.length;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++){
                if (newFlats[j].getSquare() < newFlats[j + 1].getSquare()) {
                    Space temp =  newFlats[j];
                    newFlats[j] = newFlats[j + 1];
                    newFlats[j + 1] = temp;
                }
            }
        }
    }
    public Space[] getSpaceArraySorted() { //убывание площадей
        int n = getCountSpaces();
        Space[] newFlats = new Space[n];
        for(Floor floor : floors){
            for (Space flat : floor.getArray()) {
                newFlats[n - 1] = flat;
                n--;
            }
        }
        sort(newFlats);
        return newFlats;
    }

    @Override
    public String toString(){
        StringBuffer myBuffer = new StringBuffer();
        myBuffer.append("Dwelling(" + getCount() + ", ");
        for(Floor floor : floors) {
            myBuffer.append("DwellingFloor(" + floor.getCount() + ", ");
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
        if (!(object instanceof Dwelling)) {
            return false;
        }
        Dwelling temp = (Dwelling) object;
        if(this.getCount() != temp.getCount())
            return false;
        int i = 0;
        for(Floor floor : floors)
            if (!floor.equals(temp.getFloor(i++))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + Arrays.hashCode(floors);
        return result;
    }

    @Override
    public Object clone(){
        int[] tempFloor = new int[this.getCount()];
        for(int i = 0; i < tempFloor.length; i++){
            tempFloor[i] = this.getFloor(i).getCount();
        }
        Building result = new Dwelling(tempFloor, getCount());
        for(int i = 0; i < this.getCount(); i++){
            result.setFloor(i, (Floor) getFloor(i).clone());
        }
        return result;
    }

    @Override
    public Iterator<Floor> iterator() {
        return new getIterator();
    }

    private class getIterator implements Iterator<Floor> {
        int index;

        @Override
        public boolean hasNext() {
            return index < getArray().length;
        }

        @Override
        public Floor next() {
            return getFloor(index++);
        }
    }
}