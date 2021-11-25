package buildings.office;
import buildings.dwelling.DwellingFloor;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

public class OfficeFloor implements Floor, Serializable, Cloneable {

    static public class BuildNode implements Serializable{
        private Space oneOffice; //поле списка
        private BuildNode next;

        public BuildNode() {
            next = this;
        }
    }
    private BuildNode head; //голова списка
    private int n;

    public OfficeFloor(int n) { //конструктор принимает количество оффисов
        this.head = new BuildNode();
        BuildNode tempNode = this.head;
        while(n-- > 0) {
            tempNode = tempNode.next = this.addNodeToTail();
        }
    }
    public OfficeFloor(Space[] offices) { //конструктор принимает массив оффисов
        this(offices.length);
       // BuildNode temp = head;
        for (int i = 0; i < offices.length; i++) {
            this.getNodeByIndex(i).oneOffice = offices[i];
            /*BuildNode x = new BuildNode();
            x.oneOffice = offices[i];
            temp.next = x;
            temp = x;*/
        }
        //temp.next = head.next;
    }
    private BuildNode addNodeToTail() { //добавить в хвост
        BuildNode temp = this.head;
        for (int i = 0; i < this.n; i++)
            temp = temp.next;
        temp.next = new BuildNode();
        temp.next.next = this.head.next;
        n++;
        return temp.next;
    }

    private BuildNode getNodeByIndex(int index) { //получение узла списка по его номеру
        BuildNode temp = head.next;
        for(int i = 0; i < index; i++)
            temp = temp.next;
        return temp;
    }

    //вспомогательные методы
    private void addNodeByIndex(BuildNode node, int index) { //добавление по номеру
        BuildNode temp = head.next;
        for (int i = 0; i < index; i++)
            temp = temp.next;
        node.next = temp.next;
        temp.next = node;
    }

    private void deleteNodeByIndex(int index) {
        BuildNode temp;
        int i = index;
        if(i == 0)
            temp = head.next;
        else
            temp = head;
        for (i = 0; i < index; i++) {
            temp = temp.next;
        }
        temp.next = temp.next.next;
    }

    public int getCount() { //количество квартир
        BuildNode temp = head.next;
        int res = 0;
        while (temp.next != head.next) {
            res++;
            temp = temp.next;
        }
        if (res > 0) res++;
        return res;
    }

    public double getSquare() { //площадь всех оффисов
        BuildNode temp = head.next;
        double res = 0;
        while (temp.next != head.next) {
            res += temp.oneOffice.getSquare();
            temp = temp.next;
        }
        if (res > 0) res += temp.oneOffice.getSquare();
        return res;
    }

    public int getRoomsCount(){ //количество комнат всех оффисов
        BuildNode temp = head.next;
        int res = 0;
        while (temp.next != head.next) {
            res += temp.oneOffice.getRoomsCount();
            temp = temp.next;
        }
        if (res > 0) res += temp.oneOffice.getRoomsCount();
        return res;
    }

    public Space[] getArray() { //массив оффисов этажа
        Space[] offices = new Space[getCount()];
        BuildNode current = head;
        for (int i = 0; i < getCount(); i++) {
            current = current.next;
            offices[i] = current.oneOffice;
        }
        return offices;
    }

    public Space getSpace(int index) { //получение оффиса по номеру на этаже
        if ((index >= getCount()) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).oneOffice;
    }

    public void setNewSpace(int index, Space newOffice) { //изменение квартиры по новой и номеру
        if ((index >= getCount()) || (index < 0))
            throw new SpaceIndexOutOfBoundsException();
        getNodeByIndex(index).oneOffice = newOffice;
    }

    public void addSpace(int index, Space oneOffice) { //добавить квартиру на этаж
        if ((index >= getCount()) || (index < 0))
            throw new SpaceIndexOutOfBoundsException();
        oneOffice = new Office(oneOffice.getSquare(),oneOffice.getRoomsCount());
        BuildNode newOffice = new BuildNode();
        newOffice.oneOffice =  oneOffice;
        addNodeByIndex(newOffice, index);
    }

    public void deleteSpace(int index) { //удалить квартиру с этажа
        if ((index >= getCount()) || (index < 0))
            throw new SpaceIndexOutOfBoundsException();
        deleteNodeByIndex(index);
    }
    public Space getBestSpace() {
        double bestOffice = 0;
        Space officeBestSpace = null;
        BuildNode temp = head;
        for (int i = 0; i <= getCount(); i++) {
            temp = temp.next;
            if (temp.oneOffice.getSquare() > bestOffice) {
                bestOffice = temp.oneOffice.getSquare();
                officeBestSpace = temp.oneOffice;
            }
        }
        return officeBestSpace;
    }

    @Override
    public String toString(){
        StringBuffer myBuffer = new StringBuffer();
        myBuffer.append("OfficeFloor(" + getCount() + ", ");
        for(int i = 0; i < getCount(); i++){
            myBuffer.append("Office( ").append(getSpace(i).getRoomsCount()).append("; ").append(getSpace(i).getSquare()).append(") ");
        }
        return myBuffer.toString();
    }
    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof OfficeFloor)) return false;
        OfficeFloor temp = (OfficeFloor) object;
        if(this.getCount() != temp.getCount()) return false;
        for(int i = 0; i < getCount(); i++)
            if (!getSpace(i).equals(temp.getSpace(i))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int res = 1;
        res = 31 * res + Arrays.hashCode(getArray());;
        return res;
    }

    @Override
    public Object clone() {
        Object o;
        try {
            o = super.clone();
            o = new OfficeFloor(this.getArray().clone());
            for(int i = 0; i < this.getCount(); i++){
                ((OfficeFloor) o).setNewSpace(i, (Space) getSpace(i).clone());
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
            return index < getArray().length;
        }

        @Override
        public Space next() {
            return getSpace(index++);
        }
    }

}
