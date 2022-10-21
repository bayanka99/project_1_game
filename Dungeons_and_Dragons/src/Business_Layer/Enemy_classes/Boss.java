package Business_Layer.Enemy_classes;

import java.util.Iterator;
import java.util.List;

import Business_Layer.Empty;
import Business_Layer.Position;
import Business_Layer.Tile;
import Business_Layer.Wall;
import Business_Layer.Interfaces.HeroicUnit;
import Business_Layer.Interfaces.Visitor;
import Business_Layer.Player_classes.Player;

public class Boss extends Enemy implements HeroicUnit {
	private int vision_range;
	private int Ability_Frequency;
	private int experince;
	private int combat_ticks;

	public Boss( String name,char tile, Position p1, int healthCapacity, int attack, int defense,int vision,int xp,int ability_freq) {
		super(name,tile, p1, healthCapacity, attack, defense);
		this.vision_range=vision;
		this.experince=xp;
		this.Ability_Frequency=ability_freq;
		this.combat_ticks=0;
		
	}


	
	public void Chase(Position playerpos,List<Tile> listoftiles)
	{
		int dx=playerpos.getx()-this.position.getx();
		int dy=playerpos.gety()-this.position.gety();
		
		Tile upward_tile=null;
		Tile downward_tile=null;
		Tile left_tile=null;
		Tile right_tile=null;
		
		Iterator<Tile> iter=listoftiles.iterator();
		Tile current;
		while(iter.hasNext())
		{
			current=iter.next();
			if(current.getPosition().gety()<this.position.gety())
			{
				upward_tile=current;
			}
			else
			{
				if(current.getPosition().gety()>this.position.gety())
				{
					downward_tile=current;
				}
				else
				{
					if(current.getPosition().getx()<this.position.getx())
					{
						left_tile=current;
					}
					else
					{
						right_tile=current;
					}
				}
			}
		}
		
		
		
		if(Math.abs(dx)>Math.abs(dy))
		{
			if(dx>0)
			{
				this.interact(this.position,right_tile);// the position argument here has no purpose
			}
			else
			{
				this.interact(this.position,left_tile);
			}
		}
		else
		{
			if(dy>0)
			{
				this.interact(this.position,downward_tile);
			}
			else
			{
				this.interact(this.position,upward_tile);

			}
		}
		
	
	}
	
	public int Grant_xp()
	{
		return this.experince;
	}


	@Override
	public void visit(Position playerpos, Empty e) {
		this.position.swap(e.getPosition());	
		super.locationswitcher.call(this, e);
		this.combat_ticks=this.combat_ticks+1;
	}


	@Override
	public void visit(Position playerpos, Wall w) {
		//do nothing
		this.combat_ticks=this.combat_ticks+1;
		
	}



	@Override
	public void visit(Position playerpos, Player p) {
		this.Attack(p);
		
	}


	
	public int Get_Vision_Range()
	{
		return this.vision_range;
	}



	@Override
	public void Attack(Player p) {
		
		int attack_power=r.getInstance().Genratenumber(this.attack);
		int defence_rate=r.getInstance().Genratenumber(p.getDefense());
		int damage_delt=attack_power-defence_rate;
		this.combat_ticks=this.combat_ticks+1;
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


	@Override
	public void CastAbility(List<Monster> enemylist, List<Trap> traplist) {
		// do notthing
		
	}
	

	@Override
	public void CastAbility(Player p) {
		this.combat_ticks=0;
		this.Launch_Message(super.getName()+ " used ability");
		int attack_power=this.attack;
		int defence_rate=r.getInstance().Genratenumber(p.getDefense());
		int damage_delt=attack_power-defence_rate;
		if(damage_delt>0)
		{
			this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
			p.Absorb_Damage(damage_delt);
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

	
	public boolean Can_Cast_Ability()
	{
		return this.combat_ticks==this.Ability_Frequency;
	}

	@Override
	 public void accept(Position playerpos,Visitor v)
	    {
	    	v.visit(playerpos,this);
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
	public void visit(Position playerpos, Boss b) {
		//do nothing
		
	}





}
