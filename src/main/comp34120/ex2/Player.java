package comp34120.ex2;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMI interface of the player
 */
public interface Player extends Remote {
    void checkConnection() throws RemoteException;

    void goodbye() throws RemoteException;

    void startSimulation(final int p_steps) throws RemoteException;

    void endSimulation() throws RemoteException;

    void proceedNewDay(final int p_date) throws RemoteException;
}
