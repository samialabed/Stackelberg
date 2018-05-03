package comp34120.ex2;

import comp34120.ex2.maximizer.PayoffMaximizer;
import comp34120.ex2.maximizer.SimplePayoffMaximizer;
import comp34120.ex2.regression.LSTMRegression;
import comp34120.ex2.regression.LinearRegression;
import comp34120.ex2.regression.NonLinearRegression;
import comp34120.ex2.regression.Regression;
import comp34120.ex2.utils.NeuralNetUtil;
import comp34120.ex2.utils.PlatformUtil;
import comp34120.ex2.utils.StatisticalCollector;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// Linear Regression leader using NN
final class Group5Leader extends PlayerImpl {
    private PayoffMaximizer maximizer;
    private StatisticalCollector collector;
    private Regression regression;
    final private String leaderStrategy;

    public Group5Leader(String leaderType) throws RemoteException, NotBoundException {
        super(PlayerType.LEADER, leaderType + " Regression NN Leader");
        this.leaderStrategy = leaderType;
    }

    /**
     * @param simulationSteps Indicates how many steps will the simulation perform
     * @throws RemoteException If the connection fails, the JVM will throw a RemoteException
     */
    @Override
    public void startSimulation(int simulationSteps) throws RemoteException {
        NeuralNetUtil neuralNetUtil = new NeuralNetUtil(PlatformUtil.getAllRecordsUntilDay(platform, 100));
        switch (leaderStrategy) {
            case "linear":
                this.regression = new LinearRegression(neuralNetUtil);
                break;
            case "nonlinear":
                this.regression = new NonLinearRegression(neuralNetUtil);
                break;
            case "lstm":
                this.regression = new LSTMRegression(neuralNetUtil);
                break;
            default:
                System.err.println("Unknown leader strategy selected");
                System.exit(-1);
        }

        this.regression = new LinearRegression(neuralNetUtil);
        this.maximizer = new SimplePayoffMaximizer(regression);
        this.collector = new StatisticalCollector();
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
        platform.publishPrice(playerType, (float) leaderNewPrice);

        // Log data for this day
        collector.addStatistics(lastDayPrediction, lastDayRecord.m_followerPrice, lastDayProfit);
        platform.log(playerType, "Predicted follower price: " + predictedFollowerPrice);
        platform.log(playerType, "Last day profit " + lastDayProfit);


        if (newDate == 130) {
            platform.log(playerType, this.collector.toString());
        }
    }

    public static void main(final String[] p_args) throws RemoteException, NotBoundException {
        if (p_args.length != 1) {
            System.err.println("Usage: java Group5Leader (linear| nonlinear| lstm)");
            System.exit(-1);
        }
        String leaderType = p_args[0].toLowerCase();
        if (!(leaderType.equals("linear") || leaderType.equals("nonlinear") || leaderType.equals("lstm"))) {
            System.err.println("Usage: java Group5Leader (linear| nonlinear| lstm)");
            System.exit(-1);
        }
        new Group5Leader(leaderType);
    }
}
