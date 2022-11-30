import java.lang.reflect.Method;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        while (true) {
            System.out.print("Choose a day:");
            int inp = scr.nextInt();
            if (inp <= 0 || inp > 25) break;
            Path fp = Path.of("inputs\\day" + inp + ".txt");
            try {
                Method solver = Class.forName("solutions.Day" + inp)
                        .getMethod("preprocess", Path.class, int.class);
                System.out.println("Star 1 Result: " +
                        solver.invoke(null, fp, 1));
                System.out.println("Star 2 Result: " +
                        solver.invoke(null, fp, 2));
            } catch (Exception ignore) {
                break;
            }
        }
    }
}