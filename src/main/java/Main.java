import upei.cs2920.daedalus.Cell;
import upei.cs2920.daedalus.Helper;
import upei.cs2920.daedalus.MazeBuilder;

/**
 * Manually test the Daedalus solver
 * Useful for debugging
 * Any changes in this class are not graded
 */
public class Main {

    /* Some basic functionality */
    public static void main(String[] args) {

        //create and solve an arbitrary maze
        Cell starting = MazeBuilder.mazeFor("HELLO");
        DaedalusSolver solver = new DaedalusSolver();
        String path = solver.solve(starting);

        if (Helper.isValidSolution(starting, path)) {
            System.out.println("path works");
        }
        else {
            System.out.println("oops path doesn't work");
        }
    }
}
