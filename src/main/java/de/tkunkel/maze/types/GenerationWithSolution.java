package de.tkunkel.maze.types;

public class GenerationWithSolution {
    private final String imgDataMazeBase64;
    private final String imgDataMazeWithSolutionBase64;

    public GenerationWithSolution(String imgDataMazeBase64, String imgDataMazeWithSolutionBase64) {
        this.imgDataMazeBase64 = imgDataMazeBase64;
        this.imgDataMazeWithSolutionBase64 = imgDataMazeWithSolutionBase64;
    }
}
