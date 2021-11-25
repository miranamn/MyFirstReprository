package buildings.office;
import buildings.exceptions.FloorIndexOutOfBoundsException;
import buildings.exceptions.SpaceIndexOutOfBoundsException;
import buildings.interfaces.Building;
import buildings.interfaces.Floor;
import buildings.interfaces.Space;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;

public class OfficeBuilding implements Building, Serializable, Cloneable {

    static public class Node implements Serializable{
        private Floor oneFloor; //поле списка
        private Node next;
        private Node prev;

        public Node() {
            next = this;
            prev = this;
        }
    }
    private Node head; //голова списка
    private int n;

    private OfficeBuilding(int amount) {
        this.head = new Node();
        Node tempNode = this.head;
        while(amount-- > 0) {
            tempNode = tempNode.next = this.addNodeToTail();
        }
    }
    public OfficeBuilding(int size, int[] officesAmountOnFloor) {
        this(size);
        for (int i = 0; i < size; i++) {
            this.getNodeByIndex(i).oneFloor = new OfficeFloor(officesAmountOnFloor[i]);
        }
    }

    public OfficeBuilding(Floor[] officeFloors) { //конструктор принимает массив этажей
        this(officeFloors.length); //создает список определенного размера зараннее
        for (int i = 0; i < officeFloors.length; i++) {
            this.getNodeByIndex(i).oneFloor = officeFloors[i];
        }
    }
    //вспомогательные функции:
    private Node getNodeByIndex(int index) { //вернуть объект по ссылочке
        Node temp = head;
        for (int i = 0; i < index; i++)
            temp = temp.next;
        return temp;
    }
    private Node addNodeToTail() { //добавить в хвост
        Node temp = this.head;
        for (int i = 0; i < this.n; i++)
            temp = temp.next;
        temp.next = new Node();
        if(temp != this.head)
            temp.next.prev = temp;
        temp.next.next = this.head.next;
        n++;
        return temp.next;
    }

    private Node addNodeByIndex(int index) { //добавить по индексу
        Node newNode = new Node();
        Node prevNode;
        Node nextNode;
        if (index > 0) {
            prevNode = getNodeByIndex(index - 1);
            nextNode = prevNode.next;
            newNode.prev = prevNode;
            newNode.next = nextNode;
            prevNode.next = nextNode;
            newNode.prev = nextNode;
        }
        else if (index == 0) {
            if(n > 0) {
                nextNode = this.head.next;
                prevNode = this.getNodeByIndex(n - 1);
                newNode.prev = prevNode;
                newNode.next = nextNode;
                this.head.next = newNode;
                prevNode.next = nextNode;
                nextNode.prev = newNode;
            }
            else
                this.head = newNode;
        }
        else
            return null;
        return newNode;
    }
    private Node deleteNodeByIndex(int index) { //удалить по индексу
        Node nodeToBeDeleted = this.getNodeByIndex(index);
        Node prevNode = nodeToBeDeleted.prev;
        Node nextNode = nodeToBeDeleted.next;
        if(nodeToBeDeleted != null) {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
            if(index == 0)
                this.head.next = nextNode;
        }
        return nodeToBeDeleted;
    }

    public int getCount() { //количество этажей
        Node temp = head.next;
        int res = 0;
        while (temp.next != head.next) {
            res++;
            temp = temp.next;
        }
        if (res > 0) res++;
        return res;
    }
    public int getCountSpaces() { //количество оффисов
        Node temp = head;
        int res = 0;
        int i = this.n;
        while (i-- > 0) {
            res += temp.oneFloor.getCount();
            temp = temp.next;
        }
        return res;
    }
    public double getSquare() { //площадь всех оффисов
        Node temp = head;
        double res = 0;
        int i = this.n;
        while (i-- > 0) {
            res += temp.oneFloor.getSquare();
            temp = temp.next;
        }
        return res;
    }
    public int getRoomsCount() { //количество всех комнат
        Node temp = head;
        int res = 0;
        int i = this.n;
        while (i-- > 0) {
            res += temp.oneFloor.getRoomsCount();
            temp = temp.next;
        }
        return res;
    }

