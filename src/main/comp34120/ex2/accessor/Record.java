package comp34120.ex2.accessor;

import java.io.Serializable;

/**
 * Date structure for one record in the data file
 */
public final class Record implements Serializable {
    /* The date of the record */
    private final int date;
    /* The price of the leader */
    private final float leaderPrice;
    /* The price of the follower */
    private final float followerPrice;
    /* The cost of the follower */
    private final float cost;

    public Record(final int p_date, final float p_leaderPrice, final float p_followerPrice, final float p_cost) {
        date = p_date;
        leaderPrice = p_leaderPrice;
        followerPrice = p_followerPrice;
        cost = p_cost;
    }

    private int getDate() {
        return date;
    }

    private float getLeaderPrice() {
        return leaderPrice;
    }

    private float getFollowerPrice() {
        return followerPrice;
    }

    private float getCost() {
        return cost;
    }
}
