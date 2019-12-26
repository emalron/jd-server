import java.util.ArrayList;

import model.Rank;
import model.RankDAO;

public class Main {
    public static void main(String[] args) {
        RankDAO rDao = new RankDAO();

        rDao.addRank("jes", 1222, 123, "hahah");

        ArrayList<Rank> ranks = new ArrayList<Rank>();

        ranks = rDao.showAll();

        System.out.println("**ranking**");
        for(Rank u : ranks) {
            String out = u.getName() + " " + u.getScore();
            System.out.println(out);
        }

    }
}