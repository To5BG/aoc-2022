package solutions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class Day2 {
    public static Map<String, Integer> lookupStar1 = new HashMap<>();
    public static Map<String, Integer> lookupStar2 = new HashMap<>();

    public static Object preprocess(Path filepath, int star) {
        String input;
        try {
            input = Files.readString(filepath);
        } catch (IOException e) {
            System.out.println("IO bad");
            return null;
        }
        String[] rounds = input.split("\n");
        star = Math.min(Math.max(star, 1), 2);
        for (int i = 0; i < 3; i++) {
            // x y z
            lookupStar1.put((char)(88 + i) + "", i + 1);
            lookupStar2.put((char)(88 + i) + "", 3 * i);
            // a z, b y, c x
            lookupStar1.put((char)(65 + i) + " " + (char)(90 - i), 3 * i);
            lookupStar2.put((char)(65 + i) + " " + (char)(90 - i), 2);
            // b x, c z, a y
            lookupStar1.put((char)(65 + (i + 1) % 3) + " " + (char)(90 - (i + 2) % 3), 3 * i);
            lookupStar2.put((char)(65 + (i + 1) % 3) + " " + (char)(90 - (i + 2) % 3), 1);
            // c y, a x, b z
            lookupStar1.put((char)(65 + (i + 2) % 3) + " " + (char)(90 - (i + 1) % 3), 3 * i);
            lookupStar2.put((char)(65 + (i + 2) % 3) + " " + (char)(90 - (i + 1) % 3), 3);
        }
        return solve(rounds, star == 1 ? lookupStar1 : lookupStar2);
    }
    public static Object solve(String[] input, Map<String, Integer> lookup) {
        return Arrays.stream(input).map(s -> lookup.get(s) + lookup.get(s.charAt(2) + ""))
                .reduce(0, Integer::sum);
    }
}
