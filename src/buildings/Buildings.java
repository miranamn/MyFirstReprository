package buildings;
import buildings.factory.*;
import buildings.interfaces.*;

import java.io.*;
import java.util.*;

public abstract class Buildings{

    private static BuildingFactory buildingFactory = new DwellingFactory();

    public static void setBuildingFactory(BuildingFactory buildingFactory) {
        Buildings.buildingFactory = buildingFactory;
    }

    public Space createSpace(double area){
        return buildingFactory.createSpace(area);
    }

    public Space createSpace(double area, int roomsCount){
        return buildingFactory.createSpace(area, roomsCount);
    }

    public Floor createFloor(int spacesCount){
        return buildingFactory.createFloor(spacesCount);
    }

    public Floor createFloor(Space[] spaces){
        return buildingFactory.createFloor(spaces);
    }

    public Building createBuilding(int floorsCount, int[] spacesCounts){
        return buildingFactory.createBuilding(floorsCount, spacesCounts);
    }

    public Building createBuilding(Floor[] floors){
        return buildingFactory.createBuilding(floors);
    }

 //записи данных о здании в байтовый поток
    public static void outputBuilding (Building building, OutputStream out) throws IOException{
        DataOutputStream of = new DataOutputStream(out); //создали объект потока
        of.writeInt(building.getCount()); //сначала записывается количество этажей в здании
        int count = building.getCount();
        for(int i = 0; i < count; i++){
            Floor floor = building.getFloor(i); //возвращает конкретный этаж
            of.writeInt(floor.getCount()); //количество квартир на i-ом этаже
            int amount = floor.getCount();
            for (int j = 0; j < amount; j++){ // записываются значения для каждой квартиры
                Space space = floor.getSpace(j);
                of.writeDouble(space.getSquare());
                of.writeInt(space.getRoomsCount());
            }
        }
    }

    //чтения данных о здании из байтового потока
    public static Building inputBuilding (InputStream in) throws IOException {
        DataInputStream of = new DataInputStream(in);
        int num = of.readInt(); //считали количество этажей
        Floor floor[] = new Floor[num];
        for(int i = 0; i < num; i++){
            int count = of.readInt();
            Space flat[] = new Space[count];
            for( int j = 0; j < count; j++){
                flat[j] = buildingFactory.createSpace(of.readDouble(), of.readInt());
            }
            floor[i] = buildingFactory.createFloor(flat);
        }
        return buildingFactory.createBuilding(floor);
    }
    public static void writeBuilding (Building building, Writer out)throws IOException{
        Floor floor;
        Space space;
        PrintWriter of = new PrintWriter(out); //создали объект потока
        of.print(building.getCount() + " "); //сначала записывается количество этажей в здании
        int count = building.getCount();
        for(int i = 0; i < count; i++){
            floor = building.getFloor(i); //возвращает конкретный этаж
            of.print(floor.getCount() + " "); //количество квартир на i-ом этаже
            int amount = floor.getCount();
            for (int j = 0; j < amount; j++){ // записываются значения для каждой квартиры
                space = floor.getSpace(j);
                of.print(space.getSquare() + " ");
                of.print(space.getRoomsCount() + " ");
            }
        }
    }
    public static Building readBuilding (Reader in)throws IOException{
        StreamTokenizer of = new StreamTokenizer(in); //создали объект потока
        of.nextToken();
        int count = (int) of.nval;
        Floor[] floors = new Floor[count]; // считали количество этажей и создали массив этажей
        for(int i = 0; i < count; i++){
            of.nextToken();
            int amount = (int) of.nval;
            Space[] spaces = new Space[amount]; //считали количество квартир и создали массив квартир этажа
            for(int j = 0; j < amount; j++) {
                of.nextToken();
                double sq = (double) of.nval;
                of.nextToken();
                int room = (int) of.nval;
                spaces[j] = buildingFactory.createSpace(sq, room);
            }
            floors[i] = buildingFactory.createFloor(spaces);
        }
        return buildingFactory.createBuilding(floors);
    }

    public static Building readBuilding (Scanner sc){
        String line = sc.nextLine();
        String[] numbers = line.split(" ");
        int iterator = 0;
        int count =  Integer.parseInt(numbers[iterator]);
        iterator++;
        int amount = Integer.parseInt(numbers[iterator]);
        Floor[] floors = new Floor[count];
        for(int i = 0; i < count; i++){
            Space[] spaces = new Space[amount];
            iterator++;
            for(int j = 0; j < amount; j++) {
                double sq = Double.parseDouble(numbers[iterator]);
                iterator++;
                int room = Integer.parseInt(numbers[iterator]);
                iterator++;
                spaces[j] = buildingFactory.createSpace(sq, room);;
            }
            floors[i] = buildingFactory.createFloor(spaces);
        }
        return buildingFactory.createBuilding(floors);
    }

    public static void serializeBuilding (Building building, OutputStream out) throws IOException {
        ObjectOutputStream of = new ObjectOutputStream(out);
        of.writeObject(building);
    }
    public static Building deserializeBuilding (InputStream in) throws IOException, ClassNotFoundException{
        ObjectInputStream is = new ObjectInputStream(in);
        Building build = (Building) is.readObject();
        return build;
    }

    public static void writeBuildingFormat (Building building, Writer out){
        Formatter f = new Formatter(out);
        f.format("%d ", building.getCount());
        for(int i = 0; i < building.getCount(); i++){
            f.format("%d ", building.getFloor(i).getCount());
            for(int j = 0; j < building.getFloor(i).getCount(); j++){
                f.format("%.1f ", building.getFloor(i).getSpace(j).getSquare());
                f.format("%d ", building.getFloor(i).getSpace(j).getRoomsCount());
            }
        }
    }

    public static <T extends Comparable<T>> void sort(T[] o) {
        for (int i = 0; i < o.length; i++) {
            int indx = i;
            for (int j = i + 1; j < o.length; j++) {
                if (o[j].compareTo(o[indx]) < 0)
                    indx = j;
            }
            T swapBuf = o[i];
            o[i] = o[indx];
            o[indx] = swapBuf;
        }
    }

    public static <T> void sort(T[] o, Comparator<T> comparator) {
        for (int i = 0; i < o.length; i++) {
            int indx = i;
            for (int j = i + 1; j < o.length; j++) {
                if (comparator.compare(o[j], o[indx]) < 0)
                    indx = j;
            }
            T swapBuf = o[i];
            o[i] = o[indx];
            o[indx] = swapBuf;
        }
    }




}
