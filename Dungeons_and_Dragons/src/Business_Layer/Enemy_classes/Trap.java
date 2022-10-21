package Business_Layer.Enemy_classes;
import java.util.List;

import Business_Layer.*;
import Business_Layer.Interfaces.Visitor;
import Business_Layer.Player_classes.Player;

public class Trap extends Enemy{
	
	
	private int experince;
	private int visibility;
	private int invisibility;
	private boolean visible;
	private int ticks_count;
	final private char original_tile;


	public Trap(String name,char tile, Position p1,  int healthCapacity, int attack, int defense,int xp,int visibility,int invisibility) {
		super(name,tile, p1, healthCapacity, attack, defense);
		
		this.experince=xp;
		this.visibility=visibility;
		this.invisibility=invisibility;
		this.ticks_count=0;
		visible=true;
		this.original_tile=tile;
	}
	
	
	public void Game_tick()
	{
		
		this.visible=this.ticks_count<this.visibility;
		
		if(this.ticks_count==this.visibility+this.invisibility)
		{
			this.ticks_count=0;
		}
		else
		{
			this.ticks_count=this.ticks_count+1;
		}
		
		if(!this.IsVisible())
		{
			this.SetTile('.');
		}
		else
		{
		this.SetTile(this.original_tile);
		}
		
		
	}
	
	
	public boolean IsVisible()
	{
		return this.visible;
	}
	
	
	@Override
	public void Attack(Player p) {
		this.Launch_Message(this.getName()+" is attacking you!");
		int attack_power=r.getInstance().Genratenumber(this.attack);
		int defence_rate=r.getInstance().Genratenumber(p.getDefense());
		int damage_delt=attack_power-defence_rate;
		if(damage_delt>0)
		{
			this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
			p.Absorb_Damage(damage_delt);
			this.Launch_Message(p.getName()+" health is "+p.getHealth().Get_Dynamic() +"/"+p.getHealth().Get_Health_Pool());
		}
		else
		{
			this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+0);
		}
		if(p.IsDead())
		{
			
			p.OnDeathCallBack();// updates someone that i am dead (someone is board but player does not know him)
			this.Launch_Message(p.getName() +" is dead, you lost!");
		}
		
	}
	

	public int Grant_xp()
	{
		return this.experince;
	}

	
	@Override
	public void visit(Position playerpos, Player p) {
		
		this.Game_tick();
		if(playerpos.Range(this.position)<2)
		{
			this.Attack(p);
		}
		
	}

	@Override
	public void accept(Position playerpos, Visitor v) {
		v.visit(playerpos, this);
		
	}


	@Override
	public void CastAbility(List<Monster> enemylist, List<Trap> traplist) {
		// do nothing
		
	}


	@Override
	public void visit(Position playerpos, Monster m) {
		// do nothing
		
	}


	@Override
	public void visit(Position playerpos, Trap t) {
		//do nothing
	}


	@Override
	public void visit(Position playerpos, Empty e) {
		// do nothing
	}

	@Override
	public void visit(Position playerpos, Wall w) {
		// do nothing
		
	}


	@Override
	public void visit(Position playerpos, Boss b) {
		//do nothing		
	}
	
	
	

}
