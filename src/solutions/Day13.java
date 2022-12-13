package solutions;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.*;

@SuppressWarnings("unused")
public class Day13 {
    public record Packet(List<Packet> desc, Object[] vals) implements Comparable<Packet> {
        public Packet(String s) {
            this(new ArrayList<>(), new Object[]{0, true, ""});
            vals[2] = s;
            if (s.equals("[]")) vals[0] = -1;
            if (!s.startsWith("[")) vals[0] = Integer.parseInt(s);
            else {
                s = s.substring(1, s.length() - 1);
                int y = 0; StringBuilder temp = new StringBuilder();
                for (char c : s.toCharArray()) {
                    if (c == ',' && y == 0) {
                        desc.add(new Packet(temp.toString()));
                        temp = new StringBuilder();
                    } else {
                        y += (c == '[') ? 1 : (c == ']') ? -1 : 0;
                        temp.append(c);
                    }
                }
                if (temp.length() > 0) desc.add(new Packet(temp.toString()));
                vals[1] = false;
            }
        }
        public int compareTo(Packet o) {
            if ((boolean)vals[1] && (boolean)o.vals[1]) return (int)vals[0] - (int)o.vals[0];
            if (!(boolean)vals[1] && !(boolean)o.vals[1]) {
                for (int i = 0; i < Math.min(desc.size(), o.desc.size()); i++) {
                    int val = desc.get(i).compareTo(o.desc.get(i));
                    if (val != 0) return val;
                }
                return desc.size() - o.desc.size();
            }
            Packet l = (boolean)vals[1] ? new Packet("[" + vals[0] + "]") : this;
            Packet r = (boolean)o.vals[1] ? new Packet("[" + o.vals[0] + "]") : o;
            return l.compareTo(r);
        }
    }
    public static Object preprocess(String input, int star) {
        Packet[] pairs = input.lines().filter(s -> !s.equals("")).map(Packet::new).toArray(Packet[]::new);
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(pairs) : solveStar2(Stream.concat(Arrays.stream(pairs),
                Stream.of(new Packet("[[2]]"), new Packet("[[6]]"))).toArray(Packet[]::new));
    }

    public static Object solveStar1(Packet[] input) {
        return IntStream.range(0, input.length / 2).filter(i -> input[2 * i].compareTo(input[2 * i + 1]) < 0)
                .map(i -> i + 1).sum();
    }

    public static Object solveStar2(Packet[] input) {
        AtomicInteger ai = new AtomicInteger(1);
        return IntStream.range(1, input.length + 1).boxed().sorted(Comparator.comparing(i -> input[i - 1]))
                .map(i -> new Integer[]{ai.getAndIncrement(), i})
                .filter(e -> input[e[1] - 1].vals[2].equals("[[2]]") || input[e[1] - 1].vals[2].equals("[[6]]"))
                .reduce(new Integer[]{1, 0}, (acc, e) -> new Integer[]{acc[0] * e[0], 0})[0];
    }
}
