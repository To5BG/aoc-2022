package solutions;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Day22 {
    private static void adjustForCube(HashMap<Integer, HashMap<Integer, Tile>> tiles, int faceSize, int i) {
        Tile b2 = tiles.get(1).get(faceSize * 2 + i);
        Tile b6 = tiles.get(faceSize * 4).get(i);
        b2.neighbors[3] = b6;
        b6.neighbors[1] = b2;

        for (Tuple8 t : new Tuple8[]{new Tuple8(1, faceSize + i, 3 * faceSize + i, 1, 3, 2, 1, 3),
                new Tuple8(i, faceSize * 3, faceSize * 3 - i + 1, faceSize * 2, 0, 0, 2, 2),
                new Tuple8(faceSize, faceSize * 2 + i, faceSize + i, faceSize * 2, 1, 0, 1, 3),
                new Tuple8(faceSize * 3, faceSize + i, 3 * faceSize + i, faceSize, 1, 0, 1, 3),
                new Tuple8(faceSize * 2 + i, 1, faceSize - i + 1, faceSize + 1, 2, 2, 2, 2),
                new Tuple8(faceSize + i, faceSize + 1, 2 * faceSize + 1, i, 2, 3, 3, 1)}) {
            Tile f = tiles.get(t.f_tile()).get(t.f_face());
            Tile s = tiles.get(t.s_tile()).get(t.s_face());
            f.neighbors[t.near_i()] = s;
            f.facingAdjust[t.near_i()] = t.adjust();
            s.neighbors[t.near_i2()] = f;
            s.facingAdjust[t.near_i2()] = t.adjust_2();
        }
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

    record Tuple8(int f_tile, int f_face, int s_tile, int s_face, int near_i, int near_i2, int adjust,
                  int adjust_2) {
    }

    public record Tile(int x, int y, Tile[] neighbors, int[] facingAdjust, boolean wall) {
    }

    public static Object preprocess(String input, int star) {
        String s;
        int row = 1;
        Tile start = null;
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
            assert first != null;
            first.neighbors[2] = prev;
            prev.neighbors[0] = first;
            row++;
        }
        int numRows = row - 1;
        for (var cur : tiles.entrySet())
            for (var entry : cur.getValue().entrySet()) {
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
