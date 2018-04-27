package comp34120.ex2.math.functions;

import comp34120.ex2.regression.Regression;

public class RegressionPayoffFunction implements math.functions.IFunction {
    private final Regression regression;
    private final double date;

    public RegressionPayoffFunction(Regression regression, double date){
        this.regression = regression;
        this.date = date;
    }

    @Override
    public double eval(double price) {
        double followerPrice = regression.predictFollowerPrice(date, price);
        double demandModel = 2.0 - price + 0.3 * followerPrice;
        return (price - 1.0) * demandModel;
    }
}
