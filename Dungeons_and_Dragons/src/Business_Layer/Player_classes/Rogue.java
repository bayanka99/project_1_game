package Business_Layer.Player_classes;
import java.util.Iterator;
import java.util.List;
import Business_Layer.*;
import Business_Layer.Enemy_classes.*;

public class Rogue extends Player {

		private int energy;
		private int cost;
		private int ability_range=2;

		public Rogue(String name,Position position, int healthCapacity, int attack, int defense,int cost) {
			super(name,position, healthCapacity, attack, defense);
			this.energy=100;
			this.cost=cost;
		}

		
	public void levelup()
	{
		super.levelUp();
		this.energy=100;
		super.attack=super.attack+3*super.level;
		if(super.experience>=super.REQ_EXP)// we might advance more than 1 level
		{
			this.levelup();
		}
		
	}

	
	public int Get_Range()
	{
		return this.ability_range;
	}


	@Override
	public void CastAbility(List<Monster> monsterlist, List<Trap> traplist) {
		
		
		
		if(this.energy<=this.cost)
		{
			this.Launch_Message(super.getName()+ " used ability fan of knives ");
			this.energy=this.energy-this.cost;
			
			List<Enemy> enemylist=this.Make_Enemy_List(traplist, monsterlist);			
			if(enemylist!=null && enemylist.size()!=0)
			{
				Enemy enemy=enemylist.get(r.Genratenumber(enemylist.size()-1));
				int attack_power=(int)0.1*this.getHealth().Get_Health_Pool();
				int defence_rate=r.getInstance().Genratenumber(enemy.getDefense());
				int damage_delt=attack_power-defence_rate;
				this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
				if(damage_delt>0)
				{
					enemy.Absorb_Damage(damage_delt);
					
				}
				if(enemy.IsDead())
				{
					this.Launch_Message(this.getName()+" destroyed "+enemy.getName());
					this.Obtain_xp(enemy.Grant_xp());// because there is no argument it is ok
					if(this.Check_If_Leveled_Up())
					{
						this.levelup();
					}
					enemy.OnEnemyDeathCallBack();// updates someone that i am dead (someone is board but monster does not know him)
				}
				
			}
			this.OnGameTick();
		}
		else
		{
			this.OnGameTick();
			this.Launch_Message("Not enough Energy, can't use Fan of Knives");
		}
		
		
		
	}
	
	private List<Enemy> Make_Monster_List_Within_Range(List<Monster> enemylist)
	{
		Iterator<Monster> monsteriter=enemylist.iterator();
		
		Monster m=null;
		List<Enemy> monsterlist=null;
		
		while(monsteriter.hasNext())
		{
			m=monsteriter.next();
			if(m.getPosition().Range(this.position)<this.ability_range)
			{
				monsterlist.add(m);
			}
		}
		return monsterlist;
		
	}
	private List<Enemy> Make_Trap_List_Within_Range(List<Trap> enemylist)
	{
		Iterator<Trap> trapiter=enemylist.iterator();
		
		Trap t=null;
		List<Enemy> traplist=null;
		
		while(trapiter.hasNext())
		{
			t=trapiter.next();
			if(t.IsVisible())
			{
			if(t.getPosition().Range(this.position)<this.ability_range)
			{
				traplist.add(t);
			}
			}
		}
		return traplist;
		
	}
	
	private List<Enemy> Make_Enemy_List(List<Trap> traplist,List<Monster> monsterlist)
	{
		
		List<Enemy> enemylist=this.Make_Monster_List_Within_Range(monsterlist);
		enemylist.addAll(this.Make_Trap_List_Within_Range(traplist));
		return enemylist;
		
	}

	@Override
	public void attack(Monster m) {
		
		this.Launch_Message(super.getName()+ " has Engaged "+m.getName());
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
			
			this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);if(damage_delt>0)
			{
				t.Absorb_Damage(damage_delt);
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
		this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
		if(damage_delt>0)
		{
			b.Absorb_Damage(damage_delt);
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
	@Override
	public void OnGameTick() {
		this.energy=Math.min(this.energy+10, 100);
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
