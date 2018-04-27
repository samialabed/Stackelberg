package comp34120.ex2;

import comp34120.ex2.regression.NonLinearRegression;
import comp34120.ex2.regression.Regression;
import comp34120.ex2.utils.NeuralNetUtil;
import comp34120.ex2.utils.PlatformUtil;
import comp34120.ex2.utils.StatisticalCollector;
import minimizer.AnalyticPayoffMaximizer;
import minimizer.PayoffMaximizer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// Linear Regression leader using NN
final class NonLinearRegressionLeader extends PlayerImpl {
    private final Regression regression;
    private final PayoffMaximizer maximizer;
    private final StatisticalCollector collector;

    public NonLinearRegressionLeader() throws RemoteException, NotBoundException {
        super(PlayerType.LEADER, "Linear Regression NN Leader");
        this.regression = new NonLinearRegression(new NeuralNetUtil(PlatformUtil.getAllRecordsUntilDay(platform, 100)));
        this.maximizer = new AnalyticPayoffMaximizer(regression);
        this.collector = new StatisticalCollector();
    }

    @Override
    public void goodbye() throws RemoteException {
        super.goodbye();
    }

    /**
     * @param simulationSteps Indicates how many steps will the simulation perform
     * @throws RemoteException If the connection fails, the JVM will throw a RemoteException
     */
    @Override
    public void startSimulation(int simulationSteps) throws RemoteException {
        super.startSimulation(simulationSteps);
    }

    /**
     * To inform this instance to proceed to a new simulation day
     *
     * @param newDate The date of the new day
     * @throws RemoteException If the connection fails, the JVM will throw a RemoteException
     */
    @Override
    public void proceedNewDay(int newDate) throws RemoteException {
        Record lastDayRecord = platform.query(playerType, newDate - 1);
        double lastDayPrediction = regression.predictFollowerPrice(newDate - 1, lastDayRecord.m_leaderPrice);
        double lastDayProfit = PlatformUtil.computeProfit(lastDayRecord);

        regression.update(lastDayRecord);
        double leaderNewPrice = maximizer.getOptimalPrice(newDate);
        float predictedFollowerPrice = regression.predictFollowerPrice(newDate, leaderNewPrice);
        platform.publishPrice(playerType, (float)leaderNewPrice);

        // Log data for this day
        collector.addStatistics(lastDayPrediction, lastDayRecord.m_followerPrice, lastDayProfit);
        platform.log(playerType, "Predicted follower price: " + predictedFollowerPrice);
        platform.log(playerType, "Last day profit " + lastDayProfit);

        if (newDate == 130) {
            platform.log(playerType, this.collector.toString());
        }
    }

    public static void main(final String[] p_args) throws RemoteException, NotBoundException {
        new NonLinearRegressionLeader();
    }
}
