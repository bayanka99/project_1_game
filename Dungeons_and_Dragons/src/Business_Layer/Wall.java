package Business_Layer;
import java.util.List;

import Business_Layer.Enemy_classes.Boss;
import Business_Layer.Enemy_classes.Monster;
import Business_Layer.Enemy_classes.Trap;
import Business_Layer.Interfaces.Visitor;
import Business_Layer.Player_classes.Player;

public class Wall extends Tile {

	public Wall(Position position) {
		super('#', position);
		
	}


	@Override
	public void visit(Position playerpos, Empty e) {
		// do nothing because i am wall
		
	}


	@Override
	public void visit(Position playerpos, Wall w) {
		// do nothing because i am wall
	}

	
	@Override
	public void accept(Position playerpos, Visitor v) {
		v.visit(playerpos, this);
		
	}


	@Override
	public void visit(Position playerpos, Monster m) {
		// do nothing because i am wall
		
	}


	@Override
	public void visit(Position playerpos, Trap t) {
		// do nothing because i am wall
	}

	@Override
	public void visit(Position playerpos, Player p) {
		// do nothing because i am wall
		
	}



	@Override
	public void CastAbility(List<Monster> enemylist, List<Trap> traplist) {
		// do nothing because i am wall
		
	}


	@Override
	public void visit(Position playerpos, Boss b) {
		// TODO Auto-generated method stub
		
	}











	}
	


