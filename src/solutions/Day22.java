package solutions;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Day22 {
    private static void adjustForCube(HashMap<Integer, HashMap<Integer, Tile>> tiles, int faceSize, int i) {
        Tile a1 = tiles.get(1).get(faceSize + i);
        Tile a6 = tiles.get(3 * faceSize + i).get(1);
        a1.neighbors[3] = a6;
        a1.facingAdjust[3] = 1;
        a6.neighbors[2] = a1;
        a6.facingAdjust[2] = 3;

        Tile b2 = tiles.get(1).get(faceSize * 2 + i);
        Tile b6 = tiles.get(faceSize * 4).get(i);
        b2.neighbors[3] = b6;
        b6.neighbors[1] = b2;

        Tile c2 = tiles.get(i).get(faceSize * 3);
        Tile c5 = tiles.get(faceSize * 3 - i + 1).get(faceSize * 2);
        c2.neighbors[0] = c5;
        c2.facingAdjust[0] = 2;
        c5.neighbors[0] = c2;
        c5.facingAdjust[0] = 2;

        Tile d2 = tiles.get(faceSize).get(faceSize * 2 + i);
        Tile d3 = tiles.get(faceSize + i).get(faceSize * 2);
        d2.neighbors[1] = d3;
        d2.facingAdjust[1] = 1;
        d3.neighbors[0] = d2;
        d3.facingAdjust[0] = 3;

        Tile e5 = tiles.get(faceSize * 3).get(faceSize + i);
        Tile e6 = tiles.get(3 * faceSize + i).get(faceSize);
        e5.neighbors[1] = e6;
        e5.facingAdjust[1] = 1;
        e6.neighbors[0] = e5;
        e6.facingAdjust[0] = 3;

        Tile f4 = tiles.get(faceSize * 2 + i).get(1);
        Tile f1 = tiles.get(faceSize - i + 1).get(faceSize + 1);
        f4.neighbors[2] = f1;
        f4.facingAdjust[2] = 2;
        f1.neighbors[2] = f4;
        f1.facingAdjust[2] = 2;

        Tile g3 = tiles.get(faceSize + i).get(faceSize + 1);
        Tile g4 = tiles.get(2 * faceSize + 1).get(i);
        g3.neighbors[2] = g4;
        g3.facingAdjust[2] = 3;
        g4.neighbors[3] = g3;
        g4.facingAdjust[3] = 1;
    }
    private static int walk(Tile start, String directions) {
        Pattern pat = Pattern.compile("(\\d+)([RL])?");
        Matcher m = pat.matcher(directions);
        Tile cur = start;
        int facing = 0;
        while (m.find()) {
            int steps = Integer.parseInt(m.group(1));
            while (steps > 0) {
                if (!cur.neighbors[facing].wall) {
                    int facingAdjust = cur.facingAdjust[facing];
                    cur = cur.neighbors[facing];
                    facing = (facing + facingAdjust) % 4;
                    steps--;
                } else break;
            }
            if (m.group(2) != null) {
                char turn = m.group(2).charAt(0);
                facing = (facing + (turn == 'L' ? 3 : 1)) % 4;
            }
        }
        return 1000 * cur.x + 4 * cur.y + facing;
    }
    public record Tile(int x, int y, Tile[] neighbors, int[] facingAdjust, boolean wall) {}
    public static Object preprocess(String input, int star)  {
        String s; int row = 1; Tile start = null;
        HashMap<Integer, HashMap<Integer, Tile>> tiles = new HashMap<>();
        for (String l : input.split("\n\n")[0].split("\n")) {
            Tile first = null, prev = null;
            HashMap<Integer, Tile> curRow = new HashMap<>();
            tiles.put(row, curRow);
            for (int i = 0; i < l.length(); i++) {
                if (l.charAt(i) == ' ') continue;
                Tile tile = new Tile(row, i + 1, new Tile[4], new int[4], l.charAt(i) == '#');
                if (start == null) start = tile;
                if (first == null) first = tile;
                if (prev != null) {
                    prev.neighbors[0] = tile;
                    tile.neighbors[2] = prev;
                }
                prev = tile;
                curRow.put(i + 1, tile);
            }
            first.neighbors[2] = prev;
            prev.neighbors[0] = first;
            row++;
        }
        int numRows = row - 1;
        for (var cur : tiles.entrySet()) for (var entry : cur.getValue().entrySet()) {
            Tile tile = entry.getValue();

            int tst = tile.x == 1 ? numRows : tile.x - 1;
            while (tiles.get(tst).get(tile.y) == null) tst = (tst == 1) ? numRows : tst - 1;
            Tile neighbor = tiles.get(tst).get(tile.y);
            neighbor.neighbors[1] = tile;
            tile.neighbors[3] = neighbor;

            tst = tile.x == numRows ? 1 : tile.x + 1;
            while (tiles.get(tst).get(tile.y) == null) tst = (tst == numRows) ? 1 : tst + 1;
            neighbor = tiles.get(tst).get(tile.y);
            neighbor.neighbors[3] = tile;
            tile.neighbors[1] = neighbor;
        }
        String directions = input.split("\n\n")[1];
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(start, directions) : solveStar2(start, directions, tiles, numRows);
    }
    public static Object solveStar1(Tile start, String directions) {
        return walk(start, directions);
    }
    public static Object solveStar2(Tile start, String directions,
                                    HashMap<Integer, HashMap<Integer, Tile>> tiles, int numRows) {
        int faceSize = numRows / 4;
        for (int i = 1; i <= faceSize; i++) adjustForCube(tiles, faceSize, i);
        return walk(start, directions);
    }
}
