/**
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * FileName    : RANode.java
 * Description : The programs is a simulation of the Ricart Agarwal Distributed Mutual 
 * Exclusion Algorithm. The RANode.java represents a bootstrap server or a 
 * Node on a particular server. The BootStrap Server is the process that 
 * holds the Shared Memory or the Critical Section. Only one node can acccess 
 * the Critical serction and the BootStrap Server has information of all 
 * nodes in the system
 * 
 * @version  : RANode.java v 4.0  5/13/2015 10:00 PM
 * 
 * @author 	rss2159 (Rajesh Shetty)
 * 			akn6749 (Ameya Nayak)
 * 			uxb9472 (Utkarsh Bhatia)
 * 
 *=============================================================================
 */

import java.net.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.io.*;

public class RANode extends UnicastRemoteObject implements NodeInterface,Serializable
{
	
	private static final long serialVersionUID = 1L; 
	static String sRole = ""; //Current Role of the Node
	static String sName = ""; //Friendly Name of the Current Node.
	static String sBS = ""; // IP Address of the BootStrap Server
	static String sMyIP = ""; //IP of the server running the program
	static String sStub =""; //StubName 
    static Socket socNode = null;
    
    //Variable Independent of the Role 
    static List<String> LNodes = new ArrayList<String>(); //List of Nodes. Not Used
  	static List<String> LNodesIP =  new ArrayList<String>(); //List of IP of the NOdes. Not used
  	//A Map for storing the List of All Nodes Present
  	static Map<String,String> mNodes = new HashMap<String,String>();
  	//A HasMap for Storing Remote Replies.
  	static Map<String,String> mReplyNodes = new HashMap<String,String>();
	
    //Variable Specific to BootStrap Server
  	static ServerSocket socServer = null; //Server Socket
  	static Socket socClient = null; //Server Socket
  	static String sCriticalSection = "Critical Section Unchanged "; //Critical Section is currently a String
  	static List<String> sCSLog = new ArrayList<String>(); // Maintains Log of the Critical Section.
  	static Queue<String> qCSList = new LinkedList<String>(); // List of All the waiting processes.
  	
  	//Variable Specific to ProcessNode
  	static String sCurrentState = ""; //Current State of the Process Holding the node
  	static String sNodeMessage = ""; //Not used.
  	static long lDateTime = 0; //DateTimeStampf 
  
  	/**
  	 * Constructor to Initialize the Role and State of Critical Section.
  	 * @param sR
  	 * @throws RemoteException
  	 */
  	public RANode(String sR) throws RemoteException
  	{
  		sRole = sR;
  		//If the node is recovering the lock on the CS is 
  		//by default released.
  		sCurrentState = "Released";
  	}
  	
  	/**
  	 * Constructor to initiate the Role of the Program.
  	 * @throws RemoteException
  	 */
	protected RANode() throws RemoteException 
	{
		super();
		//Printing Menu
		/*
		System.out.println(" \n\n      Simulation of Ricart-Agarwala Distributed Mutual Exclusion Algorithm\n\n");
		System.out.println("\n\nThis Node needs to Work as a : - \n\n");
		System.out.println("   1. BootStrapServer");
		System.out.println("   2. Process Node");
		System.out.print("     Enter 1 or 2 appropriately : ");
		Scanner sc  = new Scanner(System.in);
		int iChoice  = sc.nextInt();
		switch(iChoice)
		{
		  case 1 : StartBootStrapping(); break;
		  case 2 : */
		  InitializeNode();
		  /*;break;
		  default: System.out.println("  The values entered is not valid. Please try again");
		           break;
		}
		//sc.close();*/
	}
	
