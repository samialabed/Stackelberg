package comp34120.ex2;


import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMI interface of Platform
 */
public interface Platform extends Remote {
    void checkConnection(final PlayerType playerType) throws RemoteException;

    void registerPlayer(final PlayerType type, final String displayName) throws RemoteException, NotBoundException;

    Record query(final PlayerType type, final int date) throws RemoteException;

    void publishPrice(final PlayerType playerType, final float price) throws RemoteException;

    void log(final PlayerType playerType, final String text) throws RemoteException;
}
