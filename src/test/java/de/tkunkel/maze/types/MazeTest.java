package de.tkunkel.maze.types;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MazeTest {

    @Test
    public void unvisitedNeighbours_allVisited() {
        Maze maze = new Maze(3, 3, new Location(0, 0), new Location(2, 2));
        maze.getCell(new Location(1, 0)).markVisited();
        maze.getCell(new Location(0, 1)).markVisited();
        maze.getCell(new Location(1, 1)).markVisited();
        List<Cell> unvisitedNeighbours = maze.getUnvisitedOrthogonalNeighbours(maze.getCell(0, 0));
        Assertions.assertNotNull(unvisitedNeighbours);
        Assertions.assertEquals(0, unvisitedNeighbours.size());
    }

    @Test
    public void unvisitedNeighbours_noneVisited_Corner_NW() {
        Maze maze = new Maze(3, 3, new Location(0, 0), new Location(2, 2));
        List<Cell> unvisitedNeighbours = maze.getUnvisitedOrthogonalNeighbours(maze.getCell(0, 0));
        Assertions.assertNotNull(unvisitedNeighbours);
        Assertions.assertEquals(2, unvisitedNeighbours.size());
    }


    @Test
    public void unvisitedNeighbours_noneVisited_Corner_SE() {
        Maze maze = new Maze(2, 2, new Location(1, 1), new Location(2, 2));
        List<Cell> unvisitedNeighbours = maze.getUnvisitedOrthogonalNeighbours(maze.getCell(1, 1));
        Assertions.assertNotNull(unvisitedNeighbours);
        Assertions.assertEquals(2, unvisitedNeighbours.size());
    }

    @Test
    public void unvisitedNeighbours_noneVisited_center() {
        Maze maze = new Maze(3, 3, new Location(1, 1), new Location(3, 3));
        List<Cell> unvisitedNeighbours = maze.getUnvisitedOrthogonalNeighbours(maze.getCell(1, 1));
        Assertions.assertNotNull(unvisitedNeighbours);
        Assertions.assertEquals(4, unvisitedNeighbours.size());
    }

    @Test
    public void unvisitedNeighbours_eastUnvisited() {
        Maze maze = new Maze(3, 3, new Location(0, 0), new Location(2, 2));
        maze.getCell(new Location(0, 1)).markVisited();
        maze.getCell(new Location(1, 1)).markVisited();
        List<Cell> unvisitedNeighbours = maze.getUnvisitedOrthogonalNeighbours(maze.getCell(0, 0));
        Assertions.assertNotNull(unvisitedNeighbours);
        Assertions.assertEquals(1, unvisitedNeighbours.size());
    }

    @Test
    public void unvisitedNeighbours_southeastUnvisited() {
        Maze maze = new Maze(3, 3, new Location(0, 0), new Location(2, 2));
        maze.getCell(new Location(1, 0)).markVisited();
        maze.getCell(new Location(0, 1)).markVisited();
        List<Cell> unvisitedNeighbours = maze.getUnvisitedOrthogonalNeighbours(maze.getCell(0, 0));
        Assertions.assertNotNull(unvisitedNeighbours);
        Assertions.assertEquals(0, unvisitedNeighbours.size());
    }

    @Test
    public void removeWalls_simple_EW() {
        Cell cell1 = new Cell(new Location(0, 0));
        Cell cell2 = new Cell(new Location(1, 0));
        Maze maze = new Maze(3, 3, new Location(0, 0), new Location(2, 2));
        Assertions.assertTrue(cell1.isNorthWall());
        Assertions.assertTrue(cell1.isEastWall());
        Assertions.assertTrue(cell1.isSouthWall());
        Assertions.assertTrue(cell1.isWestWall());
        Assertions.assertTrue(cell2.isNorthWall());
        Assertions.assertTrue(cell2.isEastWall());
        Assertions.assertTrue(cell2.isSouthWall());
        Assertions.assertTrue(cell2.isWestWall());

        maze.removeWalls(cell1, cell2);

        Assertions.assertTrue(cell1.isNorthWall());
        Assertions.assertFalse(cell1.isEastWall());
        Assertions.assertTrue(cell1.isSouthWall());
        Assertions.assertTrue(cell1.isWestWall());
        Assertions.assertTrue(cell2.isNorthWall());
        Assertions.assertTrue(cell2.isEastWall());
        Assertions.assertTrue(cell2.isSouthWall());
        Assertions.assertFalse(cell2.isWestWall());
    }

    @Test
    public void removeWalls_simple_NS() {
        Cell cell1 = new Cell(new Location(0, 0));
        Cell cell2 = new Cell(new Location(0, 1));
        Maze maze = new Maze(3, 3, new Location(0, 0), new Location(2, 2));
        Assertions.assertTrue(cell1.isNorthWall());
        Assertions.assertTrue(cell1.isEastWall());
        Assertions.assertTrue(cell1.isSouthWall());
        Assertions.assertTrue(cell1.isWestWall());
        Assertions.assertTrue(cell2.isNorthWall());
        Assertions.assertTrue(cell2.isEastWall());
        Assertions.assertTrue(cell2.isSouthWall());
        Assertions.assertTrue(cell2.isWestWall());

        maze.removeWalls(cell1, cell2);

        Assertions.assertTrue(cell1.isNorthWall());
        Assertions.assertTrue(cell1.isEastWall());
        Assertions.assertFalse(cell1.isSouthWall());
        Assertions.assertTrue(cell1.isWestWall());
        Assertions.assertFalse(cell2.isNorthWall());
        Assertions.assertTrue(cell2.isEastWall());
        Assertions.assertTrue(cell2.isSouthWall());
        Assertions.assertTrue(cell2.isWestWall());
    }

}
