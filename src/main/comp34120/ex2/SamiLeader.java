package comp34120.ex2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// Linear Regression leader using NN
final class SamiLeader extends PlayerImpl {
    // TODO(samialab): Handle exception here
    public SamiLeader() throws RemoteException, NotBoundException {
        super(PlayerType.LEADER, "Sami Leader");
    }

    // TODO(samialab): Implement cleanup code
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
        //TODO(samialab): Put initialise code here
    }

    /**
     * To inform this instance to proceed to a new simulation day
     *
     * @param newDate The date of the new day
     * @throws RemoteException If the connection fails, the JVM will throw a RemoteException
     */
    @Override
    public void proceedNewDay(int newDate) throws RemoteException {
        Record newRecord = platform.query(playerType, newDate);
        // TODO(samialab): Implement price function
        int leaderNewPrice = 0;
        platform.publishPrice(playerType, leaderNewPrice);
    }
}
