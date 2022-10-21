package Business_Layer;
import java.util.List;

import Business_Layer.Enemy_classes.Monster;
import Business_Layer.Enemy_classes.Trap;
import Business_Layer.Interfaces.Visited;
import Business_Layer.Interfaces.Visitor;

public abstract class Tile implements Visitor,Visited {
    protected char tile;// . # @
    protected Position position;

    protected Tile(char tile,Position position1){
        this.tile = tile;
        this.position = position1;
        
    }

    public abstract void CastAbility(List<Monster> enemylist, List<Trap> traplist);// for player use only , in case of special ability

    
    public void OnPlayerDeath()
    {
    	this.tile='X';
    }
    
    
    public void interact(Position playerpos,Tile t)// t is the destination
    {
    	t.accept(playerpos,this);
    }
    
    
    public char getTile() {
        return tile;
    }
    
    public void SetTile(char c) {
    	this.tile=c;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    @Override
    public String toString() {
        return String.valueOf(tile);
    }

	
	
}
