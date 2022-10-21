package Business_Layer;

public class Position {
	
	private int x;
	private int y;
	
	public Position(int X,int Y)
	{
		x=X;
		y=Y;
	}
	
	public void swap(Position p1)
	{
		int myoldx=this.x;
		int myoldy=this.y;
		this.x=p1.x;
		this.y=p1.y;
		p1.x=myoldx;
		p1.y=myoldy;
	}
	
	
	public double Range(Position p1)
	{
		return Math.sqrt(Math.pow(this.x-p1.x, 2)+Math.pow(this.y-p1.y, 2));
	}
	
	public int getx()
	{
		return this.x;
	}
	public int gety()
	{
		return this.y;
	}

	public int compareTo(Position position) {
		if(this.x<position.x)
		{
			return -1;
		}
		else if (this.x>position.x)
		{
			
				return 1;
			
		}
		else //this.x = position.x
		{
			if (this.y<position.y)
			{
				return -1;
			}
			else 
			{
				if (this.y>position.y)
				{
				return 1;
				}
				else// they are same position
				{
					return 0;
				}
			}
		}
		
	
		
	}
	
	
	
	
	}
