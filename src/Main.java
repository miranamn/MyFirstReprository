import buildings.Buildings;
import buildings.dwelling.hotel.*;
import buildings.interfaces.Space;
import buildings.office.*;
import buildings.dwelling.*;

import java.io.*;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Office a1 = new Office(10.0 ,1);
        Office b1 = new Office(10.0, 3);
        Office c1 = new Office(10.0, 2);
        Office d1 = new Office(10.0, 1);
        Office[] f = {a1, b1, c1, d1};
        OfficeFloor first = new OfficeFloor(f); //офисный этаж
        Office a = new Office(70.0 ,2);
        Office b = new Office(55.5, 3);
        Office c = new Office(30.0, 4);
        Office[] offices = {a, b, c};
        OfficeFloor second = new OfficeFloor(offices);
        Office aa = new Office(10.0 ,1);
        Office bb = new Office(20.0, 2);
        Office cc = new Office(30.0, 3);
        Office[] offices1 = {aa, bb, cc};
        OfficeFloor third = new OfficeFloor(offices1);
        OfficeFloor[] build = {first, second, third};
        OfficeBuilding one = new OfficeBuilding(build);

        /*System.out.println(Arrays.deepToString(offices));
        Buildings.sort(offices);
        System.out.println(Arrays.deepToString(offices));

        System.out.println(Arrays.deepToString(build));
        Buildings.sort(build);
        System.out.println(Arrays.deepToString(build));*/

        System.out.println(Arrays.deepToString(offices));
        Buildings.sort(offices, new Office.SpaceComparator());
        System.out.println(Arrays.deepToString(offices));

        System.out.println(Arrays.deepToString(build));
        Buildings.sort(build, new OfficeFloor.FloorComparator());
        System.out.println(Arrays.deepToString(build));


        Flat a11 = new Flat(10.0 ,1);
        Flat b11 = new Flat(40.0, 2);
        Flat c11 = new Flat(30.0, 3);
        Flat[] offices2 = {a11, b11, c11};
        Flat a111 = new Flat(100.0 ,1);
        Flat b111 = new Flat(200.0, 2);
        Flat c111 = new Flat(30.0, 3);
        Flat[] hf2 = {a111, b111, c111};
        HotelFloor fourth = new HotelFloor(offices2);
        HotelFloor[] hf = {fourth};
        HotelFloor fifth = new HotelFloor(hf2);
        HotelFloor[] hff2 = {fifth};
         HotelFloor[] mas = {fourth, fifth};
        Hotel hff = new Hotel(mas);

        System.out.println(Arrays.deepToString(hf2));
        Buildings.sort(hf2);
        System.out.println(Arrays.deepToString(hf2));

        fourth.setStars(3);
        fifth.setStars(4);
        System.out.println(hff.getStars());
        System.out.println(hff.getBestSpace());
        System.out.println(fifth.toString());
        System.out.println(hff.toString());
        System.out.println(fifth.equals(fourth));

        for(Space space: fourth){

        }


    }
}
