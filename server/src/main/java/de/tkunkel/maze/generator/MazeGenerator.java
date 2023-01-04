package de.tkunkel.maze.generator;

import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * https://en.wikipedia.org/wiki/Maze_generation_algorithm#Iterative_implementation
 * <br>
 * A disadvantage of the first approach is a large depth of recursion – in the worst case, the routine may need to recur on every cell of the area being processed, which may exceed the maximum recursion stack depth in many environments. As a solution, the same backtracking method can be implemented with an explicit stack, which is usually allowed to grow much bigger with no harm.
 * <pre>
 * 1. Choose the initial cell, mark it as visited and push it to the stack
 * 2. While the stack is not empty
 *      1. Pop a cell from the stack and make it a current cell
 *      2. If the current cell has any neighbours which have not been visited
 *          1. Push the current cell to the stack
 *          2. Choose one of the unvisited neighbours
 *          3. Remove the wall between the current cell and the chosen cell
 *          4. Mark the chosen cell as visited and push it to the stack
 * </pre>
 */
@Service
public class MazeGenerator {
    private final Random randomGenerator = new Random();

    public void fill(Maze maze) {
        Stack<Cell> stack = new Stack<>();

        Location mazeStart = maze.getStart();
        Cell current = maze.getCell(mazeStart);
        current.markVisited();
        stack.push(current);

        processStack(maze, stack);
    }

    private void processStack(Maze maze, Stack<Cell> stack) {
        Cell current;
        while (!stack.isEmpty()) {
            current = stack.pop();
            List<Cell> unvisitedNeighbours = maze.getUnvisitedOrthogonalNeighbours(current);
            if (unvisitedNeighbours.size() > 0) {
                stack.push(current);
                Cell nextCell = getRandomCell(unvisitedNeighbours);

                maze.removeWalls(current, nextCell);
                nextCell.markVisited();
                stack.push(nextCell);
            }
        }
    }

    private Cell getRandomCell(List<Cell> unvisitedNeighbours) {
        int index = randomGenerator.nextInt(unvisitedNeighbours.size());
        return unvisitedNeighbours.get(index);
    }
}