    /**
     * Initialize The Process Node
     */
    private void InitializeNode() 
    {
    	 
    	try 
    	{
    		//Get the Process Name and BootStrap Server it is trying to connect
    	  Scanner sc = new Scanner(System.in);
    	  //System.out.print( " \nEnter the Name for the Node : ");
    	  //sName = sc.nextLine();
    	  sName = sMyIP;
    	  //System.out.print( " \nEnter the IP Address of the BootStrap Server : ");
     	  //sBS = sc.nextLine();
    	  sBS = "129.21.30.38";
     	  //Conncet to the BSS Server
   		  Socket socNode = new Socket(sBS,8081);
		  System.out.println("\n   Connection Established");
		  //Write to Output stream
		  PrintWriter pw = new PrintWriter(new OutputStreamWriter(socNode.getOutputStream()));
		  BufferedReader br = new BufferedReader(new InputStreamReader(socNode.getInputStream()));
		  //Send the NodeName to the BootStrap Server
		  pw.println(sName);
		  pw.flush();
		  pw.println(Inet4Address.getLocalHost().getHostAddress().toString());
		  pw.flush();
		  //Recieve the Stub Name
		  sStub = br.readLine();
		  //System.out.println("Recived Stub "+sStub);
		  pw.close();
	   	  br.close();
	   	  socNode.close();
	   	  //Store the IP of the current Host
	   	  sMyIP =  Inet4Address.getLocalHost().getHostAddress().toString();
	   	  
	   	  //Create a refernce for the object in RMI Registry
	     // RANode obj = new RANode(sName);
		 // Registry rNode = LocateRegistry.createRegistry(2159);
		 // rNode.rebind("Node", obj);
	  		
    	} catch (IOException e) 
    	{
			e.printStackTrace();
		}	 
	}

    public static void Register() throws RemoteException
    {
    	  RANode obj = new RANode(sName);
		  Registry rNode = LocateRegistry.createRegistry(2159);
		  rNode.rebind("Node", obj);
    }
  
	/**
	 * Starts the BootStrap Server
	 */
    private void StartBootStrapping() 
	{/*
		try 
		{
   		  //Register a Stub with Name BSS ( 2159 I am rss2159 ) 
	      RANode obj = new RANode("BSS");
		  Registry rBSS = LocateRegistry.createRegistry(2159);
		  //rBSS.rebind("BSS", obj);
		  rBSS.rebind("Node", obj);
		
		  //Start the BootStrapServer
		  System.out.println("\n    BootStrap Server Initializing ");
		  //Start the server socket 
		  socServer = new ServerSocket(8081);
		  String sIPAdress = Inet4Address.getLocalHost().toString();
		  System.out.println("\n    BootStrapServer is now running on : " + sIPAdress+" Port 8081 \n");
		  System.out.println("\n    BootStrapServer is now available to accept request \n\n\n ");
		    
		} catch (IOException e) 
		{
		   System.out.println("\n   Something went Wrong while initializing BootStrap ... \n\n");
			e.printStackTrace();
		}

	    //Keep On Accepting Connections
	    for(;;)
	    {
	    	try
	    	{
	    	   Socket soc = socServer.accept();
	    	   //System.out.println("  Connection established");
	    	   PrintWriter pw  = new PrintWriter( new OutputStreamWriter(soc.getOutputStream()));
	    	   BufferedReader br = new BufferedReader(new InputStreamReader(soc.getInputStream()));
	    	   String sNodeName = br.readLine();
	    	   String sNodeIP = br.readLine();
	    	   System.out.println("\n  Recieved Request From "+sNodeName+" : "+sNodeIP);
	    	   LNodes.add(sNodeName);
	    	   LNodesIP.add(sNodeIP);
	    	   //Add the new node if its was not added previously
	    	   if(!mNodes.containsKey(sNodeIP))
	    	      mNodes.put(sNodeIP, sNodeName);
	    	   else
	    		   System.out.println("\nSeems like "+sNodeName+" had crashed and has now recovered\n");
	    	   //Sending the stub name to the user 
	    	   pw.println("BSS");
	    	   pw.flush();
	    	   pw.close();
	    	   br.close();
	    	   soc.close();
	    	}
	    	catch(Exception e)
	    	{
	    		System.out.println("\nSomthing went wrong while connecting the BootStrapServer. You can do the following \n");
	    		System.out.println("\n   1. Check if the BSS is already running ?");
	    		System.out.println("   2. Go throuhg the StartBootStrapping Function\n");
	    		System.exit(0);
	    		//e.printStackTrace();
	    	}
	    }
	    */
	}


