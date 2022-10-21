package Presntation_Layer;
import java.io.File;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import Business_Layer.GameBoard;
import Business_Layer.Player_classes.Player;
import Instances_of_Players_and_Enemies.Players_And_Enemies;
import Presntation_Layer.GameManager;


public class Gamelauncher {
	
	private GameManager gm;
	private Level_Loader lvlloader;
	private int level;
	private int maxlevel;
	public Gamelauncher(String path)
	{
		this.lvlloader=new Level_Loader(path);
		this.level=1;
		this.maxlevel=this.lvlloader.GetNumberoflevels();
		System.out.println("please select your player");
		System.out.println();
		this.Select_player();
	}
	
	public void Select_player()
	{
		
			System.out.println("enter s for artanis");
			System.out.println();
			System.out.println(Players_And_Enemies.artanis.describe());
			System.out.println();
			
			System.out.println("enter j for jon snow");
		    System.out.println();
		    System.out.println(Players_And_Enemies.Jon_Snow.describe());
		    System.out.println();
		    
		    System.out.println("enter h for the hound");
		    System.out.println();
		    System.out.println(Players_And_Enemies.The_Hound.describe());
		    System.out.println();
		    
		    System.out.println("enter m for Melisandre");
		    System.out.println();
		    System.out.println(Players_And_Enemies.Melisandre.describe());
		    System.out.println();

		    System.out.println("enter t for Thoros of Myr");
		    System.out.println();
		    System.out.println(Players_And_Enemies.Thoros_of_Myr.describe());
		    System.out.println();
		    
		    System.out.println("enter a for Arya Stark");
		    System.out.println();
		    System.out.println(Players_And_Enemies.Arya_Stark.describe());
		    System.out.println();
		    
		    System.out.println("enter b for bronn");
		    System.out.println();
		    System.out.println(Players_And_Enemies.Bronn.describe());
		    System.out.println();
		    
		    System.out.println("enter y for Ygritte");
		    System.out.println();
		    System.out.println(Players_And_Enemies.Ygritte.describe());
		    System.out.println();
		    
		    
		 
		    Scanner charscanner = new Scanner(System.in);
			String input=charscanner.nextLine();
	    	boolean validinput=false;
	    	char ch = 0;
		    
		    while(!validinput)
	    	{
	    		if(input.length()!=1)
	    		{
	    			System.out.println("please enter valid input");
	    			input=charscanner.nextLine();
	    		}
	    		else
	    		{
	    			if(input.charAt(0)!='w' & input.charAt(0)!='q' & input.charAt(0)!='e' & input.charAt(0)!='d'& input.charAt(0)!='s'& input.charAt(0)!='a')
	    			{
	    				System.out.println("please enter valid input");
	    				input=charscanner.nextLine();
	    			}
	    			else
	    			{
	    				 ch = input.charAt(0);
	    				validinput=true;
	    			}
	    		}
	    	}
		    
		    switch(ch)
		    {
		    	case 's':
		    		this.Initiate(Players_And_Enemies.artanis);
		    		break;
		    	case 'j':
		    		this.Initiate(Players_And_Enemies.Jon_Snow);
		    		break;
		    	case 'h':
		    		this.Initiate(Players_And_Enemies.The_Hound);
		    		break;
		    	case 'm':
		    		this.Initiate(Players_And_Enemies.Melisandre);
		    		break;
		    	case 't':
		    		this.Initiate(Players_And_Enemies.Thoros_of_Myr);
		    		break;
		    	case 'a':
		    		this.Initiate(Players_And_Enemies.Arya_Stark);
		    		break;
		    	case 'b':
		    		this.Initiate(Players_And_Enemies.Bronn);
		    		break;
		    	case 'y':
		    		this.Initiate(Players_And_Enemies.Ygritte);
		    		break;
		    
		    }
		    
		    
}
	
	public void Initiate(Player player)
	{	
		String levelinstring=this.lvlloader.getlevelinstring(this.level);
		char [] [] encodedlevel=this.lvlloader.encode_Level(levelinstring);
		GameBoard gb=new GameBoard(encodedlevel,player);
		System.out.println(gb);
		System.out.println();
		this.gm=new GameManager(gb);
	}
	
	
	public void proccedtonextlevel(Player player,int level)
	{
		String levelinstring=this.lvlloader.getlevelinstring(this.level);
		char [] [] encodedlevel=this.lvlloader.encode_Level(levelinstring);
		GameBoard gb=new GameBoard(encodedlevel,player);
		System.out.println(gb);
		System.out.println();
		this.gm.setnewboard(gb);
		
	}
	
	public void Run()
	{
		
		    while(!this.gm.GameCompleted())
		    {
		    
	    	System.out.println("enter your action");
	    	 Scanner charscanner = new Scanner(System.in);
	    	String input=charscanner.nextLine();
	    	boolean validinput=false;
	    	char ch = 0;
	    	while(!validinput)
	    	{
	    		if(input.length()!=1)
	    		{
	    			System.out.println("please enter valid input");
	    			input=charscanner.nextLine();
	    		}
	    		else
	    		{
	    			if(input.charAt(0)!='w' & input.charAt(0)!='q' & input.charAt(0)!='e' & input.charAt(0)!='d'& input.charAt(0)!='s'& input.charAt(0)!='a')
	    			{
	    				System.out.println("please enter valid input");
	    				input=charscanner.nextLine();
	    			}
	    			else
	    			{
	    				 ch = input.charAt(0);
	    				validinput=true;
	    			}
	    		}
	    	}
	    	 
		       this.gm.GameTick(ch);
		    	
		    }
		    if(this.gm.Transfer_Player().IsDead())
		    {
		    	System.out.println("player is dead, you have lost");
		    }
		    else
		    {
		    	System.out.println("you have finished stage "+this.level+" , procceed to next one");
		    	this.level=this.level+1;
		    	Player player=this.gm.Transfer_Player();
		    	if(this.level>this.maxlevel)
		    	{
		    		System.out.println("Congratulations you WON!!!!");
		    	}
		    	else
		    	{
		    		this.proccedtonextlevel(player,this.level);
		    	}
		    	
		    	
		    }
		    
		
	}
	
	
	
	public static void main(String[] args){

		
		String path="C:/Users/idk/Desktop/Dungeons_and_Dragons/levels_dir";
		Gamelauncher g=new Gamelauncher(path);
		g.Run();
		
	}

}
