package Business_Layer;

public class NumericGenerator {
	private static NumericGenerator numbergenerator;
	
	private NumericGenerator() {} 
	
	public static NumericGenerator getInstance()
	{
		if(numbergenerator==null)
		{
			numbergenerator= new NumericGenerator();
		}
		return numbergenerator;
	}
	
	public int Genratenumber(int maxnumber)
	{
		return (int)Math.round(Math.random()*maxnumber);
		
	}
	
	

}