	/**
	 * EnterCS sends are request to all the nodes currently active in the systems and 
	 * follows the Ricart-Agarwala algorithm to achieve the mutual exclusion in the 
	 * Distributed System.
	 */
    public static void EnterCS()
	{
		
		try
		{
	
		  //Change State Of current process
		  System.out.println("\n\n Requesting Access for Critical Section \n\n");
		  //Reset the Remote Request Map
		  mReplyNodes.clear();
		  sCurrentState = "Wanted"; // As you want to access the CS
		  //Prepare Message<ProcessId,TimeStamp>
		  lDateTime = System.currentTimeMillis(); //RecordingTimeStamp
		  //Get List Of All Processes from the BootStrapServer
		  GetAllNodes(sBS, sStub);
		  //Array to store reply from all processes
		  String sReplies[] = new String[mNodes.size()];//Not Used		
		
		  //No Need to Check All These if I am the only Node in the System
		  if(mNodes.size()>1)
		  {
			//Loop through each process
			//For each Node in the List of Nodes
			for(Map.Entry e : mNodes.entrySet())
			{
				//Get the IP of the node we are trying to commnuicate 
				String sCurrentNode = (String) e.getKey();
				//Check for all Nodes in the list except mine
				if(!sCurrentNode.equals(sMyIP))
				{
					//Ask the Node if it needs the Critical Section
					System.out.println("Requesting Access from " + e.getKey()+ " : " + e.getValue());
					//If the Node is Alive then Send request
					if(CheckIsAlive(sCurrentNode))
					{
						//Prep the RMI Objects
						try
						{
						   Registry rRemote = LocateRegistry.getRegistry(sCurrentNode,2159);
						   NodeInterface objNI;
						   objNI = (NodeInterface) rRemote.lookup("Node");
						   String sCurrent = objNI.RequestCS(sMyIP,lDateTime);
						   //Wait Till Node Replies OK
						   while(!(sCurrent.equals("OK")))
						   {
							
							 try
							 {
								//Check if the node possibly holding the Critical Section is Still Alive ?
								if(!objNI.CheckIfAlive())
									System.out.println("Seems " + sCurrentNode+ " has crashed\n");
							 }
							 catch(Exception ex)
							 {
								System.out.println("  Node " + e.getKey()+ "  " + e.getValue() + " seems to be unavaiablable.");
								System.out.println("  Assuming "+ e.getKey()+" has crashed. Implied reply OK");
								//Breaking from the While Loop as the node we are awaiting to get reply from is Dead
								break;
							 }
							 //Check if Anyone has sent a reply
							 if(mReplyNodes.containsKey(sCurrentNode))
							 {
								sCurrent = mReplyNodes.get(sCurrentNode);
								//Recieved a Remote Reply 
								System.out.println(sCurrentNode + " has replied OK. ");
								continue;
							 }
							 System.out.println(qCSList.toString()+"\n");
							 //System.out.println("  Replies Remote Recieved :" + mReplyNodes.toString());
							 Thread.sleep(5000); // Wait for 5 secs
							 System.out.println("  Waiting for Node  "+e.getKey()+ " : " + e.getValue()+ " to Reply");
						}
						System.out.println("\n  Recieved Reply from " + e.getKey()+ " : " + e.getValue());
						}
						catch(Exception ex)
						{
							System.out.println("\n\n   Node" + e.getKey()+ "  " + e.getValue() + " has crashed. Implied reply OK");
							continue;
						}
					}
					else
					{
						System.out.println("\n\n  Node" + e.getKey()+ "  " + e.getValue() + " has crashed. Implied reply OK");
						continue;
					}
				}
			}
		}
		
		//Finally Recieved Replies from all Nodes
		sCurrentState = "Held"; // Change Status
		Date dTime = new Date();
		//Prep Message
		String sMessage = " Node " + sName + " entered Critical Section at " + dTime;
		Registry rRemote = LocateRegistry.getRegistry(sBS,2159);
		NodeInterface objPrint;
		objPrint = (NodeInterface) rRemote.lookup("Node");
	    objPrint.UpdateCriticalSection(sMessage);
	    System.out.println("\n"+sMessage+"\n");
	    Thread.sleep(5000);
		ExitCS();
		}
		catch(Exception ex)
		{
			System.out.println("\n Somthing went wrong while connecting the BootStrapServer. You can do the following \n\n");
    		System.out.println("\n   1. Check if the BSS is already running ?");
    		System.out.println("    2. Go throuhg the EnterCS! \n");
    		ex.printStackTrace();
    		System.exit(0);
		}
	}
	
