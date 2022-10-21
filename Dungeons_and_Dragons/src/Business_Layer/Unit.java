package Business_Layer;

import Business_Layer.Interfaces.MessageCallback;
import Business_Layer.Interfaces.Switchlocations;
import Business_Layer.Player_classes.Player;

public abstract class Unit extends Tile {
    // A singleton object for generating numbers - NumericGenerator is an interface, implemented by a RandomGenerator and a DeterministicGenerator
	protected static final NumericGenerator r = NumericGenerator.getInstance();
    protected MessageCallback messageCallback; 
    protected Switchlocations locationswitcher;
    protected String name;
    protected Resource health;
    protected int attack;
    protected int defense;

    protected Unit(char tile,Position p1, String name, int healthCapacity, int attack, int defense) {
    	
        super(tile,p1);
        this.name = name;
        this.health = new Resource(healthCapacity, healthCapacity);
        this.attack = attack;
        this.defense = defense;
        
    }
    
    
    public void setSwitchlocation(Switchlocations switcher) {
		
		this.locationswitcher=switcher;
	}
	
	public void OnSwitch(Tile t1,Tile t2)
	{
		locationswitcher.call(t1,t2);
	}
	
	public void setMessageCallback(MessageCallback msb)
	{
		this.messageCallback=msb;
	}
	
	public void Launch_Message(String msg)
	{
		this.messageCallback.send(msg);
	}
	
    public String getName() {
        return name;
    }

    public Resource getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    
    
   
    
    public abstract void visit(Position playerpos,Player p);
  
    
    public  void Absorb_Damage(int damage_amount)
    {
    	health.Reduce(damage_amount);
    }
    
    public boolean IsDead()
    {
    	return this.health.Depleted();
    }
    
    
 
    
  

    public String describe() {
        return String.format("%s\t\tHealth: %s\t\tAttack: %d\t\tDefense: %d", getName(), getHealth().Get_Dynamic(), getAttack(), getDefense());
    }
}
