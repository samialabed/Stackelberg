package comp34120.ex2.regression;

import comp34120.ex2.Record;

public interface Regression {

    float predictFollowerPrice(int day, double leaderPrice);

    void update(Record record);


}
