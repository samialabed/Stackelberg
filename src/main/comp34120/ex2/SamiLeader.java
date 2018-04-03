package comp34120.ex2;

import comp34120.ex2.accessor.DataAccessor;
import comp34120.ex2.accessor.DataAccessorImpl;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

// Linear Regression leader using NN
final class SamiLeader extends PlayerImpl {
    public static final String HISTORIC_DATA_FILEPATH = "data14.xls";
    private static DataAccessor historicDataAccessor;

    // TODO(samialab): Handle exception here
    public SamiLeader() throws RemoteException, NotBoundException {
        super(PlayerType.LEADER, "Sami Leader");
        try {
            historicDataAccessor = new DataAccessorImpl(HISTORIC_DATA_FILEPATH);
        } catch (IOException e) {
            // TODO(samialab): make this robust but for now just do what Alan Ramsay taught you
            e.printStackTrace();
            System.err.println("hide any errors that occur in synthesise.py.");
        }
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

    public static void main(final String[] p_args)
            throws RemoteException, NotBoundException {
        new SamiLeader();
        historicDataAccessor.getLeaderParameter();
        System.out.println("Works");
    }
}
