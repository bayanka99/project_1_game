package Instances_of_Players_and_Enemies;
import Business_Layer.Player_classes.*;
import Business_Layer.Enemy_classes.*;

public final class Players_And_Enemies {
	
	// position is null, Game Board will set the suitable position
	
	
	//Players:
	public static final Player artanis=new Warrior("Artanis", null, 500, 50, 50	,8);
	public static final Player Jon_Snow=new Warrior("Jon Snow", null, 300, 30, 4, 3);
	public static final Player The_Hound=new Warrior("the Hound", null, 400, 20, 6, 5);
	public static final Player Melisandre=new Mage("Melisandre", null, 100, 5, 1,300, 30, 15,5,6);
	public static final Player Thoros_of_Myr=new Mage("Thoros of Myr", null, 250, 25, 4,150, 20, 20,3,4);
	public static final Player Arya_Stark=new Rogue("Arya Stark", null, 150, 40, 2,20);
	public static final Player Bronn=new Rogue("Bronn", null, 250, 35, 3,50);
	public static final Player Ygritte=new Hunter("Ygritte", null, 220, 30, 2	,6);
	
	
	//Bosses:
	public static final Boss The_Mountain=new Boss("The Mountain", 'M', null, 1000, 60, 25,6, 500,10);
	public static final Boss Queen_Cersei =new Boss("Queen Cersei", 'C', null, 100, 10, 10,1, 1000,10);
	public static final Boss Night_King=new Boss("Night’s King", 's', null, 5000, 300, 150,8, 5000,10);
	
	
	//Monsters:
	public static final Monster Lannister_Solider=new Monster("Lannister Solider", 's', null, 80, 8, 3,3, 25);
	public static final Monster Lannister_Knight=new Monster("Lannister Knight", 'k', null, 200, 14, 8,4, 50);
	public static final Monster Queen_Guard=new Monster("Queen’s Guard", 'q', null, 400, 20, 15,5, 100);
	public static final Monster Wright=new Monster("Wright", 'z', null, 600, 30, 15,3, 100);
	public static final Monster Bear_Wright =new Monster("Bear-Wright ", 'b', null, 1000, 75, 30,4, 250);
	public static final Monster Giant_Wright=new Monster("Giant-Wright", 'g', null, 1500, 100, 40,5, 500);
	public static final Monster White_Walker=new Monster("White Walker", 'w', null, 2000, 150, 50,6, 1000);
	
	
	
	//Traps:
	public static final Trap Bonus_Trap =new Trap("Bonus Trap", 'B', null, 1, 1, 1, 250, 1, 5);
	public static final Trap Queen_Trap =new Trap("Queen's Trap", 'Q', null, 250, 50, 10, 100, 3, 7);
	public static final Trap Death_Trap =new Trap("Death Trap", 'D', null, 500, 100, 20, 250, 1, 10);
}
