package comp34120.ex2.regression;

import comp34120.ex2.Record;

public interface Regression {

    float predictFollowerPrice(double day, double leaderPrice);

    void update(Record record);


}
