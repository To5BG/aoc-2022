package solutions;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("unused")
public class Day8 {

    @FunctionalInterface
    public interface FourParameterFunctionByReturn {
        Integer apply(Integer[][] a, Integer[][] b, Integer i, Integer j);
    }

    public static Integer[][] transpose(Integer[][] a) {
        Integer[][] res = new Integer[a.length][a[0].length];
        IntStream.range(0, a.length).forEach(i -> IntStream.range(0, a[0].length)
                .forEach(j -> res[j][i] = a[i][j]));
        return res;
    }

    public static Integer[] reverse(Integer[] arr) {
        Integer[] rev = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) rev[i] = arr[arr.length - i - 1];
        return rev;
    }

    public static Object preprocess(String input, int star) {
        star = Math.min(Math.max(star, 1), 2);
        return star == 1 ? solveStar1(input) : solveStar2(input);
    }

    public static Stream<Integer> solve(String input, FourParameterFunctionByReturn f) {
        Integer[][] forest = input.lines().map(s -> s.chars().map(i -> i - 48)
                .boxed().toArray(Integer[]::new)).toArray(Integer[][]::new);
        Integer[][] tforest = transpose(forest);
        Integer[][] res = new Integer[forest.length][forest[0].length];
        for (int i = 0; i < res.length; i++)
            for (int j = 0; j < res[0].length; j++) {
                if (i == 0 || j == 0 || i == res.length - 1 || j == res[0].length - 1) res[i][j] = 1;
                res[i][j] = f.apply(forest, tforest, i, j);
            }
        return Arrays.stream(res).flatMap(Arrays::stream);
    }

    public static Object solveStar1(String input) {
        return solve(input, (a, b, i, j) ->
                (Arrays.stream(a[i]).limit(j).dropWhile(num -> num < a[i][j]).findAny().isEmpty()
                        | Arrays.stream(b[j]).limit(i).dropWhile(num -> num < a[i][j]).findAny().isEmpty()
                        | Arrays.stream(a[i]).skip(j + 1).dropWhile(num -> num < a[i][j]).findAny().isEmpty()
                        | Arrays.stream(b[j]).skip(i + 1).dropWhile(num -> num < a[i][j]).findAny().isEmpty()) ? 1 : 0)
                .filter(b -> b == 1).count();
    }

    public static Object solveStar2(String input) {
        return solve(input, (a, b, i, j) -> {
            long down = Arrays.stream(b[j]).skip(i + 1).takeWhile(num -> num < a[i][j]).count() + 1;
            long right = Arrays.stream(a[i]).skip(j + 1).takeWhile(num -> num < a[i][j]).count() + 1;
            long left = Arrays.stream(reverse(Arrays.stream(a[i]).limit(j).toArray(Integer[]::new)))
                    .takeWhile(num -> num < a[i][j]).count();
            long up = Arrays.stream(reverse(Arrays.stream(b[j]).limit(i).toArray(Integer[]::new)))
                    .takeWhile(num -> num < a[i][j]).count();
            return (int) (((b[j].length == i + down ? -1 : 0) + down) *
                    ((a[i].length == j + right ? -1 : 0) + right) *
                    ((i != up ? 1 : 0) + up) *
                    ((j != left ? 1 : 0) + left));
        }).max(Integer::compareTo).orElse(-1);
    }
}
