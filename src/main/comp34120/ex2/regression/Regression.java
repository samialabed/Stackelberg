package comp34120.ex2.regression;

import comp34120.ex2.Record;

public interface Regression {

    float predictFollowerPrice(int day, float leaderPrice, float unitCost);

    void update(Record record);
}