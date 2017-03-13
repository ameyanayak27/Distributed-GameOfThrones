
/**
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * FileName    : PlayerInterface.java
 * 
 * Description : Interface class defines the remote methods that can be 
 * used for Remote Invocation while communicating within Nodes in the system. 
 * This interface is implemented by players and bots.
 * 
 * @version  : PlayerInterface.java v 4.0  5/13/2015 10:00 PM
 * 
 * @author 	rss2159 (Rajesh Shetty)
 * 			akn6749 (Ameya Nayak)
 * 			uxb9472 (Utkarsh Bhatia)
 * 
 * 
 *=============================================================================
 */
import java.awt.Color;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


public interface PlayerInterface extends Remote 
{

   public void mDetails(Map<String,String> mD) throws RemoteException;
   public String getReply(int level, int coin, int soldier,String IP) throws RemoteException;
   public List<String> getNeigh()  throws RemoteException;
   public int get_coin() throws RemoteException;
   public int get_soldier() throws RemoteException;
   public void updateChild(int iBtnId, Color c, String sPlayer) throws RemoteException;
   public String getVID() throws RemoteException;
   public void EndGame(String sMessage) throws RemoteException;
   
}