    /**
     * CheckIAlive checks if a specific host to up and running or not,
     * @param sCurrentNode
     * @return
     */
	public static boolean CheckIsAlive(String sCurrentNode) 
	{   
		try 
		{
			//Check if the Node is Reachable or Not
			return Inet4Address.getByName(sCurrentNode).isReachable(3000);
		}
		catch (IOException e) 
		{	System.out.println("\n\n Somthing wrong in while checking if the server is alive or not.");
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * RequestCS sends request to all the methods to approve its request to enter the Critical Section
	 */
	@Override
	public String RequestCS(String sPID, long sTS) throws RemoteException 
	{
		String sReply = "NOPE";
		//If the process is currently holding the CS.
		if(sCurrentState.equals("Held"))
		{
			//Reply NOPE as the request it not approved
			sReply = "NOPE";
			System.out.println("\n\n" +sName+" is currently Holding the Critical Section");
			System.out.println("Queing up the request from " + sPID + "\n");
			//Queue the request so the reply can be sent later
			qCSList.add(sPID);
			System.out.print("\n  Enter '2' to  Exit Critical Section : ");
		}
		//Else if the reciever processs also wants to enter the Critical Section
		else if(sCurrentState.equals("Wanted") && lDateTime < sTS)
		{
			System.out.println(sName+" also wants to access the Critical section");
			sReply = "NOPE";
			//Enqueue The Request
			System.out.println("\n\n Queing up the request from " + sPID + "\n");
			qCSList.add(sPID);
			System.out.print("\n  Enter '2' to  Exit Critical Section : ");
		}
		//The reciever Process is not interested. Hence simply approve the request.
		else
		{
			System.out.println(sName+" is not interested. Replying OK");
			sReply = "OK";
		}
		return sReply;
	}
	
	/**
	 * ExitCS is when the process id done with its work in the Critical Section 
	 * releases the hold on the CS
	 */
	private static void ExitCS() 
	{
		try 
		{
			//System.out.println("Under Construction \n\n");
			lDateTime = Long.MAX_VALUE;
			sCurrentState = "Released";
			NodeInterface objNI;
			//Reply to all Nodes in the Queue
			while(!qCSList.isEmpty())
			{
				    //Pop the object from the queue
				    String sIP = qCSList.remove();
				    //Connect to its RMI
					RANode objRemote = new RANode("Node");
					Registry rRemote = LocateRegistry.getRegistry(sIP,2159);
					objNI = (NodeInterface)rRemote.lookup("Node");
					//Reply back the request
					objNI.StoreRemoteReplies(sMyIP,"OK");
					System.out.println("\n    Replies OK to Node : " + sIP);
			}
			//Prep Message
			Date dTime = new Date();
			String sMessage = " Node " + sName + " released Critical Section at " + dTime;
			System.out.println("\n"+sMessage+"\n");
			Registry rRemote = LocateRegistry.getRegistry(sBS,2159);
			NodeInterface objPrint;
			objPrint = (NodeInterface) rRemote.lookup("Node");
		    objPrint.UpdateCriticalSection(sMessage);
			
		}
		catch (RemoteException e) 
		{
			System.out.println("\n\n Somthing wrong in while exiting the Critical Section.");
			e.printStackTrace();
		}
		catch (NotBoundException e) 
		{
			System.out.println("\n\n Somthing wrong in while exiting the Critical Section.");
			e.printStackTrace();
		}
	}

	
	/**
	 * Get info of all the nodes from the BSS Server
	 * @param sBS
	 * @param sStub
	 */
	public static void GetAllNodes(String sBS, String sStub)
    {
    	//Get the List of All Servers currently active in the Ring
    	try 
    	{
    		//System.out.println(" "+sBS+" ");
			Registry rRet = LocateRegistry.getRegistry(sBS,2159);
			NodeInterface obj;
			//obj = (NodeInterface) rRet.lookup("BSS");
			obj = (NodeInterface) rRet.lookup("Node");
			LNodes = new ArrayList<String>(obj.getAllNodes());
			mNodes = obj.getNodesInfo();
			
		} 
    	catch (RemoteException e) 
    	{
			System.out.println("\n\n Somthing wrong in while exiting the Critical Section.");
			e.printStackTrace();
		} catch (NotBoundException e) 
    	{
			System.out.println("\n\n Somthing wrong in while exiting the Critical Section.");
			e.printStackTrace();
		}
    }
	
	
    	//Methods available for Remote Invocation.
	    /**
	     * Returns the List of All Nodes with the Boostrap Server
	     */
		@Override
		public List getAllNodes() throws RemoteException 
		{
			return LNodes;
		}
		
		/**
		 * Returns the Map with all nodes and their IP Addresses
		 */
		@Override
		public Map getNodesInfo() throws RemoteException {
			
			return mNodes;
		}
		
		/**
		 * Store an Reply recieved from a Remote Node 
		 */
		@Override
		public void StoreRemoteReplies(String sIP, String sReply) throws RemoteException 
		{
		  System.out.println("\n\n Registering Remote Reply from Node " + sIP);
		  mReplyNodes.put(sIP,sReply);
		}

		/**
		 * Check if an function is alive. Not used 
		 */
		@Override
		public boolean CheckIfAlive() throws RemoteException 
		{
				return true;
		}

		/**
		 * View the details on who entered the Critical Section
		 * Returns the List with the details of all the updations of the Critical
		 * Section.
		 */
		@Override
		public List ViewCSLog() throws RemoteException 
		{
		   System.out.println("\n\n Viewing CS Log  on "+ sMyIP);
		   //System.out.println(" Critical Section Log" + sCSLog.toString());
		   return sCSLog; 
		}

		/**
		 * Update the values of the Critical Section
		 */
		@Override
		public void UpdateCriticalSection(String sMessage) throws RemoteException 
		{
			
			sCriticalSection = sMessage;
			sCSLog.add(sMessage);
			System.out.println("\n CS Section updated \n");		
		}


		/**
		 * Print the history of all the processes that entered the critical section.
		 */
		private static void PrintCSLog() 
		{
		   System.out.println("\n Printing the history of all the processes that entered the Critical section\n");
		   Registry rPrint;
		   try 
		   {
			  //Conncet RMI
			rPrint = LocateRegistry.getRegistry(sBS,2159);
			NodeInterface objPrint;
			objPrint = (NodeInterface) rPrint.lookup("Node");
			ArrayList<String> LPrint = (ArrayList<String>) objPrint.ViewCSLog();
			//Print All the Logged Values 
			for(String sPrint : LPrint)
			{
				System.out.println(" > " + sPrint.toString());
			}
	       }
		   catch (RemoteException e) 
		   {
			e.printStackTrace();
		   } catch (NotBoundException e) 
		   {
			e.printStackTrace();
		   }
		}
		   
		
	/**
	 * Main Function
	 * @param args
	 */
	public static void main(String[] args) 
	{
	  try
	  {
		  //Initialize the Constructor
		  new RANode();
		  //boolean bEntered  = false;
		  //boolean bExited = true;
		  

		  /*
          while(true)
		  {
		    System.out.println("\n\nSelect the task that needs to be completed : - \n\n");
		    if(!bEntered)
		    {
		      System.out.println("   1. Enter Critical Section");
		    }
		    if(!bExited)
		    {
		        System.out.println("   2. Exit Critical Section");
		    }
		    System.out.println("   3. View the Current Waiting Requests");
		    System.out.println("   4. View the History of All processes in the Critical Section");
		    System.out.println("   5. End Program");
		    System.out.print("     Enter 1 - 2 - 3 appropriately : ");
		 
		    Scanner scScan  = new Scanner(System.in);
		    int iChoice  = scScan.nextInt();
		    switch(iChoice)
		    {
			  case 1 : if(!bEntered)
				             {
				               EnterCS();
				               bEntered = true;
				               bExited = false;
				               break;
				             }
			            else
			            	System.out.println("\nAlready In Critical Section\n");break;
				  
			  case 2 : if(!bExited)
				       {
				         ExitCS();
				         bExited = true;
				         bEntered=false;
				         break;
				       }
           			   else
                	   {
           				System.out.println("\nCurrently Not In To Critical Section\n");break;
           		       }
	 			  
			  case 3 : System.out.println(qCSList.toString()+" \n");;break;
			  case 4 : PrintCSLog();break;
			  case 5 : System.exit(0);break;
			  default: System.out.println("The values entered is not valid. Please try again");
			           break;
		    }
		  
		  //scScan.close();
		  }
		  */
		
	  }
	  catch(Exception ex)
	  {
		
		System.out.println("\n\n OOoops!. Something Went Wrong while Performing Selection ");
		System.out.println("\n\n Somthing wrong in while exiting the Critical Section.");
		System.exit(0);
	  }
	  

	}
	

}

	
	
	

