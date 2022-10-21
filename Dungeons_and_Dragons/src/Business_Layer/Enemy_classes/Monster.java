package Business_Layer.Enemy_classes;
import java.util.Iterator;
import java.util.List;

import Business_Layer.*;

import Business_Layer.Interfaces.Visitor;
import Business_Layer.Player_classes.Player;

public class Monster extends Enemy {
	private int vision_range;
	private int experince;

	public Monster( String name,char tile, Position p1, int healthCapacity, int attack, int defense,int vision,int xp) {
		super(name,tile, p1, healthCapacity, attack, defense);
		this.vision_range=vision;
		this.experince=xp;
		
	}

	public void Chase(Position playerpos,List<Tile> listoftiles)
	{
		this.Launch_Message(this.name+" is chasing you!");
		int dx=Math.abs(playerpos.getx()-this.position.getx());
		int dy=Math.abs(playerpos.gety()-this.position.gety());
		
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
				this.interact(this.position,left_tile);// the position argument here has no purpose
			}
			else
			{
				this.interact(this.position,right_tile);
			}
		}
		else
		{
			if(dy>0)
			{
				this.interact(this.position,upward_tile);
			}
			else
			{
				this.interact(this.position,downward_tile);

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
	}


	@Override
	public void visit(Position playerpos, Wall w) {
		//do nothing
		
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
		// TODO Auto-generated method stub
		
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
		// do nothing
	}




	@Override
	public void accept(Position playerpos, Visitor v) {
		v.visit(playerpos,this);
		
	}

}
