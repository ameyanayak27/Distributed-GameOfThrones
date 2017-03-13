/**
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * FileName    : GameServer.java
 * Description : This program acts as the bootstrap server. All players and pots connects to this server. Also stores the backup of bots.
 * Provides a hashmap with IP  as keys an VID as values to everyone so that all the nodes can find their neighbours 
 * 
 * @version  : GameServer.java v 4.0  5/13/2015 10:00 PM
 * 
 * @author 	rss2159 (Rajesh Shetty)
 * 			akn6749 (Ameya Nayak)
 * 			uxb9472 (Utkarsh Bhatia)
 * 
 *=============================================================================
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
/*

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
 */

public class GameServer extends UnicastRemoteObject implements GameInterface,Serializable
{
	
	private static final long serialVersionUID = 7L;
	public static HashMap<String,String> sDetails = new HashMap<String,String>();
	public static HashMap<String,String> sIPVID = new HashMap<String,String>();
	
	public static HashMap<String,String> sPlayerDetails = new HashMap<String,String>();
	public static HashMap<String,String> sDisplayGrid = new HashMap<String,String>();
	public static String[] sPlayer = {"01010","1111","00000","1010"};
	//public static String[] sPlayer = {"00","01","11","10"};
	public static HashMap<String,Double> Backup = new HashMap<String,Double>();
	public static HashMap<String,Integer> BackupSoldier = new HashMap<String,Integer>();
	public static HashMap<String,Integer> BackupCoin = new HashMap<String,Integer>();
	public static ArrayList<String> sPlayersList = new ArrayList<String>(); //Stores the IP
	public static ArrayList<String> BackupFlag = new ArrayList<String>(); //Stores the Backup

	
	public static int countT=0;
	public static int level[]=new int[4];
	public static int iColor;
	public static String[] sColors;
	public static String[]  arrList;
	public static int iConnected;
	public static 
    ArrayList<String> sList = new ArrayList<String>();
	static int count=0;
	static ArrayList<String> VIDlist = new ArrayList<String>();
	static ArrayList<String> VIDlistCopy = new ArrayList<String>();
	
    
	protected GameServer() throws RemoteException 
	{
		super();
		

		sColors = new String[]{"O","Y","R","B"};
		arrList = new String[]{"prometheus.cs.rit.edu","perseus.cs.rit.edu",
				"pegasus.cs.rit.edu","cyclops.cs.rit.edu","odysseus.cs.rit.edu ",
				"centaur.cs.rit.edu","gorgon.cs.rit.edu ",
				"achilles.cs.rit.edu","cerberus.cs.rit.edu",
				"oedipus.cs.rit.edu","rhea.cs.rit.edu",
				"siren.cs.rit.edu","medusa.cs.rit.edu","heracles.cs.rit.edu",
				"andromeda.cs.rit.edu","dione.cs.rit.edu"," agamemnon.cs.rit.edu",
				"idaho.cs.rit.edu","arkansas.cs.rit.edu","arizona.cs.rit.edu","illinois.cs.rit.edu",
				 "vermont.cs.rit.edu","indiana.cs.rit.edu","missouri.cs.rit.edu","iowa.cs.rit.edu",
				 "nevada.cs.rit.edu","utah.cs.rit.edu","massachusetts.cs.rit.edu","alabama.cs.rit.edu",
				 "rhodeisland.cs.rit.edu","kansas.cs.rit.edu","delaware.cs.rit.edu","nebraska.cs.rit.edu",
				 "georgia.cs.rit.edu","maine.cs.rit.edu","california.cs.rit.edu","newyork.cs.rit.edu"		
		
		};
		
		VIDlist.add("01010");//P1
		VIDlist.add("01011");
		VIDlist.add("01110");
		VIDlist.add("01111");
		VIDlist.add("1101");
		VIDlist.add("1111");//P2
		VIDlist.add("01000");
		VIDlist.add("01001");
		VIDlist.add("01100");
		VIDlist.add("01101");
		VIDlist.add("1100");
		VIDlist.add("1110");
		VIDlist.add("00010");
		VIDlist.add("00011");
		VIDlist.add("00110");
		VIDlist.add("00111");
		VIDlist.add("1001");
		VIDlist.add("1011");
		VIDlist.add("00000");//P3
		VIDlist.add("00001");
		VIDlist.add("00100");
		VIDlist.add("00101");
		VIDlist.add("1000");
		VIDlist.add("1010");//P4
		
		
		VIDlistCopy.add("01011");
		VIDlistCopy.add("01110");
		VIDlistCopy.add("01111");
		VIDlistCopy.add("1101");
		VIDlistCopy.add("01000");
		VIDlistCopy.add("01001");
		VIDlistCopy.add("01100");
		VIDlistCopy.add("01101");
		VIDlistCopy.add("1100");
		VIDlistCopy.add("1110");
		VIDlistCopy.add("00010");
		VIDlistCopy.add("00011");
		VIDlistCopy.add("00110");
		VIDlistCopy.add("00111");
		VIDlistCopy.add("1001");
		VIDlistCopy.add("1011");
		VIDlistCopy.add("00001");
		VIDlistCopy.add("00100");
		VIDlistCopy.add("00101");
		VIDlistCopy.add("1000");
		VIDlistCopy.add("01010");//P1
		VIDlistCopy.add("1111");//P2
		VIDlistCopy.add("00000");//P3
		VIDlistCopy.add("1010");//P4
		
		
		
		for(int i=0;i<arrList.length;i++)
		{
			sDetails.put(arrList[0], "GREEN");
		}
		
		for(int i=0;i<24;i++)
		{
			sPlayerDetails.put(VIDlist.get(i), "0");
		}
	
	}

