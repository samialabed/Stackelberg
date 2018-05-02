package comp34120.ex2.utils;


import java.util.ArrayList;
import java.util.List;

public class StatisticalCollector {
    private List<Double> predictions;
    private List<Double> targets;
    private double totalProfit;

    public StatisticalCollector() {
        this.predictions = new ArrayList<>();
        this.targets = new ArrayList<>();
        this.totalProfit = 0;
    }

    public void addStatistics(double prediction, double target, double profit) {
        this.predictions.add(prediction);
        this.targets.add(target);
        this.totalProfit += profit;
    }

    @Override
    public String toString() {
        String repr = String.format("Total Profit is: %f\n", this.totalProfit);
        double mse = 0;
        for(int i = 0; i < predictions.size(); i++) {
            mse += Math.pow(targets.get(i) - predictions.get(i), 2);
        }
        mse /= (double)predictions.size();

        repr += String.format("Mean Squared Error: %f\n", mse);
        return repr;
    }
}
