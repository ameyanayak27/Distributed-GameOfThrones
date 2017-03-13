

/**
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * FileName    : NodeInterface.java
 * Description : Interface class defines the remote methods that can be 
 * used for Remote Invocation while communicating within Players for the critical section.
 * @version  : NodeInterface.java v 4.0  5/13/2015 10:00 PM
 * 
 * @author 	rss2159 (Rajesh Shetty)
 * 			akn6749 (Ameya Nayak)
 * 			uxb9472 (Utkarsh Bhatia)
 * 
 * 
 *=============================================================================
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;


public interface NodeInterface extends Remote
{
	//Returns all the Active nodes in the List
    public List getAllNodes() throws RemoteException; 
    //Returns the MAP<NodeIP,NodeName> of Nodes in the networks . 
    public Map getNodesInfo() throws RemoteException;
    //Request to enter the Critcal Section or Shared Resource
    public String RequestCS(String sPID,long sTI) throws RemoteException;
    //Store a Remote Reply approving its access to Critical section
    public void StoreRemoteReplies(String sIP,String sReply) throws RemoteException;
    //Check if the Remote Server is Alive by Pinging or Remotely invoking the class
    public boolean CheckIfAlive() throws RemoteException;
    //View the details on who all have updated the Critical Section
    public List ViewCSLog() throws RemoteException;
    //After entering the Critical Section Update the Critical Section
    public void UpdateCriticalSection(String sMessage) throws RemoteException;
}
