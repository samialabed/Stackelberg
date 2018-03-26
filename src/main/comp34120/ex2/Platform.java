package comp34120.ex2;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMI interface of Platform
 */
public interface Platform extends Remote {
    void checkConnection(final PlayerType p_type) throws RemoteException;

    void registerPlayer(final PlayerType p_type, final String p_displayName) throws RemoteException, NotBoundException;

    Record query(final PlayerType p_type, final int p_queryDate) throws RemoteException;

    void publishPrice(final PlayerType p_type, final float p_price) throws RemoteException;

    void log(final PlayerType p_type, final String p_text) throws RemoteException;
}
