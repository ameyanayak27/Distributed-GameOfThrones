/**
 * ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * FileName    : Bots.java
 * Description : This program includes the game logic for bots. 
 * Does not involve swing components
 * 
 * @version  : Bots.java v 4.0  5/13/2015 10:00 PM
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

public class Bots extends UnicastRemoteObject implements PlayerInterface,Serializable
{

	private static JFrame frame;
	public static String currentVID;
	public static String sMyIP;
	public static HashMap<String,String> sIPVID = new HashMap<String,String>();
	static List<String> neighbourList = new ArrayList<String>();
	static List<String> Territories = new ArrayList<String>();
	Color bg[] = new Color[]{Color.BLUE,Color.YELLOW,Color.RED,Color.GREEN};
	static Color myColor = Color.DARK_GRAY;
	//static ArrayList<String> VIDlist = new ArrayList<String>();
	static int level;
	static int soldier;
	static int coin;
	static ArrayList<String> VIDlist = new ArrayList<String>();
	public final static JButton bots[] = new JButton[24];
	static String sPlayer = "Player ";
	static String LeaderIP="";
	static Bots window;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		level = Integer.parseInt(args[0]);
		soldier = Integer.parseInt(args[1]);
		coin = Integer.parseInt(args[2]);
		System.out.println(" Level : " + level + " Soldiers" + soldier+" Coins " + coin);
		try
		{
		  Registry rReg = LocateRegistry.getRegistry("129.21.30.38",9999);
		  GameInterface objGS;
		  objGS = (GameInterface) rReg.lookup("BSS");
		  objGS.backup(sMyIP, soldier, coin);
		  System.out.println("Saving BackUp of Bots");	
		}
		catch(Exception e)
		{
			System.out.println("Problem Saving Backup of Bots");
			e.printStackTrace();
		}
		
		try {
			Bots b = new Bots();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try {
					window = new Bots();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		*/
		while(true)
		{
			
		}
		
		
	}

	/**
	 * Create the application.
	 */
	public Bots() throws RemoteException
	{
		super();
		try 
		{
			int i = 0;
			Bots p = new Bots(i);
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

		//initialize();
		
		Registry rReg;
		try 
		{
			rReg = LocateRegistry.getRegistry("129.21.30.38",9999);
			 GameInterface objSupp;
			  objSupp = (GameInterface) rReg.lookup("BSS");
			  sMyIP = Inet4Address.getLocalHost().getHostAddress();
			  currentVID = objSupp.joinedPlayer(sMyIP);
			  //JOptionPane.showMessageDialog(frame, "RMI Connection Sucessfull. Recieved VID : " + currentVID);
			  //JOptionPane.showMessageDialog(frame, "Connected to Game Server. Recieved VID : " + currentVID);
			  //sPlayer = sPlayer + currentVID.substring(currentVID.length()-1);
			  //int iCol = Integer.parseInt(currentVID.substring(currentVID.length()-1));
			  //currentVID = currentVID.substring(0,currentVID.length()-1);
			  //int iCol = Integer.parseInt( "" + currentVID.charAt(currentVID.length()-1));
			  //System.out.println(" My VID is " + currentVID  + " iCol is  " + iCol);
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
			  //bots[iMatch].setToolTipText("You Are Here" );
			  //System.out.println("VID Found in list at  " +iMatch);
			  //System.out.println(" icolor " + iCol);
			  //--iCol;
			  /*
			  switch(iCol)
			  {
			     case 1 :  bots[iMatch].setBackground(bg[iCol]);myColor = bg[iCol];break;
			     case 2 :  bots[iMatch].setBackground(bg[iCol]);myColor = bg[iCol];break;
			     case 3 :  bots[iMatch].setBackground(bg[iCol]);myColor = bg[iCol];break;
			     case 4 :  bots[iMatch].setBackground(bg[iCol]);myColor = bg[iCol];break;
			     default : break;
			     
			  }
			  */
			  
			  System.out.println("Tested Connection");
		} catch (RemoteException e2) {
			 //JOptionPane.showMessageDialog(frame, "RMI Connection UNN Sucessfull" + e2.getMessage());
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (NotBoundException e1) {
			//JOptionPane.showMessageDialog(frame, "RMI Connection UNN Sucessfull" + e1.getMessage());
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnknownHostException e3) {
			//JOptionPane.showMessageDialog(frame, "Unable to Get IP Address " + e3.getMessage());
			//e.printStackTrace();
		}
		
		
	}
	
	
	public Bots(int i) throws RemoteException
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
		
		
		JButton Player1 = new JButton("Player 1");
		Player1.setBounds(x1,y1,x2,y2);
		
		panel.add(Player1);
		
		JButton Player2 = new JButton("Player 2");
		Player2.setBounds(646, 117, 143, 35);
		//Player2.setBounds(x1,y1,x2,y2);
		panel.add(Player2);
		
		JButton Player3 = new JButton("Player 3");
		Player3.setBounds(85, 325, 148, 35);
		//Player3.setBounds(x1,y1,x2,y2);
		panel.add(Player3);
		
		JButton Player4 = new JButton("Player 4");
		Player4.setBounds(652, 325, 137, 35);
		//Player4.setBounds(x1,y1,x2,y2);
		panel.add(Player4);
		
		JButton Player5 = new JButton("Player 5");
		Player5.setBounds(652, 325, 137, 35);
		//Player4.setBounds(x1,y1,x2,y2);
		panel.add(Player5);
		JButton btnAttack = new JButton("Attack");
		
		
		btnAttack.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				//JOptionPane.showMessageDialog(frame, "Now Attacking !!!  ");
				attack();
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
			
			bots[i].addActionListener(new ActionListener() 
			{
				public void actionPerformed(ActionEvent arg0) 
				{
					try{
						Registry r = LocateRegistry.getRegistry("129.21.37.29", 5281);	
					     PlayerInterface obj = (PlayerInterface) r.lookup("PLR");
					     //obj.updateChild(sPlayer);
					     //JOptionPane.showMessageDialog(frame, " Here ");
					   }
						catch(Exception e)
						{
							e.printStackTrace();
						}
				}
			});
			
			
			panel.add(bots[i]);
		}
		
		
		
		
		//Create Your Own Registry
		
		
		//Remote Registry Reference
		
		
		
		window.frame.setVisible(false);
	}


	@Override
	public void mDetails(Map<String, String> mD) throws RemoteException 
	{
		System.out.println("Hash Map sent from Server" + mD.toString());
		////JOptionPane.showMessageDialog(frame, "Recieved Map From User" + mD.toString());
		sIPVID = (HashMap<String, String>) mD;
		
		for(Map.Entry e: mD.entrySet())
		{
			String neighVID=""+e.getValue();
			if(checkneighbour(currentVID,neighVID))
			{
				System.out.println("Neighbours " + e.getValue() + " " + e.getKey());
				neighbourList.add(""+e.getKey());
				
			}
		}
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
		for(int i=0;i<list.size();i++)
		{ PlayerInterface obj=null;
		String Neighbour_ip=list.get(i);
		try{
			Registry r = LocateRegistry.getRegistry(Neighbour_ip, 5281);	
		     obj = (PlayerInterface) r.lookup("PLR");
		    String x= obj.getReply(level,coin,soldier,sMyIP);
		    System.out.println("  " + x);
		    if(x.equals("draw"))
		    {
		    	//JOptionPane.showMessageDialog(frame, " Draw ho gaya");
		    }
		    else if(x.equals("Yeild"))
		    {
		    	List<String> n=obj.getNeigh();
		    	addneigh(n);
		    	coin+=obj.get_coin();
		    	soldier+=obj.get_soldier();
		    	level = soldier/100;
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
		    	Territories.add(Neighbour_ip);
		    	//String sVID = obj.getVID();
		    	//iBtn = GetBtnId(sVID);
		    	//updateLocale(iBtn, myColor, sVID);
		    	//obj.updateChild(iBtn, myColor, sVID);
		    	
		    }
		    else
		    	{
		    	 System.out.println("You Lose");
				//window.frame.setVisible(false);
		    	}
		}
		catch(Exception e)
		{
			/*
			System.out.println("y");
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
	}}}
	
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
			Registry rReg;
			try 
			{
				rReg = LocateRegistry.getRegistry("129.21.30.38",9999);
				 GameInterface objSupp;
				 objSupp = (GameInterface) rReg.lookup("BSS");
				 objSupp.updateflag(sMyIP);
				 
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
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
		//bots[iBtnId].setText(sPlayer);
		///bots[iBtnId].setBackground(c);
		System.out.println(" This function was called " + sPlayer);
		
	}
	
	public static void updateLocale(int iBtnId,Color c, String sPlayer ) 
	{
		bots[iBtnId].setText(sPlayer);
		bots[iBtnId].setBackground(c);
		System.out.println(" This function was called " + sPlayer);
		
	}
	
	public static int  GetBtnId(String VID)
	{
		int iMatch =0;
		  for(int i = 0; i<24; i++)
		  {
			  if((VIDlist.get(i).toString()).equals(currentVID))
			  {  iMatch = i;
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
	public void EndGame(String sMessage) throws RemoteException {
		// TODO Auto-generated method stub
		
	}
}
