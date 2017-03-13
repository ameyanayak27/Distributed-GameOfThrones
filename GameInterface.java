/**
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * FileName    : GameInterface.java
 * Description :  Interface class defines the remote methods that can be 
 * used for Remote Invocation while communicating within Nodes in the system. 
 * This interface is implemented by GameServer.
 * 
 * @version  : GameInterface.java v 4.0  5/13/2015 10:00 PM
 * 
 * @author 	rss2159 (Rajesh Shetty)
 * 			akn6749 (Ameya Nayak)
 * 			uxb9472 (Utkarsh Bhatia)
 * 
 *=============================================================================
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface GameInterface extends Remote 
{
	public boolean updateMap(String sKey,String sValue) throws RemoteException;
	public HashMap<String,String> getMap() throws RemoteException;
	public String joinedPlayer(String IP) throws RemoteException;
	public void backup(String x, double factor) throws RemoteException;
	public  double find(String ip) throws RemoteException;
	public ArrayList<String> GetList() throws RemoteException;
	int findCoin(String ip) throws RemoteException;
	int findSoldier(String ip)throws RemoteException;
	void backup(String x, int soldiers, int coins) throws RemoteException;
	void countTerritories() throws RemoteException;
	void updateLevel(int i, int l) throws RemoteException;
	public void updateflag(String sKey) throws RemoteException;
	public String checkflag(String sKey) throws RemoteException;

}
