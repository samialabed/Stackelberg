package comp34120.ex2;

import java.io.Serializable;

/**
 * Date structure for one record in the data file. Has to be in this structure
 */
public final class Record implements Serializable {
    private static final long serialVersionUID = -2729479511679039076L;
    public final int m_date;
    public final float m_leaderPrice;
    public final float m_followerPrice;
    public final float m_cost;

    public Record(int date, float leaderPrice, float followerPrice, float cost) {
        this.m_date = date;
        this.m_leaderPrice = leaderPrice;
        this.m_followerPrice = followerPrice;
        this.m_cost = cost;
    }
}
