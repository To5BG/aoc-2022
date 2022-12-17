package solutions;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@SuppressWarnings("unused")
public class Day15 {
    public record Pair(Integer[] p) {}

    public static Object preprocess(String input, int star) {
        HashMap<Pair, Pair> sensorAndBeacon = new HashMap<>();
        for (String s : input.split("\n")) {
            String[] words = s.split(", |: | ");
            Pair se = new Pair(new Integer[]{Integer.parseInt(words[2].split("=")[1]),
                    Integer.parseInt(words[3].split("=")[1])});
            Pair be = new Pair(new Integer[]{Integer.parseInt(words[8].split("=")[1]),
                    Integer.parseInt(words[9].split("=")[1])});
            sensorAndBeacon.put(se, be);
        }
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(sensorAndBeacon) : solveStar2(sensorAndBeacon);
    }
    public static String solveStar1(HashMap<Pair, Pair> sensorAndBeacon) {
        ArrayList<Pair> ranges = mergeRanges(noBeaconRanges(sensorAndBeacon,2000000));
        return Integer.toString(ranges.get(0).p[1] - ranges.get(0).p[0]);
    }
    public static String solveStar2(HashMap<Pair, Pair> sensorAndBeacon) {
        for (int y = 4000000; y > 0; y--) {
            ArrayList<Pair> ranges = mergeRanges(noBeaconRanges(sensorAndBeacon,y));
            if (ranges.size() > 1) {
                int trueX = 0;
                loop: for (int x = 0; x < 4000000; x++) {
                    for(Pair c : ranges) if(x >= c.p[0] && x <= c.p[1]) continue loop;
                    trueX = x;
                    break;
                }
                return Long.toString(trueX * 4000000L + y);
            }
        }
        return null;
    }
    public static ArrayList<Pair> noBeaconRanges(HashMap<Pair, Pair> map, int y) {
        ArrayList<Pair> ranges = new ArrayList<>();
        for(Pair c : map.keySet()) {
            int dist = Math.abs(c.p[0] - map.get(c).p[0]) + Math.abs(c.p[1] - map.get(c).p[1]);
            int xRange = dist - Math.abs(c.p[1] - y);
            if(xRange > 0) ranges.add(new Pair(new Integer[]{c.p[0] - xRange, c.p[0] + xRange}));
        }
        return ranges;
    }
    public static ArrayList<Pair> mergeRanges(ArrayList<Pair> ranges) {
        ranges.sort(Comparator.comparing(o -> o.p[0]));
        ArrayList<Pair> newRanges = new ArrayList<>();
        newRanges.add(ranges.get(0));
        for(int i = 1; i < ranges.size(); i++) {
            Pair range = ranges.get(i);
            Pair end = newRanges.get(newRanges.size() - 1);
            if(range.p[1] >= end.p[0] && range.p[1] <= end.p[1])
                newRanges.get(newRanges.size()-1).p[0] = Math.min(range.p[0], end.p[0]);
            if(range.p[0] >= end.p[0] && range.p[0] <= end.p[1])
                newRanges.get(newRanges.size() - 1).p[1] = Math.max(range.p[1],end.p[1]);
            if(!(range.p[0] >= end.p[0] && range.p[0] <= end.p[1]) &&
                    !(range.p[1] >= end.p[0] && range.p[1] <= end.p[1]))
                newRanges.add(0,range);
        }
        return newRanges;
    }
}
