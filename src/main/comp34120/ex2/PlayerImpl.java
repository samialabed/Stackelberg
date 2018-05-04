package comp34120.ex2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * To implement some common method of players
 */
public abstract class PlayerImpl implements Player {
    public enum LOG_LEVELS {NONE, DEBUG}

    /* The stub of the platform */
    protected Platform platform;
    protected LOG_LEVELS logLevel = LOG_LEVELS.NONE;
    /* The type of this player, i.e. LEADER or FOLLOWER */
    protected final PlayerType playerType;

    protected PlayerImpl(final PlayerType playerType, final String playerDisplayName)
            throws RemoteException, NotBoundException {
        this.playerType = playerType;
        registerRMI();
        registerPlatform(playerDisplayName);
    }

    protected void setLogLevel(LOG_LEVELS logLevel)
    {
        this.logLevel = logLevel;
    }
    /**
     * Register this instance to RMI registry
     *
     * @throws RemoteException
     */
    private void registerRMI() throws RemoteException {
        final Player player = (Player) UnicastRemoteObject.exportObject(this, 0);
        final Registry registry = LocateRegistry.getRegistry();
        registry.rebind(playerType.toString(), player);
    }

    /**
     * Get the stub of the platform, then call its initialization method to make it get the stub of this player
     *
     * @param playerDisplayName The name to be displayed in GUI
     * @throws RemoteException
     * @throws NotBoundException
     */
    private void registerPlatform(final String playerDisplayName) throws RemoteException, NotBoundException {
        final Registry registry = LocateRegistry.getRegistry();
        platform = (Platform) registry.lookup("Platform");
        platform.registerPlayer(playerType, playerDisplayName);
    }

    /**
     * To check the availability of this player
     *
     * @throws RemoteException
     */
    @Override
    public void checkConnection() throws RemoteException {
    }

    /**
     * To inform this instance the start of the simulation
     *
     * @param simulationSteps Indicates how many steps will this round of simulation perform
     * @throws RemoteException
     */
    @Override
    public void startSimulation(int simulationSteps) throws RemoteException {
        platform.log(playerType, "startSimulation(): Not supported yet.");
    }

    /**
     * To inform this instance the end of the simulation
     *
     * @throws RemoteException
     */
    @Override
    public void endSimulation() throws RemoteException {
        platform.log(playerType, "endSimulation(): Not supported yet.");
    }

    /**
     * To inform this instance the end of the session
     *
     * @throws RemoteException
     */
    @Override
    public void goodbye() throws RemoteException {
        platform.log(playerType, "goodbye(): Not supported yet.");
    }
}
