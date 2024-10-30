package de.tkunkel.maze.output;

public enum TileSetArrangements {
    TILE_WITH_WALLS_N_E(new int[][][]{
            {{8, 3}, {8, 3}, {3, 2}},
            {{10, 2}, {10, 2}, {6, 2}},
            {{10, 2}, {10, 2}, {6, 2}},
    }),
    TILE_WITH_WALLS_E(new int[][][]{
            {{10, 2}, {10, 2}, {6, 2}},
            {{10, 2}, {10, 2}, {6, 2}},
            {{10, 2}, {10, 2}, {6, 2}},
    }),
    TILE_WITH_WALLS_N_W(new int[][][]{
            {{5, 2}, {8, 3}, {8, 3}},
            {{9, 2}, {10, 2}, {10, 2}},
            {{9, 2}, {10, 2}, {10, 2}},
    }),
    TILE_WITH_WALLS_E_S(new int[][][]{
            {{10, 2}, {10, 2}, {6, 2}},
            {{10, 2}, {10, 2}, {6, 2}},
            {{7, 1}, {7, 1}, {3, 1}},
    }),
    TILE_WITH_WALLS_S(new int[][][]{
            {{10, 2}, {10, 2}, {10, 2}},
            {{10, 2}, {10, 2}, {10, 2}},
            {{7, 1}, {7, 1}, {7, 1}},
    }),
    TILE_WITH_WALLS_S_W(new int[][][]{
            {{9, 2}, {10, 2}, {10, 2}},
            {{9, 2}, {10, 2}, {10, 2}},
            {{5, 1}, {7, 1}, {7, 1}},
    }),
    TILE_WITH_WALLS_E_W(new int[][][]{
            {{9, 2}, {10, 2}, {6, 2}},
            {{9, 2}, {10, 2}, {6, 2}},
            {{9, 2}, {10, 2}, {6, 2}},
    }),
    TILE_WITH_WALLS_N(new int[][][]{
            {{8, 3}, {8, 3}, {8, 3}},
            {{10, 2}, {10, 2}, {10, 2}},
            {{10, 2}, {10, 2}, {10, 2}},
    }),
    TILE_WITH_WALLS_W(new int[][][]{
            {{9, 2}, {10, 2}, {10, 2}},
            {{9, 2}, {10, 2}, {10, 2}},
            {{9, 2}, {10, 2}, {10, 2}},
    }),
    TILE_WITH_WALLS_N_S_W(new int[][][]{
            {{5, 2}, {8, 3}, {8, 3}},
            {{9, 2}, {10, 2}, {10, 2}},
            {{5, 1}, {7, 1}, {7, 1}},
    }),
    TILE_WITH_WALLS_N_E_S_W(new int[][][]{
            {{9, 2}, {8, 3}, {8, 3}},
            {{9, 2}, {10, 2}, {6, 2}},
            {{7, 1}, {7, 1}, {7, 1}},
    }),
    TILE_WITH_WALLS_N_S(new int[][][]{
            {{8, 3}, {8, 3}, {8, 3}},
            {{10, 2}, {10, 2}, {10, 2}},
            {{7, 1}, {7, 1}, {7, 1}},
    }),
    TILE_WITH_WALLS_E_S_W(new int[][][]{
            {{9, 2}, {10, 2}, {6, 2}},
            {{9, 2}, {10, 2}, {6, 2}},
            {{5, 1}, {7, 1}, {3, 1}},
    }),
    TILE_WITH_WALLS_N_E_W(new int[][][]{
            {{5, 2}, {8, 3}, {3, 2}},
            {{9, 2}, {10, 2}, {6, 2}},
            {{9, 2}, {10, 2}, {6, 2}},
    }),
    TILE_WITH_WALLS_N_E_S(new int[][][]{
            {{8, 3}, {8, 3}, {3, 2}},
            {{10, 2}, {10, 2}, {6, 2}},
            {{7, 1}, {7, 1}, {3, 1}},
    }),
    TILE_WITH_NO_WALLS(new int[][][]{
            {{10, 2}, {10, 2}, {10, 2}},
            {{10, 2}, {10, 2}, {10, 2}},
            {{10, 2}, {10, 2}, {10, 2}},
    });

    public final int[][][] data;

    TileSetArrangements(final int[][][] data) {
        this.data = data.clone();
        rotate90Clockwise(this.data);
        rotateHorizontally(this.data);
    }

    // Function to rotate (flip) the 2D array horizontally
    public static void rotateHorizontally(int[][][] matrix) {
        int n = matrix.length;

        // Iterate through each row
        for (int i = 0; i < n; i++) {
            // Reverse the current row by swapping elements from the start and end
            for (int j = 0; j < matrix[i].length / 2; j++) {
                // Swap matrix[i][j] with matrix[i][matrix[i].length - 1 - j]
                int[] temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix[i].length - 1 - j];
                matrix[i][matrix[i].length - 1 - j] = temp;
            }
        }
    }

    // Function to rotate the matrix by 90 degrees clockwise
    public static void rotate90Clockwise(int[][][] matrix) {
        int n = matrix.length;

        // Step 1: Transpose the matrix
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                // Swap matrix[i][j] and matrix[j][i]
                int[] temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // Step 2: Reverse each row
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                // Swap matrix[i][j] and matrix[i][n - 1 - j]
                int[] temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
}
