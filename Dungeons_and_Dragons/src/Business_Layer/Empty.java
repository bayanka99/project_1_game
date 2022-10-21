package Business_Layer;
import java.util.List;

import Business_Layer.Enemy_classes.Boss;
import Business_Layer.Enemy_classes.Monster;
import Business_Layer.Enemy_classes.Trap;
import Business_Layer.Interfaces.Visitor;
import Business_Layer.Player_classes.Player;

public class Empty extends Tile {

	public  Empty(Position position) {
		super('.',position);
		
	}

	@Override
	public void visit(Position playerpos, Empty e) {
		// do nothing i am empty
		
	}
	@Override
	public void visit(Position playerpos, Wall w) {
		// do nothing i am empty
		
	}
	
	@Override
	public void accept(Position playerpos, Visitor v) {
		v.visit(playerpos, this);
		
	}


	@Override
	public void CastAbility(List<Monster> enemylist, List<Trap> traplist) {
		// do nothing i am empty
		
	}

	@Override
	public void visit(Position playerpos, Monster m) {
		// do nothing i am empty
		
	}

	@Override
	public void visit(Position playerpos, Trap t) {
		// do nothing i am empty
		
	}

	@Override
	public void visit(Position playerpos, Player p) {
		// do nothing i am empty
		
	}

	@Override
	public void visit(Position playerpos, Boss b) {
		// do nothing i am empty
		
	}
	

	

}
