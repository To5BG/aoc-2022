package solutions;

@SuppressWarnings("unused")
public class Template {
    public static Object preprocess(String input, int star) {
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(input) : solveStar2(input);
    }

    public static Object solve(String input) {
        return null;
    }

    public static Object solveStar1(String input) {
        return null;
    }

    public static Object solveStar2(String input) {
        return null;
    }
}
