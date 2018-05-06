package comp34120.ex2.maximizer;

import comp34120.ex2.regression.Regression;

public class SimplePayoffMaximizer implements PayoffMaximizer{
    private static final double priceUpperBound = 10.0;

    private Regression reactionFunction;

    public SimplePayoffMaximizer(Regression reactionFunction) {
        this.reactionFunction = reactionFunction;
    }

    private double getPredictedPayoff(double date, double price){
        double followerPrice = reactionFunction.predictFollowerPrice(date, price);
        double demandModel = 2.0 - price + 0.3 * followerPrice;
        return (price - 1.0) * demandModel;
    }

    @Override
    public double getOptimalPrice(double date) {
        double optimalPayoff = -1000000;
        double optimalPrice = 1.0;
        for(double price = 1.0; price <= priceUpperBound; price += 0.005) {
            double predictedPayoff = getPredictedPayoff(date, price);
            if(predictedPayoff > optimalPayoff) {
                optimalPayoff = predictedPayoff;
                optimalPrice = price;
            }
        }
        return optimalPrice;
    }
}
