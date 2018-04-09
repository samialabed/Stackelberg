package comp34120.ex2;

import comp34120.ex2.Utils.NeuralNetUtil;
import comp34120.ex2.Utils.PlatformUtil;
import comp34120.ex2.regression.LinearRegression;
import comp34120.ex2.regression.Regression;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// Linear Regression leader using NN
final class LinearRegressionLeader extends PlayerImpl {
    private final Regression regression;

    public LinearRegressionLeader() throws RemoteException, NotBoundException {
        super(PlayerType.LEADER, "Linear Regression NN Leader");
        this.regression = new LinearRegression(new NeuralNetUtil(PlatformUtil.getAllRecordsUntilDay(platform, 100)));
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
        regression.update(lastDayRecord);
        platform.log(playerType, "Last day profit " + PlatformUtil.computeProfit(lastDayRecord));

        // TODO(samialab): Implement price function
        int leaderNewPrice = 2;
        float predictedFollowerPrice = regression.predictFollowerPrice(newDate, leaderNewPrice);
        platform.log(playerType, "Predicted follower price: " + predictedFollowerPrice);

        platform.publishPrice(playerType, leaderNewPrice);
    }

    public static void main(final String[] p_args) throws RemoteException, NotBoundException {
        new LinearRegressionLeader();
    }
}
