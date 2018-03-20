******************
* 1.Introduction *
******************

This experiment platform is implemented using Java SE 6 with RMI, so that you can implement your own Java classes and run them without knowing the detail of the platform.

To do so, you need to implement a leader class by extending the abstract class PlayerImpl in package comp34120.ex2, and some essential source codes are under /src.

**********************
* 2.Running the demo *
**********************

A very simple example of leader class, SimpleLeader, has been provided, and its source code is under /src as well.

To run the simulation with the SimpleLeader, you need to

i) run

rmiregistry &

to enable RMI registration;

ii) run

java -classpath poi-3.7-20101029.jar: -Djava.rmi.server.hostname=127.0.0.1 comp34120.ex2.Main &

to run the GUI of the platform;

iii) run

java -Djava.rmi.server.hostname=127.0.0.1 SimpleLeader &

to run the SimpleLeader.

And after these steps, you can play with the GUI to get some ideas of how the platform works.

****************************************
* 3.Implementing your own leader class *
****************************************

3.1. How does the platform work

The platform is like a referee of the game. Every step of the simulation begins after the platform informing every player the beginning of a new day. And the players need to ask the platform for the price information, and finally submit a new price of the day to the platform to end its phase.

3.2. The platform interface

There are five method that platform provides for the players to call: (YOU DO NOT NEED TO IMPLEMENT THEM, THEY ARE PROVIDED BY THE PLATFORM)

3.2.1. Checking the status of the RMI connection

public void checkConnection(final PlayerType p_type)
	throws RemoteException;

It simply does nothing. If the connection fails, the JVM will throw a RemoteException, so that you know the failure of the connection. Normally nothing will be done by this method. You may not need this method. The platform provide this just in case you want to make your leader program more robust.

3.2.2. Registering to the platform

public void registerPlayer(final PlayerType p_type,
	final String p_displayName)
	throws RemoteException, NotBoundException;

Register this leader instance to the platform. Normally you will not need to call this method because RMI registeration procedure has already been implemented in the abstract class PlayerImpl which you need to extend to implement your own leader. This method will be called automatically in the constructor of the PlayerImpl which you need to call in the first line of the constructor of your own leader class.

3.2.3. Get the information of price and cost

public Record query(final PlayerType p_type,
	final int p_queryDate)
	throws RemoteException;

By calling this method, you can get the price of both the leader and the follower as well as the cost of a given day. The Record is a public class which contains the price and cost in it. This is a very important method you may want to use in your own leader.

3.2.4. Publish your new price

public void publishPrice(final PlayerType p_type,
	final float p_price)
	throws RemoteException;

Publish your new price by calling this method. After calculating your profit and get a optimal price, you need to call this method to finish your phase of this simulation day.

3.2.5. Output some information to the GUI

public void log(final PlayerType p_type,
	final String p_text)
	throws RemoteException;

By calling this method you can publish some information (e.g. debug information) on the GUI.

3.3. The player interface

3.3.1. Check the status of the RMI connection

public void checkConnection()
	throws RemoteException;

Similar to the method of the platform, this method does nothing, just for detection of connection failure. You do not need to override this method.

3.3.2. Inform the end of the program

public void goodbye()
	throws RemoteException;

The platform will call this method when you close the GUI of the platform. An example of the use of this method can be found in SimpleLeader, after calling which the program exits. You may make the choice whether override this method or not.

3.3.3. Start the simulation

public void startSimulation(final int p_steps)
	throws RemoteException;

The platform will call this method when the simulation begins. You may want to do some initialization here, otherwise you don't need to override it.

3.3.4. End the simulation

public void endSimulation()
	throws RemoteException;

The platform will call this method when the simulation ends. You may want to do some finalization here, otherwise you don't need to override it.

3.3.5. Proceed to a new day

public void proceedNewDay(final int p_date)
	throws RemoteException;

Platform will call this method when the simulation proceeds to a new day. This is the only method you *MUST* implement. Here you may want to ask the platform of the price and cost of the day before the current day. You may also put all your math stuff here to compute an optimal price. Your phase will end after your done everything here.

3.4. Summary of what you need to do

You can find the pseudo-code of a leader in the directory that you may want to follow. By implementing this code, you will get your own leader.

*********************************
* 4.Compiling the leader class *
*********************************

No fancy parameters for the compiling, just

javac YourLeader.java
