import java.util.ArrayList;

import model.Rank;
import model.RankDAO;

public class Main {
    public static void main(String[] args) {
        RankDAO rDao = new RankDAO();

/*         rDao.addRank("jes", 1);
        rDao.addRank("poo", 13);
        rDao.addRank("ee", 4);
        rDao.addRank("32", 15);
        rDao.addRank("ema", 8); */

        ArrayList<Rank> ranks = new ArrayList<Rank>();

        ranks = rDao.showAll();

        System.out.println("**ranking**");
        for(Rank u : ranks) {
            String out = u.getName() + " " + u.getScore();
            System.out.println(out);
        }

        ranks = rDao.search("jes");

        System.out.println("");
        System.out.println("search result---");
        for(Rank u : ranks) {
            String out = u.getName() + " " + u.getScore();
            System.out.println(out);
        }

    }
}