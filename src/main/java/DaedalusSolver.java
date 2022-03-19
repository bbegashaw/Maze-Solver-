import jdk.swing.interop.SwingInterOpUtils;
import upei.cs2920.daedalus.*;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Solve a daedalus maze to produce a door path leading to all three
 * items of interest: Minotaur, Ring and Sword
 */
public class DaedalusSolver {

    //boolean global variables
    boolean foundRing;
    boolean foundSword;
    boolean foundMin;
    ArrayList<String> itemsFound = new ArrayList<>(); // arraylist to hold the three items


    /**
     * Construct a DaedalusSolver object
     */
    public DaedalusSolver() {

        //start solver by declaring items as not found
        foundRing = false;
        foundSword = false;
        foundMin = false;

    }

    /**
     * return a String representing the path of doors to go through that
     * leads from the starting cell of a maze such that the path will lead through
     * cells containing all 3 of the items of interest (Minotaur, Ring and Sword)
     *
     * @param start the starting cell or entry point into the maze
     * @return A String giving the sequence of doors on the path
     * @throws IllegalArgumentException if a path leading to all 3 special items cannot be found
     */
    public String solve(Cell start)  {
        //complete this method


        String stringOfDoorColors = "";
        Stack<Cell> nextRoomToVisit = new Stack<>();
        ArrayList<String> roomNames = new ArrayList<>();

        nextRoomToVisit.add(start);

        while (!(foundRing && foundMin  && foundSword )) {
            Cell currentRoom = nextRoomToVisit.pop();

            if (!(currentRoom.contents.name().equals("EMPTY"))) {
                pickItems(currentRoom);
            }

            Door pickedDoor = pickDoor(currentRoom.doors.keySet());

            stringOfDoorColors += pickedDoor.name().substring(0, 1);
            roomNames.add(currentRoom.toString());
            if(roomNames.size() > 700)
            {
                throw new IllegalArgumentException("not a valid maze");
            }else{
                Cell nextRoom1 = goToNextRoom(currentRoom,pickedDoor);
                nextRoomToVisit.push(nextRoom1);
            }
        }

        if(!(itemsFound.contains("RING") || itemsFound.contains("MINOTAUR") || itemsFound.contains("SWORD"))){
            throw new IllegalArgumentException("not a valid maze");
        }

        return stringOfDoorColors;
    }

    /**
     * Choose a random number between 0 and input number
     * @param x max bound for method to choose between
     * @return random number
     */
    private int chooseRandomly(int x) {
        return (int) (Math.floor(Math.random() * (x  + 1)));
    }

    /**
     * Method to pick any door from a room randomly
     * @param doorSet Set of doors available in a certain room
     * @return random door to go through
     */
    private Door pickDoor(Set<Door> doorSet) {
        Iterator<Door> doorIterator = doorSet.iterator();
        int size = doorSet.size();
        Door door = null;
        ArrayList<Door> doorArrayList = new ArrayList<>();
        if (doorSet.size() == 1) {
            while (doorIterator.hasNext()) {
                door = doorIterator.next();
            }
        } else {
            while (doorIterator.hasNext()) {
                doorArrayList.add(doorIterator.next());
            }
            door = doorArrayList.get(chooseRandomly(size - 1));
        }
        return door;
    }


    /**
     * Collect items in arraylist and declare them to be found
     * @param cell
     */
    private void pickItems(Cell cell) {
        String item = cell.contents.name();
        switch (item) {
            case "RING":
                foundRing = true;
                itemsFound.add(item);
                break;
            case "MINOTAUR":
                foundMin = true;
                itemsFound.add(item);
                break;
            case "SWORD":
                foundSword = true;
                itemsFound.add(item);
                break;
        }
    }

    /**
     * Go to the next room from the current room
     * @param cell Current cell to go from
     * @param pickedDoor door to go through
     * @return the next room
     */
    private Cell goToNextRoom(Cell cell, Door pickedDoor) {
        Cell nextRoom = cell.doors.get(pickedDoor);
        return nextRoom;
    }
}
