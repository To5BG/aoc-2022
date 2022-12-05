package solutions;

@SuppressWarnings("unused")
public class Day5 {
    public static Object preprocess(String input, int star) {
        String[] arr = input.split("\n");
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(arr) : solveStar2(arr);
    }
    public static Object solve(String[] input) {
        return null;
    }
    public static Object solveStar1(String[] input) {
        return null;
    }
    public static Object solveStar2(String[] input) {
        return null;
    }
}
