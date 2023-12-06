package solutions;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class Day19 {
    static int geodeBest;
    static int maxTime;

    public static Object preprocess(String input, int star) {
        star = Math.min(Math.max(star, 1), 2);
        String[] blueprints = input.split("\n");
        List<int[]> costs = new ArrayList<>();
        for (String bprint : blueprints) {
            int[] curr = new int[6];
            for (int i = 0; i < 6; i++)
                curr[i] = Integer.parseInt(bprint.split("\\D+")[i + 2]);
            costs.add(curr);
        }
        return star == 1 ? solveStar1(costs) : solveStar2(costs);
    }

    public static int solve(int[] costs, int o, int c, int ob, int g, int or, int cr, int obr, int gr, int time) {
        if (time == maxTime) {
            geodeBest = Math.max(geodeBest, g);
            return g;
        }

        int minsLeft = maxTime - time;
        int maxGeodesPossible = g;
        for (int i = 0; i < minsLeft; i++) maxGeodesPossible += gr + i;
        if (maxGeodesPossible < geodeBest) return 0;

        int no = o + or;
        int nc = c + cr;
        int nob = ob + obr;
        int ng = g + gr;

        if (o >= costs[4] && ob >= costs[5])
            return solve(costs, no - costs[4], nc, nob - costs[5], ng, or, cr, obr, gr + 1, time + 1);
        if (cr >= costs[3] && obr < costs[5] && o >= costs[2] && c >= costs[3])
            return solve(costs, no - costs[2], nc - costs[3], nob, ng, or, cr, obr + 1, gr, time + 1);

        int best = 0;
        if (obr < costs[5] && o >= costs[2] && c >= costs[3])
            best = Math.max(best, solve(costs, no - costs[2], nc - costs[3], nob, ng, or, cr,
                    obr + 1, gr, time + 1));
        if (cr < costs[3] && o >= costs[1])
            best = Math.max(best, solve(costs, no - costs[1], nc, nob, ng, or, cr + 1, obr, gr, time + 1));
        if (or < 4 && o >= costs[0])
            best = Math.max(best, solve(costs, no - costs[0], nc, nob, ng, or + 1, cr, obr, gr, time + 1));
        if (o <= 4)
            best = Math.max(best, solve(costs, no, nc, nob, ng, or, cr, obr, gr, time + 1));
        return best;
    }

    public static Object solveStar1(List<int[]> costs) {
        int quality = 0;
        for (int i = 0; i < costs.size(); i++) {
            geodeBest = 0;
            maxTime = 24;
            quality += (i + 1) * solve(costs.get(i), 0, 0, 0, 0, 1, 0, 0, 0, 0);
        }
        return quality;
    }

    public static Object solveStar2(List<int[]> costs) {
        int quality = 1;
        for (int i = 0; i < Math.min(3, costs.size()); i++) {
            geodeBest = 0;
            maxTime = 32;
            quality *= solve(costs.get(i), 0, 0, 0, 0, 1, 0, 0, 0, 0);
        }
        return quality;
    }
}
