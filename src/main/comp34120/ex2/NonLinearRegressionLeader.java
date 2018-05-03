package comp34120.ex2;

import comp34120.ex2.maximizer.SimplePayoffMaximizer;
import comp34120.ex2.regression.NonLinearRegression;
import comp34120.ex2.regression.Regression;
import comp34120.ex2.utils.NeuralNetUtil;
import comp34120.ex2.utils.PlatformUtil;
import comp34120.ex2.utils.StatisticalCollector;
import comp34120.ex2.maximizer.AnalyticPayoffMaximizer;
import comp34120.ex2.maximizer.PayoffMaximizer;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// Non Linear Regression leader using NN
final class NonLinearRegressionLeader extends PlayerImpl {
    private PayoffMaximizer maximizer;
    private StatisticalCollector collector;
    private Regression regression;

    public NonLinearRegressionLeader() throws RemoteException, NotBoundException {
        super(PlayerType.LEADER, "Non Linear Regression NN Leader");
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
        regression = new NonLinearRegression(new NeuralNetUtil(PlatformUtil.getAllRecordsUntilDay(platform, 100)));
        maximizer = new SimplePayoffMaximizer(regression);
        collector = new StatisticalCollector();
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