	public static void main(String[] args) 
	{	
		//Registering Remote Object
		//System.setSecurityManager(new RMISecurityManager());
		
		try 	
		{
			GameServer g = new GameServer();
			Registry r1 = LocateRegistry.createRegistry(9999);
			r1.rebind("BSS", g);

			System.out.println("\n Object CAN Registerd in RMI Succesfully. \n");
		}
		catch (RemoteException e) 
		{
			System.out.println(" Something Wrong Here in : reg() \n\n\n");
		}
		
		String user = "";
		String password = "";
		String command = "java Peer";
		
		/*
		for(int iLoop=0;iLoop<arrList.length && iConnected != 20 ;iLoop++)
		{
			String host = arrList[iLoop];
			
			sIPVID.put(VIDlist.get(iLoop)+ "" , arrList[iLoop]);
			command = command +" "+ VIDlist.get(iLoop).toString();
			try
			{  
				java.util.Properties config = new java.util.Properties(); 
				config.put("StrictHostKeyChecking", "no");
				JSch jsch = new JSch();
				Session session=jsch.getSession(user, host, 22);
				session.setPassword(password);
				session.setConfig(config);
				//session.connect();
				System.out.println(++iConnected + " Connected to "+ arrList[iLoop]);
			
                Channel channel=session.openChannel("exec");
                ((ChannelExec)channel).setCommand(command);
                channel.setInputStream(null);
                ((ChannelExec)channel).setErrStream(System.err);

            
            InputStream in=channel.getInputStream();
            channel.connect();
            byte[] tmp=new byte[1024];
            while(true)
            {
              //while(in.available()>0)
              //{
                int i=in.read(tmp, 0, 1024);
                if(i<0)break;
                System.out.print(new String(tmp, 0, i));
             // }
              if(channel.isClosed()){
                System.out.println("exit-status: "+channel.getExitStatus());
                break;
              }
              try{Thread.sleep(1000);}catch(Exception ee){}
              break;
            }
		
            //channel.disconnect();
             // session.disconnect();
			 // System.out.println("DONE");
		    }catch(Exception e)
			{
			//	System.out.println("Unable to Connect to Server " + e.getMessage().toString());
				//e.printStackTrace();
			}
		}
		
		*/
		
	
		
		System.out.println("Drawing Map \n\n");
		DrawGrid();
		
		System.out.println("Waiting For All Players to Join");
        while(iColor != 24)
        {
        	System.out.println("" + iColor);
        }
		
        System.out.println("All Players Joined. Total "+ iColor);
		String sIP = "";
		for(Map.Entry e : sIPVID.entrySet())
		{
			sIP = (String) e.getKey();
			try 
			{
				Registry rPlayer = LocateRegistry.getRegistry(sIP,5281);
				PlayerInterface pInt = (PlayerInterface) rPlayer.lookup("PLR");
				pInt.mDetails(sIPVID);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		}

        
		System.out.println("All players have joined ..... \n\n");
		System.out.println("\n\n The Game will being in few minutes ..... \n\n");
		
		
		while(true)
		{
			System.out.println(countT);
			
			if (countT==20)
			{
				int max=level[0];
			     int j=1;
				for(int i=1; i<level.length;i++ )
				{
					if(max<level[i])
					{
						max=level[i];
						j=i+1;
					}
						
				}
				//sIPVID = (HashMap<String, String>) mD;
				
				for(Map.Entry e: sIPVID.entrySet())
				{
					String neighVID=""+e.getValue();
					if(neighVID.equals("01010") || neighVID.equals("1111") || neighVID.equals("00000") || neighVID.equals("1010"))
					{
						String value = neighVID;
						sPlayersList.add((String) e.getKey());
						System.out.println(" Added Player "  + neighVID + e.getKey());
					}

				}
				
				System.out.println(sPlayersList.toString());
				for(int i=0;i<sPlayersList.size();i++)
				{
					
					try
					{
					 Registry r = LocateRegistry.getRegistry(sPlayersList.get(i), 5281);	
				     PlayerInterface obj123 = (PlayerInterface) r.lookup("PLR");
				     obj123.EndGame("Player " + j + " has won !!! ");
					}
					catch(Exception e)
					{
					 System.out.println("Unable to EndGame");
					 //e.printStackTrace();
					 continue;
					}
				}
				
			}
		}
		
	}
	
	public static void DrawGrid()
	{ 
		int iCount =0;
		for(int i = 0 ; i <4;i++)
		{
			for(int j= 0; j<6; j++)
			{
				System.out.print(sPlayerDetails.get(VIDlist.get(iCount++)) + "     ");
			}
			System.out.println(" \n");
		}

	}

	@Override
	public boolean updateMap(String sKey,String sValue) throws RemoteException 
	{
	    sDetails.put(sKey, sValue);
	    sPlayerDetails.put(sKey, sValue);
	    return true;
	}

	@Override
	public HashMap<String, String> getMap() throws RemoteException 
	{
		return sDetails;
	}

	@Override
	public String joinedPlayer(String IP) throws RemoteException 
	{
		String sVID = "";
		
		
		if(iColor < 24)
		{	
			//sVID = sPlayer[iColor];
			sVID = VIDlistCopy.get(iColor);
			//sPlayerDetails.put(sVID, "" + ++iColor);
			sIPVID.put(IP,sVID);
			System.out.println(IP + " is now connected. Total Connected :  " + iConnected );
			iConnected++;
			//DrawGrid();
			++iColor;
			return sVID;
		}
		else
		{
			return "";
		}
		
	}
	
	public void backup(String x, double factor)
	{
		Backup.put(x, factor);
		count++;
	}

	public  double find(String ip)
	{
		return Backup.get(ip);
	}

	@Override
	public ArrayList<String> GetList() throws RemoteException {

		ArrayList<String> vList = new ArrayList<String>();
		int iCount =0;
		for(int i = 0 ; i <4;i++)
		{
			for(int j= 0; j<6; j++)
			{
				//System.out.print(sPlayerDetails.get(VIDlist.get(iCount++)) + "     ");
				vList.add(sPlayerDetails.get(VIDlist.get(iCount++)));
			}
			//System.out.println(" \n");
		}


		// TODO Auto-generated method stub
		return vList;
	}
	@Override
	public  int findSoldier(String ip)
	{
		return BackupSoldier.get(ip);
	}
	@Override
	public  int findCoin(String ip)
	{
		return BackupCoin.get(ip);
	}

	@Override
	public void backup(String x, int soldiers, int coins)
	{
		BackupSoldier.put(x, soldiers);
		BackupCoin.put(x, coins);

		count++;
	}

	@Override
	public void countTerritories()
	{
		++countT;
		System.out.println("Territories  " + countT);
	}
	@Override
	public void updateLevel(int i,int l)
	{
		level[i-1]=l;
		System.out.println("level" + level.toString());
	}
	@Override
	public void updateflag(String sKey) throws RemoteException 
	{
		BackupFlag.add(sKey);
	}
	@Override
	public String checkflag(String sKey) throws RemoteException
	{
		if(BackupFlag.contains(sKey))
		{
			return "1";
		}
		return ""+sIPVID.get(sKey);
	}


}
