package Business_Layer.Interfaces;
import Business_Layer.*;
import Business_Layer.Enemy_classes.*;
import Business_Layer.Player_classes.Player;

public interface Visitor {
		
	public void visit(Position playerpos,Empty e);
	public void visit(Position playerpos,Wall w);
	public void visit(Position playerpos,Monster m);
	public void visit(Position playerpos,Trap t);
	public void visit(Position playerpos,Boss b);
		public void visit(Position playerpos,Player p);
}
