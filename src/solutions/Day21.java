package solutions;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class Day21 {
    public record Monkey (Operation op, String[] names, Monkey[] monkes, Long v) {
        public long getValue() { return (v == null) ? op.apply(monkes[0].getValue(), monkes[2].getValue()) : v; }
        public String getName() { return this.names[1]; }
        public void linkMonkeys(final Map<String, Monkey> allMonkeys) {
            monkes[0] = allMonkeys.get(names[0]); if (monkes[0] != null) monkes[0].monkes[1] = this;
            monkes[2] = allMonkeys.get(names[2]); if (monkes[2] != null) monkes[2].monkes[1] = this;
        }
        public long calculateNeededValue() {
            return ("root".equals(monkes[1].names[1])) ? monkes[1].getOtherChild(this).getValue()
                    : monkes[1].op.applyInverse(monkes[1].getOtherChild(this).getValue(),
                    monkes[1].calculateNeededValue(), monkes[1].monkes[0] == this);
        }
        private Monkey getOtherChild(final Monkey monkey) { return monkes[monkey == monkes[0] ? 2 : 0]; }
    }
    public enum Operation {
        ADD,
        SUBTRACT,
        MULTIPLY,
        DIVIDE;
        public static Operation save(char operation) {
            return switch (operation) {
                case '+' -> ADD; case '-' -> SUBTRACT; case '*' -> MULTIPLY; case '/' -> DIVIDE;
                default -> throw new IllegalArgumentException();
            };
        }
        public long apply(long x, long y) {
            return switch (this) {
                case ADD -> x + y; case SUBTRACT -> x - y; case MULTIPLY -> x * y; case DIVIDE -> x / y;
            };
        }
        public long applyInverse(long in, long out, boolean isLeft) {
            return switch (this) {
                case ADD -> out - in; case MULTIPLY -> out / in;
                case SUBTRACT -> isLeft ? in + out : in - out; case DIVIDE -> isLeft ? in * out : in / out;
            };
        }
    }
    public static Monkey createMonke(String str) {
        Long v = null; Operation op = null;
        String[] names = new String[3]; names[1] = str.substring(0, 4);
        if (str.length() == 17) {
            op = Operation.save(str.charAt(11));
            names[0] = str.substring(6, 10); names[2] = str.substring(13);
        } else v = Long.parseLong(str.substring(6));
        return new Monkey(op, names, new Monkey[3], v);
    }
    public static Object preprocess(String input, int star) {
        final Map<String, Monkey> monkes = input.lines().map(String::trim).map(Day21::createMonke)
                .collect(Collectors.toMap(Monkey::getName, Function.identity()));
        monkes.forEach((n, m) -> m.linkMonkeys(monkes));
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(monkes) : solveStar2(monkes);
    }
    public static Object solveStar1(Map<String, Monkey> monke) {
        return monke.get("root").getValue();
    }
    public static Object solveStar2(Map<String, Monkey> monke) {
        return monke.get("humn").calculateNeededValue();
    }
}
