package Business_Layer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import Instances_of_Players_and_Enemies.Players_And_Enemies;
import Business_Layer.Enemy_classes.*;
import Business_Layer.Player_classes.*;


public class GameBoard {
	
	
    private List<Tile> board;
    private List<Monster> monsterlist;
    private List<Trap> traplist;
    private List<Boss> bosslist;
    public Queue<String> messages;// stores events of the game
    private Player player;
    int x;// left to right length
    int y;//up to down length
    
    public GameBoard(char[][] arr,Player p){
    	 
    	this.player=p;
    	this.board=new ArrayList<Tile>();
    	this.monsterlist=new ArrayList<Monster>();
    	this.traplist=new ArrayList<Trap>();
    	this.bosslist=new ArrayList<Boss>();
    	this.messages=new ArrayDeque<String>();
    
    	
    	for (int i=0;i<arr.length;i=i+1)
    	{
			for (int j=0;j<arr[i].length;j=j+1)
			{
				Position pos = new Position(i, j);
				
				if (arr[i][j] == '#')
				{
					board.add(new Wall(pos));
				}
				else if (arr[i][j] == '.')
				{
					board.add(new Empty(pos));
				}
				else if (arr[i][j] == '@')
				{
					p.setPosition(pos); // player already exists, byt postion was null, here we set the position and all callbacks
					p.setDeathCallback(() -> this.OnPlayerDeath(p));
					p.setSwitchlocation((t1,t2)->this.switchlocations(t1, t2));// it will change the board 
					p.setMessageCallback((msg)->this.InsetIntoMailBox(msg));
					board.add(p);
				}
				else 
				{
					
					this.Board_Construction_Worker(arr[i][j], pos);
					
				}
				
				
				
				
			}
    	}
          
		x=arr[0].length;
		y=arr.length;
		
    }
    
    public Player Get_Player()
    {
    	return this.player;
    }
    
    public void InsetIntoMailBox(String msg)
    {
    	this.messages.add(msg);
    }
    
    public Queue<String> Get_Mailbox()
    {
    	return this.messages;
    }
    
    public void Clear_Mailbox()
    {
    	this.messages.clear();
    }

    private void Board_Construction_Worker(char ch,Position pos)
    {
    	switch(ch)
    	{
    		case 'B':
    			Trap B=Players_And_Enemies.Bonus_Trap;
    			this.Trap_Setter(B, pos);
    			break;
    		case 'Q':
    			Trap Q=Players_And_Enemies.Queen_Trap;
    			this.Trap_Setter(Q, pos);
    			break;
    		case 'D':
    			Trap D=Players_And_Enemies.Death_Trap;
    			this.Trap_Setter(D, pos);
    			break;
    		case 's':
    			Monster s=Players_And_Enemies.Lannister_Solider;
    			this.Monster_Maker(s, pos);
    			break;
    		case 'k':
    			Monster k=Players_And_Enemies.Lannister_Knight;
    			this.Monster_Maker(k, pos);
    			break;
    		case 'q':
    			Monster q=Players_And_Enemies.Queen_Guard;
    			this.Monster_Maker(q, pos);
    			break;
    		case 'z':
    			Monster z=Players_And_Enemies.Wright;
    			this.Monster_Maker(z, pos);
    		case 'b':
    			Monster b=Players_And_Enemies.Bear_Wright;
    			this.Monster_Maker(b, pos);
    			break;
    		case 'g':
    			Monster g=Players_And_Enemies.Giant_Wright;
    			this.Monster_Maker(g, pos);
    			break;
    		case 'w':
    			Monster w=Players_And_Enemies.White_Walker;
    			this.Monster_Maker(w, pos);
    			break;
    		case 'M':
    			Boss M=Players_And_Enemies.The_Mountain;
    			this.Boss_Maker(M, pos);
    			break;
    		case 'C':
    			Boss C=Players_And_Enemies.Queen_Cersei;
    			this.Boss_Maker(C, pos);
    			break;
    		case 'K':
    			Boss K=Players_And_Enemies.Night_King;
    			this.Boss_Maker(K, pos);	
    			break;
    			
    		
    	}
    	
    	
    	
    }
    
