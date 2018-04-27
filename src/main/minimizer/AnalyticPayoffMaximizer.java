package minimizer;

import comp34120.ex2.math.calculus.Differentiation;
import comp34120.ex2.math.functions.RegressionPayoffFunction;
import comp34120.ex2.regression.Regression;

import java.util.Random;

public class AnalyticPayoffMaximizer implements PayoffMaximizer{
    private final Regression reactionFunction;

    public AnalyticPayoffMaximizer(Regression reactionFunction) {
        this.reactionFunction = reactionFunction;
    }

    @Override
    public double getOptimalPrice(int date) {
        // Start from multiple points to avoid ending up in a local maximum
        math.functions.IFunction payoffFunction = new RegressionPayoffFunction(this.reactionFunction, date);
        double optimalPayoff = -100000;
        double optimalPrice = 1.0;
        double learningRate = 0.001;

        Random random = new Random();
        for(int i = 0; i < 4; i++) {
            double price = 1.0 + 3 * random.nextDouble();
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
