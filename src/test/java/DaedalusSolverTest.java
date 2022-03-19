import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import upei.cs2920.daedalus.*;

import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class DaedalusSolverTest {

    /**
     * Construct a basic maze with
     * 3 cell neighbours branching from the starting cell
     * Each neighbour contains a special item
     *
     * @return the starting cell
     */
    private static Cell buildBasicMaze() {
        Cell starting = new Cell();
        starting.contents = Contents.EMPTY;

        Cell minotaur = new Cell();
        minotaur.contents = Contents.MINOTAUR;

        Cell ring = new Cell();
        ring.contents = Contents.RING;

        Cell sword = new Cell();
        sword.contents = Contents.SWORD;

        //link the doors
        starting.doors.put(Door.RED, minotaur);
        minotaur.doors.put(Door.GREEN, starting);

        starting.doors.put(Door.GREEN, ring);
        ring.doors.put(Door.RED, starting);

        starting.doors.put(Door.BLUE, sword);
        sword.doors.put(Door.YELLOW, starting);

        //no Yellow door in starting

        return starting;
    }

    /**
     * Construct a basic maze with
     * a line of cells leading from a starting cell then cycling
     * back to starting
     * each successive neighbour contains a special item
     *
     * @return the starting cell
     */
    private static Cell buildLinearCycleMaze() {
        Cell starting = new Cell();
        starting.contents = Contents.EMPTY;

        Cell minotaur = new Cell();
        minotaur.contents = Contents.MINOTAUR;

        Cell ring = new Cell();
        ring.contents = Contents.RING;

        Cell sword = new Cell();
        sword.contents = Contents.SWORD;

        //link the doors
        starting.doors.put(Door.RED, minotaur);
        minotaur.doors.put(Door.GREEN, starting);

        minotaur.doors.put(Door.YELLOW, ring);
        ring.doors.put(Door.RED, minotaur);

        ring.doors.put(Door.BLUE, sword);
        sword.doors.put(Door.YELLOW, ring);

        sword.doors.put(Door.RED, starting);
        starting.doors.put(Door.BLUE, sword);

        return starting;
    }

    /**
     * Construct a basic maze with
     * a line of cells leading from a starting cell then cycling
     * back to starting
     * each successive neighbour contains a special item
     *
     * @return the starting cell
     */
    private static Cell buildLinearMaze() {
        Cell starting = new Cell();
        starting.contents = Contents.EMPTY;

        Cell minotaur = new Cell();
        minotaur.contents = Contents.MINOTAUR;

        Cell ring = new Cell();
        ring.contents = Contents.RING;

        Cell sword = new Cell();
        sword.contents = Contents.SWORD;

        //link the doors
        starting.doors.put(Door.RED, minotaur);
        minotaur.doors.put(Door.GREEN, starting);

        minotaur.doors.put(Door.YELLOW, ring);
        ring.doors.put(Door.RED, minotaur);

        ring.doors.put(Door.BLUE, sword);
        sword.doors.put(Door.YELLOW, ring);

        //sword.doors.put(Door.RED, starting);

        return starting;
    }

    /**
     * A maze with a special item in the starting cell
     * @return the starting cell
     */
    private static Cell startingCellWithSpecialItem() {
        Cell starting = new Cell();
        starting.contents = Contents.RING;

        Cell minotaur = new Cell();
        minotaur.contents = Contents.MINOTAUR;

        Cell sword = new Cell();
        sword.contents = Contents.SWORD;

        Cell empty1 = new Cell();
        empty1.contents = Contents.EMPTY;

        Cell empty2 = new Cell();
        empty2.contents = Contents.EMPTY;

        starting.doors.put(Door.RED, empty1);
        empty1.doors.put(Door.RED, starting);

        empty1.doors.put(Door.BLUE, sword);
        sword.doors.put(Door.BLUE, empty1);

        starting.doors.put(Door.GREEN, empty2);
        empty2.doors.put(Door.GREEN, starting);

        empty2.doors.put(Door.YELLOW, minotaur);
        minotaur.doors.put(Door.YELLOW, empty2);

        return starting;
    }

    /**
     * Maze with two branches off of the starting cell
     * @return the starting cell
     */
    private static Cell buildTwoProngedMaze() {

        Cell starting = new Cell();
        starting.contents = Contents.EMPTY;

        Cell empty = new Cell();
        empty.contents = Contents.EMPTY;

        Cell minotaur = new Cell();
        minotaur.contents = Contents.MINOTAUR;

        Cell ring = new Cell();
        ring.contents = Contents.RING;

        Cell sword = new Cell();
        sword.contents = Contents.SWORD;

        starting.doors.put(Door.YELLOW, minotaur);
        minotaur.doors.put(Door.GREEN, starting);

        minotaur.doors.put(Door.BLUE, ring);
        ring.doors.put(Door.RED, minotaur);

        starting.doors.put(Door.RED, empty);
        empty.doors.put(Door.GREEN, starting);

        empty.doors.put(Door.BLUE, sword);
        sword.doors.put(Door.YELLOW, empty);

        return starting;
    }

    Cell buildMazeMissing1Item() {
        Cell starting = new Cell();
        starting.contents = Contents.EMPTY;

        Cell minotaur = new Cell();
        minotaur.contents = Contents.MINOTAUR;

        Cell ring = new Cell();
        ring.contents = Contents.RING;

        Cell sword = new Cell();
        sword.contents = Contents.SWORD;

        Cell empty = new Cell();
        empty.contents = Contents.EMPTY;

        //can't actually reach sword
        sword.doors.put(Door.GREEN, starting);

        //link the doors
        starting.doors.put(Door.RED, minotaur);
        minotaur.doors.put(Door.GREEN, starting);

        minotaur.doors.put(Door.YELLOW, ring);
        ring.doors.put(Door.RED, minotaur);

        ring.doors.put(Door.GREEN, empty);
        empty.doors.put(Door.YELLOW, starting);

        return starting;
    }

    /**
     * Maze with 3 minotaurs and no other special objects
     * @return the starting cell
     */
    Cell buildMazeWith3Minotaurs() {
        Cell start = new Cell();
        start.contents = Contents.EMPTY;

        Cell min1 = new Cell();
        min1.contents = Contents.MINOTAUR;

        Cell min2 = new Cell();
        min2.contents = Contents.MINOTAUR;

        Cell min3 = new Cell();
        min3.contents = Contents.MINOTAUR;

        start.doors.put(Door.RED, min1);
        min1.doors.put(Door.GREEN, start);

        start.doors.put(Door.BLUE, min2);
        min2.doors.put(Door.GREEN, start);

        start.doors.put(Door.YELLOW, min3);
        min3.doors.put(Door.GREEN, start);

        return start;
    }

    private static final String ARBITRARY_STRING="HELLO";
    private static final String OTHER_STRING = "WORLD!";

    //parameters passed to the testing below
    static Stream<Arguments> customMazes() {
        return Stream.of(
                arguments("starting cell at centre with 3 branches", buildBasicMaze()),
                arguments("straight line maze", buildLinearMaze()),
                arguments("cycle maze", buildLinearCycleMaze()),
                arguments("starting centre with two prongs", buildTwoProngedMaze()),
                arguments("special item in first cell", startingCellWithSpecialItem()),
                arguments("random grid maze", MazeBuilder.mazeFor(ARBITRARY_STRING)),
                arguments("another random grid maze", MazeBuilder.mazeFor(OTHER_STRING)),
                arguments("random complex maze", MazeBuilder.complexMazeFor(ARBITRARY_STRING)),
                arguments("another random complex maze", MazeBuilder.complexMazeFor(OTHER_STRING))
        );
    }

    @ParameterizedTest(name="TestNumber: {index}, Test Description: {0}")
    @MethodSource("customMazes")
    void testSolveMaze(String description, Cell startingCell) {
        DaedalusSolver solver = new DaedalusSolver();
        String solutionPath = solver.solve(startingCell);
        boolean isSolved = Helper.isValidSolution(startingCell, solutionPath);
        assertTrue(isSolved);
    }

    @Test
    void testBrokenMazeShouldThrowException() {
        Cell start = new Cell();
        start.contents = Contents.EMPTY;

        DaedalusSolver solver = new DaedalusSolver();
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            solver.solve(start);
        });
        System.out.println(e.getMessage());
    }

    @Test
    void testMazeMissing1ItemShouldThrowException() {
        Cell start = buildMazeMissing1Item();
        DaedalusSolver solver = new DaedalusSolver();
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            solver.solve(start);
        });
        System.out.println(e.getMessage());
    }

    @Test
    void testMazeWithLotsOfMinotaursThrows() {
        Cell start = buildMazeMissing1Item();
        DaedalusSolver solver = new DaedalusSolver();
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, ()-> {
            solver.solve(start);
        });
        System.out.println(e.getMessage());
    }


    //seems for autograding we can't use parameterized tests
    //list the parameterized tests from above manually
    @Test
    void testCustomMaze1() {
        Cell start = buildBasicMaze();
        DaedalusSolver solver = new DaedalusSolver();
        String solutionPath = solver.solve(start);
        boolean isSolved = Helper.isValidSolution(start, solutionPath);
        assertTrue(isSolved);
    }

    @Test
    void testCustomMaze2() {
        Cell start = buildLinearMaze();
        DaedalusSolver solver = new DaedalusSolver();
        String solutionPath = solver.solve(start);
        boolean isSolved = Helper.isValidSolution(start, solutionPath);
        assertTrue(isSolved);
    }
    @Test
    void testCustomMaze3() {
        Cell start = buildLinearCycleMaze();
        DaedalusSolver solver = new DaedalusSolver();
        String solutionPath = solver.solve(start);
        boolean isSolved = Helper.isValidSolution(start, solutionPath);
        assertTrue(isSolved);
    }

    @Test
    void testCustomMaze4() {
        Cell start = buildTwoProngedMaze();
        DaedalusSolver solver = new DaedalusSolver();
        String solutionPath = solver.solve(start);
        boolean isSolved = Helper.isValidSolution(start, solutionPath);
        assertTrue(isSolved);
    }

    @Test
    void testCustomMaze5() {
        Cell start = startingCellWithSpecialItem();
        DaedalusSolver solver = new DaedalusSolver();
        String solutionPath = solver.solve(start);
        boolean isSolved = Helper.isValidSolution(start, solutionPath);
        assertTrue(isSolved);
    }

    @Test
    void testRandomMaze1() {
        Cell start = MazeBuilder.mazeFor(ARBITRARY_STRING);
        DaedalusSolver solver = new DaedalusSolver();
        String solutionPath = solver.solve(start);
        boolean isSolved = Helper.isValidSolution(start, solutionPath);
        assertTrue(isSolved);
    }

    @Test
    void testRandomComplexMaze1() {
        Cell start = MazeBuilder.complexMazeFor(OTHER_STRING);
        DaedalusSolver solver = new DaedalusSolver();
        String solutionPath = solver.solve(start);
        boolean isSolved = Helper.isValidSolution(start, solutionPath);
        assertTrue(isSolved);
    }

}