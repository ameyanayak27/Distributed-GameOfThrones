/**
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * FileName    : Player.java
 * Description : This program runs on the client side. Provides GUI to player 
 * and also includes the gameplay logic.
 * 
 * @version  : Player.java v 4.0  5/13/2015 10:00 PM
 * 
 * @author 	rss2159 (Rajesh Shetty)
 * 			akn6749 (Ameya Nayak)
 * 			uxb9472 (Utkarsh Bhatia)
 * 
 *=============================================================================
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.io.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Player extends UnicastRemoteObject implements PlayerInterface,Serializable
{

	private static JFrame frame;
	public static String currentVID;
	public static String sMyIP;
	public static HashMap<String,String> sIPVID = new HashMap<String,String>();
	public static HashMap<String,String> sPlayersMap = new HashMap<String,String>();
	public static ArrayList<String> sPlayersList = new ArrayList<String>(); //Stores the IP
	static List<String> neighbourList = new ArrayList<String>();
	static List<String> Territories = new ArrayList<String>();
	public static String[] sPlayerID = {"01010","1111","00000","1010"};
	RANode objCS = new RANode();
	
	Color bg[] = new Color[]{Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN};
	static Color myColor = Color.DARK_GRAY;
	//static ArrayList<String> VIDlist = new ArrayList<String>();
	static int level;
	static int soldier;
	static int coin;
	static ArrayList<String> VIDlist = new ArrayList<String>();
	public final static JButton bots[] = new JButton[24];
	static int iMyPlayerID;
	static String sPlayer = "Player ";
	static String LeaderIP="";
	static Player window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		level = Integer.parseInt(args[0]);
		soldier = Integer.parseInt(args[1]);
		coin = Integer.parseInt(args[2]);
		
		System.out.println(" Level : " + level + " Soldiers" + soldier+" Coins " + coin);
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					window = new Player();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		
		
		
	}

	/**
	 * Create the application.
	 */
	public Player() throws RemoteException
	{
		//super();
		try 
		{
			int i = 0;
			Player p = new Player(i);
			Registry r1 = LocateRegistry.createRegistry(5281);
			r1.rebind("PLR", p);
			
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
			//VIDlist.add("00");//P1
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		initialize();
		
		
		
	}
	
	
	public Player(int i) throws RemoteException
	{
		System.out.println(" Contructor Initialized");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() 
	{
		frame = new JFrame();
		frame.setBounds(100, 100, 848, 468);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		//frame.getContentPane().setLayout(new FlowLayout());
		
		
		JPanel  pMain = new JPanel(new BorderLayout());
		JPanel pTop = new JPanel(new GridLayout(1, 4));
		
		
		
		JPanel panel = new JPanel();
		//pMain.add(pTop);
		//pMain.add(panel);
		//frame.getContentPane().add(pMain);
		frame.getContentPane().add(panel);
		//frame.repaint(5000);
		panel.setLayout(new GridLayout(5,6));
		int x1,y1,x2,y2;
		x1 =85;y1=117;x2=143;y2=35;
		
		
		JButton Player1 = new JButton("GAME OF THRONES");
		Player1.setBounds(x1,y1,x2,y2);
		
		panel.add(Player1);
		
		JButton Player2 = new JButton("ALL PEERS MUST DIE !!!");
		Player2.setBounds(646, 117, 143, 35);
		//Player2.setBounds(x1,y1,x2,y2);
		panel.add(Player2);
		
		final JButton GetSoldiers = new JButton("Get More Soldiers");
		GetSoldiers.setBounds(85, 325, 148, 35);
		//Player3.setBounds(x1,y1,x2,y2);
		panel.add(GetSoldiers);
		GetSoldiers.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				
				if(coin >= 200 )
				{
				  JOptionPane.showMessageDialog(frame,  " Training Unsullied Soldiers");
				  GetSoldiers.setText("Training Soldiers");
				  try 
				  {
					Thread.sleep(2000);
				  } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				  }
				  coin -= 200;
				  soldier += 100;
				  JOptionPane.showMessageDialog(frame," 100 Unsullied Soldiers added to the Army");
				  GetSoldiers.setText("Get More Soldiers ");
				  
				}
				else
				{
					JOptionPane.showMessageDialog(frame,  " You need a minimum of 200 coins to train Soldiers. Take a Loan From Iron Bank");
				}
			}
		});
		
		
		
		final JButton GetCoins = new JButton("Get Coins From Iron Bank");
		GetCoins.setBounds(652, 325, 137, 35);
		//Player4.setBounds(x1,y1,x2,y2);
		panel.add(GetCoins);
		try 
		{
			RANode.Register();
		} catch (RemoteException e4) 
		{
			// TODO Auto-generated catch block
			e4.printStackTrace();
		}
		GetCoins.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog(frame,  " Requesting Coins");
				GetCoins.setText("Waiting for Coins For Critical Section");
				objCS.EnterCS();
				JOptionPane.showMessageDialog(frame,  " Got 100 Coins");
				GetCoins.setText("Get Coins From Iron Bank");
				coin +=200;
			}
		});
		
		JButton CurrentStatus = new JButton(" Current Gold Status ");
		CurrentStatus.setBounds(652, 325, 137, 35);
		//Player4.setBounds(x1,y1,x2,y2);
		
		CurrentStatus.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog(frame,  "Level : " + level + " Soldiers" + soldier+" Coins " + coin);
				
			}
		});
		
		panel.add(CurrentStatus);
		
		
		JButton btnAttack = new JButton("Attack");
		
		
		btnAttack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				JOptionPane.showMessageDialog(frame, "Now Attacking !!!  ");
				if(soldier <=100)
				{
					JOptionPane.showMessageDialog(frame, "You Do not have enough soldiers. Please buy more soldiers ");
					//break;
				}
				else
				{
					attack();
				}
				System.out.println("Territtories \n" + Territories.toString());
			}
		});
		btnAttack.setBounds(326, 216, 186, 68);
		panel.add(btnAttack);
		
		for(int i = 0; i< 24 ; i++)
		{
			bots[i] = new JButton(VIDlist.get(i).toString());
			bots[i].setBounds(85, 325, 148, 35);
			bots[i].setText(VIDlist.get(i).toString());
			final int f = i;
			bots[i].addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					
					try{
						//Registry r = LocateRegistry.getRegistry("129.21.37.29", 5281);	
					     //PlayerInterface obj = (PlayerInterface) r.lookup("PLR");
					     //obj.updateChild(sPlayer);
					     //JOptionPane.showMessageDialog(frame, " Button ID " + f);
					   }
						catch(Exception e)
						{
							e.printStackTrace();
						}
				}
			});
			
			
			panel.add(bots[i]);
		}
		
		bots[0].setBackground(bg[0]);
		bots[5].setBackground(bg[1]);
		bots[18].setBackground(bg[2]);
		bots[23].setBackground(bg[3]);
		
		
		
		//Create Your Own Registry
		
		
		//Remote Registry Reference
		
		Registry rReg;
		try 
		{
			rReg = LocateRegistry.getRegistry("129.21.30.38",9999);
			 GameInterface objSupp;
			  objSupp = (GameInterface) rReg.lookup("BSS");
			  sMyIP = Inet4Address.getLocalHost().getHostAddress();
			  LeaderIP = sMyIP; 
			  currentVID = objSupp.joinedPlayer(sMyIP);
			  //JOptionPane.showMessageDialog(frame, "RMI Connection Sucessfull. Recieved VID : " + currentVID);
			  JOptionPane.showMessageDialog(frame, "Connected to Game Server. Recieved VID : " + currentVID);
			  //sPlayer = sPlayer + currentVID.substring(currentVID.length()-1);
			  //int iCol = Integer.parseInt(currentVID.substring(currentVID.length()-1));
			  //currentVID = currentVID.substring(0,currentVID.length()-1);
			  //int iCol = Integer.parseInt( "" + currentVID.charAt(currentVID.length()-1));
			  
			  int iMatch =0;
			  for(int i = 0; i<24; i++)
			  {
				  //if((VIDlist.get(i).toString()).equals(currentVID.substring(0,currentVID.length()-1)));
				  if((VIDlist.get(i).toString()).equals(currentVID))
				  {
				  	  //bots[i].setText(sPlayer);
				  	  //System.out.println(" " + currentVID.substring(currentVID.length()-1)  +  " " + bg[Integer.parseInt(currentVID.substring(currentVID.length()-1))]);
				  	  //bots[i].setBackground(bg[Integer.parseInt(currentVID.substring(currentVID.length()-1))]);
					  iMatch = i;
					  break;
				  }   
			  }
			  System.out.println(" My VID is " + currentVID  + " iPlayerID  " + iMatch);
			  if(iMatch == 0)
				  myColor = bg[0];
			  else if (iMatch == 5)
				  myColor = bg[1];
			  else if (iMatch == 18)
				  myColor = bg[2];
			  else
				  myColor = bg[3];
			  bots[iMatch].setToolTipText("You Are Here" );
			  
			  
			  for(int i = 0; i<4; i++)
			  {
				  //if((VIDlist.get(i).toString()).equals(currentVID.substring(0,currentVID.length()-1)));
				  if((sPlayerID[i].equals(currentVID)))
				  {
				  	  //bots[i].setText(sPlayer);
				  	  //System.out.println(" " + currentVID.substring(currentVID.length()-1)  +  " " + bg[Integer.parseInt(currentVID.substring(currentVID.length()-1))]);
				  	  //bots[i].setBackground(bg[Integer.parseInt(currentVID.substring(currentVID.length()-1))]);
					  iMyPlayerID = i;
					  break;
				  }   
			  }
			  iMyPlayerID++;
			  bots[iMatch].setText("Player "+ iMyPlayerID);
			  //System.out.println("VID Found in list at  " +iMatch);
			  //System.out.println(" icolor " + iCol);
			  /*
			  --iCol;
			  switch(iCol)
			  {
			     case 1 :  bots[iMatch].setBackground(bg[iCol]);myColor = bg[iCol];break;
			     case 2 :  bots[iMatch].setBackground(bg[iCol]);myColor = bg[iCol];break;
			     case 3 :  bots[iMatch].setBackground(bg[iCol]);myColor = bg[iCol];break;
			     case 4 :  bots[iMatch].setBackground(bg[iCol]);myColor = bg[iCol];break;
			     default : break;
			  }*/
			  System.out.println("Tested Connection");
		} catch (RemoteException e2) {
			 JOptionPane.showMessageDialog(frame, "RMI Connection UNN Sucessfull" + e2.getMessage());
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NotBoundException e1) {
			JOptionPane.showMessageDialog(frame, "RMI Connection UNN Sucessfull" + e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownHostException e3) {
			JOptionPane.showMessageDialog(frame, "Unable to Get IP Address " + e3.getMessage());
			//e.printStackTrace();
		}
		
		
		
	}


	@Override
	public void mDetails(Map<String, String> mD) throws RemoteException 
	{
		System.out.println("Hash Map sent from Server" + mD.toString());
		//JOptionPane.showMessageDialog(frame, "Recieved Map From User" + mD.toString());
		sIPVID = (HashMap<String, String>) mD;
		
		for(Map.Entry e: mD.entrySet())
		{
			String neighVID=""+e.getValue();
			if(checkneighbour(currentVID,neighVID))
			{
				System.out.println("Neighbours " + e.getValue() + " " + e.getKey());
				neighbourList.add(""+e.getKey());
			}
			
			if(neighVID.equals("01010") || neighVID.equals("1111") || neighVID.equals("00000") || neighVID.equals("1010"))
			{
				String value = neighVID;
				sPlayersList.add((String) e.getKey());
				System.out.println(" Added Player "  + neighVID + e.getKey());
			}
		}
		
		System.out.println(sPlayersList.toString());
	}
	
	
	public static boolean checkneighbour(String x,String y)
	{
		double xa[]=getcoords(x);
		double ya[]=getcoords(y);
		if(xa[0]==ya[1]||xa[1]==ya[0])
		{
			if(ya[2]==xa[3]||ya[3]==xa[2]||((xa[2]>ya[2])&&(xa[3]>ya[3]))||((xa[2]<ya[2])&&(xa[3]<ya[3])))
				return false;
			else 
				return true;
		}
		else if(ya[2]==xa[3]||ya[3]==xa[2])
		{
			if(xa[0]==ya[1]||xa[1]==ya[0]||((xa[0]>ya[0])&&(xa[1]>ya[1]))||((xa[0]<ya[0])&&(xa[1]<ya[1])))
				return false;
			else
				return true;
		}
		else
			return false;
	}
	
	public static double[] getcoords(String s)
	{
		double x1=0.0;
		double x2=1.0;
		double y1=0.0;
		double y2=1.0;
		for(int i=0;i<s.length();i++)
		{
			double rangex=x2-x1;
			double rangey=y2-y1;

			if(i%2==0)
			{

				if(s.charAt(i)=='1')
				{
					x1+=rangex/2;
				}
				else if(s.charAt(i)=='0')
				{
					x2-=rangex/2;
				}
			}
			else
			{
				if(s.charAt(i)=='1')
				{
					y1+=rangey/2;
				}
				else if(s.charAt(i)=='0')
				{
					y2-=rangey/2;

				}
			}
		}
		double a[]={x1,x2,y1,y2};
		return a;
	}
	
	public static void attack()
	{
		List<String> x=new ArrayList();
		for(int i=0;i<neighbourList.size();i++)
		{
			if(!Territories.contains(neighbourList.get(i)))
				x.add(neighbourList.get(i));
				
		}
		leader_elect(x);
	}
	
	public static void leader_elect(List<String> list)
	{
		int iBtn = 0;
		System.out.println("Continuing");
	
	    
		for(int i=0;i<list.size();i++)
		{
			System.out.println(" Printing" +i);
			if(soldier <=100)
			{
				JOptionPane.showMessageDialog(frame, "You Do not have enough soldiers. Please buy more soldiers ");
				break;
			}
		   PlayerInterface obj=null;
		   String Neighbour_ip=list.get(i);
		   try
		   {
			Registry r = LocateRegistry.getRegistry(Neighbour_ip, 5281);	
			obj = (PlayerInterface) r.lookup("PLR");
			String x= obj.getReply(level,coin,soldier,sMyIP);
			System.out.println("  " + x);
			if(x.equals("draw"))
			{
				JOptionPane.showMessageDialog(frame, " Retreat !");
			}
			else if(x.equals("Yeild"))
			{
				List<String> n=obj.getNeigh();
				addneigh(n);
				coin+=obj.get_coin();
				soldier -=100;
				//soldier+=obj.get_soldier();
				//level = soldier/100;
				++level;

				GameInterface objGS = null;
				//Saving Backup of Level
				try
				{
					Registry rReg = LocateRegistry.getRegistry("129.21.30.38",9999);

					objGS = (GameInterface) rReg.lookup("BSS");
					objGS.updateLevel(iMyPlayerID, level);

					System.out.println("Updated Level of Server Saved");	
				}
				catch(Exception e)
				{
					System.out.println("Problem Updating Level");
					continue;
					//e.printStackTrace();
				}

				//Update the Map
				/*U
				Registry rReg;
				try {
					rReg = LocateRegistry.getRegistry("129.21.30.38",9999);
					  GameInterface objSupp;
					  objSupp = (GameInterface) rReg.lookup("BSS");
					  //objSupp.updateMap(currentVID, iPlayer);
					  //VIDlist = objSupp.GetList();		  
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 */

				//Connect All the Players

				try
				{
					String sVID = obj.getVID();
					System.out.println( " Svid" + sVID);
					iBtn = GetBtnId(sVID);

					
						
					B:
					for(int i1=0;i1<4;i1++)
					{
						try
						{
						String s = sPlayersList.get(i1).toString();	
						System.out.println("Connecting to Player " + s);
						Registry rRemote = LocateRegistry.getRegistry(s, 5281);	
						PlayerInterface objRemote = (PlayerInterface) rRemote.lookup("PLR");
						//JOptionPane.showMessageDialog(frame, "Btn sVID" + iBtn + " " + sVID);
						objRemote.updateChild(iBtn, myColor, sVID+sPlayer);
						Territories.add(Neighbour_ip);
						//updateLocale(iBtn, myColor, sVID+sPlayer);
						}
						catch(Exception e)
						{
							System.out.println("Exception in coloring ");
							continue B;
						}

					}
					objGS.countTerritories();

				}
				catch(Exception e)
				{
					System.out.println("Problem occured while coloring the nodes. 1 ");
					continue;
					//e.printStackTrace();
				}

			}
			else
			{
				System.out.println("You Lose");
				window.frame.setVisible(false);
			}
		}
		catch(Exception e)
		{
			System.out.println(" Requesting Backup from Game Sever");
			
			
			Registry rReg;
			try 
			{
				rReg = LocateRegistry.getRegistry("129.21.30.38",9999);
				 GameInterface objSupp;
				 objSupp = (GameInterface) rReg.lookup("BSS");
				 String sCatch = objSupp.checkflag(Neighbour_ip);
				 System.out.println("Value of sCatch " + sCatch);
				 if(!sCatch.equals("1"))
				 {
					 System.out.println(" Working From Backup 1");
					 coin +=100;
					 soldier -= 100;
					 level++;
					 objSupp.updateLevel(iMyPlayerID, level);
					 
					 
					//Connect All the Players

						try
						{
							String sVID = sCatch;
							System.out.println( " Svid" + sVID);
							iBtn = GetBtnId(sVID);
							System.out.println(" Coloring Nodes with Backup");
							B:
							for(int i1=0;i1<4;i1++)
							{
								try
								{
								String s = sPlayersList.get(i1).toString();	
								System.out.println("Connecting to Player " + s);
								Registry rRemote = LocateRegistry.getRegistry(s, 5281);	
								PlayerInterface objRemote = (PlayerInterface) rRemote.lookup("PLR");
								//JOptionPane.showMessageDialog(frame, "Btn sVID" + iBtn + " " + sVID);
								objRemote.updateChild(iBtn, myColor, sVID+sPlayer);
								Territories.add(Neighbour_ip);
								//updateLocale(iBtn, myColor, sVID+sPlayer);
								}
								catch(Exception e1)
								{
									System.out.println(" Coloring exception : 1 ");
									continue B;
								}

							}
							objSupp.countTerritories();

						}
						catch(Exception e1)
						{
							System.out.println("Problem occured while coloring the nodes.");
							continue;
							//e.printStackTrace();
						}
					 
					 
				 }
				 else
				 {
					 System.out.println(" Time to Retreat !");
					 JOptionPane.showMessageDialog(frame, " Retreat !");
				 }
				 continue;
				 
			} catch (RemoteException e1) {
				System.out.println(" Exception in Backup !!!");
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e2) {
				System.out.println(" Exception in Backup !!! ");
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			/*System.out.println("y");
			try{
				Registry reg = LocateRegistry.getRegistry("129.21.30.38",8989);	
			GameInterface x = (GameInterface) reg.lookup("BSS");
			double factor2=x.find(Neighbour_ip);
			if(factor2<(0.75*soldier+0.25*coin))
			{
				List<String> n=obj.getNeigh();
		    	addneigh(n);
		    	Territories.add(Neighbour_ip);
			}
			else
		    	System.out.println("You Lose");
		}
			catch(Exception ex)
			{
				System.out.println("x");
			}*/

			e.printStackTrace();
		}
		}
	}
	
	public  String getReply(int l,int c, int s,String IP)
	{
		System.out.println(" Level : " + l + " Soldiers" + s+" Coins " + c);
		if(!LeaderIP.equals("") ||level==l)
		{
			return "draw";
		}
		else if(level<l)
		{
			//flag=true;
			System.out.println("Yeild");
			LeaderIP=IP;
			return "Yeild";
		}
		else
			{/*
				double factor1= 0.75*soldier+0.25*coin;
				double factor2= 0.75*s+0.25*c;
				if(factor1>factor2)
				{
					System.out.println("Lose");
					return "You lose";
				}
				else 
				{
					//flag=true;
					System.out.println("Yeild2 Else Condition ");
					return "Yield";
				}
				*/
				return "You Lose";
			}
	}
	public  List<String> getNeigh()
	{
		return neighbourList;
	}
	
	public static void addneigh(List<String> X)
	{
		for(int i=0;i<X.size();i++)
		{
			if(!neighbourList.contains(X.get(i))&&!X.get(i).equals(sMyIP))
			{
				neighbourList.add(X.get(i));
			}
		}
	}
	
	public int get_coin()
	{
		return coin;
	}
	public int get_soldier()
	{
		return soldier;
	}

	@Override
	public void updateChild(int iBtnId,Color c, String sPlayer ) 
	{
		bots[iBtnId].setText("Captured !");
		bots[iBtnId].setBackground(c);
		System.out.println("Remote Update  : This function was called " + sPlayer +" BtnID " +  iBtnId);
		
	}
	
	public static void updateLocale(int iBtnId,Color c, String sPlayer ) 
	{
		bots[iBtnId].setText("Captured !");
		bots[iBtnId].setBackground(c);
		System.out.println(iBtnId+ " " + c + " " + sPlayer);
		System.out.println("UpdateLocale : This function was called " + sPlayer +" BtnID " +  iBtnId);
		
	}
	
	public static int  GetBtnId(String VID)
	{
		int iMatch =0;
		  for(int i = 0; i<24; i++)
		  {
			  if((VIDlist.get(i).toString()).equals(VID))
			  {  iMatch = i;
			     System.out.println(" Neighbour Surrendered has Button ID " + iMatch);
				  break;
			  }   
		  }
		return iMatch;
		
	}

	@Override
	public String getVID() throws RemoteException 
	{
		return currentVID;
	}

	@Override
	public void EndGame(String sMessage) throws RemoteException 
	{
		JOptionPane.showMessageDialog(frame, "Behold Players :  " +sMessage );
		System.exit(0);
	}
}
