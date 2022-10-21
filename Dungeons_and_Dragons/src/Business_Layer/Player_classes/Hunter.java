package Business_Layer.Player_classes;
import java.util.List;

import Business_Layer.*;
import Business_Layer.Enemy_classes.*;


public class Hunter extends Player {
	
	private int arrow_counts;
	private int ability_range;
	private int tick_counts;

	
	public Hunter(String name,Position position, int healthCapacity,int attack, int defense , int range) {
		super(name,position, healthCapacity, attack, defense);
		
		this.arrow_counts=10;
		this.ability_range=range;
		this.tick_counts=0;

		
	}

	
	@Override
	public void attack(Monster m) {
		this.Launch_Message(super.getName()+ " has Engaged  "+m.getName());
		int attack_power=r.getInstance().Genratenumber(this.attack);
		int defence_rate=r.getInstance().Genratenumber(m.getDefense());
		int damage_delt=attack_power-defence_rate;
		this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
		if(damage_delt>0)
		{
			m.Absorb_Damage(damage_delt);
		}
		if(m.IsDead())
		{
			this.Launch_Message(this.getName()+" killed "+m.getName());
			this.Obtain_xp(m.Grant_xp());
			
			if(this.Check_If_Leveled_Up())
			{
				this.levelup();
			}
			m.OnEnemyDeathCallBack();// updates someone that i am dead (someone is board but monster does not know him)
			
			this.position.swap(m.getPosition());// after death and updates switch places
			this.OnGameTick();// reduce cooldown by 1 (if it is > 0)
		}
		else
		{
			this.OnGameTick();
			
		}
		
		
	}
	

	@Override
	public void attack(Trap t) {
		if(t.IsVisible())
		{
			this.Launch_Message(super.getName()+ " has Engaged Trap "+t.getName());
			int attack_power=r.getInstance().Genratenumber(this.attack);
			int defence_rate=r.getInstance().Genratenumber(t.getDefense());
			int damage_delt=attack_power-defence_rate;
			if(damage_delt>0)
			{
				this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
				t.Absorb_Damage(damage_delt);
				this.Launch_Message(t.getName()+" health is "+t.getHealth().Get_Dynamic() +"/"+t.getHealth().Get_Health_Pool());
			}
			else
			{
				this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+0);
			}
			if(t.IsDead())
			{
				this.Launch_Message(this.getName()+" disarmed and sat-off "+t.getName());
				this.Obtain_xp(t.Grant_xp());
				if(this.Check_If_Leveled_Up())
				{
					this.levelup();
				}
				t.OnEnemyDeathCallBack();// updates someone that i am dead (someone is board but monster does not know him)
				
				this.position.swap(t.getPosition());// after death and updates switch places
				this.OnGameTick();// reduce cooldown by 1 (if it is > 0)
				
			}
		}
		else
		{
			this.OnGameTick();
		}
		
	}


	@Override
	public void attack(Boss b) {
		this.Launch_Message(super.getName()+ " has Engaged  "+b.getName());
		int attack_power=r.getInstance().Genratenumber(this.attack);
		int defence_rate=r.getInstance().Genratenumber(b.getDefense());
		int damage_delt=attack_power-defence_rate;
		if(damage_delt>0)
		{
			this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
			b.Absorb_Damage(damage_delt);
			this.Launch_Message(b.getName()+" health is "+b.getHealth().Get_Dynamic() +"/"+b.getHealth().Get_Health_Pool());
		}
		else
		{
			this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+0);
		}
		if(b.IsDead())
		{
			this.Launch_Message(this.getName()+" killed "+b.getName());
			this.Obtain_xp(b.Grant_xp());
			
			if(this.Check_If_Leveled_Up())
			{
				this.levelup();
			}
			b.OnEnemyDeathCallBack();// updates someone that i am dead (someone is board but monster does not know him)
			
			this.position.swap(b.getPosition());// after death and updates switch places
			this.OnGameTick();// reduce cooldown by 1 (if it is > 0)
		}
		else
		{
			this.OnGameTick();
			
		}
		
		
	}
	
	public void levelup()
	{
		
		super.levelUp();
		this.arrow_counts=this.arrow_counts+10*this.level;
		super.attack=super.attack+2*super.level;
		super.defense=super.defense+super.level;
		if(super.experience>=super.REQ_EXP)// we might advance more than 1 level
		{
			this.levelup();
		}
		
	}

	private Enemy closest_enemy(List<Trap> traplist,List<Monster> monsterlist)
	{
		int shortests_distance=this.ability_range;
		Monster monst=null;
		for(Monster m : monsterlist)
		{
			if(m.getPosition().Range(this.position)<shortests_distance)
			{
				monst=m;
			}
			if(monst.getPosition().Range(this.position)==1)
			{
				break;
			}
		}
		
		Trap trap=null;
		for(Trap t : traplist)
		{
			if(t.IsVisible())
			{
			if(t.getPosition().Range(this.position)<shortests_distance)
			{
				trap=t;
			}
			if(trap.getPosition().Range(this.position)==1)
			{
				break;
			}
			}
		}
		
		if(trap==null)
		{
			return monst;// trap is null monst maybe is null
		}
		else
		{
			if(monst!=null)
			{
				if(r.Genratenumber(1)==1) // both not null return one of them randomly
				{
					return monst;
				}
				else
				{
					return trap;
				}
			}
			else
			{
				return trap;// monst is null and trap is not null
			}
		}
		
		
		
	}
	

	@Override
	public int Get_Range() {
		return ability_range;
	}

	
	@Override
	public void OnGameTick() {
		if(this.tick_counts==10)
		{
			this.arrow_counts=this.arrow_counts+this.level;
			this.tick_counts=0;
		}
		else
		{
			this.tick_counts=this.tick_counts+1;
		}
		
	}


	@Override
	public void CastAbility(List<Monster> monsterlist, List<Trap> traplist) {
		
		Enemy e=null;
		e=this.closest_enemy(traplist, monsterlist);
		
		if(this.arrow_counts!=0 & e!=null )
		{
			this.Launch_Message(super.getName()+ " used ability shoot ");
				int attack_power=this.attack;
				int defence_rate=r.getInstance().Genratenumber(e.getDefense());
				int damage_delt=attack_power-defence_rate;
				this.arrow_counts=this.arrow_counts-1;
				if(damage_delt>0)
				{
					this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
					e.Absorb_Damage(damage_delt);
					this.Launch_Message(e.getName()+" health is "+e.getHealth().Get_Dynamic() +"/"+e.getHealth().Get_Health_Pool());
				}
				else
				{
					this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+0);
				}
				if(e.IsDead())
				{
					this.Launch_Message(this.getName()+" destroyed "+e.getName());
					this.Obtain_xp(e.Grant_xp());// because there is no argument it is ok
					if(this.Check_If_Leveled_Up())
					{
						this.levelup();
					}
					e.OnEnemyDeathCallBack();// updates someone that i am dead (someone is board but monster does not know him)
				}
		}
		else
		{
			this.OnGameTick();
		}
			
	}
	

	public void visit(Position playerpos, Monster m) {
		this.attack(m);
	}
	
	public void visit(Position playerpos, Trap t) {
		this.attack(t);
	}


	@Override
	public void visit(Position playerpos, Boss b) {
		this.attack(b);
		
	}
	





}
