package Business_Layer.Player_classes;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import Business_Layer.*;
import Business_Layer.Enemy_classes.*;

public class Warrior extends Player {

	
	private int ability_cooldown;
	private int current_cooldown;
	private int ability_range =3;
	
	public Warrior(String name,Position position, int healthCapacity, int attack, int defense,int Cooldown) {
		super(name,position, healthCapacity, attack, defense);
		this.ability_cooldown=Cooldown;
		this.current_cooldown=0;
	}


	public int Get_Range()
	{
		return this.ability_range;
	}

	
	public void levelup()
	{
		super.levelUp();
		this.current_cooldown=0;
		super.health.warriorbonus(super.level);
		super.attack=super.attack+2*super.level;
		super.defense=super.defense+super.level;
		if(super.experience>=super.REQ_EXP)// we might advance more than 1 level
		{
			this.levelup();
		}
		
	}


	@Override
	public void OnGameTick()
	{
		if(this.current_cooldown>0)
		{
			this.current_cooldown=this.current_cooldown-1;
		}
		
	}


	@Override
	public void attack(Monster m) {
		this.Launch_Message(super.getName()+ " has Engaged  "+m.getName());
		int attack_power=r.getInstance().Genratenumber(this.attack);
		int defence_rate=r.getInstance().Genratenumber(m.getDefense());
		int damage_delt=attack_power-defence_rate;
		if(damage_delt>0)
		{
			this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
			m.Absorb_Damage(damage_delt);
			this.Launch_Message(m.getName()+" health is "+m.getHealth().Get_Dynamic() +"/"+m.getHealth().Get_Health_Pool());
		}
		else
		{
			this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+0);
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
	
	@Override
	public void CastAbility(List<Monster> monsterlist, List<Trap> trapslist) {
		
		
		if(this.current_cooldown==0)
		{
			this.Launch_Message(super.getName()+ " used ability Avenger's shield ");
			this.health.Avengers_Shield(this.defense);
			this.current_cooldown=this.ability_cooldown;
			
			List<Enemy> enemylist=this.Make_Enemy_List(trapslist, monsterlist);			
			if(enemylist!=null && enemylist.size()!=0)
			{
				Enemy enemy=enemylist.get(r.Genratenumber(enemylist.size()-1));
				int attack_power=(int)0.1*this.getHealth().Get_Health_Pool();
				int defence_rate=r.getInstance().Genratenumber(enemy.getDefense());
				int damage_delt=attack_power-defence_rate;
				if(damage_delt>0)
				{
					this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+damage_delt);
					enemy.Absorb_Damage(damage_delt);
					this.Launch_Message(enemy.getName()+" health is "+enemy.getHealth().Get_Dynamic() +"/"+enemy.getHealth().Get_Health_Pool());
				}
				else
				{
					this.Launch_Message("attack points: "+attack_power+"  defence points: "+defence_rate+"   damage dealt: "+0);
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
			this.Launch_Message("Cool down is not 0, can't use Avenger's shield");
			this.OnGameTick();
			
		}
		
		
	}
	
	private List<Enemy> Make_Monster_List_Within_Range(List<Monster> enemylist)
	{
		Iterator<Monster> monsteriter=enemylist.iterator();
		
		Monster m=null;
		List<Enemy> monsterlist=new ArrayList<Enemy>();
		
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
		List<Enemy> traplist=new ArrayList<Enemy>();
		
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
		
		List<Enemy> trapslist=this.Make_Trap_List_Within_Range(traplist);
		
		List<Enemy> monsterslist=this.Make_Monster_List_Within_Range(monsterlist);
		List<Enemy> enemylist = new ArrayList<Enemy>();
		if(trapslist!=null)
		{
			enemylist.addAll(trapslist);
		}
		if(monsterslist!=null)
		{
		enemylist.addAll(monsterslist);
		}
		return enemylist;
		
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
