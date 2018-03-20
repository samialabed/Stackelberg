import comp34120.ex2.PlayerImpl;
import comp34120.ex2.PlayerType;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * A pseudo leader. The members m_platformStub and m_type are declared
 * in the PlayerImpl, and feel free to use them. You may want to check
 * the implementation of the PlayerImpl. You will use m_platformStub to access
 * the platform by calling the remote method provided by it.
 * @author Xin
 */
final class PseudoLeader
	extends PlayerImpl
{
	/**
	 * In the constructor, you need to call the constructor
	 * of PlayerImpl in the first line, so that you don't need to
	 * care about how to connect to the platform. You may want to throw
	 * the two exceptions declared in the prototype, or you may handle it
	 * by using "try {} catch {}". It's all up to you.
	 * @throws RemoteException
	 * @throws NotBoundException
	 */
	PseudoLeader()
		throws RemoteException, NotBoundException
	{
		/* The first parameter *MUST* be PlayerType.LEADER, you can change
		 * the second parameter, the name of the leader, such as "My Leader" */
		super(PlayerType.LEADER, "Pseudo Leader");
	}

	/**
	 * You may want to delete this method if you don't want modify
	 * the original connection checking behavior of the platform.
	 * Actually I recommend you to delete this method from your own code
	 * @throws RemoteException If implemented, the RemoteException *MUST* be
	 * thrown by this method
	 */
	@Override
	public void checkConnection()
		throws RemoteException
	{
		super.checkConnection();
		//TO DO: delete the line above and put your own code here
	}

	/**
	 * You may want to delete this method if you don't want the platform
	 * to control the exit behavior of your leader class
	 * @throws RemoteException If implemented, the RemoteException *MUST* be
	 * thrown by this method
	 */
	@Override
	public void goodbye()
		throws RemoteException
	{
		super.goodbye();
		//TO DO: delete the line above and put your own exit code here
	}

	/**
	 * You may want to delete this method if you don't want to do any
	 * initialization
	 * @param p_steps Indicates how many steps will the simulation perform
	 * @throws RemoteException If implemented, the RemoteException *MUST* be
	 * thrown by this method
	 */
	@Override
	public void startSimulation(int p_steps)
		throws RemoteException
	{
		super.startSimulation(p_steps);
		//TO DO: delete the line above and put your own initialization code here
	}

	/**
	 * You may want to delete this method if you don't want to do any
	 * finalization
	 * @throws RemoteException If implemented, the RemoteException *MUST* be
	 * thrown by this method
	 */
	@Override
	public void endSimulation()
		throws RemoteException
	{
		super.endSimulation();
		//TO DO: delete the line above and put your own finalization code here
	}

	/**
	 * To inform this instance to proceed to a new simulation day
	 * @param p_date The date of the new day
	 * @throws RemoteException This exception *MUST* be thrown by this method
	 */
	@Override
	public void proceedNewDay(int p_date)
		throws RemoteException
	{
		/*
		 * Check for new price
		 * Record l_newRecord = m_platformStub.query(m_type, p_date);
		 *
		 * Your own math model to compute the price here
		 * ...
		 * float l_newPrice = ....
		 *
		 * Submit your new price, and end your phase
		 * m_platformStub.publishPrice(m_type, l_newPrice);
		 */
	}
}
