package Business_Layer.Interfaces;

import Business_Layer.Position;

public interface Visited {
	public void accept(Position playerpos,Visitor v);
}
