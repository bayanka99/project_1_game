package Presntation_Layer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import Business_Layer.GameBoard;
import Business_Layer.NumericGenerator;
import Business_Layer.Tile;
import Business_Layer.Enemy_classes.*;
import Business_Layer.Player_classes.Player;

public class GameManager {
	private GameBoard gb;
	private boolean game_completed=false;
	
	 
	public GameManager(GameBoard gameboard)
	{
		this.gb=gameboard;
		
	}
	
	public void setnewboard(GameBoard gameboard)
	{
		this.gb=gameboard;
	}
	
	
	public boolean GameCompleted()
	{
		return this.game_completed;
	}
	
	public Player Transfer_Player()
	{
		return this.gb.Get_Player();
	}
	
	public void GameTick(char action)
	{
		

		Tile destination_tile=this.Get_Destination(action);
		Player player=this.gb.Get_Player();
		
		
		if(destination_tile!=null) // it means action is w,a,s,d
		{    	                        
			player.interact(player.getPosition(),destination_tile);
			this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
			
			
		}
		
		else
		{
			//action = e or q
			if(action =='e')
			{
				
				//send action with enemy list and traps
				player.CastAbility(gb.AccessMonsterList(), gb.AccessTrapList());
				this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
				
			}
			else
			{
				//q- do nothing
			}
		}
		
		System.out.println(gb.toString());
		System.out.println();
		
		
		
		List<Monster> monstlist = gb.AccessMonsterList();
		
		for(Monster m : monstlist)
		{
			if(player.IsDead())
			{
				break;
			}
			
			if(m.getPosition().Range(player.getPosition())<=m.Get_Vision_Range())
			{
				if(m.getPosition().Range(player.getPosition())==1)
				{
					m.interact(player.getPosition(), player);
					this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
				}
				else
				{
					m.Chase(player.getPosition(),this.Make_Neg_List(m));
					this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
				}
			}
			else
			{
			char enemyaction=this.Get_Random_Action();
			if(enemyaction!='q')
			{
			m.interact(player.getPosition(), this.Get_Destination(enemyaction));// let monster do random action
			this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
			}
			}
			
			
			
		}
	
		
		List<Trap> traplist=this.gb.AccessTrapList();
		
		for(Trap t : traplist)
		{
			if(player.IsDead())
			{
				break;
			}
			t.interact(player.getPosition(), player);
			this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
		}
		
		
	List<Boss> bosslist=this.gb.AccessBossList();
		
		for(Boss b : bosslist)
		{
			if(player.IsDead())
			{
				break;
			}
			
			if(b.getPosition().Range(player.getPosition())<=b.Get_Vision_Range())
			{
				
				if(b.Can_Cast_Ability())
				{
					b.CastAbility(player);
				}
				else
				{
				if(b.getPosition().Range(player.getPosition())==1)
				{
					b.interact(player.getPosition(), player);
					this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
				}
				else
				{
					b.Chase(player.getPosition(),this.Make_Neg_List(b));
					this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
				}
				}
			}
			else
			{
			
				char enemyaction=this.Get_Random_Action();
				if(enemyaction!='q')
				{
				b.interact(player.getPosition(), this.Get_Destination(enemyaction));// let monster do random action
				this.Check_GB_Mailbox();// prints on screen if there is any messages about game events from game board
				}
			}
			
		}
		
		
			
		
			//if there are no more monsters and traps and player still alive or player is dead
			if(gb.AccessMonsterList().size()==0 & gb.AccessTrapList().size()==0 & !gb.Get_Player().IsDead() | gb.Get_Player().IsDead())
			{
				this.game_completed=true;
			}
			
			System.out.println(gb.toString());
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println();
			
		
		}
		
		
	private void Check_GB_Mailbox()
	{
		Queue<String> mailbox=this.gb.Get_Mailbox();
		while(!mailbox.isEmpty())
		{
			System.out.println(mailbox.remove());
		}
		this.gb.Clear_Mailbox();
		
		
	}
	
	public boolean Level_Completed()
	{
		return this.gb.AccessMonsterList().size()==0 && this.gb.AccessTrapList().size()==0;
	}
	
	private Tile Get_Destination(char action)
	{
		Tile nearme=null;
		
		
		if(action=='w')
		{
			nearme=gb.getTile(gb.playerposition().getx()-1, gb.playerposition().gety());
		}
		
		else if(action=='s')
		{
			nearme=gb.getTile(gb.playerposition().getx()+1, gb.playerposition().gety());
		}
		
		else if(action=='d')
		{
			nearme=gb.getTile(gb.playerposition().getx(), gb.playerposition().gety()+1);
		}
		
		else if(action=='a' )
		{
			nearme=gb.getTile(gb.playerposition().getx(), gb.playerposition().gety()-1);
		}
		
		return nearme;
	}
	
	private char Get_Random_Action()
	{
		int result=NumericGenerator.getInstance().Genratenumber(4);
		
		if(result==0)
		{
			return 'w';
		}
		
		else if(result==1)
		{
			return 'a';
		}
		else if(result==2)
		{
			return 's';
		}
		else if(result==3)
		{
			return 'd';
		}
		else
		{
			return 'q';
		}
		
	} 
	
	private List<Tile> Make_Neg_List(Tile currenttile)
	{
		
		
		return gb.AccessList().stream().filter((tile)->(tile.getPosition().Range(currenttile.getPosition())==1)).collect(Collectors.toList());
		//it was 2
	}
	
	

}
 