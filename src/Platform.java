package comp34120.ex2;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMI interface of Platform
 * @author Xin
 */
public interface Platform
	extends Remote
{
	public void checkConnection(final PlayerType p_type)
		throws RemoteException;

	public void registerPlayer(final PlayerType p_type,
		final String p_displayName)
		throws RemoteException, NotBoundException;
	
	public Record query(final PlayerType p_type,
		final int p_queryDate)
		throws RemoteException;

	public void publishPrice(final PlayerType p_type,
		final float p_price)
		throws RemoteException;

	public void log(final PlayerType p_type,
		final String p_text)
		throws RemoteException;
}
