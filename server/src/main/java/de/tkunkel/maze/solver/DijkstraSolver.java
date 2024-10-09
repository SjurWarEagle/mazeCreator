package de.tkunkel.maze.solver;

import de.tkunkel.maze.output.RenderImage;
import de.tkunkel.maze.types.Cell;
import de.tkunkel.maze.types.Location;
import de.tkunkel.maze.types.Maze;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * https://en.wikipedia.org/wiki/Maze-solving_algorithm
 */
@Service
public class DijkstraSolver {

    private void fillSolutionMarkers(Maze maze) {
        Location start = maze.getStart();
        Location finish = maze.getFinish();
        int iterations = 0;
        boolean reached = false;
        Cell current = maze.getCell(finish);

        while (!reached) {
            List<Cell> cells = maze.getWalkableOrthogonalNeighbours(current).stream().sorted(Comparator.comparingInt(Cell::getDistanceFromStart)).toList();
            current = cells.get(0);
            if (current.getLocation().equals(start)) {
                reached = true;
            } else {
                current.markAsPartOfSolution();
            }
            if (iterations++ > 1_000_000_000) {
                throw new RuntimeException("Too many loops!");
            }
        }
    }

    public void solve(Maze maze) {

        Cell current = maze.getCell(maze.getStart());
        current.setDistanceFromStart(0);
        int iterations = 0;
        Stack<Cell> stack = new Stack<>();
        stack.push(current);

        while (!stack.isEmpty()) {
            current = stack.pop();

            List<Cell> unvisitedOrthogonalNeighbours = maze.getWalkableOrthogonalNeighbours(current).stream().filter(cell -> cell.getDistanceFromStart() == -1).toList();
            for (Cell neighbour : unvisitedOrthogonalNeighbours) {
                neighbour.setDistanceFromStart(current.getDistanceFromStart() + 1);

                stack.push(neighbour);
            }
            if (iterations++ > 1_000_000_000) {
                throw new RuntimeException("Too many loops!");
            }
        }

        fillSolutionMarkers(maze);
    }

    private void drawSolutionIteration(int iterations, Maze maze) throws IOException {
        RenderImage imageRender = new RenderImage();
        BufferedImage mazeImage = imageRender.render(maze, 10, 1);
        BufferedImage mazeWithSolutionImage = imageRender.renderSolutionProgress(mazeImage, maze, 10,2);

        File outputfile = new File("/tmp/image_%03d.jpg".formatted(iterations));
        ImageIO.write(mazeWithSolutionImage, "jpg", outputfile);
    }

}

