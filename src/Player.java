package comp34120.ex2;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * The RMI interface of the player
 * @author Xin
 */
public interface Player
	extends Remote
{
	public void checkConnection()
		throws RemoteException;

	public void goodbye()
		throws RemoteException;

	public void startSimulation(final int p_steps)
		throws RemoteException;

	public void endSimulation()
		throws RemoteException;

	public void proceedNewDay(final int p_date)
		throws RemoteException;
}
