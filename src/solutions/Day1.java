package solutions;

import java.nio.file.Path;

@SuppressWarnings("unused")
public class Day1 {
    public static Object preprocess(Path filepath, int star) {
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1() : solveStar2();
    }
    public static Object solveStar1() {
        return 3;
    }
    public static Object solveStar2() {
        return 2;
    }
}