    private void Trap_Setter(Trap t,Position pos)
    {
    	t.setPosition(pos);
    	t.setDeathCallback(() -> this.OnTrapDeath(t));
		t.setSwitchlocation((t1,t2)->this.switchlocations(t1, t2));
		t.setMessageCallback((msg)->this.InsetIntoMailBox(msg));
		traplist.add(t);
		board.add(t);
    }
    
    private void Monster_Maker(Monster m,Position pos)
    {
    	m.setPosition(pos);
    	m.setDeathCallback(() -> this.OnMonsterDeath(m));
		m.setSwitchlocation((t1,t2)->this.switchlocations(t1, t2));
		m.setMessageCallback((msg)->this.InsetIntoMailBox(msg));
		monsterlist.add(m);
		board.add(m);
    }
    
    private void Boss_Maker(Boss b,Position pos)
    {
    	b.setPosition(pos);
    	b.setDeathCallback(() -> this.OnBossDeath(b));
		b.setSwitchlocation((t1,t2)->this.switchlocations(t1, t2));
		b.setMessageCallback((msg)->this.InsetIntoMailBox(msg));
		bosslist.add(b);
		board.add(b);
    }
    
 private void OnMonsterDeath(Monster m) {
    	
    	Empty emptytile=new Empty(m.position);
		board.add(emptytile);
		this.switchlocations(m, emptytile);
		this.board.remove(m);
		this.monsterlist.remove(m);
		/*
		for(Monster monster : this.monsterlist)
		{
			if(monster.IsDead())
			{
				this.monsterlist.remove(monster);
				
			}
			
		}
		*/
		
	}
    
 private void OnTrapDeath(Trap t) {
 	
 	Empty emptytile=new Empty(t.position);
		board.add(emptytile);
		this.switchlocations(t, emptytile);
		this.board.remove(t);
		this.traplist.remove(t);
		/*
		for(Trap trap : this.traplist)
		{
			if(trap.IsDead())
			{
				this.monsterlist.remove(trap);
			}
		}
		
		*/
	}

 	private void OnBossDeath(Boss b) {
	 	
	 	Empty emptytile=new Empty(b.position);
			board.add(emptytile);
			this.switchlocations(b, emptytile);
			this.board.remove(b);
			this.bosslist.remove(b);
			/*
			for(Boss boss : this.bosslist)
			{
				if(boss.IsDead())
				{
					this.bosslist.remove(boss);
				}
			}
			*/
			
		}
 
    
    private void OnPlayerDeath(Player p) {
    	p.OnPlayerDeath();
    	
	}
 
    private void switchlocations(Tile t1,Tile t2)
    {
    	Collections.swap(this.board,this.board.indexOf(t1), this.board.indexOf(t2));
    }
 

	public List<Tile> AccessList()
    {
    	return this.board;
    }
    
    public List<Monster> AccessMonsterList()
    {
    	return this.monsterlist;
    }
    
    public List<Trap> AccessTrapList()
    {
    	return this.traplist;
    }
    
    
    public List<Boss> AccessBossList()
    {
    	return this.bosslist;
    }
    
    public Position playerposition()
    {
    	
    	return this.player.position;
    	
    
    }
    
    public Tile getTile(int tile_x,int tile_y)
    {
    	if(tile_x>x | tile_x <0 | tile_y>y | tile_y <0)
    	{
    		throw new IllegalArgumentException("invalid input");
    	}
    	Iterator<Tile> boarditer=board.iterator();
    	while(boarditer.hasNext())
    	{
    		Tile nexttile=boarditer.next();
    		if(nexttile.getPosition().getx()==tile_x & nexttile.getPosition().gety()==tile_y )
    		{
    			return nexttile;
    	    	
    		}
    	}
    	
    	return null;
    	
    	
    	
    	//return board.stream().filter((tile)->(tile.position.getx()==tile_x &tile.position.gety()==tile_y));
    }
     
    public String toString()
    {
    	Iterator<Tile> iterator =board.iterator();
    	int counter=0;
    	String boardtoprint="";
    	while(iterator.hasNext())
    	{
    		if(counter==x)
    		{
    			counter=0;
    			boardtoprint=boardtoprint+"\n";
    		}
    		counter=counter+1;
    		boardtoprint=boardtoprint+iterator.next().toString();
    		
    	}
    	
    	return boardtoprint;
    	
    }
   
	
}
