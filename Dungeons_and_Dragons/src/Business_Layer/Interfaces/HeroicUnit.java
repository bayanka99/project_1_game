package Business_Layer.Interfaces;

import java.util.List;

import Business_Layer.Enemy_classes.Monster;
import Business_Layer.Enemy_classes.Trap;
import Business_Layer.Player_classes.Player;

public interface HeroicUnit {
	
	public void CastAbility(List<Monster> enemylist, List<Trap> traplist);// for players use only
	public void CastAbility(Player p);// for bosses use only
}
