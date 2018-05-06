package comp34120.ex2.maximizer;

import comp34120.ex2.math.calculus.Differentiation;
import comp34120.ex2.math.functions.IFunction;
import comp34120.ex2.math.functions.RegressionPayoffFunction;
import comp34120.ex2.regression.Regression;

import java.util.Random;

public class AnalyticPayoffMaximizer implements PayoffMaximizer{
    private final Regression reactionFunction;
    private static final Random random = new Random();
    private static final double LOWER_BOUND = 1.0;
    private static final double HIGHER_BOUND = 4.0;

    public AnalyticPayoffMaximizer(Regression reactionFunction) {
        this.reactionFunction = reactionFunction;
    }

    @Override
    public double getOptimalPrice(double date) {
        // Start from multiple points to avoid ending up in a local maximum
        IFunction payoffFunction = new RegressionPayoffFunction(this.reactionFunction, date);
        double optimalPayoff = -100000;
        double optimalPrice = 1.0;
        double learningRate = 0.005;

        for(int i = 0; i < 4; i++) {
            double price = LOWER_BOUND + (HIGHER_BOUND - LOWER_BOUND) * random.nextDouble();
            // The maximum is usually found in 1000 steps from my tests
            for(int j = 0; j < 1000; j++) {
                double derivative = Differentiation.derivative(payoffFunction, price);
                price = price + learningRate * derivative;
            }

            double payoff = payoffFunction.eval(price);
            if(payoff > optimalPayoff && price >= 1.0) {
                optimalPayoff = payoff;
                optimalPrice = price;
            }
        }
        return optimalPrice;
    }
}
