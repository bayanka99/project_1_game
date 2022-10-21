package Business_Layer.Player_classes;

import java.util.List;

import Business_Layer.*;
import Business_Layer.Enemy_classes.*;
import Business_Layer.Interfaces.*;


public abstract class Player extends Unit implements HeroicUnit {
    public static final char playerTile = '@';
    protected int level;
    protected int experience;
    protected  int REQ_EXP = 50*level;
    protected PlayerDeathCallback deathCallback;

    public Player(String name,Position position, int healthCapacity, int attack, int defense) {
        super(playerTile,position, name, healthCapacity, attack, defense);
        this.level = 1;
        this.experience = 0;
    }


    
	public void setDeathCallback(PlayerDeathCallback pdcb) {
		
		this.deathCallback=pdcb;
	}
	
	public void OnDeathCallBack()
	{
		this.deathCallback.call();
	}
    
	
	public abstract void CastAbility(List<Monster> enemylist, List<Trap> traplist);// for player use only , in case of special ability
	
	public  void CastAbility(Player p)
	{
		//do nothing
	}
	
	
	@Override
	public void visit(Position playerpos, Player p) {
		//no need to implement
		return;
	}
	
    @Override
	public void visit(Position playerpos, Empty e) {
		this.position.swap(e.getPosition());	
		super.locationswitcher.call(this, e);
		this.OnGameTick();
	}
    
    @Override
  	public void visit(Position playerpos, Wall w) {
    	this.OnGameTick();
  		//do nothing
  	}
    
    @Override
	public void accept(Position playerpos, Visitor v) {
		v.visit(playerpos, this);
		
	}
	
   
    public abstract void attack(Monster m);
    public abstract void attack(Trap t);
    public abstract void attack(Boss b);
    	
    public abstract int Get_Range();
  
   
	// Player level up
    public void levelUp(){
    	
    	this.Launch_Message(super.getName()+ " has Advanced a Level, new states: \n "+super.describe());
		this.experience=this.experience-(50*this.level);
		this.level=this.level+1;
		health.updatehealth(this.level);// check not sure maybe super.health=health... and make update health void
		this.attack=this.attack+4*this.level;
		this.defense=this.defense+this.level;
		REQ_EXP=50*level;
		
		
    }

    
    
    public void Obtain_xp(int xp)
    {
    	this.Launch_Message(this.getName()+" gained "+ xp +" experinces");
    	this.experience=this.experience+xp;
    }
    
    public boolean Check_If_Leveled_Up()
    {
    	return this.experience>=this.REQ_EXP;
    }
    
  
    public abstract void OnGameTick();
  
    public int getlevel()
   {
	   return this.level;
   }

    public String describe() {
        return String.format("%s\t\tLevel: %d\t\tExperience: %d/%d", super.describe(), this.level, this.experience, this.REQ_EXP);
    }
    
}
