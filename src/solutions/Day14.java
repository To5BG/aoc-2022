package solutions;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Day14 {
    public static Object preprocess(String input, int star) {
        List<String> rocks = Arrays.stream(input.split("\n")).collect(Collectors.toList());
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(rocks) : solveStar2(rocks);
    }
    public static Object solveStar1(List<String> input) {
        final char[][] grid = buildGrid(input);
        final int maxY = getMaxY(grid);
        int nrSand = 0;
        while (pour(grid, 500, 0, maxY)) nrSand++;
        return String.valueOf(nrSand);
    }
    public static Object solveStar2(List<String> input) {
        final char[][] grid = buildGrid(input);
        final int maxY = getMaxY(grid) + 2;
        addFloor(grid, maxY);
        int nrSand = 0;
        while (pour(grid, 500, 0, maxY)) nrSand++;
        return String.valueOf(nrSand + 1);
    }

    private static int getMaxY(final char[][] grid) {
        int maxY = 0;
        for (int y = 0; y < grid.length; y++) for (int x = 0; x < grid[0].length; x++)
            if (grid[y][x] != '.') maxY = Math.max(y, maxY);
        return maxY;
    }

    private static char[][] buildGrid(final List<String> input) {
        final char[][] grid = new char[300][1000];
        for (char[] chars : grid) Arrays.fill(chars, '.');
        grid[0][500] = '+';
        for (final String line : input) {
            final String[] coordinates = line.split(" -> ");
            for (int i = 0; i < coordinates.length - 1; i++) {
                final int x1 = Integer.parseInt(coordinates[i].split(",")[0]);
                final int y1 = Integer.parseInt(coordinates[i].split(",")[1]);
                final int x2 = Integer.parseInt(coordinates[i + 1].split(",")[0]);
                final int y2 = Integer.parseInt(coordinates[i + 1].split(",")[1]);
                for (int y = Math.min(y1, y2); y <= Math.max(y1, y2); y++) {
                    for (int x = Math.min(x1, x2); x <= Math.max(x1, x2); x++) grid[y][x] = '#';
                }
            }
        }
        return grid;
    }

    private static void addFloor(final char[][] grid, final int floorY) {
        for (int x = 0; x < grid[0].length; x++) grid[floorY][x] = '#';
    }

    private static boolean isBlocked(final char[][] grid, final int x, final int y) {
        return grid[y][x] != '.';
    }

    private static boolean pour(final char[][] grid, final int x, final int y, final int maxY) {
        if (y >= maxY) return false;
        if (grid[y + 1][x] == '.') {
            grid[y][x] = '.';
            grid[y + 1][x] = 'o';
            return pour(grid, x, y + 1, maxY);
        }
        if (isBlocked(grid, x, y + 1)) {
            if (!isBlocked(grid, x - 1, y + 1)) {
                grid[y][x] = '.';
                grid[y + 1][x - 1] = 'o';
                return pour(grid, x - 1, y + 1, maxY);
            } else if (!isBlocked(grid, x + 1, y + 1)) {
                grid[y][x] = '.';
                grid[y + 1][x + 1] = 'o';
                return pour(grid, x + 1, y + 1, maxY);
            }
        }
        return !(x == 500 && y == 0);
    }
}
