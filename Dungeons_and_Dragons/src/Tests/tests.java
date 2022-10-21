package Tests;



import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Business_Layer.Empty;
import Business_Layer.GameBoard;
import Business_Layer.Position;
import Business_Layer.Tile;
import Business_Layer.Wall;
import Business_Layer.Enemy_classes.Monster;
import Business_Layer.Enemy_classes.Trap;
import Business_Layer.Interfaces.MessageCallback;
import Business_Layer.Interfaces.*;
import Business_Layer.Player_classes.Player;
import Business_Layer.Player_classes.Warrior;

public class tests {
	
	Warrior player_test;
	Monster monster_test;
	Trap trap_test_visible;
	Trap trap_test_invisible;
	String ok;
	MessageCallback mcb;
	Switchlocations switchlocation;
	PlayerDeathCallback player;
	
	
	
	@BeforeEach
	void setUp() {
		
		 player_test=new Warrior("test", null, 1000, 100, 0, 1);
		 player_test.setMessageCallback((msg)->ok=msg.toString());
		 monster_test=new Monster("monster_test", '0', null, 100, 50, 0, 0, 100000);
		 trap_test_visible=new Trap("trap_test_visible", '0', null, 10000, 10, 10, 1, 10000, 0);//visible
		 trap_test_invisible=new Trap("trap_test_invisible", '0', null, 10, 10, 10, 1, 0, 10000);//invisible
		
		 
		
	}
	
	
	@Test
	void testAbsorbDamage() {
		
		player_test.Absorb_Damage(200);
		Assertions.assertEquals(new Integer(800),player_test.getHealth().Get_Dynamic());
		monster_test.Absorb_Damage(50);
		Assertions.assertEquals(new Integer(50),monster_test.getHealth().Get_Dynamic());
		monster_test.Absorb_Damage(100);
		Assertions.assertEquals(new Integer(0),monster_test.getHealth().Get_Dynamic());// check if health does not get bellow 0 (no negative health)
		
		}
	
	
	@Test
	void testLevelUp() {
		
		player_test.Obtain_xp(50);
		player_test.levelup();
		Assertions.assertEquals(new Integer(2),player_test.getlevel());
		player_test.Obtain_xp(350);
		player_test.levelup();
		Assertions.assertEquals(new Integer(4),player_test.getlevel());
		
		}
		
	@Test
	void testAttackInvisibletrap() {
		
		trap_test_invisible.Game_tick();
		player_test.attack(trap_test_invisible);
		Assertions.assertEquals(new Integer(10),trap_test_invisible.getHealth().Get_Dynamic());
		
		}
	
	@Test
	void testAttackvisibletrap() {
		
		trap_test_visible.Game_tick();
		player_test.attack(trap_test_visible);
		Assertions.assertEquals(trap_test_visible.getHealth().Get_Dynamic()<trap_test_visible.getHealth().Get_Health_Pool(), trap_test_visible.getHealth().Get_Dynamic()<trap_test_visible.getHealth().Get_Health_Pool());
		}
	
	
	
	
	
}