    public Floor[] getArray() { //возвращает массив
        Floor[] officeFloors = new Floor[getCount()];
        Node current = head;
        for (int i = 0; i < getCount(); i++) {
            current = current.next;
            officeFloors[i] = current.oneFloor;
        }
        return officeFloors;
    }
    public Floor getFloor(int index) { //возвращает объект этажа по индексу
        if ((index >= getCount()) || (index < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        return getNodeByIndex(index).oneFloor;
    }
    public void setFloor(int index, Floor newFloor) { //изменяет этаж по номеру
        if ((index >= getCount()) || (index < 0)) {
            throw new FloorIndexOutOfBoundsException();
        }
        //реализация интерфейса
        Space[] temp = new Space[newFloor.getCount()];
        for(int i = 0; i < newFloor.getCount(); i++){
           Space a = new Office(newFloor.getSpace(i).getSquare(), newFloor.getSpace(i).getRoomsCount());
            temp[i] =  a;
        }
        newFloor = new OfficeFloor(temp);
        getNodeByIndex(index).oneFloor = (OfficeFloor) newFloor;
    }

    public Space getSpace(int index) { //получение объекта офиса по его номеру в офисном здании
        if ((index >= getCountSpaces()) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node temp = head;
        int sum = head.oneFloor.getCount();
        while(sum <= index){
            temp = temp.next;
            sum += temp.oneFloor.getCount();
        }
        int ourIndex = temp.oneFloor.getCount() - (sum - index);
        return temp.oneFloor.getSpace(ourIndex);
    }

    public void setSpace(int index, Space newOffice) { //изменение объекта офиса по его номеру в доме и ссылке типа офиса
        if ((index >= getCountSpaces()) || (index < 0)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node temp = head;
        int sum = head.oneFloor.getCount();
        while(sum <= index){
            temp = temp.next;
            sum += temp.oneFloor.getCount();
        }
        int ourIndex = temp.oneFloor.getCount() - (sum - index);
        temp.oneFloor.setNewSpace(ourIndex, newOffice);
    }

    public void addSpace(int index, Space newOffice) { //типа добавление оффиса
        if ((index > getCountSpaces()) || (index < -1)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node temp = head;
        int sum = head.oneFloor.getCount();
        while(sum <= index){
            temp = temp.next;
            sum += temp.oneFloor.getCount();
        }
        int ourIndex = temp.oneFloor.getCount() - (sum - index);
        temp.oneFloor.addSpace(ourIndex, newOffice);
    }

    public void deleteSpace(int index) { // удаление оффиса
        if ((index > getCountSpaces()) || (index < -1)) {
            throw new SpaceIndexOutOfBoundsException();
        }
        Node temp = head;
        int sum = head.oneFloor.getCount();
        while(sum <= index){
            temp = temp.next;
            sum += temp.oneFloor.getCount();
        }
        int ourIndex = temp.oneFloor.getCount() - (sum - index);
        temp.oneFloor.deleteSpace(ourIndex);
    }

    public Space getBestSpace() { //самый трушный оффис по площади
        Node temp = head;
        Space bestOffice = head.oneFloor.getBestSpace();
        for (int i = 1; i < this.n; i++) {
            temp = temp.next;
            if (bestOffice.getSquare() < temp.oneFloor.getBestSpace().getSquare()) {
                bestOffice = temp.oneFloor.getBestSpace();
            }
        }
        return bestOffice;
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

    public Space[] getSpaceArraySorted() {
        int n = getCountSpaces();
        Space[] newFlats = new Space[n];
        Node temp = head;
        int sum = 0;
        int i = this.n;
        while (i-- > 0) {
            System.arraycopy(temp.oneFloor.getArray(), 0, newFlats, sum, temp.oneFloor.getCount());
            sum += temp.oneFloor.getCount();
            temp = temp.next;
        }
        sort(newFlats);
        return newFlats;
    }

    @Override
    public String toString(){
        StringBuffer myBuffer = new StringBuffer();
        myBuffer.append("OfficeBuilding(" + getCount() + ", ");
        for(int i = 0; i < getCount(); i++) {
            myBuffer.append("OfficeFloor(" + getFloor(i).getCount() + ", ");
            for (int j = 0; j < getFloor(i).getCount(); j++) {
                myBuffer.append("Office( ").append(getFloor(i).getSpace(j).getRoomsCount()).append("; ").append(getFloor(i).getSpace(j).getSquare()).append(") ");
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
        if (!(object instanceof OfficeBuilding)) {
            return false;
        }
        OfficeBuilding temp = (OfficeBuilding) object;
        if(this.getCount() != temp.getCount())
            return false;
        for(int i = 0; i < getCount(); i++)
            if (!getFloor(i).equals(temp.getFloor(i))) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int res = 1;
        res = 31 * res + Arrays.hashCode(getArray());
        return res;
    }

    @Override
    public Object clone() {
        int[] tempFloor = new int[this.getCount()];
        for(int i = 0; i < tempFloor.length; i++){
            tempFloor[i] = this.getFloor(i).getCount();
        }
        Building res = new OfficeBuilding(getCount(), tempFloor);
        for(int i = 0; i < this.getCount(); i++){
            res.setFloor(i, (Floor) getFloor(i).clone());
        }
        return res;
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
