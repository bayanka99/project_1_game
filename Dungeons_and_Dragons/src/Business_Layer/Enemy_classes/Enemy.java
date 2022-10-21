package Business_Layer.Enemy_classes;
import Business_Layer.Position;
import Business_Layer.Unit;
import Business_Layer.Interfaces.EnemyDeathCallback;
import Business_Layer.Interfaces.Visitor;
import Business_Layer.Player_classes.Player;

public abstract class Enemy extends Unit {
	
	private EnemyDeathCallback edcb;

	protected Enemy(String name,char tile, Position p1, int healthCapacity, int attack, int defense) {
		super(tile, p1, name, healthCapacity, attack, defense);
	
		
	}
	

	public void setDeathCallback(EnemyDeathCallback edc) {
		
		this.edcb=edc;
	}
	
	public void OnEnemyDeathCallBack()
	{
		edcb.call();
	}
	
	 public abstract void Attack(Player p);
	 
	 public abstract int Grant_xp();
	
	
	
	
	
	
	
	

}
