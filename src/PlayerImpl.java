package comp34120.ex2;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * To implement some common method of players
 * @author Xin
 */
public abstract class PlayerImpl
	implements Player
{
	/* The stub of the platform */
	protected Platform m_platformStub;
	/* The type of this player, i.e. LEADER or FOLLOWER */
	protected final PlayerType m_type;

	protected PlayerImpl(final PlayerType p_type,
		final String p_displayName)
		throws RemoteException, NotBoundException
	{
		m_type = p_type;
		registerRMI();
		registerPlatform(p_displayName);
	}

	/**
	 * Register this instance to RMI registry
	 * @throws RemoteException
	 */
	private void registerRMI()
		throws RemoteException
	{
		final Player l_playerStub = (Player) UnicastRemoteObject.exportObject(this, 0);
		final Registry l_registry = LocateRegistry.getRegistry();
		l_registry.rebind(m_type.toString(), l_playerStub);
	}

	/**
	 * Get the stub of the platform, then call its initialization method to make
	 * it get the stub of this player
	 * @param p_displayName The name to be displayed in GUI
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	private void registerPlatform(final String p_displayName)
		throws RemoteException, NotBoundException
	{
		final Registry l_registry = LocateRegistry.getRegistry();
		m_platformStub = (Platform) l_registry.lookup("Platform");
		m_platformStub.registerPlayer(m_type, p_displayName);
	}

	/**
	 * To check the availability of this player
	 * @throws RemoteException
	 */
	@Override
	public void checkConnection()
		throws RemoteException {}

	/**
	 * To inform this instance the start of the simulation
	 * @param p_steps Indicates how many steps will this round of simulation perform
	 * @throws RemoteException
	 */
	@Override
	public void startSimulation(int p_steps)
		throws RemoteException
	{
		m_platformStub.log(m_type, "startSimulation(): Not supported yet.");
	}

	/**
	 * To inform this instance the end of the simulation
	 * @throws RemoteException
	 */
	@Override
	public void endSimulation()
		throws RemoteException
	{
		m_platformStub.log(m_type, "endSimulation(): Not supported yet.");
	}

	/**
	 * To inform this instance the end of the session
	 * @throws RemoteException
	 */
	@Override
	public void goodbye()
		throws RemoteException
	{
		m_platformStub.log(m_type, "goodbye(): Not supported yet.");
	}
}
