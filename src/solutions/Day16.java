package solutions;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Day16 {
    public record Valve(String start, int flow, List<String> ends) {}

    public static Object preprocess(String input, int star) {
        Pattern p = Pattern.compile("([A-Z]{2})");
        Map<String, Valve> valves = input.lines().map(l -> {
            Matcher m = p.matcher(l);
            List<String> v = new ArrayList<>();
            while(m.find()) v.add(m.group(1));
            return new Valve(v.get(0), Integer.parseInt(l.split("\\D+")[1]), v.subList(1, v.size()));
        }).collect(Collectors.toMap(x -> x.start, x -> x));
        Map<Valve, Map<Valve, Integer>> paths = floydWarshall(valves.values(), v -> v.ends.stream()
                .map(valves::get)::iterator);
        List<Valve> clean = valves.values().stream().filter(v -> v.flow != 0).collect(Collectors.toList());

        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(paths, clean, valves) : solveStar2(paths, clean, valves);
    }
    public static Map<Valve, Map<Valve, Integer>> floydWarshall(Iterable<Valve> nodes,
                                                                Function<Valve, Iterable<Valve>> neighbors) {
        Map<Valve, Map<Valve, Integer>> res = new HashMap<>();
        for (Valve x : nodes) {
            res.computeIfAbsent(x, i -> new HashMap<>()).put(x, 0);
            for (Valve y : neighbors.apply(x)) res.computeIfAbsent(x, i -> new HashMap<>()).put(y, 1);
        }
        for (Valve k : nodes) for (Valve i : nodes) for (Valve j : nodes)
            res.computeIfAbsent(i, ii -> new HashMap<>()).put(j, Math.min(
                    res.getOrDefault(i, new HashMap<>()).getOrDefault(j, Integer.MAX_VALUE),
                    res.getOrDefault(i, new HashMap<>()).getOrDefault(k, 10000) +
                            res.getOrDefault(k, new HashMap<>()).getOrDefault(j,10000)));
        return res;
    }

    public static int getDist(Map<Valve, Map<Valve, Integer>> p, Valve x, Valve y) {
        return p.computeIfAbsent(x, ign -> new HashMap<>()).computeIfAbsent(y, ign2 -> Integer.MAX_VALUE);
    }
    public static Map<Set<Valve>, Integer> solve(Map<Valve, Map<Valve, Integer>> path, List<Valve> cleanValves,
                                                 Set<Valve> open, Valve start, int timeRemaining, int flowSoFar,
                                          Map<Set<Valve>, Integer> bestFlowByValveSet) {
        bestFlowByValveSet.merge(open, flowSoFar, Math::max);
        for (Valve v : cleanValves) {
            int timeAfter = timeRemaining - getDist(path, start, v) - 1;
            if (open.contains(v) || timeAfter <= 0) continue;
            HashSet<Valve> newOpen = new HashSet<>(open);
            newOpen.add(v);
            solve(path, cleanValves, newOpen, v, timeAfter, timeAfter * v.flow + flowSoFar,
                    bestFlowByValveSet);
        }
        return bestFlowByValveSet;
    }
    public static Object solveStar1(Map<Valve, Map<Valve, Integer>> path, List<Valve> clean,
                                    Map<String, Valve> valves) {
        return solve(path, clean, new HashSet<>(), valves.get("AA"), 30, 0,
                        new HashMap<>()).values().stream().mapToInt(x -> x).max().orElse(-1);
    }
    public static Object solveStar2(Map<Valve, Map<Valve, Integer>> path, List<Valve> clean,
                                    Map<String, Valve> valves) {
        Map<Set<Valve>, Integer> st = solve(path, clean, new HashSet<>(), valves.get("AA"),
                26, 0, new HashMap<>());
        return st.entrySet().stream().flatMapToInt(e -> st.entrySet().stream().filter(e2 -> {
            HashSet<Valve> comb = new HashSet<>(e.getKey());
            comb.addAll(e2.getKey());
            return comb.size() == e.getKey().size() + e2.getKey().size();
        }).mapToInt(e2 -> e.getValue() + e2.getValue())).max().orElse(-1);
    }
}
